package tenniscomp.controller;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import tenniscomp.data.Player;
import tenniscomp.data.Tournament;
import tenniscomp.model.Model;
import tenniscomp.utils.PlayerUtils;
import tenniscomp.utils.TableUtils;
import tenniscomp.view.AddMatchWindow;
import tenniscomp.view.TournamentDetailsWindow;

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
        view.setRankingLimit(tournament.getRankingLimit());
        
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
                    PlayerUtils.convertDateFormat(registration.getDate())
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
                    if (model.isPlayerWinner(player.getPlayerId(), match.getMatchId())) {
                        winners.add(player);
                    } else {
                        opponents.add(player);
                    }
                }
            }
        
            final var courtNumber = model.getCourtById(match.getCourtId()).getNumber();
            final Object[] rowData = {
                match.getMatchId(),
                getMatchPlayersString(winners),
                getMatchPlayersString(opponents),
                match.getResult(),
                PlayerUtils.convertDateFormat(match.getDate()),
                courtNumber
            };
            tableModel.addRow(rowData);
        }
        
        TableUtils.adjustColumnWidths(table);
    }

    private void setupListeners() {
        view.setAddMatchListener(e -> openAddMatchWindow());
    }

    private void openAddMatchWindow() {
        final var registeredPlayers = model.getTournamentPlayers(this.tournament.getTournamentId());
        
        if (registeredPlayers.size() < 2) {
            showError("Sono necessari almeno 2 giocatori iscritti per inserire una partita.");
            return;
        }
        
        final var courts = model.getCourtsByClub(this.tournament.getClubId());
        if (courts.isEmpty()) {
            showError("Nessun campo trovato per il circolo associato al torneo.");
            return;
        }
        
        final var addMatchWindow = new AddMatchWindow(view, registeredPlayers, courts);
        addMatchWindow.setMatchType(this.tournament.getType());
        addMatchWindow.setMatchTypeEditable(false);
        
        addMatchWindow.setSaveButtonListener(e -> {
            final var matchDate = addMatchWindow.getMatchDate();
            final var winner = addMatchWindow.getWinner();
            final var opponent = addMatchWindow.getOpponent();
            final var court = addMatchWindow.getCourt();
            final var result = addMatchWindow.getResult();
            
            if (matchDate != null && winner != null && opponent != null && 
                    court != null && !result.isEmpty()) {
                
                if (winner.getPlayerId() == opponent.getPlayerId()) {
                    showError("I giocatori selezionati devono essere diversi.");
                    return;
                }
                
                // TODO: doubles + manage referee
                if (model.addTournamentMatch(matchDate, result, tournament.getTournamentId(), 
                        court.getCourtId(), null, List.of(winner.getPlayerId()), List.of(opponent.getPlayerId()))) {
                    loadMatches();
                    addMatchWindow.dispose();
                } else {
                    showError("Errore durante il salvataggio della partita.");
                }
            } else {
                showError("Tutti i campi sono obbligatori.");
            }
        });
        
        addMatchWindow.setCancelButtonListener(e -> addMatchWindow.dispose());
        addMatchWindow.setVisible(true);
    }

    private String getMatchPlayersString(final List<Player> players) {
        return switch (players.size()) {
            case 1 -> players.get(0).toString();
            case 2 -> players.get(0).getSurname() + "/" + players.get(1).getSurname();
            default -> throw new IllegalArgumentException("Invalid number of players (must be 1 or 2 per team).");
        };
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