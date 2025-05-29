package tenniscomp.controller;

import java.util.List;
import java.util.Optional;

import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import tenniscomp.data.Club;
import tenniscomp.data.Player;
import tenniscomp.model.Model;
import tenniscomp.utils.CommonUtils;
import tenniscomp.utils.TableUtils;
import tenniscomp.view.BasePlayerTableWindow;

/**
 * Base controller class for windows that display a table of players.
 * Provides common functionality for search, table management, and player data loading.
 */
public abstract class BasePlayerTableController {
    
    protected final BasePlayerTableWindow view;
    protected final Model model;
    private List<Player> players;

    protected BasePlayerTableController(final BasePlayerTableWindow view, final Model model) {
        this.view = view;
        this.model = model;
        
        setupSearchFunctionality();
        setupButtonListeners();
        setupAdditionalComponents();
    }
    
    /**
     * Loads player data into the table.
     * Subclasses should call this method to populate the table.
     */
    protected void loadPlayers() {
        final var tableModel = view.getTableModel();
        TableUtils.clearTable(tableModel);
        
        this.players = getPlayersToDisplay();
        
        for (final Player player : players) {
            final Object[] rowData = createRowData(player);
            tableModel.addRow(rowData);
        }
        
        TableUtils.adjustColumnWidths(view.getPlayersTable());
    }
    
    /**
     * Creates row data array for a player.
     * Subclasses can override this to customize the data displayed.
     */
    protected Object[] createRowData(final Player player) {
        final Club club = Optional.ofNullable(player.getClubId())
                .map(model::getClubById)
                .orElse(null);

        final var rowData = new Object[]{
            player.getPlayerId(),
            player.getSurname(),
            player.getName(),
            CommonUtils.convertDateFormat(player.getBirthDate()),
            player.getGender().getCode(),
            player.getEmail(),
            player.getPhone(),
            player.getRanking().getLabel(),
            club != null ? club.getName() : ""
        };
        return rowData;
    }
    
    /**
     * Returns the list of players to display in the table.
     * Subclasses must implement this method.
     */
    protected abstract List<Player> getPlayersToDisplay();
    
    /**
     * Sets up additional components specific to the subclass.
     * Subclasses can override this method to add their specific setup logic.
     */
    protected abstract void setupAdditionalComponents();
    
    /**
     * Sets up button listeners specific to the subclass.
     * Subclasses must implement this method.
     */
    protected abstract void setupButtonListeners();
    
    /**
     * Sets up search functionality for the table.
     */
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
    
    /**
     * Applies search filter to the table based on the search field text.
     * Filters on the surname column (index 1).
     */
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
    
    /**
     * Finds a player by ID from the current players list.
     */
    protected Player findPlayerById(final int playerId) {
        return players.stream()
                .filter(player -> player.getPlayerId() == playerId)
                .findFirst()
                .orElse(null);
    }
    
    protected int getPlayerIdFromRow(final int row) {
        return (int) view.getTableModel().getValueAt(row, 0);
    }
    
    protected void showError(final String message) {
        JOptionPane.showMessageDialog(
            view,
            message,
            "Errore",
            JOptionPane.ERROR_MESSAGE
        );
    }
    
    protected boolean showConfirmation(final String message, final String title) {
        final int result = JOptionPane.showConfirmDialog(
            view,
            message,
            title,
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );
        return result == JOptionPane.YES_OPTION;
    }
}
