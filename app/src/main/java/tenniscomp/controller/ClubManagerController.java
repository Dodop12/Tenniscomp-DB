package tenniscomp.controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;

import tenniscomp.data.Club;
import tenniscomp.model.Model;
import tenniscomp.utils.ImmutableTableModel;
import tenniscomp.utils.Surface;
import tenniscomp.utils.TableUtils;
import tenniscomp.view.ClubManager;
import tenniscomp.view.CourtRegistrationWindow;

public class ClubManagerController {
    
    private final ClubManager view;
    private final Model model;
    
    public ClubManagerController(final ClubManager view, final Model model) {
        this.view = view;
        this.model = model;
        
        loadClubs();
        setupContextMenu();
    }
    
    public void loadClubs() {
        final var tableModel = (ImmutableTableModel) view.getClubsTable().getModel();
        TableUtils.clearTable(tableModel);
        
        final var clubs = model.getAllClubs();
        for (final Club club : clubs) {
            final int courtCount = model.getCourtCountByClub(club.getClubId());
            final Object[] rowData = {
                club.getClubId(),
                club.getName(),
                club.getAddress(),
                club.getCity(),
                courtCount
            };
            tableModel.addRow(rowData);
        }

        TableUtils.adjustColumnWidths(view.getClubsTable());
    }

    private void setupContextMenu() {
        final var table = this.view.getClubsTable();
        final var contextMenu = new JPopupMenu();
        
        final var addCourtItem = new JMenuItem("Registra campo");
        
        contextMenu.add(addCourtItem);
        
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
                }
                contextMenu.show(e.getComponent(), e.getX(), e.getY());
            }
        });
        
        addCourtItem.addActionListener(e -> handleAddCourtAction());
    }

    private Club getSelectedClub() {
        final var table = view.getClubsTable();
        final int selectedRow = table.getSelectedRow();
        
        // Invalid row selected
        if (selectedRow < 0) {
            return null;
        }

        final int clubId = (int) table.getValueAt(selectedRow, 0); // ID is the first column
        return model.getClubById(clubId);
    }

    private void handleAddCourtAction() {
        final Club club = getSelectedClub();
        if (club == null) {
             throw new IllegalArgumentException("Club not found");
        }
        
        final var courtRegistrationWindow = new CourtRegistrationWindow(view, club);
        
        courtRegistrationWindow.setSaveButtonListener(e -> {
            final int courtNumber = courtRegistrationWindow.getCourtNumber();
            final String surface = courtRegistrationWindow.getSurface();
            final boolean indoor = courtRegistrationWindow.isIndoor();

            if (model.courtNumberExists(courtNumber, club.getClubId())) {
                showError("Il campo numero " + courtNumber + " esiste già in questo circolo");
                return;
            }
            
            final boolean success = model.addCourt(courtNumber, Surface.fromLabel(surface), indoor, club.getClubId());
            
            if (success) {
                loadClubs();
                courtRegistrationWindow.dispose();
                JOptionPane.showMessageDialog(
                    view,
                    "Campo registrato con successo.",
                    "Campo Aggiunto",
                    JOptionPane.INFORMATION_MESSAGE
                );
            } else {
                showError("Errore durante l'aggiunta del campo");
            }
        });
        
        courtRegistrationWindow.setCancelButtonListener(e -> courtRegistrationWindow.dispose());
        courtRegistrationWindow.setVisible(true);
    }

    private void showError(final String message) {
        JOptionPane.showMessageDialog(
            view,
            message,
            "Errore",
            JOptionPane.ERROR_MESSAGE
        );
    }
    
}
