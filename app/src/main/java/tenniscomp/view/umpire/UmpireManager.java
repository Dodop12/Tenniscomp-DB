package tenniscomp.view.umpire;

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
import javax.swing.event.DocumentListener;
import javax.swing.table.TableRowSorter;

import tenniscomp.utils.ImmutableTableModel;

public class UmpireManager extends JFrame {
    
    private final JTextField searchField;
    private final JButton addUmpireButton;
    private final JButton closeButton;
    private final JTable umpiresTable;
    private final ImmutableTableModel tableModel;
    private final TableRowSorter<ImmutableTableModel> sorter;

    public UmpireManager() {
        setTitle("Gestione Arbitri");
        setSize(1000, 600);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        
        // Search and add panel
        final var topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        
        final var searchLabel = new JLabel("Cerca per cognome:");
        this.searchField = new JTextField(20);
        this.addUmpireButton = new JButton("Aggiungi Arbitro");
        
        topPanel.add(searchLabel);
        topPanel.add(this.searchField);
        topPanel.add(this.addUmpireButton);
        
        // Close button
        final var buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        this.closeButton = new JButton("Chiudi");
        buttonPanel.add(this.closeButton);
        
        // Umpires table
        final String[] columns = {"ID", "Cognome", "Nome", "Data Nascita", "Sesso", "Email", "Telefono", "Qualifica"};
        this.tableModel = new ImmutableTableModel(columns, 0);
        this.umpiresTable = new JTable(tableModel);
        
        // Set up the row sorter for filtering
        this.sorter = new TableRowSorter<>(tableModel);
        umpiresTable.setRowSorter(sorter);
        
        final var tableScrollPane = new JScrollPane(umpiresTable);
        tableScrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        add(topPanel, BorderLayout.NORTH);
        add(tableScrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        
        setLocationRelativeTo(null);
    }
    
    public JTable getUmpiresTable() {
        return umpiresTable;
    }

    public ImmutableTableModel getTableModel() {
        return tableModel;
    }

    public TableRowSorter<ImmutableTableModel> getTableSorter() {
        return sorter;
    }

    public JTextField getSearchField() {
        return searchField;
    }

    public void setSearchFieldListener(final DocumentListener listener) {
        this.searchField.getDocument().addDocumentListener(listener);
    }

    public void setAddUmpireButtonListener(final ActionListener listener) {
        this.addUmpireButton.addActionListener(listener);
    }

    public void setCloseButtonListener(final ActionListener listener) {
        this.closeButton.addActionListener(listener);
    }
    
    public void display() {
        setVisible(true);
    }
}
