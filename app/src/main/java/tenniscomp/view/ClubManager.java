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

public class ClubManager extends JFrame {
    
    private final JTextField searchField;
    private final JButton addClubButton;
    private final JButton closeButton;
    private final JTable clubsTable;
    private final TableRowSorter<ImmutableTableModel> sorter;

    public ClubManager() {
        setTitle("Gestione Circoli");
        setSize(800, 600);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        
        // Search panel
        final var searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        searchPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        
        final var searchLabel = new JLabel("Cerca per nome:");
        this.searchField = new JTextField(20);
        this.addClubButton = new JButton("Aggiungi Circolo");
        
        searchPanel.add(searchLabel);
        searchPanel.add(this.searchField);
        searchPanel.add(this.addClubButton);
        
        // Close button
        final var buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        this.closeButton = new JButton("Chiudi");
        buttonPanel.add(this.closeButton);
        
        // Clubs table
        final String[] columns = {"ID", "Nome", "Indirizzo", "Città", "Numero Campi"};
        final var tableModel = new ImmutableTableModel(columns, 0);
        this.clubsTable = new JTable(tableModel);
        
        // Set up the row sorter for filtering
        this.sorter = new TableRowSorter<>(tableModel);
        clubsTable.setRowSorter(sorter);
        
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
        
        final var tableScrollPane = new JScrollPane(clubsTable);
        tableScrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        add(searchPanel, BorderLayout.NORTH);
        add(tableScrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        
        setLocationRelativeTo(null);
    }
    
    public void setAddClubButtonListener(final ActionListener listener) {
        this.addClubButton.addActionListener(listener);
    }
    
    public void setCloseButtonListener(final ActionListener listener) {
        this.closeButton.addActionListener(listener);
    }
    
    public void display() {
        setVisible(true);
    }

    private void applySearchFilter() {
        final String text = searchField.getText();
        if (text.trim().length() == 0) {
            sorter.setRowFilter(null);
        } else {
            // Filter on the name column (index 1)
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text, 1));
        }
    }
}