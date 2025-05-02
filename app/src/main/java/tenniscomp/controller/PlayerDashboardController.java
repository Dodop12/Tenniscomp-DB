package tenniscomp.controller;

import java.util.Optional;

import tenniscomp.data.Player;
import tenniscomp.model.Model;
import tenniscomp.utils.PlayerUtils;
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
        this.view.setPlayerName(player.getSurname() + " " + player.getName());
        this.view.setPlayerRanking(player.getRanking());

        final String category = PlayerUtils.calculateCategory(player.getBirthDate());
        this.view.setPlayerCategory(category);

        Optional.ofNullable(player.getCardId())
            .map(model::getCardById)
            .ifPresent(card -> view.setCardInfo(card.getCardNumber(), card.getExpiryDate()));
    }
    
}
