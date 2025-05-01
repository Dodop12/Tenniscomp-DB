package tenniscomp.controller;

import tenniscomp.data.Referee;
import tenniscomp.model.Model;
import tenniscomp.view.ClubManager;
import tenniscomp.view.PlayerManager;
import tenniscomp.view.RefereeDashboard;

public class RefereeDashboardController {
    
    private final RefereeDashboard view;
    private final Model model;
    private final Referee referee;
    
    public RefereeDashboardController(final RefereeDashboard view, final Model model, final Referee referee) {
        this.view = view;
        this.model = model;
        this.referee = referee;
        
        loadRefereeData();
        setupListeners();
    }
    
    private void loadRefereeData() {
        view.setRefereeName(referee.getName() + " " + referee.getSurname());
        view.setRefereeTitle(referee.getTitle());
    }

    private void setupListeners() {
        view.setManagePlayersListener(e -> openPlayerManager());
        view.setManageClubsListener(e -> openClubManager());
    }

     private void openPlayerManager() {
        final var playerManager = new PlayerManager();
        playerManager.setCloseButtonListener(e -> playerManager.dispose());
        playerManager.display();
    }
    
    private void openClubManager() {
        final var clubManager = new ClubManager();
        clubManager.setCloseButtonListener(e -> clubManager.dispose());
        clubManager.setAddClubButtonListener(e -> {
            // TODO
        }); 
        clubManager.display();
    }
}
