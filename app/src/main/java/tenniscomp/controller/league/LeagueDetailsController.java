package tenniscomp.controller.league;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import tenniscomp.data.League;
import tenniscomp.data.Player;
import tenniscomp.model.Model;
import tenniscomp.utils.CommonUtils;
import tenniscomp.utils.TableUtils;
import tenniscomp.view.league.AddTieWindow;
import tenniscomp.view.league.LeagueDetailsWindow;
import tenniscomp.view.league.RegisterTeamWindow;
import tenniscomp.view.league.TeamDetailsWindow;
import tenniscomp.view.league.TieDetailsWindow;

public class LeagueDetailsController {

    private final LeagueDetailsWindow view;
    private final Model model;
    private final League league;

    public LeagueDetailsController(final LeagueDetailsWindow view, final Model model, final League league) {
        this.view = view;
        this.model = model;
        this.league = league;

        loadLeagueData();
        loadTeams();
        loadTies();
        setupListeners();
    }

    private void loadLeagueData() {
        view.setLeagueName(league.getSeries().name(), league.getCategory().getLabel(),
                league.getGender().getType(), league.getYear());
        // TODO: decommentare se servono
        /* view.setSeries(league.getSeries());
        view.setCategory(league.getCategory());
        view.setGender(league.getGender());
        view.setYear(league.getYear()); */
    }

    private void loadTeams() {
        final var table = view.getTeamsTable();
        final var tableModel = (DefaultTableModel) table.getModel();
        TableUtils.clearTable(tableModel);
        
        final var teams = model.getLeagueTeams(league.getLeagueId());
        for (final var team : teams) {
            final var club = model.getClubById(team.getClubId());
            if (club != null) {
                final int playerCount = model.getTeamPlayerCount(team.getTeamId());
                final Object[] rowData = {
                    team.getTeamId(),
                    club.getName(),
                    club.getCity(),
                    playerCount
                };
                tableModel.addRow(rowData);
            }
        }
        
        TableUtils.adjustColumnWidths(table);
    }

    private void loadTies() {
        final var table = view.getTiesTable();
        final var tableModel = (DefaultTableModel) table.getModel();
        TableUtils.clearTable(tableModel);
        
        final var ties = model.getLeagueTies(league.getLeagueId());
        for (final var tie : ties) {
            final Object[] rowData = {
                tie.getTieId(),
                CommonUtils.convertDateFormat(tie.getDate()),
                model.getClubByTeamId(tie.getHomeTeamId()).getName(),
                model.getClubByTeamId(tie.getAwayTeamId()).getName(),
                tie.getResult()
            };
            tableModel.addRow(rowData);
        }
        
        TableUtils.adjustColumnWidths(table);
    }

    private void setupListeners() {
        view.setRegisterTeamListener(e -> openRegisterTeamWindow());
        view.setAddTieListener(e -> openAddTieWindow());
        view.setCloseButtonListener(e -> view.dispose());
        setupTeamDetailsListener();
        setupTieDetailsListener();
    }

    private void openRegisterTeamWindow() {
        final var eligiblePlayers = getEligiblePlayers();
        
        if (eligiblePlayers.isEmpty()) {
            showError("Nessun giocatore idoneo trovato per questo campionato.");
            return;
        }

        final var registerTeamWindow = new RegisterTeamWindow(view);
        final var registerTeamController = new RegisterTeamController(registerTeamWindow, eligiblePlayers,
                this.model, this.league.getLeagueId());
        // If the controller reports a new team was added, reload the list
        registerTeamController.setOnTeamRegisteredCallback(this::loadTeams);
        registerTeamWindow.display();
    }

    private void openAddTieWindow() {
        final var registeredTeams = model.getLeagueTeams(this.league.getLeagueId());
        
        if (registeredTeams.size() < 2) {
            showError("Sono necessarie almeno 2 squadre iscritte per inserire un incontro.");
            return;
        }

        final var addTieWindow = new AddTieWindow(view);
        addTieWindow.setTeams(registeredTeams, team -> {
            final var club = model.getClubById(team.getClubId());
            return club != null ? club.getName() : "";
        });
        
        addTieWindow.setSaveButtonListener(e -> {
            final var tieDate = addTieWindow.getTieDate();
            final var homeTeam = addTieWindow.getHomeTeam();
            final var awayTeam = addTieWindow.getAwayTeam();

            if (tieDate == null || tieDate.isEmpty() || homeTeam == null || awayTeam == null) {
                showError("Tutti i campi sono obbligatori.");
                return;
            }
            
            if (homeTeam.getTeamId() == awayTeam.getTeamId()) {
                showError("Le squadre selezionate devono essere diverse.");
                return;
            }

            if (model.addLeagueTie(tieDate, league.getLeagueId(),
                    homeTeam.getTeamId(), awayTeam.getTeamId())) {
                loadTies();
                addTieWindow.dispose();
            } else {
                showError("Errore durante l'aggiunta dell'incontro.");
            }
        
        });
        
        addTieWindow.setCancelButtonListener(e -> addTieWindow.dispose());
        addTieWindow.setVisible(true);
    }

    private void setupTeamDetailsListener() {
        final var teamsTable = view.getTeamsTable();
        teamsTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(final MouseEvent e) {
                if (e.getClickCount() == 2) {
                    final int selectedRow = teamsTable.getSelectedRow();
                    if (selectedRow >= 0) {
                        final int teamId = (int) teamsTable.getValueAt(selectedRow, 0);
                        final String teamName = (String) teamsTable.getValueAt(selectedRow, 1);
                        openTeamDetailsWindow(teamId, teamName);
                    }
                }
            }
        });
    }

    private void setupTieDetailsListener() {
        final var tiesTable = view.getTiesTable();
        tiesTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(final MouseEvent e) {
                if (e.getClickCount() == 2) {
                    final int selectedRow = tiesTable.getSelectedRow();
                    if (selectedRow >= 0) {
                        final int tieId = (int) tiesTable.getValueAt(selectedRow, 0);
                        openTieDetailsWindow(tieId);
                    }
                }
            }
        });
    }

    private void openTeamDetailsWindow(final int teamId, final String teamName) {
        final var teamDetailsWindow = new TeamDetailsWindow(view);
        final var teamDetailsController = new TeamDetailsController(teamDetailsWindow,
                model, teamId);
        
        // Set up callback to refresh teams table when a player is removed
        teamDetailsController.setOnPlayerRemovedCallback(() -> {
            loadTeams();
        });
        teamDetailsWindow.display();
    }

    private void openTieDetailsWindow(final int tieId) {
        final var tie = model.getLeagueTieById(tieId);
        if (tie == null) {
            showError("Incontro non trovato.");
            return;
        }

        final var tieDetailsWindow = new TieDetailsWindow(view);
        final var tieDetailsController = new TieDetailsController(tieDetailsWindow, model, tie);
        
        // Set up callback to refresh ties table when tie is updated
        tieDetailsController.setOnTieUpdatedCallback(() -> {
            loadTies();
        });
        
        tieDetailsWindow.display();
    }

    private List<Player> getEligiblePlayers() {
        final var eligiblePlayers = model.getPlayersByCategoryAndGender(this.league.getCategory(),
                this.league.getGender());

        return eligiblePlayers.stream()
                .filter(player -> player.getCardId() != null)
                .filter(player -> {
                    final var card = model.getCardById(player.getCardId());
                    return card != null && !CommonUtils.isCardExpired(card);
                })
                .filter(player -> player.getClubId() != null)
                .filter(player -> !model.isPlayerInLeague(player.getPlayerId(), this.league.getLeagueId()))
                .toList();
    }

    private void showError(final String message) {
        JOptionPane.showMessageDialog(
            view,
            message,
            "Errore",
            JOptionPane.ERROR_MESSAGE
        );
    }
}
