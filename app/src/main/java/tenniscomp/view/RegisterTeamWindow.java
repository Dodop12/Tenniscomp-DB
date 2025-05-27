package tenniscomp.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableRowSorter;

import tenniscomp.utils.ImmutableTableModel;

public class RegisterTeamWindow extends JDialog {
    
    private final JTextField searchField;
    private final JButton confirmButton;
    private final JButton cancelButton;
    private final JTable playersTable;
    private final ImmutableTableModel tableModel;
    private final TableRowSorter<ImmutableTableModel> sorter;

    public RegisterTeamWindow(final JFrame parent) {
        super(parent, "Registra Squadra", true);
        
        setSize(1000, 600);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        setLocationRelativeTo(parent);
        
        final var infoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        final var infoLabel = new JLabel("Seleziona i giocatori per la squadra:");
        infoPanel.add(infoLabel);
        
        final var searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        searchPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        
        final var searchLabel = new JLabel("Cerca per cognome:");
        this.searchField = new JTextField(20);
        
        searchPanel.add(searchLabel);
        searchPanel.add(this.searchField);
        
        final var topPanel = new JPanel(new BorderLayout());
        topPanel.add(infoPanel, BorderLayout.NORTH);
        topPanel.add(searchPanel, BorderLayout.SOUTH);
        
        final var buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        this.confirmButton = new JButton("Conferma");
        this.cancelButton = new JButton("Annulla");
        buttonPanel.add(this.confirmButton);
        buttonPanel.add(this.cancelButton);
        
        // Players table
        final String[] columns = {"ID", "Cognome", "Nome", "Data Nascita", "Sesso", "Email", "Telefono",
                "Classifica", "Circolo"};
        this.tableModel = new ImmutableTableModel(columns, 0);
        this.playersTable = new JTable(tableModel);
        
        // Disable default selection behaviour
        this.playersTable.setRowSelectionAllowed(false);
        
        // Set up the row sorter for filtering
        this.sorter = new TableRowSorter<>(tableModel);
        playersTable.setRowSorter(sorter);
        
        final var tableScrollPane = new JScrollPane(playersTable);
        tableScrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        add(topPanel, BorderLayout.NORTH);
        add(tableScrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public JTable getPlayersTable() {
        return playersTable;
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

    public void setPlayersTableMouseListener(final MouseListener listener) {
        this.playersTable.addMouseListener(listener);
    }

    public void setConfirmButtonListener(final ActionListener listener) {
        this.confirmButton.addActionListener(listener);
    }

    public void setCancelButtonListener(final ActionListener listener) {
        this.cancelButton.addActionListener(listener);
    }
    
    public void display() {
        setVisible(true);
    }
}