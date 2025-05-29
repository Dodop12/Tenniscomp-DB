package tenniscomp.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import tenniscomp.data.Court;
import tenniscomp.data.Player;
import tenniscomp.utils.MatchType;

public class AddLeagueMatchWindow extends JDialog {

    private static final String[] WINNER_OPTIONS = {"Casa", "Trasferta"};

    private final JComboBox<String> typeComboBox;
    private final JComboBox<Player> homePlayer1ComboBox;
    private final JComboBox<Player> homePlayer2ComboBox;
    private final JComboBox<Player> awayPlayer1ComboBox;
    private final JComboBox<Player> awayPlayer2ComboBox;
    private final JComboBox<Court> courtComboBox;
    private final JComboBox<String> winnerComboBox;
    private final JTextField resultField;
    private final JButton saveButton;
    private final JButton cancelButton;

    public AddLeagueMatchWindow(final JDialog parent, final List<Player> homeTeamPlayers, 
            final List<Player> awayTeamPlayers, final List<Court> courts) {
        super(parent, "Aggiungi Partita Campionato", true);
        setSize(450, 450);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(parent);

        final var mainPanel = new JPanel(new GridLayout(8, 2, 10, 10));
        mainPanel.setBorder(new EmptyBorder(20, 20, 10, 20));

        // Match type selector
        mainPanel.add(new JLabel("Tipo:"));
        final var types = MatchType.getAllLabels();
        this.typeComboBox = new JComboBox<>(types.toArray(new String[0]));
        this.typeComboBox.addActionListener(e -> updatePlayerFieldsVisibility());
        mainPanel.add(this.typeComboBox);

        // Home team players
        mainPanel.add(new JLabel("Giocatore Casa 1:"));
        this.homePlayer1ComboBox = new JComboBox<>();
        for (final var player : homeTeamPlayers) {
            this.homePlayer1ComboBox.addItem(player);
        }
        mainPanel.add(this.homePlayer1ComboBox);

        mainPanel.add(new JLabel("Giocatore Casa 2:"));
        this.homePlayer2ComboBox = new JComboBox<>();
        this.homePlayer2ComboBox.addItem(null); // Allow null for singles
        for (final var player : homeTeamPlayers) {
            this.homePlayer2ComboBox.addItem(player);
        }
        mainPanel.add(this.homePlayer2ComboBox);

        // Away team players
        mainPanel.add(new JLabel("Giocatore Ospite 1:"));
        this.awayPlayer1ComboBox = new JComboBox<>();
        for (final var player : awayTeamPlayers) {
            this.awayPlayer1ComboBox.addItem(player);
        }
        mainPanel.add(this.awayPlayer1ComboBox);

        mainPanel.add(new JLabel("Giocatore Ospite 2:"));
        this.awayPlayer2ComboBox = new JComboBox<>();
        this.awayPlayer2ComboBox.addItem(null); // Allow null for singles
        for (final var player : awayTeamPlayers) {
            this.awayPlayer2ComboBox.addItem(player);
        }
        mainPanel.add(this.awayPlayer2ComboBox);

        // Court selector
        mainPanel.add(new JLabel("Campo:"));
        this.courtComboBox = new JComboBox<>();
        for (final var court : courts) {
            this.courtComboBox.addItem(court);
        }
        mainPanel.add(this.courtComboBox);

        // Winner selector
        mainPanel.add(new JLabel("Vincitore:"));
        this.winnerComboBox = new JComboBox<>(WINNER_OPTIONS);
        mainPanel.add(this.winnerComboBox);

        // Result field
        mainPanel.add(new JLabel("Risultato:"));
        this.resultField = new JTextField(15);
        mainPanel.add(this.resultField);

        final var buttonsPanel = new JPanel(new FlowLayout());
        buttonsPanel.setBorder(new EmptyBorder(10, 20, 20, 20));
        this.saveButton = new JButton("Salva");
        this.cancelButton = new JButton("Annulla");
        buttonsPanel.add(this.saveButton);
        buttonsPanel.add(this.cancelButton);

        add(mainPanel, BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.SOUTH);

        updatePlayerFieldsVisibility();
    }

    private void updatePlayerFieldsVisibility() {
        final boolean isDoubles = MatchType.DOPPIO.getLabel().equals(this.typeComboBox.getSelectedItem());
        this.homePlayer2ComboBox.setEnabled(isDoubles);
        this.awayPlayer2ComboBox.setEnabled(isDoubles);
        
        if (!isDoubles) {
            this.homePlayer2ComboBox.setSelectedItem(null);
            this.awayPlayer2ComboBox.setSelectedItem(null);
        }
    }

    public String getMatchType() {
        return (String) this.typeComboBox.getSelectedItem();
    }

    public Player getHomePlayer1() {
        return (Player) this.homePlayer1ComboBox.getSelectedItem();
    }

    public Player getHomePlayer2() {
        return (Player) this.homePlayer2ComboBox.getSelectedItem();
    }

    public Player getAwayPlayer1() {
        return (Player) this.awayPlayer1ComboBox.getSelectedItem();
    }

    public Player getAwayPlayer2() {
        return (Player) this.awayPlayer2ComboBox.getSelectedItem();
    }

    public String getWinner() {
        return (String) this.winnerComboBox.getSelectedItem();
    }

    public Court getCourt() {
        return (Court) this.courtComboBox.getSelectedItem();
    }

    public String getResult() {
        return this.resultField.getText().trim();
    }

    public void setSaveButtonListener(final ActionListener listener) {
        this.saveButton.addActionListener(listener);
    }

    public void setCancelButtonListener(final ActionListener listener) {
        this.cancelButton.addActionListener(listener);
    }
}
