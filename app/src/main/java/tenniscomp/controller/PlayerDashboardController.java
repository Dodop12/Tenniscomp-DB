package tenniscomp.controller;

import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.util.Optional;

import javax.swing.JOptionPane;
import javax.swing.event.MouseInputAdapter;

import tenniscomp.data.Player;
import tenniscomp.data.Tournament;
import tenniscomp.model.Model;
import tenniscomp.utils.ImmutableTableModel;
import tenniscomp.utils.CommonUtils;
import tenniscomp.utils.TableUtils;
import tenniscomp.view.PlayerDashboard;

public class PlayerDashboardController {
    
    private final PlayerDashboard view;
    private final Model model;
    private final Player player;
    
    public PlayerDashboardController(final PlayerDashboard view, final Model model, final Player player) {
        this.view = view;
        this.model = model;
        this.player = player;
        
        loadPlayerData();
        loadTournaments();
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
                tournament.getStartDate(),
                tournament.getEndDate(),
                tournament.getRegistrationDeadline(),
                tournament.getType(),
                tournament.getGender().getLabel(),
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
            loadTournaments();
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
