package tenniscomp.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import tenniscomp.utils.ImmutableTableModel;

public class TieDetailsWindow extends JDialog {

    private static final String FONT_STYLE = "Arial";
    private static final String MATCHES_TITLE = "Partite dell'incontro";

    private final JLabel tieInfoLabel;
    private final JTable matchesTable;
    private final JButton addMatchButton;
    private final JButton closeButton;

    public TieDetailsWindow(final JFrame parent) {
        super(parent, "Dettagli Incontro", true);
        setSize(800, 500);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        setLocationRelativeTo(parent);
        
        final var infoPanel = new JPanel(new GridLayout(1, 1, 5, 5));
        infoPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(10, 10, 10, 10),
            BorderFactory.createTitledBorder("Informazioni Incontro")
        ));
        
        this.tieInfoLabel = new JLabel();
        this.tieInfoLabel.setFont(new Font(FONT_STYLE, Font.BOLD, 16));
        this.tieInfoLabel.setHorizontalAlignment(SwingConstants.LEFT);
        
        infoPanel.add(this.tieInfoLabel);
        
        final var matchesPanel = new JPanel(new BorderLayout());
        final String[] matchesColumns = {"ID", "Tipo", "Vincitore", "Circolo",
            "Avversario", "Risultato", "Campo", "Arbitro"};
        final var matchesModel = new ImmutableTableModel(matchesColumns, 0);
        this.matchesTable = new JTable(matchesModel);

        final var matchesButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        this.addMatchButton = new JButton("Aggiungi Partita");
        matchesButtonPanel.add(this.addMatchButton);
        
        final var matchesScrollPane = new JScrollPane(matchesTable);
        matchesScrollPane.setBorder(BorderFactory.createTitledBorder(MATCHES_TITLE));

        matchesPanel.add(matchesButtonPanel, BorderLayout.NORTH);
        matchesPanel.add(matchesScrollPane, BorderLayout.CENTER);
        
        final var lowerButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        this.closeButton = new JButton("Chiudi");
        lowerButtonPanel.add(this.closeButton);

        add(infoPanel, BorderLayout.NORTH);
        add(matchesPanel, BorderLayout.CENTER);
        add(lowerButtonPanel, BorderLayout.SOUTH);
    }
    
    public void setTieInfo(final String homeTeam, final String awayTeam, final String date, final String result) {
        this.tieInfoLabel.setText(homeTeam + " vs " + awayTeam + " - " + date + " (" + result + ")");
    }
    
    public JTable getMatchesTable() {
        return matchesTable;
    }

    public void setAddMatchListener(final ActionListener listener) {
        this.addMatchButton.addActionListener(listener);
    }
    
    public void setCloseButtonListener(final ActionListener listener) {
        this.closeButton.addActionListener(listener);
    }
    
    public void display() {
        setVisible(true);
    }
}
