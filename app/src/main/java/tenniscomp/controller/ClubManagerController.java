package tenniscomp.controller;

import java.util.List;
import tenniscomp.data.Club;
import tenniscomp.model.Model;
import tenniscomp.view.ClubManager;
import tenniscomp.utils.ImmutableTableModel;

public class ClubManagerController {
    
    private final ClubManager view;
    private final Model model;
    
    public ClubManagerController(final ClubManager view, final Model model) {
        this.view = view;
        this.model = model;
        
        loadClubs();
    }
    
    private void loadClubs() {
        final var tableModel = view.getTableModel();
        clearTable(tableModel);
        
        final List<Club> clubs = model.getAllClubs();
        for (final Club club : clubs) {
            final Object[] rowData = {
                club.getClubId(),
                club.getName(),
                club.getAddress(),
                club.getCity()
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
