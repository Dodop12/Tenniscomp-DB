package tenniscomp.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PlayerDashboard extends JFrame {

    private static final String CARD_TITLE = "Tessera Giocatore";
    private static final String CARD_NUMBER_TEXT = "Numero tessera: ";
    private static final String EXP_DATE_TEXT = "Data di scadenza: ";

    private final JLabel nameLabel;
    private final JLabel rankingLabel;
    private final JLabel categoryLabel;

    private final JLabel cardNumberLabel;
    private final JLabel cardExpiryDateLabel;

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
        this.nameLabel.setFont(new Font("Arial", Font.BOLD, 22));
        
        this.rankingLabel = new JLabel("Classifica");
        this.rankingLabel.setFont(new Font("Arial", Font.PLAIN, 16));

        this.categoryLabel = new JLabel("Categoria");
        this.categoryLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        
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
        
        // TODO: Tournaments panel
        
        // TODO: Matches panel
        
        // TODO: Bottom stats panel
        
        add(topPanel, BorderLayout.NORTH);
        
        setLocationRelativeTo(null);
        setVisible(true);
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
