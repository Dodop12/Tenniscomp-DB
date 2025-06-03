package tenniscomp.controller.player;

import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.util.Optional;

import javax.swing.JOptionPane;
import javax.swing.event.MouseInputAdapter;

import tenniscomp.data.Player;
import tenniscomp.data.Tournament;
import tenniscomp.model.Model;
import tenniscomp.utils.ImmutableTableModel;
import tenniscomp.utils.MatchType;
import tenniscomp.utils.CommonUtils;
import tenniscomp.utils.TableUtils;
import tenniscomp.view.player.PlayerDashboard;

public class PlayerDashboardController {
    
    private final PlayerDashboard view;
    private final Model model;
    private final Player player;

    private int matchesCount = 0;
    private int matchesWon = 0;
    private int tournamentTitles = 0;
    
    public PlayerDashboardController(final PlayerDashboard view, final Model model, final Player player) {
        this.view = view;
        this.model = model;
        this.player = player;
        
        loadPlayerData();
        loadTournaments();
        loadMatches();
        displayStats();
        setupListeners();
    }

    private void loadPlayerData() {
        this.view.setPlayerName(player.getSurname() + " " + player.getName());
        this.view.setPlayerRanking(player.getRanking().getLabel());

        final String category = CommonUtils.calculateCategory(player.getBirthDate());
        this.view.setPlayerCategory(category);

        Optional.ofNullable(player.getCardId())
            .map(model::getCardById)
            .ifPresent(card -> view.setCardInfo(card.getCardNumber(),
                    CommonUtils.convertDateFormat(card.getExpiryDate())));

        TableUtils.adjustColumnWidths(view.getTournamentsTable());
        TableUtils.adjustColumnWidths(view.getMatchesTable());
    }

    private void loadTournaments() {
        final var table = view.getTournamentsTable();
        final var tableModel = (ImmutableTableModel) table.getModel();
        TableUtils.clearTable(tableModel);
        
        final var tournaments = model.getEligibleTournaments(this.player.getRanking(), this.player.getGender());
         for (final var tournament : tournaments) {
            final Object[] rowData = {
                tournament.getTournamentId(),
                tournament.getName(),
                CommonUtils.convertDateFormat(tournament.getStartDate()),
                CommonUtils.convertDateFormat(tournament.getEndDate()),
                CommonUtils.convertDateFormat(tournament.getRegistrationDeadline()),
                tournament.getType(),
                tournament.getGender().getType(),
                tournament.getRankingLimit()
            };
            tableModel.addRow(rowData);

            final boolean isRegistered = model.isPlayerRegisteredForTournament(
                this.player.getPlayerId(), tournament.getTournamentId());
            if (isRegistered) {
                TableUtils.highlightRow(table, tableModel.getRowCount() - 1);
            }
        }

        TableUtils.adjustColumnWidths(table);
    }

    private void loadMatches() {
        final var table = view.getMatchesTable();
        final var tableModel = (ImmutableTableModel) table.getModel();
        TableUtils.clearTable(tableModel);

        loadTournamentMatches(tableModel);
        loadLeagueMatches(tableModel);
        
        TableUtils.adjustColumnWidths(table);
    }

    private void displayStats() {
        final double winRate = this.matchesCount > 0 ?
            (double) this.matchesWon / this.matchesCount * 100 : 0.0;
        
        this.view.setMatchStats(this.matchesCount, this.matchesWon,
                (int) winRate, this.tournamentTitles);
    }

    private void setupListeners() {
        // Double-click listener for tournament registration
        view.getTournamentsTable().addMouseListener(new MouseInputAdapter() {
            @Override
            public void mouseClicked(final MouseEvent e) {
                if (e.getClickCount() == 2) {
                    handleTournamentRegistration();
                }
            }
        });
    }

    private void loadTournamentMatches(final ImmutableTableModel tableModel) {
        final var tournamentMatches = model.getTournamentMatchesByPlayer(this.player.getPlayerId());
        for (final var match : tournamentMatches) {
            final var tournament = model.getTournamentById(match.getTournamentId());
            // Load only singles matches
            if (tournament != null && tournament.getType().equals(MatchType.SINGOLARE)) {
                final var opponent = model.getPlayersByTournamentMatch(match.getMatchId())
                        .stream()
                        .filter(p -> p.getPlayerId() != this.player.getPlayerId())
                        .findFirst()
                        .orElse(null);
                final var opponentName = opponent != null ?
                        opponent.getSurname() + " " + opponent.getName() : "";
                final String tournamentName = tournament.getName();
                final String result = model.isPlayerTournamentMatchWinner(this.player.getPlayerId(), match.getMatchId())
                        ? "W" : "L";
                
                final Object[] rowData = {
                    "TM" + match.getMatchId(),
                    tournamentName,
                    CommonUtils.convertDateFormat(match.getDate()),
                    opponentName,
                    result,
                    match.getResult(),
                };
                tableModel.addRow(rowData);
            }
        }
    }

    private void loadLeagueMatches(final ImmutableTableModel tableModel) {
        final var leagueMatches = model.getLeagueMatchesByPlayer(this.player.getPlayerId());
        for (final var match : leagueMatches) {
            final var league = model.getLeagueByMatchId(match.getMatchId());
            final var tie = model.getLeagueTieById(match.getTieId());
            // Load only singles matches
            if (league != null && tie != null && match.getType().equals(MatchType.SINGOLARE)) {
                final var opponent = model.getPlayersByLeagueMatch(match.getMatchId())
                        .stream()
                        .filter(p -> p.getPlayerId() != this.player.getPlayerId())
                        .findFirst()
                        .orElse(null);
                final var opponentName = opponent != null ?
                        opponent.getSurname() + " " + opponent.getName() : "";
                final String leagueName = "Campionato " + league.getSeries() +
                        " " + league.getCategory().getLabel();
                final String result = model.isPlayerLeagueMatchWinner(this.player.getPlayerId(), match.getMatchId())
                        ? "W" : "L";
                updateStats(result);
                
                final Object[] rowData = {
                    "LM" + match.getMatchId(),
                    leagueName,
                    CommonUtils.convertDateFormat(tie.getDate()),
                    opponentName,
                    result,
                    match.getResult(),
                };
                tableModel.addRow(rowData);
            }
        }
    }

    // TODO: tournament wins
    private void updateStats(final String result) {
        this.matchesCount++;
        if ("W".equals(result)) {
            this.matchesWon++;
        }
        displayStats(); // Refresh stats panel
    }

    private void handleTournamentRegistration() {
        final var table = view.getTournamentsTable();
        final int selectedRow = table.getSelectedRow();
        
        if (selectedRow >= 0) {
            final int tournamentId = (int) table.getValueAt(selectedRow, 0);
            final var tournament = model.getTournamentById(tournamentId);

            final boolean isRegistered = model.isPlayerRegisteredForTournament(
                this.player.getPlayerId(), tournamentId);
            if (!isRegistered) {
                final var cardId = player.getCardId();
                if (cardId == null || CommonUtils.isCardExpired(model.getCardById(cardId))) {
                    showError("Impossibile iscriversi al torneo \"" + tournament.getName() +
                        "\":\n tessera scaduta o non valida.");
                    return;
                }
                if(isRegistrationClosed(tournament.getRegistrationDeadline())) {
                    showError("Impossibile iscriversi al torneo \"" + tournament.getName() +
                        "\":\n registrazioni chiuse.");
                    return;
                }

                openRegistrationDialog(tournament);
            }
        }
    }

    private void openRegistrationDialog(final Tournament tournament) {
        final int response = JOptionPane.showConfirmDialog(
            view,
            "Vuoi confermare l'iscrizione al torneo \"" + tournament.getName() + "\"?",
            "Conferma iscrizione",
            JOptionPane.YES_NO_OPTION
        );
        
        if (response == JOptionPane.YES_OPTION) {
            model.registerPlayerForTournament(this.player.getPlayerId(), tournament.getTournamentId());
            JOptionPane.showMessageDialog(
                view,
                "Iscrizione al torneo \"" + tournament.getName() + "\" avvenuta con successo!",
                "Iscrizione completata",
                JOptionPane.INFORMATION_MESSAGE
            );
            loadTournaments();
        }
    }

    private boolean isRegistrationClosed(final String deadlineStr) {
        try {
            final var today = LocalDate.now();
            final var deadline = LocalDate.parse(deadlineStr);
            return today.isAfter(deadline);
        } catch (final Exception e) {
            return true; // If date can't be parsed, assume registration is closed
        }
    }

    private void showError(final String message) {
        JOptionPane.showMessageDialog(
            view,
            message,
            "Errore iscrizione",
            JOptionPane.ERROR_MESSAGE
        );
    }

}
