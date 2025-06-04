package tenniscomp.view.player;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
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
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import tenniscomp.utils.ImmutableTableModel;

public class PlayerDashboard extends JFrame {

    private static final String FONT_STYLE = "Arial";
    private static final String CARD_TITLE = "Tessera Giocatore";
    private static final String CARD_NUMBER_TEXT = "Numero tessera";
    private static final String EXP_DATE_TEXT = "Data di scadenza";
    private static final String TOURNAMENTS_TITLE = "Tornei";
    private static final String MATCHES_TITLE = "Partite (singolare)";
    private static final String STATS_TITLE = "Statistiche";
    private static final String MATCHES_PLAYED_STAT = "Partite giocate";
    private static final String MATCHES_WON_STAT = "Partite vinte";
    private static final String WIN_RATE_STAT = "Win rate";

    private final JLabel nameLabel;
    private final JLabel rankingLabel;
    private final JLabel categoryLabel;
    private final JLabel cardNumberLabel;
    private final JLabel cardExpiryDateLabel;

    private final DefaultTableModel tournamentsModel;
    private final DefaultTableModel matchesModel;
    private final JTable tournamentsTable;
    private final JTable matchesTable;
    private final JPanel statsPanel;

    private final JLabel totalMatchesLabel;
    private final JLabel matchesWonLabel;
    private final JLabel winRateLabel;

    private final JButton logoutButton;

    public PlayerDashboard() {
        setTitle("TennisComp - Giocatore");
        setSize(1000, 750);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Main info panel
        final var playerInfoPanel = new JPanel();
        playerInfoPanel.setLayout(new GridLayout(3, 1));
        playerInfoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        this.nameLabel = new JLabel("Nome");
        this.nameLabel.setFont(new Font(FONT_STYLE, Font.BOLD, 22));
        
        this.rankingLabel = new JLabel("Classifica");
        this.rankingLabel.setFont(new Font(FONT_STYLE, Font.PLAIN, 16));

        this.categoryLabel = new JLabel("Categoria");
        this.categoryLabel.setFont(new Font(FONT_STYLE, Font.PLAIN, 16));
        
        playerInfoPanel.add(this.nameLabel);
        playerInfoPanel.add(this.rankingLabel);
        playerInfoPanel.add(this.categoryLabel);
        
        // Card info panel
        final var cardPanel = new JPanel();
        cardPanel.setLayout(new GridLayout(2, 1));
        cardPanel.setBorder(BorderFactory.createTitledBorder(CARD_TITLE));
        cardPanel.setPreferredSize(new Dimension(300, 100));
        
        this.cardNumberLabel = new JLabel(CARD_NUMBER_TEXT + ":");
        this.cardExpiryDateLabel = new JLabel(EXP_DATE_TEXT + ":");
        
        cardPanel.add(this.cardNumberLabel);
        cardPanel.add(this.cardExpiryDateLabel);
        
        // Top panel containing card and main player info
        final JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(playerInfoPanel, BorderLayout.WEST);
        topPanel.add(cardPanel, BorderLayout.EAST);
        
        // Tournaments panel
        final String[] tournamentColumns = {"ID", "Nome", "Data inizio", "Data fine",
                "Scadenza iscrizioni", "Tipo", "Sesso", "Limite Classifica"};
        this.tournamentsModel = new ImmutableTableModel(tournamentColumns, 0);
        this.tournamentsTable = new JTable(tournamentsModel);
        final var tournamentsScrollPane = new JScrollPane(tournamentsTable);
        tournamentsScrollPane.setBorder(BorderFactory.createTitledBorder(TOURNAMENTS_TITLE));
        
        // Matches panel
        final String[] matchColumns = {"ID", "Competizione", "Data", "Avversario", "Risultato", "Punteggio"};
        this.matchesModel = new ImmutableTableModel(matchColumns, 0);
        this.matchesTable = new JTable(matchesModel);
        final var matchesScrollPane = new JScrollPane(matchesTable);
        matchesScrollPane.setBorder(BorderFactory.createTitledBorder(MATCHES_TITLE));

        final var centerPanel = new JPanel(new GridLayout(2, 1, 0, 10));
        centerPanel.add(tournamentsScrollPane);
        centerPanel.add(matchesScrollPane);
        
        // Bottom stats panel
        statsPanel = new JPanel(new GridLayout(1, 4, 10, 0));
        statsPanel.setBorder(BorderFactory.createTitledBorder(STATS_TITLE));

        this.totalMatchesLabel = new JLabel("0", SwingConstants.CENTER);
        this.matchesWonLabel = new JLabel("0", SwingConstants.CENTER);
        this.winRateLabel = new JLabel("0%", SwingConstants.CENTER);
        
        final var matchesPlayedPanel = createStatPanel(this.totalMatchesLabel, MATCHES_PLAYED_STAT);
        final var matchesWonPanel = createStatPanel(this.matchesWonLabel, MATCHES_WON_STAT);
        final var winRatePanel = createStatPanel(this.winRateLabel, WIN_RATE_STAT);
        
        this.statsPanel.add(matchesPlayedPanel);
        this.statsPanel.add(matchesWonPanel);
        this.statsPanel.add(winRatePanel);

        final var logoutPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        logoutPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        this.logoutButton = new JButton("Logout");
        logoutPanel.add(this.logoutButton);

        final var bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(statsPanel, BorderLayout.CENTER);
        bottomPanel.add(logoutPanel, BorderLayout.SOUTH);
        
        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
        
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public JTable getTournamentsTable() {
        return tournamentsTable;
    }
    
    public JTable getMatchesTable() {
        return matchesTable;
    }
    
    public void setPlayerName(final String name) {
        nameLabel.setText(name);
    }
    
    public void setPlayerRanking(final String ranking) {
        rankingLabel.setText(ranking);
    }

    public void setPlayerCategory(final String category) {
        categoryLabel.setText(category);
    }
    
    public void setCardInfo(final String cardNumber, final String expiryDate) {
        cardNumberLabel.setText(CARD_NUMBER_TEXT + ":  " + cardNumber);
        cardExpiryDateLabel.setText(EXP_DATE_TEXT + ":  " + expiryDate);
    }

    public void setMatchStats(final int totalMatches, final int matchesWon,
            final int winRate, final int tournamentTitles) {
        this.totalMatchesLabel.setText(String.valueOf(totalMatches));
        this.matchesWonLabel.setText(String.valueOf(matchesWon));
        this.winRateLabel.setText(String.valueOf(winRate) + "%");
    }

    public void setLogoutListener(final ActionListener listener) {
        this.logoutButton.addActionListener(listener);
    }
    
    private JPanel createStatPanel(JLabel statLabel, final String title) {
        final var panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        
        final var titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font(FONT_STYLE, Font.PLAIN, 14));
        
        statLabel.setFont(new Font(FONT_STYLE, Font.BOLD, 24));
        
        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(statLabel, BorderLayout.CENTER);
        
        return panel;
    }
}
