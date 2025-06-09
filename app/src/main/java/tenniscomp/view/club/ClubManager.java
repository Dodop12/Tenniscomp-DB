package tenniscomp.view.club;

import java.awt.BorderLayout;
import java.awt.Dimension;
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

import tenniscomp.utils.CommonUtils;
import tenniscomp.utils.ImmutableTableModel;

public class ClubManager extends JFrame {

    private static final double WIDTH_RATIO = 0.45;
    private static final double HEIGHT_RATIO = 0.4;
    
    private final JTextField searchField;
    private final JButton addClubButton;
    private final JButton closeButton;
    private final JTable clubsTable;
    private final ImmutableTableModel tableModel;
    private final TableRowSorter<ImmutableTableModel> sorter;

    public ClubManager() {
        setTitle("Gestione Circoli");
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
        this.tableModel = new ImmutableTableModel(columns, 0);
        this.clubsTable = new JTable(tableModel);
        
        // Set up the row sorter for filtering
        this.sorter = new TableRowSorter<>(tableModel);
        clubsTable.setRowSorter(sorter);
        
        final var tableScrollPane = new JScrollPane(clubsTable);
        tableScrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        final var screenSize = CommonUtils.getScreenSize();
        final int width = (int) (screenSize.width * WIDTH_RATIO);
        final int height = (int) (screenSize.height * HEIGHT_RATIO);
        tableScrollPane.setPreferredSize(new Dimension(width, height));
        
        add(searchPanel, BorderLayout.NORTH);
        add(tableScrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public JTable getClubsTable() {
        return clubsTable;
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
    
    public void setAddClubButtonListener(final ActionListener listener) {
        this.addClubButton.addActionListener(listener);
    }
    
    public void setCloseButtonListener(final ActionListener listener) {
        this.closeButton.addActionListener(listener);
    }
    
    public void display() {
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}