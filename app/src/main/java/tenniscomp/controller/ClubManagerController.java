package tenniscomp.controller;

import tenniscomp.data.Club;
import tenniscomp.model.Model;
import tenniscomp.view.ClubManager;
import tenniscomp.utils.ImmutableTableModel;
import tenniscomp.utils.TableUtils;

public class ClubManagerController {
    
    private final ClubManager view;
    private final Model model;
    
    public ClubManagerController(final ClubManager view, final Model model) {
        this.view = view;
        this.model = model;
        
        loadClubs();
    }
    
    public void loadClubs() {
        final var tableModel = (ImmutableTableModel) view.getClubsTable().getModel();
        TableUtils.clearTable(tableModel);
        
        final var clubs = model.getAllClubs();
        for (final Club club : clubs) {
            final Object[] rowData = {
                club.getClubId(),
                club.getName(),
                club.getAddress(),
                club.getCity()
            };
            tableModel.addRow(rowData);
        }

        TableUtils.adjustColumnWidths(view.getClubsTable());
    }
    
}
