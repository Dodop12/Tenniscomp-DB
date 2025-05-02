package tenniscomp.controller;

import tenniscomp.data.Referee;
import tenniscomp.model.Model;
import tenniscomp.view.AddClubWindow;
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
        new PlayerManagerController(playerManager, model);
        playerManager.setCloseButtonListener(e -> playerManager.dispose());
        playerManager.display();
    }
    
    private void openClubManager() {
        final var clubManager = new ClubManager();
        final var clubManagerController = new ClubManagerController(clubManager, model);
        clubManager.setCloseButtonListener(e -> clubManager.dispose());
        clubManager.setAddClubButtonListener(e -> 
            openAddClubWindow(clubManager, clubManagerController)
        ); 
        clubManager.display();
    }

    private void openAddClubWindow(final ClubManager clubManager, final ClubManagerController controller) {
        final var addClubWindow = new AddClubWindow(clubManager);
        
        addClubWindow.setSaveButtonListener(e -> {
            final String name = addClubWindow.getClubName();
            final String address = addClubWindow.getClubAddress();
            final String city = addClubWindow.getClubCity();
            
            if (!name.isEmpty() && !address.isEmpty() && !city.isEmpty()) {
                if (model.addClub(name, address, city)) {
                    // Refresh the list
                    controller.loadClubs();
                    addClubWindow.dispose();
                }
            }
        });
        
        addClubWindow.setCancelButtonListener(e -> addClubWindow.dispose());
        addClubWindow.setVisible(true);
    }
}
