package tenniscomp.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
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
    private static final String CARD_NUMBER_TEXT = "Numero tessera: ";
    private static final String EXP_DATE_TEXT = "Data di scadenza: ";
    private static final String TOURNAMENTS_TITLE = "Tornei";
    private static final String MATCHES_TITLE = "Partite";
    private static final String STATS_TITLE = "Statistiche";
    private static final String MATCHES_PLAYED_STAT = "Partite giocate";
    private static final String MATCHES_WON_STAT = "Partite vinte";
    private static final String WIN_RATE_STAT = "Win rate";
    private static final String TOURNAMENTS_WON_STAT = "Tornei vinti";

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

    public PlayerDashboard() {
        setTitle("TennisComp - Player Dashboard");
        setSize(1000, 700);
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
        
        this.cardNumberLabel = new JLabel(CARD_NUMBER_TEXT);
        this.cardExpiryDateLabel = new JLabel(EXP_DATE_TEXT);
        
        cardPanel.add(this.cardNumberLabel);
        cardPanel.add(this.cardExpiryDateLabel);
        
        // Top panel containing card and main player info
        final JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(playerInfoPanel, BorderLayout.WEST);
        topPanel.add(cardPanel, BorderLayout.EAST);
        
        // Tournaments panel
        final String[] tournamentColumns = {"ID", "Nome", "Data inizio", "Data fine", "Stato", "Categoria"};
        this.tournamentsModel = new ImmutableTableModel(tournamentColumns, 0);
        this.tournamentsTable = new JTable(tournamentsModel);
        final var tournamentsScrollPane = new JScrollPane(tournamentsTable);
        tournamentsScrollPane.setBorder(BorderFactory.createTitledBorder(TOURNAMENTS_TITLE));
        
        // Matches panel
        final String[] matchColumns = {"ID", "Torneo", "Data", "Avversario", "Risultato", "Punteggio"};
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
        
        // TODO: calculate data
        final var matchesPlayedPanel = createStatPanel(MATCHES_PLAYED_STAT, "0");
        final var matchesWonPanel = createStatPanel(MATCHES_WON_STAT, "0");
        final var winRatePanel = createStatPanel(WIN_RATE_STAT, "0%");
        final var tournamentWinsPanel = createStatPanel(TOURNAMENTS_WON_STAT, "0");
        
        this.statsPanel.add(matchesPlayedPanel);
        this.statsPanel.add(matchesWonPanel);
        this.statsPanel.add(winRatePanel);
        this.statsPanel.add(tournamentWinsPanel);
        
        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(statsPanel, BorderLayout.SOUTH);
        
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel createStatPanel(final String title, final String value) {
        final var panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        
        final var titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font(FONT_STYLE, Font.PLAIN, 14));
        
        final var valueLabel = new JLabel(value, SwingConstants.CENTER);
        valueLabel.setFont(new Font(FONT_STYLE, Font.BOLD, 24));
        
        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(valueLabel, BorderLayout.CENTER);
        
        return panel;
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
    
    public void setCardInfo(final String cardId, final String membership, final String expiryDate) {
        cardNumberLabel.setText(CARD_NUMBER_TEXT + cardId);
        cardExpiryDateLabel.setText(EXP_DATE_TEXT + expiryDate);
    }
}
