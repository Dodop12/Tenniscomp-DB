package tenniscomp.controller.league;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTable;

import tenniscomp.controller.player.BasePlayerTableController;
import tenniscomp.data.Player;
import tenniscomp.model.Model;
import tenniscomp.view.league.TeamDetailsWindow;

public class TeamDetailsController extends BasePlayerTableController {
    
    private final int teamId;
    private Runnable onPlayerRemovedCallback;

    public TeamDetailsController(final TeamDetailsWindow view, final Model model, 
            final int teamId) {
        super(view, model);
        this.teamId = teamId;
        
        view.setTeamName(model.getClubByTeamId(teamId).getName());
        loadPlayers();
    }
    
    @Override
    protected List<Player> getPlayersToDisplay() {
        return model.getPlayersByTeam(teamId);
    }
    
    @Override
    protected void setupAdditionalComponents() {
        setupContextMenu();
    }
    
    @Override
    protected void setupButtonListeners() {
        final var teamView = (TeamDetailsWindow) view;
        teamView.setCloseButtonListener(e -> view.dispose());
    }
    
    private void setupContextMenu() {
        final var table = view.getPlayersTable();
        final var contextMenu = new JPopupMenu();
        
        final var removePlayerItem = new JMenuItem("Rimuovi dalla squadra");
        contextMenu.add(removePlayerItem);
        
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
                    contextMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });
        
        removePlayerItem.addActionListener(e -> handleRemovePlayerAction(getSelectedPlayer(table)));
    }
    
    private Player getSelectedPlayer(final JTable table) {
        final int selectedRow = table.getSelectedRow();
        // Invalid row selected
        if (selectedRow < 0) {
            return null;
        }

        final int playerId = getPlayerIdFromRow(selectedRow);
        return findPlayerById(playerId);
    }
    
    private void handleRemovePlayerAction(final Player player) {
        if (player == null) {
            throw new IllegalArgumentException("Player not found");
        }

        if (model.getTeamPlayerCount(teamId) <= 2) {
            showError("Impossibile rimuovere il giocatore: una squadra deve avere almeno 2 giocatori.");
            return;
        }
        
        final String confirmationMessage = "Sei sicuro di voler rimuovere " + player.getSurname() + " " + player.getName() 
                + " dalla squadra?";
        
        if (showConfirmation(confirmationMessage, "Rimuovi Giocatore")) {
            if (model.updatePlayerTeam(player.getPlayerId(), null)) {
                loadPlayers();
                // Notify parent controller that a player was removed
                if (onPlayerRemovedCallback != null) {
                    onPlayerRemovedCallback.run();
                }
            } else {
                showError("Errore durante la rimozione del giocatore dalla squadra.");
            }
        }
    }
    
    public void setOnPlayerRemovedCallback(final Runnable callback) {
        this.onPlayerRemovedCallback = callback;
    }
}