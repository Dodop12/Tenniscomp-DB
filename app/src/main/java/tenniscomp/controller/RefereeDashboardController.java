package tenniscomp.controller;

import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.MouseInputAdapter;

import tenniscomp.data.Club;
import tenniscomp.data.Referee;
import tenniscomp.model.Model;
import tenniscomp.utils.CommonUtils;
import tenniscomp.utils.Gender;
import tenniscomp.utils.ImmutableTableModel;
import tenniscomp.utils.LeagueCategory;
import tenniscomp.utils.LeagueSeries;
import tenniscomp.utils.MatchType;
import tenniscomp.utils.Ranking;
import tenniscomp.utils.TableUtils;
import tenniscomp.view.AddLeagueWindow;
import tenniscomp.view.AddTournamentWindow;
import tenniscomp.view.ClubManager;
import tenniscomp.view.LeagueDetailsWindow;
import tenniscomp.view.PlayerManager;
import tenniscomp.view.PrizesWindow;
import tenniscomp.view.RefereeDashboard;
import tenniscomp.view.TournamentDetailsWindow;
import tenniscomp.view.UmpireManager;

public class RefereeDashboardController {
    
    private final RefereeDashboard view;
    private final Model model;
    private final Referee referee;
    
    public RefereeDashboardController(final RefereeDashboard view, final Model model, final Referee referee) {
        this.view = view;
        this.model = model;
        this.referee = referee;
        
        loadRefereeData();
        loadTournaments();
        loadLeagues();
        setupListeners();
    }
    
    private void loadRefereeData() {
        view.setRefereeName(referee.getName() + " " + referee.getSurname());
        view.setRefereeTitle(referee.getTitle());

        TableUtils.adjustColumnWidths(view.getTournamentsTable());
        TableUtils.adjustColumnWidths(view.getLeaguesTable());
    }

    private void loadTournaments() {
        final var table = view.getTournamentsTable();
        final var tableModel = (ImmutableTableModel) table.getModel();
        TableUtils.clearTable(tableModel);
        
        final var tournaments = model.getTournamentsByReferee(this.referee.getRefereeId());
         for (final var tournament : tournaments) {
            final Object[] rowData = {
                tournament.getTournamentId(),
                tournament.getName(),
                CommonUtils.convertDateFormat(tournament.getStartDate()),
                CommonUtils.convertDateFormat(tournament.getEndDate()),
                CommonUtils.convertDateFormat(tournament.getRegistrationDeadline()),
                tournament.getType(),
                tournament.getGender().getCode(),
                tournament.getRankingLimit()
            };
            tableModel.addRow(rowData);
        }

        TableUtils.adjustColumnWidths(table);
    }

    private void loadLeagues() {
        final var table = view.getLeaguesTable();
        final var tableModel = (ImmutableTableModel) table.getModel();
        TableUtils.clearTable(tableModel);
        
        final var leagues = model.getLeaguesByReferee(this.referee.getRefereeId());
         for (final var league : leagues) {
            final Object[] rowData = {
                league.getLeagueId(),
                league.getSeries(),
                league.getCategory(),
                league.getGender().getCode(),
                league.getYear()
            };
            tableModel.addRow(rowData);
        }

        TableUtils.adjustColumnWidths(table);
    }

    private void setupListeners() {
        view.setManagePlayersListener(e -> openPlayerManager());
        view.setManageClubsListener(e -> openClubManager());
        view.setManageUmpiresListener(e -> openUmpireManager());
        view.setAddTournamentListener(e -> openAddTournamentWindow());
        view.setAddTeamCompetitionListener(e -> openAddLeagueWindow());

        // Double-click listener for tournament details
        view.getTournamentsTable().addMouseListener(new MouseInputAdapter() {
            @Override
            public void mouseClicked(final MouseEvent e) {
                if (e.getClickCount() == 2) {
                    openTournamentDetails();
                }
            }
        });
        
        // Double-click listener for league details
        view.getLeaguesTable().addMouseListener(new MouseInputAdapter() {
            @Override
            public void mouseClicked(final MouseEvent e) {
                if (e.getClickCount() == 2) {
                    openLeagueDetails();
                }
            }
        });
    }

    private void openTournamentDetails() {
        final JTable table = view.getTournamentsTable();
        final int selectedRow = table.getSelectedRow();
        
        if (selectedRow >= 0) {
            final int tournamentId = (int) table.getValueAt(selectedRow, 0);
            final var tournament = model.getTournamentById(tournamentId);
            
            if (tournament != null) {
                final var detailsWindow = new TournamentDetailsWindow(view);
                new TournamentDetailsController(detailsWindow, model, tournament);
                detailsWindow.display();
            }
        }
    }

    private void openLeagueDetails() {
        final JTable table = view.getLeaguesTable();
        final int selectedRow = table.getSelectedRow();
        
        if (selectedRow >= 0) {
            final int leagueId = (int) table.getValueAt(selectedRow, 0);
            final var league = model.getLeagueById(leagueId);
            
            if (league != null) {
                final var detailsWindow = new LeagueDetailsWindow(view);
                new LeagueDetailsController(detailsWindow, model, league);
                detailsWindow.display();
            }
        }
    }

    private void openPlayerManager() {
        final var playerManager = new PlayerManager();
        new PlayerManagerController(playerManager, model);
        playerManager.setCloseButtonListener(e -> playerManager.dispose());
        playerManager.display();
    }
    
    private void openClubManager() {
        final var clubManager = new ClubManager();
        final var clubManagerController = new ClubManagerController(clubManager, model);
        clubManager.setCloseButtonListener(e -> clubManager.dispose());
        clubManager.setAddClubButtonListener(e -> 
            clubManagerController.openAddClubWindow()
        ); 
        clubManager.display();
    }

    private void openUmpireManager() {
        final var umpireManager = new UmpireManager();
        final var umpireManagerController = new UmpireManagerController(umpireManager, model);
        umpireManager.setCloseButtonListener(e -> umpireManager.dispose());
        umpireManager.setAddUmpireButtonListener(e -> 
            umpireManagerController.openAddUmpireWindow()
        );
        umpireManager.display();
    }

    private void openAddTournamentWindow() {
        final var addTournamentWindow = new AddTournamentWindow(view, model.getAllClubs());

        addTournamentWindow.setConfigurePrizesButtonListener(e -> configurePrizes(addTournamentWindow));
        addTournamentWindow.setSaveButtonListener(e -> handleSaveTournament(addTournamentWindow));
        addTournamentWindow.setCancelButtonListener(e -> addTournamentWindow.dispose());
        addTournamentWindow.setVisible(true);
    }

    private void openAddLeagueWindow() {
        final var addLeagueWindow = new AddLeagueWindow(view);
        
        addLeagueWindow.setSaveButtonListener(e -> {
            final String series = addLeagueWindow.getSeries();
            final String category = addLeagueWindow.getCategory();
            final String gender = addLeagueWindow.getGender();
            final int year = addLeagueWindow.getYear();
            
            if (model.addLeague(LeagueSeries.valueOf(series), LeagueCategory.fromLabel(category),
                    Gender.fromCode(gender), year, referee.getRefereeId())) {
                loadLeagues();
                addLeagueWindow.dispose();
            }
        });
        
        addLeagueWindow.setCancelButtonListener(e -> addLeagueWindow.dispose());
        addLeagueWindow.setVisible(true);
    }

    private void configurePrizes(final AddTournamentWindow addTournamentWindow) {
        try {
            final double prizeMoney = Double.parseDouble(addTournamentWindow.getPrizeMoney().trim());
            if (prizeMoney < 0) {
                    showError("Il montepremi deve essere un numero positivo.");
                    return;
                }

            final var prizeWindow = new PrizesWindow(addTournamentWindow, prizeMoney);
            final var prizeController = new PrizesController(prizeWindow, prizeMoney);
            
            prizeWindow.setSaveButtonListener(saveEvent -> {
                if (prizeController.showSaveConfirmation()) {
                    final List<Double> prizeDistribution = prizeController.getPrizeDistribution();
                    addTournamentWindow.setPrizeDistribution(prizeDistribution);
                    prizeWindow.dispose();
                }
            });

            prizeWindow.setCancelButtonListener(cancelEvent -> prizeWindow.dispose());
            prizeWindow.setVisible(true);              
        } catch (final NumberFormatException ex) {
            showError("Inserire un valore numerico valido per il montepremi.");
        }
    }

    private void handleSaveTournament(final AddTournamentWindow addTournamentWindow) {
        final String name = addTournamentWindow.getTournamentName();
        final String startDate = addTournamentWindow.getStartDate();
        final String endDate = addTournamentWindow.getEndDate();
        final String registrationDeadline = addTournamentWindow.getRegistrationDeadline();
        final String tournamentType = addTournamentWindow.getTournamentType();
        final String gender = addTournamentWindow.getGender();
        final String rankingLimit = addTournamentWindow.getRankingLimit();
        final String prizeMoney = addTournamentWindow.getPrizeMoney();
        final var selectedClub = addTournamentWindow.getSelectedClub();
        final var prizeDistribution = addTournamentWindow.getPrizeDistribution();

        if(!validateTournamentInput(name, startDate, endDate, registrationDeadline, tournamentType, gender, 
                rankingLimit, prizeMoney, selectedClub, prizeDistribution)) {
            return;
        }

        try {
            final double prizeMoneyValue = Double.parseDouble(prizeMoney);
            if (model.addTournament(name, startDate, endDate, registrationDeadline, 
                    MatchType.fromLabel(tournamentType), Gender.fromCode(gender),
                    Ranking.fromLabel(rankingLimit), prizeMoneyValue, prizeDistribution,
                    referee.getRefereeId(), selectedClub.getClubId())) {
                loadTournaments();
                addTournamentWindow.dispose();
            }
        } catch (final Exception ex) {
            showError("Errore durante il salvataggio del torneo: " + ex.getMessage());
        }
    }

    private boolean validateTournamentInput(final String name, final String startDate, final String endDate,
            final String registrationDeadline, final String type, final String gender, final String ranking,
            final String prizeMoneyStr, final Club selectedClub, final List<Double> prizeDistribution) {
        if (name.isBlank() || startDate.isBlank() || endDate.isBlank()
                || registrationDeadline.isBlank() || type == null || gender == null
                || ranking == null || selectedClub == null || prizeMoneyStr.isBlank()) {
            showError("Tutti i campi obbligatori devono essere compilati.");
            return false;
        }

        final var formatter = CommonUtils.getDmyDateFormatter();
        final LocalDate start, end, deadline;
        try {
            start = LocalDate.parse(startDate, formatter);
            end = LocalDate.parse(endDate, formatter);
            deadline = LocalDate.parse(registrationDeadline, formatter);
        } catch (final DateTimeParseException e) {
            showError("Formato date non valido. Usa il formato gg/mm/aaaa.");
            return false;
        }
        if (start.isAfter(end)) {
            showError("La data di inizio deve essere precedente alla data di fine.");
            return false;
        }
        if (!deadline.isBefore(start)) {
            showError("La scadenza per l'iscrizione deve essere precedente alla data di inizio.");
            return false;
        }

        final double prizeMoney;
        try {
            prizeMoney = Double.parseDouble(prizeMoneyStr.trim());
        } catch (final NumberFormatException e) {
            showError("Il montepremi deve essere un numero valido.");
            return false;
        }
        if (prizeMoney < 0) {
            showError("Il montepremi deve essere un numero positivo.");
            return false;
        }

        return true;
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
