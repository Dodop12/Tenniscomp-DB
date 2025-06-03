package tenniscomp.controller.umpire;

import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import tenniscomp.model.Model;
import tenniscomp.utils.CommonUtils;
import tenniscomp.utils.Gender;
import tenniscomp.utils.ImmutableTableModel;
import tenniscomp.utils.TableUtils;
import tenniscomp.view.umpire.AddUmpireWindow;
import tenniscomp.view.umpire.UmpireManager;

public class UmpireManagerController {
    
    private final UmpireManager view;
    private final Model model;
    
    public UmpireManagerController(final UmpireManager view, final Model model) {
        this.view = view;
        this.model = model;
        
        loadUmpires();
        setupSearchFunctionality();
    }
    
    public void loadUmpires() {
        final var table = view.getUmpiresTable();
        final var tableModel = (ImmutableTableModel) table.getModel();
        TableUtils.clearTable(tableModel);
        
        final var umpires = model.getAllUmpires();
        for (final var umpire : umpires) {
            final Object[] rowData = {
                umpire.getUmpireId(),
                umpire.getSurname(),
                umpire.getName(),
                CommonUtils.convertDateFormat(umpire.getBirthDate()),
                umpire.getGender().getCode(),
                umpire.getEmail(),
                umpire.getPhone(),
                umpire.getTitle()
            };
            tableModel.addRow(rowData);
        }
        
        TableUtils.adjustColumnWidths(table);
    }
    
    private void setupSearchFunctionality() {
        // Search functionality
        view.setSearchFieldListener(new DocumentListener() {
            @Override
            public void insertUpdate(final DocumentEvent e) {
                filterTable();
            }
            
            @Override
            public void removeUpdate(final DocumentEvent e) {
                filterTable();
            }
            
            @Override
            public void changedUpdate(final DocumentEvent e) {
                filterTable();
            }
        });
    }
    
    public void openAddUmpireWindow() {
        final var addUmpireWindow = new AddUmpireWindow(view);
        
        addUmpireWindow.setSaveButtonListener(e -> {
            final String surname = addUmpireWindow.getSurname();
            final String name = addUmpireWindow.getName();
            final String email = addUmpireWindow.getEmail();  
            final String birthDate = addUmpireWindow.getBirthDate();
            final String gender = addUmpireWindow.getGender();
            final String phone = addUmpireWindow.getPhone();
            final String title = addUmpireWindow.getTitle();
            
            if (!surname.isEmpty() && !name.isEmpty() && !email.isEmpty() && !phone.isEmpty()) {
                if (model.addUmpire(surname, name, email, birthDate, 
                        Gender.fromCode(gender), phone, title)) {
                    loadUmpires();
                    addUmpireWindow.dispose();
                }
            }
        });
        
        addUmpireWindow.setCancelButtonListener(e -> addUmpireWindow.dispose());
        addUmpireWindow.setVisible(true);
    }
    
    private void filterTable() {
        final String searchText = view.getSearchField().getText().trim().toLowerCase();
        
        if (searchText.isEmpty()) {
            view.getTableSorter().setRowFilter(null);
        } else {
            view.getTableSorter().setRowFilter(RowFilter.regexFilter("(?i)" + searchText, 1)); // Filter by surname (column 1)
        }
    }
}
