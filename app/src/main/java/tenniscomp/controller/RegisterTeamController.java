package tenniscomp.controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import tenniscomp.data.Club;
import tenniscomp.data.Player;
import tenniscomp.model.Model;
import tenniscomp.utils.TableUtils;
import tenniscomp.view.RegisterTeamWindow;

public class RegisterTeamController {
    
    private final RegisterTeamWindow view;
    private final List<Player> eligiblePlayers;
    private final Set<Integer> selectedRows;
    private final Model model;
    private final int leagueId;

    private Runnable onTeamRegisteredCallback;

    public RegisterTeamController(final RegisterTeamWindow view, final List<Player> eligiblePlayers, 
            final Model model, final int leagueId) {
        this.view = view;
        this.eligiblePlayers = eligiblePlayers;
        this.selectedRows = new HashSet<>();
        this.model = model;
        this.leagueId = leagueId;
        
        loadEligiblePlayers();
        setupSearchFunctionality();
        setupTableSelection();
        setupButtonListeners();
    }
    
    private void loadEligiblePlayers() {
        final var tableModel = view.getTableModel();
        TableUtils.clearTable(tableModel);
        
        for (final var player : eligiblePlayers) {
            final Club club = Optional.ofNullable(player.getClubId())
                    .map(model::getClubById)
                    .orElse(null);
            final Object[] rowData = {
                player.getPlayerId(),
                player.getSurname(),
                player.getName(),
                player.getBirthDate(),
                player.getGender().getCode(),
                player.getEmail(),
                player.getPhone(),
                player.getRanking().getLabel(),
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
    
    private void setupTableSelection() {
        view.setPlayersTableMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(final MouseEvent e) {
                if (e.getClickCount() == 1) {
                    final var table = view.getPlayersTable();
                    final int viewRow = table.rowAtPoint(e.getPoint());
                    if (viewRow >= 0) {
                        final int modelRow = table.convertRowIndexToModel(viewRow);
                        toggleRowSelection(modelRow);
                        updateTableHighlighting();
                    }
                }
            }
        });
    }
    
    private void toggleRowSelection(final int modelRow) {
        if (selectedRows.contains(modelRow)) {
            selectedRows.remove(modelRow);
        } else {
            selectedRows.add(modelRow);
        }
    }
    
    private void updateTableHighlighting() {
        final var table = view.getPlayersTable();
        
        // Convert model rows to view rows for highlighting
        final Set<Integer> viewRowsToHighlight = new HashSet<>();
        for (final int modelRow : selectedRows) {
            final int viewRow = table.convertRowIndexToView(modelRow);
            if (viewRow >= 0) {
                viewRowsToHighlight.add(viewRow);
            }
        }
        
        // Use TableUtils to highlight the selected rows
        TableUtils.highlightRows(table, viewRowsToHighlight);
        table.repaint();
    }
    
    private void setupButtonListeners() {
        view.setConfirmButtonListener(e -> handleConfirm());
        view.setCancelButtonListener(e -> view.dispose());
    }
    
    private void handleConfirm() {
        final var selectedPlayers = getSelectedPlayers();
        
        if (selectedPlayers.isEmpty()) {
            showError("Seleziona almeno un giocatore per la squadra.");
            return;
        }
        
        final var clubIds = selectedPlayers.stream()
            .map(Player::getClubId)
            .distinct()
            .toList();
        if (clubIds.size() != 1) {
            showError("Tutti i giocatori selezionati devono appartenere allo stesso circolo.");
            return;
        }
        
        final int clubId = clubIds.get(0);
        final var selectedPlayerIds = selectedPlayers.stream()
            .map(Player::getPlayerId)
            .toList();
        
        if (model.registerTeamForLeague(clubId, this.leagueId, selectedPlayerIds)) {
            view.dispose();
            // Notify parent controller that team was registered successfully for the table refresh
            if (onTeamRegisteredCallback != null) {
                onTeamRegisteredCallback.run();
            }
        } else {
            showError("Errore durante la registrazione della squadra.");
        }
    }
    
    public void setOnTeamRegisteredCallback(final Runnable callback) {
        this.onTeamRegisteredCallback = callback;
    }
    
    public List<Player> getSelectedPlayers() {
        final var selectedPlayers = new ArrayList<Player>();
        for (final int modelRow : this.selectedRows) {
            final int playerId = (int) view.getTableModel().getValueAt(modelRow, 0);
            for (final var player : this.eligiblePlayers) {
                if (player.getPlayerId() == playerId) {
                    selectedPlayers.add(player);
                    break; // Found the player, no need to continue searching
                }
            }
        }
        return selectedPlayers;
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