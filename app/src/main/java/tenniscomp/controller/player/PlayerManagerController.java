package tenniscomp.controller.player;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import tenniscomp.data.Card;
import tenniscomp.data.Club;
import tenniscomp.data.Player;
import tenniscomp.model.Model;
import tenniscomp.utils.CommonUtils;
import tenniscomp.utils.Ranking;
import tenniscomp.utils.TableUtils;
import tenniscomp.view.club.AssignClubWindow;
import tenniscomp.view.player.PlayerManager;

public class PlayerManagerController {

    private final PlayerManager view;
    private final Model model;

    public PlayerManagerController(final PlayerManager view, final Model model) {
        this.view = view;
        this.model = model;

        loadPlayers();
        setupSearchFunctionality();
        setupContextMenu();
    }

    private void loadPlayers() {
        final var tableModel = view.getTableModel();
        TableUtils.clearTable(tableModel);

        for (final Player player : model.getAllPlayers()) {
            final Card card = Optional.ofNullable(player.getCardId())
                    .map(model::getCardById)
                    .orElse(null);
            final Club club = Optional.ofNullable(player.getClubId())
                    .map(model::getClubById)
                    .orElse(null);

            final Object[] rowData = {
                    player.getPlayerId(),
                    player.getSurname(),
                    player.getName(),
                    CommonUtils.convertDateFormat(player.getBirthDate()),
                    player.getGender().getCode(),
                    player.getEmail(),
                    player.getPhone(),
                    player.getRanking(),
                    card != null ? card.getCardNumber() : "",
                    card != null ? CommonUtils.convertDateFormat(card.getExpiryDate()) : "",
                    club != null ? club.getName() : ""
            };
            tableModel.addRow(rowData);
        }

        TableUtils.adjustColumnWidths(view.getPlayersTable());
    }

    private void setupSearchFunctionality() {
        view.setSearchFieldListener(new DocumentListener() {
            @Override
            public void insertUpdate(final DocumentEvent e) {
                applySearchFilter();
            }

            @Override
            public void removeUpdate(final DocumentEvent e) {
                applySearchFilter();
            }

            @Override
            public void changedUpdate(final DocumentEvent e) {
                applySearchFilter();
            }
        });
    }

    private void applySearchFilter() {
        final String searchText = view.getSearchField().getText();
        final var sorter = view.getTableSorter();
        
        if (searchText.trim().length() == 0) {
            sorter.setRowFilter(null);
        } else {
            // Filter on the surname column (index 1)
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchText, 1));
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
                    final var cardId = player.getCardId();

                    // Can add a new card only if the player does not have one
                    addCardItem.setEnabled(cardId == null);
                    // Can renew the card only if the player has one
                    renewCardItem.setEnabled(cardId != null
                        && CommonUtils.isCardExpired(model.getCardById(cardId)));
                    
                    rankingItem.setEnabled(cardId != null
                        && !CommonUtils.isCardExpired(model.getCardById(cardId)));
                    clubItem.setEnabled(cardId != null
                        &&! CommonUtils.isCardExpired(model.getCardById(cardId)));
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
            cardNumber = CommonUtils.generateCardNumber();
        } while (model.checkCardNumberExists(cardNumber));

        final var expiryDate = CommonUtils.getCardExpiryDate();

        final int result = showConfirm(
            "Conferma tesseramento giocatore " + player.getSurname() + " " + player.getName()
                + "\n\nNumero tessera: " + cardNumber
                + "\nData di scadenza: " + expiryDate,
            "Tesseramento - " + player.getSurname() + " " + player.getName()
        );
        if (result == JOptionPane.OK_OPTION) {
            model.addCard(cardNumber);
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
        if (player == null) {
            throw new IllegalArgumentException("Player not found");
        }
        if (player.getCardId() == null) {
            showError("Il giocatore non ha una tessera da rinnovare.");
            return;
        }

        final var card = model.getCardById(player.getCardId());
        if (card == null) {
            showError("Tessera non trovata.");
            return;
        }

        final var newExpiryDate = CommonUtils.getCardExpiryDate();
        final int result = showConfirm(
            "Conferma rinnovo tessera per " + player.getSurname() + " " + player.getName()
                + "\nNumero tessera: " + card.getCardNumber()
                + "\n\nVecchia scadenza: " + CommonUtils.convertDateFormat(card.getExpiryDate())
                + "\nNuova scadenza: " + newExpiryDate,
            "Rinnovo tessera - " + player.getSurname() + " " + player.getName()
        );
        if (result == JOptionPane.OK_OPTION) {
            model.renewCard(card.getCardId());
            loadPlayers();
        }
    }

    private void handleEditRankingAction(final Player player) {
        if (player == null) {
            throw new IllegalArgumentException("Player not found");
        }

        final var allRankings = Ranking.getAllLabels().stream()
                .filter(e -> !e.equals(player.getRanking().getLabel())) // Exclude current ranking
                .toArray(String[]::new);
        final var newRanking = JOptionPane.showInputDialog(
            null,
            "Inserisci la classifica aggiornata per " + player.getSurname() + " " + player.getName()
            + "\nClassifica attuale: " + player.getRanking().getLabel(),
            "Modifica Classifica",
            JOptionPane.PLAIN_MESSAGE,
            null,
            allRankings,
            allRankings[0]
        );

        // If ranking editing has been confirmed, update it in the database
        if (newRanking != null) {
            model.updatePlayerRanking(player.getPlayerId(), Ranking.fromLabel(newRanking.toString()));
            loadPlayers();
        }
    }

    private void handleAssignClubAction(final Player player) {
        if (player == null) {
            throw new IllegalArgumentException("Player not found");
        }

        final List<Club> clubs = new ArrayList<>(model.getAllClubs());
        if (clubs.isEmpty()) {
            showError("Nessun circolo disponibile nel sistema.");
            return;
        }

        // Remove the current club from the list
        if (player.getClubId() != null) {
            final var currentClub = model.getClubById(player.getClubId());
            clubs.removeIf(club -> club.getClubId() == currentClub.getClubId());
        }

        final var assignClubWindow = new AssignClubWindow(view, player, model, clubs);

        assignClubWindow.setSaveButtonListener(e -> {
            final Club selectedClub = assignClubWindow.getSelectedClub();
            if (selectedClub != null) {
                model.updatePlayerClub(player.getPlayerId(), selectedClub.getClubId());
                loadPlayers();
                assignClubWindow.dispose();
            }
        });
        assignClubWindow.setCancelButtonListener(e -> assignClubWindow.dispose());
        
        assignClubWindow.setVisible(true);
    }

    private int showConfirm(final String message, final String title) {
        return JOptionPane.showConfirmDialog(
            this.view,
            message,
            title,
            JOptionPane.OK_CANCEL_OPTION
        );
    }

    private void showError(final String message) {
        JOptionPane.showMessageDialog(
            this.view,
            message,
            "Errore",
            JOptionPane.ERROR_MESSAGE
        );
    }
}