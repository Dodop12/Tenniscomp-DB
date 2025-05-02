package tenniscomp.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableRowSorter;

import tenniscomp.utils.ImmutableTableModel;

public class PlayerManager extends JFrame {
    
    private final JTextField searchField;
    private final JButton closeButton;
    private final JTable playersTable;
    private final ImmutableTableModel tableModel;
    private final TableRowSorter<ImmutableTableModel> sorter;

    public PlayerManager() {
        setTitle("Gestione Giocatori");
        setSize(1000, 600);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        
        // Search panel
        final var searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        searchPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        
        final var searchLabel = new JLabel("Cerca per cognome:");
        this.searchField = new JTextField(20);
        
        searchPanel.add(searchLabel);
        searchPanel.add(this.searchField);
        
        // Close button
        final var buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        this.closeButton = new JButton("Chiudi");
        buttonPanel.add(this.closeButton);
        
        // Players table
        final String[] columns = {"ID", "Cognome", "Nome", "Data Nascita", "Sesso", "Email", "Telefono",
                "Classifica", "Numero Tessera", "Scadenza Tessera", "Circolo"};
        this.tableModel = new ImmutableTableModel(columns, 0);
        this.playersTable = new JTable(tableModel);
        
        // Set up the row sorter for filtering
        this.sorter = new TableRowSorter<>(tableModel);
        playersTable.setRowSorter(sorter);

        // Search functionality
        this.searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(final DocumentEvent e) {
                applySearchFilter();
            }

            @Override
            public void removeUpdate(final DocumentEvent e) {
                applySearchFilter();
            }

            @Override
            public void changedUpdate(final DocumentEvent e) {
                applySearchFilter();
            }
        });
        
        final var tableScrollPane = new JScrollPane(playersTable);
        tableScrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        add(searchPanel, BorderLayout.NORTH);
        add(tableScrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        
        setLocationRelativeTo(null);
    }
    
    public ImmutableTableModel getTableModel() {
        return tableModel;
    }

    public void setCloseButtonListener(final ActionListener listener) {
        this.closeButton.addActionListener(listener);
    }
    
    public void display() {
        setVisible(true);
    }

    private void applySearchFilter() {
        final String text = this.searchField.getText();
        if (text.trim().length() == 0) {
            this.sorter.setRowFilter(null);
        } else {
            // Filter on the surname column (index 1)
            this.sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text, 1));
        }
    }
}
