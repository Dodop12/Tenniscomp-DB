package tenniscomp.controller.tournament;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import tenniscomp.data.Player;
import tenniscomp.data.Tournament;
import tenniscomp.model.Model;
import tenniscomp.utils.CommonUtils;
import tenniscomp.utils.MatchType;
import tenniscomp.utils.TableUtils;
import tenniscomp.view.tournament.AddTournamentMatchWindow;
import tenniscomp.view.tournament.TournamentDetailsWindow;

public class TournamentDetailsController {

    private final TournamentDetailsWindow view;
    private final Model model;
    private final Tournament tournament;

    public TournamentDetailsController(final TournamentDetailsWindow view, final Model model, final Tournament tournament) {
        this.view = view;
        this.model = model;
        this.tournament = tournament;

        loadTournamentData();
        loadRegistrations();
        loadMatches();
        setupListeners();
    }

    private void loadTournamentData() {
        view.setTournamentName(tournament.getName());
        view.setDates(tournament.getStartDate(), tournament.getEndDate(), tournament.getRegistrationDeadline());
        view.setRankingLimit(tournament.getRankingLimit().getLabel());
        
        final var club = model.getClubById(tournament.getClubId());
        if (club != null) {
            view.setClub(club.getName());
        }
        
        view.setPrizeMoney(tournament.getPrizeMoney());
    }

    private void loadRegistrations() {
        final var table = view.getRegistrationsTable();
        final var tableModel = (DefaultTableModel) table.getModel();
        TableUtils.clearTable(tableModel);
        
        final var registrations = model.getTournamentRegistrations(tournament.getTournamentId());
        for (final var registration : registrations) {
            final var player = model.getPlayerById(registration.getPlayerId());
            if (player != null) {
                final Object[] rowData = {
                    registration.getRegistrationId(),
                    player.getName() + " " + player.getSurname(),
                    player.getRanking(),
                    CommonUtils.convertDateFormat(registration.getDate())
                };
                tableModel.addRow(rowData);
            }
        }
        
        TableUtils.adjustColumnWidths(table);
    }

    private void loadMatches() {
        final var table = view.getMatchesTable();
        final var tableModel = (DefaultTableModel) table.getModel();
        TableUtils.clearTable(tableModel);
        
        final var matches = model.getTournamentMatches(tournament.getTournamentId());
        for (final var match : matches) {
            final var players = model.getPlayersByTournamentMatch(match.getMatchId());
            final var winners = new ArrayList<Player>();
            final var opponents = new ArrayList<Player>();
            for (final var player : players) {
                if (player != null) {
                    if (model.isPlayerTournamentMatchWinner(player.getPlayerId(), match.getMatchId())) {
                        winners.add(player);
                    } else {
                        opponents.add(player);
                    }
                }
            }
        
            final var courtNumber = model.getCourtById(match.getCourtId()).getNumber();
            final var umpire = model.getUmpireById(match.getUmpireId());
            final var umpireName = umpire != null ? umpire.toString() : "";
            final Object[] rowData = {
                match.getMatchId(),
                CommonUtils.getMatchPlayersString(winners),
                CommonUtils.getMatchPlayersString(opponents),
                match.getResult(),
                CommonUtils.convertDateFormat(match.getDate()),
                courtNumber,
                umpireName
            };
            tableModel.addRow(rowData);
        }
        
        TableUtils.adjustColumnWidths(table);
    }

    private void setupListeners() {
        view.setAddMatchListener(e -> openAddMatchWindow());
        view.setCloseButtonListener(e -> view.dispose());
    }

    private void openAddMatchWindow() {
        final var registeredPlayers = model.getTournamentPlayers(this.tournament.getTournamentId());
        
        final int minPlayers = tournament.getType() == MatchType.SINGOLARE ? 2 : 4;
        if (registeredPlayers.size() < minPlayers) {
            final String message = tournament.getType() == MatchType.SINGOLARE 
                ? "Sono necessari almeno 2 giocatori iscritti per inserire una partita."
                : "Sono necessari almeno 4 giocatori iscritti per inserire una partita di doppio.";
            showError(message);
            return;
        }
        
        final var courts = model.getCourtsByClub(this.tournament.getClubId());
        if (courts.isEmpty()) {
            showError("Nessun campo trovato per il circolo associato al torneo.");
            return;
        }
        final var umpires = model.getAllUmpires();

        final var addMatchWindow = new AddTournamentMatchWindow(view, registeredPlayers, courts, umpires);
        addMatchWindow.setTournamentType(this.tournament.getType());
        
        addMatchWindow.setSaveButtonListener(e -> {
            final var matchDate = addMatchWindow.getMatchDate();
            final var court = addMatchWindow.getCourt();
            final var umpire = addMatchWindow.getUmpire();
            final var result = addMatchWindow.getResult();
            
            if (matchDate == null || matchDate.isEmpty() || court == null || result.isEmpty()) {
                showError("Tutti i campi sono obbligatori.");
                return;
            }
            
            final Integer umpireId = umpire != null ? umpire.getUmpireId() : null;
            boolean success = false;
            
            if (tournament.getType() == MatchType.SINGOLARE) {
                final var winner = addMatchWindow.getWinner();
                final var opponent = addMatchWindow.getOpponent();
                
                if (winner == null || opponent == null) {
                    showError("Seleziona i giocatori.");
                    return;
                }
                
                if (winner.getPlayerId() == opponent.getPlayerId()) {
                    showError("I giocatori selezionati devono essere diversi.");
                    return;
                }
                
                success = model.addTournamentMatch(matchDate, result, tournament.getTournamentId(), 
                        court.getCourtId(), umpireId, winner.getPlayerId(), opponent.getPlayerId());
            } else {
                // Doubles match
                final var winnerPair = addMatchWindow.getWinnerPair();
                final var opponentPair = addMatchWindow.getOpponentPair();
                
                if (winnerPair == null || opponentPair == null) {
                    showError("Seleziona due giocatori per ogni coppia.");
                    return;
                }
                
                // Check that all players are different
                final var allPlayers = new ArrayList<Player>();
                allPlayers.addAll(List.of(winnerPair.x(), winnerPair.y()));
                allPlayers.addAll(List.of(opponentPair.x(), opponentPair.y()));
                if (allPlayers.stream().distinct().count() != allPlayers.size()) {
                    showError("I giocatori selezionati devono essere diversi.");
                    return;
                }
            
                final var winnerIds = List.of(winnerPair.x(), winnerPair.y()).stream()
                    .map(Player::getPlayerId)
                    .toList();
                final var opponentIds = List.of(opponentPair.x(), opponentPair.y()).stream()
                    .map(Player::getPlayerId)
                    .toList();
                
                success = model.addTournamentMatch(matchDate, result, tournament.getTournamentId(), 
                        court.getCourtId(), umpireId, winnerIds, opponentIds);
            }
            
            if (success) {
                loadMatches();
                addMatchWindow.dispose();
            } else {
                showError("Errore durante il salvataggio della partita.");
            }
        });
        
        addMatchWindow.setCancelButtonListener(e -> addMatchWindow.dispose());
        addMatchWindow.setVisible(true);
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