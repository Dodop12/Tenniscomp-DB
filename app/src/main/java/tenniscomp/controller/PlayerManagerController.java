package tenniscomp.controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Optional;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTable;

import tenniscomp.data.Card;
import tenniscomp.data.Player;
import tenniscomp.model.Model;
import tenniscomp.utils.ImmutableTableModel;
import tenniscomp.utils.PlayerUtils;
import tenniscomp.view.PlayerManager;

public class PlayerManagerController {

    private final PlayerManager view;
    private final Model model;

    public PlayerManagerController(final PlayerManager view, final Model model) {
        this.view = view;
        this.model = model;

        loadPlayers();
        setupContextMenu();
    }

    private void loadPlayers() {
        final var tableModel = view.getTableModel();
        clearTable(tableModel);

        final List<Player> players = model.getAllPlayers();
        for (final Player player : players) {
            final Card card = Optional.ofNullable(player.getCardId())
                    .map(model::getCardById)
                    .orElse(null);
            final Object[] rowData = {
                    player.getPlayerId(),
                    player.getSurname(),
                    player.getName(),
                    player.getBirthDate(),
                    player.getGender(),
                    player.getEmail(),
                    player.getPhone(),
                    player.getRanking(),
                    card != null ? card.getCardNumber() : "",
                    card != null ? card.getExpiryDate() : ""
                    // TODO: circolo
            };
            tableModel.addRow(rowData);
        }
    }

    private void clearTable(final ImmutableTableModel model) {
        while (model.getRowCount() > 0) {
            model.removeRow(0);
        }
    }

    private void setupContextMenu() {
        final var table = this.view.getPlayersTable();
        final var contextMenu = new JPopupMenu();
        
        final var addCardItem = new JMenuItem("Effettua tesseramento");
        final var renewCardItem = new JMenuItem("Rinnova tessera");
        final var rankingItem = new JMenuItem("Modifica classifica");  
        final var clubItem = new JMenuItem("Assegna circolo");
        
        contextMenu.add(addCardItem);
        contextMenu.add(renewCardItem);
        contextMenu.add(rankingItem);
        contextMenu.add(clubItem);
        
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(final MouseEvent e) {
                if (e.isPopupTrigger()) {
                    handlePopup(e);
                }
            }

            @Override
            public void mouseReleased(final MouseEvent e) {
                if (e.isPopupTrigger()) {
                    handlePopup(e);
                }
            }

            private void handlePopup(final MouseEvent e) {
                final int row = table.rowAtPoint(e.getPoint());
                if (row >= 0 && row < table.getRowCount()) {
                    table.setRowSelectionInterval(row, row);

                    final int playerId = getSelectedPlayerId(table);
                    final var player = model.getPlayerById(playerId);

                    // Can add a new card only if the player does not have one
                    addCardItem.setEnabled(player.getCardId() == null);
                    // Can renew the card only if the player has one
                    renewCardItem.setEnabled(player.getCardId() != null);
                }

                contextMenu.show(e.getComponent(), e.getX(), e.getY());
            }
        });
        
        addCardItem.addActionListener(e -> handleAddCardAction(getSelectedPlayerId(table)));
        renewCardItem.addActionListener(e -> handleRenewCardAction(getSelectedPlayerId(table)));
        rankingItem.addActionListener(e -> handleEditRankingAction(getSelectedPlayerId(table)));
        clubItem.addActionListener(e -> handleAssignClubAction(getSelectedPlayerId(table)));
    }

    private int getSelectedPlayerId(final JTable table) {
        final int selectedRow = table.getSelectedRow();
        // Invalid row selected
        if (selectedRow < 0) {
            return -1;
        }
        return (int) table.getValueAt(selectedRow, 0); // ID is the first column
    }

    private void handleAddCardAction(final int playerId) {

    }

    private void handleRenewCardAction(final int playerId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handleRenewCardAction'");
    }

    private void handleEditRankingAction(final int playerId) {
        final var player = model.getPlayerById(playerId);
        if (player == null) {
            throw new IllegalArgumentException("Player not found");
        }
        if (PlayerUtils.isCardExpired(model.getCardById(player.getCardId()))) {
            showError(
                "La tessera del giocatore è scaduta. È necessario rinnovarla prima di aggiornare la classifica."
            );
            return;
        }

        final var allRankings = PlayerUtils.getAllRankings().stream()
                .filter(e -> !e.equals(player.getRanking())) // Exclude current ranking
                .toArray(String[]::new);
        final var newRanking = JOptionPane.showInputDialog(
            null,
            "Inserisci la classifica aggiornata per " + player.getSurname() + " " + player.getName(),
            "Modifica Classifica",
            JOptionPane.PLAIN_MESSAGE,
            null,
            allRankings,
            allRankings[0]
        );

        // If ranking editing has been confirmed, update it in the database
        if (newRanking != null) {
            model.updatePlayerRanking(playerId, newRanking.toString());
            loadPlayers(); // Refresh the table
        }
    }

    private void handleAssignClubAction(final int playerId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handleClubAction'");
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