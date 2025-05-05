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

                    final var player = getSelectedPlayer(table);

                    // Can add a new card only if the player does not have one
                    addCardItem.setEnabled(player.getCardId() == null);
                    // Can renew the card only if the player has one
                    renewCardItem.setEnabled(player.getCardId() != null);
                }

                contextMenu.show(e.getComponent(), e.getX(), e.getY());
            }
        });
        
        addCardItem.addActionListener(e -> handleAddCardAction(getSelectedPlayer(table)));
        renewCardItem.addActionListener(e -> handleRenewCardAction(getSelectedPlayer(table)));
        rankingItem.addActionListener(e -> handleEditRankingAction(getSelectedPlayer(table)));
        clubItem.addActionListener(e -> handleAssignClubAction(getSelectedPlayer(table)));
    }

    private Player getSelectedPlayer(final JTable table) {
        final int selectedRow = table.getSelectedRow();
        // Invalid row selected
        if (selectedRow < 0) {
            return null;
        }

        final int playerId = (int) table.getValueAt(selectedRow, 0); // ID is the first column
        return model.getPlayerById(playerId); 
    }

    private void handleAddCardAction(final Player player) {
        if (player == null) {
            throw new IllegalArgumentException("Player not found");
        }

        String cardNumber;
        do {
            cardNumber = PlayerUtils.generateCardNumber();
        } while (model.checkCardNumberExists(cardNumber));

        final String expiryDate = PlayerUtils.generateCardExpiryDate();

        final int result = JOptionPane.showConfirmDialog(
            view,
            "Conferma tesseramento giocatore " + player.getSurname() + " " + player.getName()
                + "\n\nNumero tessera: " + cardNumber
                + "\nData di scadenza: " + PlayerUtils.convertDateFormat(expiryDate),
            "Tesseramento - " + player.getSurname() + " " + player.getName(),
            JOptionPane.OK_CANCEL_OPTION
        );
        if (result == JOptionPane.OK_OPTION) {
            model.addCard(cardNumber, expiryDate);
            final var card = model.getCardByNumber(cardNumber);
            if (card == null) {
                showError("Errore durante il tesseramento del giocatore.");
                return;
            }
            model.updatePlayerCard(player.getPlayerId(), card.getCardId());
            loadPlayers(); // Refresh the table
        }
    }

    private void handleRenewCardAction(final Player player) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handleRenewCardAction'");
    }

    private void handleEditRankingAction(final Player player) {
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
            "Inserisci la classifica aggiornata per " + player.getSurname() + " " + player.getName()
            + "\nClassifica attuale: " + player.getRanking(),
            "Modifica Classifica",
            JOptionPane.PLAIN_MESSAGE,
            null,
            allRankings,
            allRankings[0]
        );

        // If ranking editing has been confirmed, update it in the database
        if (newRanking != null) {
            model.updatePlayerRanking(player.getPlayerId(), newRanking.toString());
            loadPlayers(); // Refresh the table
        }
    }

    private void handleAssignClubAction(final Player player) {
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