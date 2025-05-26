package tenniscomp.controller;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.MouseInputAdapter;
import java.awt.event.MouseEvent;

import tenniscomp.data.Referee;
import tenniscomp.model.Model;
import tenniscomp.utils.Gender;
import tenniscomp.utils.ImmutableTableModel;
import tenniscomp.utils.LeagueCategory;
import tenniscomp.utils.LeagueSeries;
import tenniscomp.utils.MatchType;
import tenniscomp.utils.PlayerUtils;
import tenniscomp.utils.Ranking;
import tenniscomp.utils.TableUtils;
import tenniscomp.view.AddClubWindow;
import tenniscomp.view.AddLeagueWindow;
import tenniscomp.view.AddTournamentWindow;
import tenniscomp.view.ClubManager;
import tenniscomp.view.LeagueDetailsWindow;
import tenniscomp.view.PlayerManager;
import tenniscomp.view.RefereeDashboard;
import tenniscomp.view.TournamentDetailsWindow;

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
                PlayerUtils.convertDateFormat(tournament.getStartDate()),
                PlayerUtils.convertDateFormat(tournament.getEndDate()),
                PlayerUtils.convertDateFormat(tournament.getRegistrationDeadline()),
                tournament.getType(),
                tournament.getGender().getLabel(),
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
                league.getGender(),
                league.getYear()
            };
            tableModel.addRow(rowData);
        }

        TableUtils.adjustColumnWidths(table);
    }

    private void setupListeners() {
        view.setManagePlayersListener(e -> openPlayerManager());
        view.setManageClubsListener(e -> openClubManager());
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
            openAddClubWindow(clubManager, clubManagerController)
        ); 
        clubManager.display();
    }

    private void openAddClubWindow(final ClubManager clubManager, final ClubManagerController controller) {
        final var addClubWindow = new AddClubWindow(clubManager);
        
        addClubWindow.setSaveButtonListener(e -> {
            final String name = addClubWindow.getClubName();
            final String address = addClubWindow.getClubAddress();
            final String city = addClubWindow.getClubCity();
            
            if (!name.isEmpty() && !address.isEmpty() && !city.isEmpty()) {
                if (model.addClub(name, address, city)) {
                    // Refresh the list
                    controller.loadClubs();
                    addClubWindow.dispose();
                }
            }
        });
        
        addClubWindow.setCancelButtonListener(e -> addClubWindow.dispose());
        addClubWindow.setVisible(true);
    }

    private void openAddTournamentWindow() {
        final var addTournamentWindow = new AddTournamentWindow(view, model.getAllClubs());
        
        addTournamentWindow.setSaveButtonListener(e -> {
            final String name = addTournamentWindow.getTournamentName();
            final String startDate = addTournamentWindow.getStartDate();
            final String endDate = addTournamentWindow.getEndDate();
            final String registrationDeadline = addTournamentWindow.getRegistrationDeadline();
            final String tournamentType = addTournamentWindow.getTournamentType();
            final String gender = addTournamentWindow.getGender();
            final String rankingLimit = addTournamentWindow.getRankingLimit();
            final String prizeMoney = addTournamentWindow.getPrizeMoney();
            final var selectedClub = addTournamentWindow.getSelectedClub();
            
            if (!name.isEmpty() && !startDate.isEmpty() && !endDate.isEmpty() 
                    && !registrationDeadline.isEmpty() && selectedClub != null && !prizeMoney.isEmpty()) {
                try {
                    final double prizeMoneyValue = Double.parseDouble(prizeMoney);
                    if (model.addTournament(name, startDate, endDate, registrationDeadline, 
                            MatchType.fromLabel(tournamentType), Gender.fromCode(gender), Ranking.fromLabel(rankingLimit),
                            prizeMoneyValue, referee.getRefereeId(), selectedClub.getClubId())) {
                        loadTournaments();
                        addTournamentWindow.dispose();
                    }
                } catch (final NumberFormatException ex) {
                    JOptionPane.showMessageDialog(
                        view, "Inserire un valore numerico valido per il montepremi.", "Errore", JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        });
        
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
                    gender, year, referee.getRefereeId())) {
                loadLeagues();
                addLeagueWindow.dispose();
            }
        });
        
        addLeagueWindow.setCancelButtonListener(e -> addLeagueWindow.dispose());
        addLeagueWindow.setVisible(true);
    }
}
