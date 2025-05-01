package tenniscomp.controller;

import java.util.List;
import tenniscomp.data.Player;
import tenniscomp.model.Model;
import tenniscomp.view.PlayerManager;
import tenniscomp.utils.ImmutableTableModel;

public class PlayerManagerController {
    
    private final PlayerManager view;
    private final Model model;
    
    public PlayerManagerController(final PlayerManager view, final Model model) {
        this.view = view;
        this.model = model;
        
        loadPlayers();
    }
    
    private void loadPlayers() {
        final var tableModel = view.getTableModel();
        clearTable(tableModel);

        final List<Player> players = model.getAllPlayers();
        for (final Player player : players) {
            final Object[] rowData = {
                player.getPlayerId(),
                player.getSurname(),
                player.getName(),
                player.getBirthDate(),
                player.getGender(),
                player.getEmail(),
                player.getPhone(),
                player.getRanking()
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
}