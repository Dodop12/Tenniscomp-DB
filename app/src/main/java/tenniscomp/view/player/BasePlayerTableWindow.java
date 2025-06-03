package tenniscomp.view.player;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

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

/**
 * Base class for windows that display a table of players with search functionality.
 */
public abstract class BasePlayerTableWindow extends JDialog {
    
    private final JLabel infoLabel;
    private final JTextField searchField;
    private final JTable playersTable;
    private final ImmutableTableModel tableModel;
    private final TableRowSorter<ImmutableTableModel> sorter;
    private final JPanel buttonPanel;

    protected BasePlayerTableWindow(final JFrame parent, final String title) {
        super(parent, title, true);
        
        setSize(1000, 600);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        setLocationRelativeTo(parent);
        
        final var infoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        this.infoLabel = new JLabel(getInfoLabelText());
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
        
        this.buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        this.buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        
        // Players table
        final String[] columns = getTableColumns();
        this.tableModel = new ImmutableTableModel(columns, 0);
        this.playersTable = new JTable(tableModel);
        configureTableSelection();
        
        // Set up the row sorter for filtering
        this.sorter = new TableRowSorter<>(tableModel);
        playersTable.setRowSorter(sorter);
        
        final var tableScrollPane = new JScrollPane(playersTable);
        tableScrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        add(topPanel, BorderLayout.NORTH);
        add(tableScrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    /**
     * Returns the text for the info label at the top of the window.
     * Subclasses should override this to provide their specific text.
     */
    protected abstract String getInfoLabelText();

    /**
     * Sets the text of the info label with the current one.
     * This can be called to update the label after changes in the data.
     */
    protected void refreshInfoLabel() {
        this.infoLabel.setText(getInfoLabelText());
    }
    
    /**
     * Returns the column names for the players table.
     * Subclasses can override this to customize the columns.
     */
    protected String[] getTableColumns() {
        return new String[]{"ID", "Cognome", "Nome", "Data Nascita", "Sesso", "Email", "Telefono",
                "Classifica", "Circolo"};
    }

    protected void configureTableSelection() {
        // Default: do nothing
    }

    protected void addButtonToPanel(final JButton button) {
        this.buttonPanel.add(button);
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
    
    public void display() {
        setVisible(true);
    }
}
