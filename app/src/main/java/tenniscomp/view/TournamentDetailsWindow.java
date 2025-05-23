package tenniscomp.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JSplitPane;
import javax.swing.SwingConstants;

import tenniscomp.utils.ImmutableTableModel;
import tenniscomp.utils.PlayerUtils;

public class TournamentDetailsWindow extends JFrame {

    private static final String FONT_STYLE = "Arial";
    private static final String REGISTRATIONS_TITLE = "Iscrizioni";
    private static final String MATCHES_TITLE = "Partite";

    private final JLabel tournamentNameLabel;
    private final JLabel datesLabel;
    private final JLabel rankingLimitLabel;
    private final JLabel clubLabel;
    private final JLabel prizeMoneyLabel;
    
    private final JTable registrationsTable;
    private final JTable matchesTable;
    
    private final JButton addMatchButton;
    private final JButton closeButton;

    public TournamentDetailsWindow(final JFrame parent) {
        setTitle("Dettagli Torneo");
        setSize(1000, 700);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        setLocationRelativeTo(parent);
        
        final var infoPanel = new JPanel(new GridLayout(5, 1, 5, 5));
        infoPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(10, 10, 10, 10),
            BorderFactory.createTitledBorder("Informazioni Torneo")
        ));
        
        this.tournamentNameLabel = new JLabel("Nome Torneo");
        this.tournamentNameLabel.setFont(new Font(FONT_STYLE, Font.BOLD, 18));
        this.tournamentNameLabel.setHorizontalAlignment(SwingConstants.LEFT);
        
        this.datesLabel = new JLabel();
        this.datesLabel.setFont(new Font(FONT_STYLE, Font.PLAIN, 14));
        
        this.rankingLimitLabel = new JLabel("Limite Classifica: ");
        this.rankingLimitLabel.setFont(new Font(FONT_STYLE, Font.PLAIN, 14));
        
        this.clubLabel = new JLabel("Circolo: ");
        this.clubLabel.setFont(new Font(FONT_STYLE, Font.PLAIN, 14));
        
        this.prizeMoneyLabel = new JLabel("Montepremi: ");
        this.prizeMoneyLabel.setFont(new Font(FONT_STYLE, Font.PLAIN, 14));
        
        infoPanel.add(this.tournamentNameLabel);
        infoPanel.add(this.datesLabel);
        infoPanel.add(this.rankingLimitLabel);
        infoPanel.add(this.clubLabel);
        infoPanel.add(this.prizeMoneyLabel);
        
        final var tablesPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        tablesPanel.setDividerLocation(450);
        
        final var registrationsPanel = new JPanel(new BorderLayout());
        final String[] registrationColumns = {"ID", "Giocatore", "Classifica", "Data iscrizione"};
        final var registrationsModel = new ImmutableTableModel(registrationColumns, 0);
        this.registrationsTable = new JTable(registrationsModel);
        
        final var registrationsScrollPane = new JScrollPane(registrationsTable);
        registrationsScrollPane.setBorder(BorderFactory.createTitledBorder(REGISTRATIONS_TITLE));
        registrationsPanel.add(registrationsScrollPane, BorderLayout.CENTER);
        
        final var matchesPanel = new JPanel(new BorderLayout());
        final String[] matchesColumns = {"ID", "Vincitore", "Avversario", "Risultato", "Campo"};
        final var matchesModel = new ImmutableTableModel(matchesColumns, 0);
        this.matchesTable = new JTable(matchesModel);

        final var matchesButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        this.addMatchButton = new JButton("Aggiungi Partita");
        matchesButtonPanel.add(this.addMatchButton);
        
        final var matchesScrollPane = new JScrollPane(matchesTable);
        matchesScrollPane.setBorder(BorderFactory.createTitledBorder(MATCHES_TITLE));

        matchesPanel.add(matchesButtonPanel, BorderLayout.NORTH);
        matchesPanel.add(matchesScrollPane, BorderLayout.CENTER);
        
        tablesPanel.setLeftComponent(registrationsPanel);
        tablesPanel.setRightComponent(matchesPanel);
        
        final var buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        this.closeButton = new JButton("Chiudi");
        buttonsPanel.add(this.closeButton);

        final var mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(matchesButtonPanel, BorderLayout.NORTH);
        mainPanel.add(tablesPanel, BorderLayout.CENTER);
        
        add(infoPanel, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.SOUTH);
    }
    
    public void setTournamentName(final String name) {
        this.tournamentNameLabel.setText(name);
    }
    
    public void setDates(final String startDate, final String endDate, final String registrationDeadline) {
        final var formattedStartDate = PlayerUtils.convertDateFormat(startDate);
        final var formattedEndDate = PlayerUtils.convertDateFormat(endDate);
        final var formattedDeadline = PlayerUtils.convertDateFormat(registrationDeadline);
        
        this.datesLabel.setText("dal " + formattedStartDate + " al " + formattedEndDate + 
                " (Scadenza iscrizioni: " + formattedDeadline + ")");
    }
    
    public void setRankingLimit(final String rankingLimit) {
        this.rankingLimitLabel.setText("Limite Classifica: " + rankingLimit);
    }
    
    public void setClub(final String clubName) {
        this.clubLabel.setText("Circolo: " + clubName);
    }
    
    public void setPrizeMoney(final double prizeMoney) {
        this.prizeMoneyLabel.setText("Montepremi: € " + String.valueOf(Math.round(prizeMoney)));
    }
    
    public JTable getRegistrationsTable() {
        return registrationsTable;
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
