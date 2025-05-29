package tenniscomp.controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import tenniscomp.data.Player;
import tenniscomp.model.Model;
import tenniscomp.utils.TableUtils;
import tenniscomp.view.RegisterTeamWindow;

public class RegisterTeamController extends BasePlayerTableController {
    
    private final List<Player> eligiblePlayers;
    private final Set<Integer> selectedRows;
    private final int leagueId;
    private Runnable onTeamRegisteredCallback;

    public RegisterTeamController(final RegisterTeamWindow view, final List<Player> eligiblePlayers, 
            final Model model, final int leagueId) {
        super(view, model);
        this.eligiblePlayers = eligiblePlayers;
        this.selectedRows = new HashSet<>();
        this.leagueId = leagueId;
        
        loadPlayers();
    }
    
    @Override
    protected List<Player> getPlayersToDisplay() {
        return eligiblePlayers;
    }
    
    @Override
    protected void setupAdditionalComponents() {
        setupTableSelection();
    }
    
    @Override
    protected void setupButtonListeners() {
        final var registerView = (RegisterTeamWindow) view;
        registerView.setConfirmButtonListener(e -> handleConfirm());
        registerView.setCancelButtonListener(e -> view.dispose());
    }

    public void setOnTeamRegisteredCallback(final Runnable callback) {
        this.onTeamRegisteredCallback = callback;
    }
    
    public List<Player> getSelectedPlayers() {
        final var selectedPlayers = new ArrayList<Player>();
        for (final int modelRow : this.selectedRows) {
            final int playerId = getPlayerIdFromRow(modelRow);
            final Player player = findPlayerById(playerId);
            if (player != null) {
                selectedPlayers.add(player);
            }
        }
        return selectedPlayers;
    }
    
    private void setupTableSelection() {
        final var registerView = (RegisterTeamWindow) view;

        registerView.setPlayersTableMouseListener(new MouseAdapter() {
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
        
        TableUtils.highlightRows(table, viewRowsToHighlight);
        table.repaint();
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
}