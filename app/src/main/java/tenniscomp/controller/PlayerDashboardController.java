package tenniscomp.controller;

import tenniscomp.data.Player;
import tenniscomp.model.Model;
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
    }
    
    private void loadPlayerData() {
        view.setPlayerName(player.getName() + " " + player.getSurname());
        view.setPlayerRanking(player.getRanking());
    }
    
}
