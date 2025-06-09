package tenniscomp.view.tournament;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;

import tenniscomp.data.Court;
import tenniscomp.data.Player;
import tenniscomp.data.Umpire;
import tenniscomp.utils.MatchType;
import tenniscomp.utils.Pair;
import tenniscomp.utils.CommonUtils;

public class AddTournamentMatchWindow extends JDialog {

    private static final int SINGLES_ROWS = 7;
    private static final int DOUBLES_ROWS = 9;

    private final DatePicker datePicker;
    private final JComboBox<String> typeComboBox;
    
    // Player components
    private final JComboBox<Player> player1ComboBox;
    private final JComboBox<Player> player2ComboBox;
    private final JComboBox<Player> opponent1ComboBox;
    private final JComboBox<Player> opponent2ComboBox;
    
    // Labels for player components
    private final JLabel player1Label;
    private final JLabel player2Label;
    private final JLabel opponent1Label;
    private final JLabel opponent2Label;
    
    private final JComboBox<Court> courtComboBox;
    private final JComboBox<Umpire> umpireComboBox;
    private final JTextField resultField;

    private final JButton saveButton;
    private final JButton cancelButton;
    private final JPanel mainPanel;

    public AddTournamentMatchWindow(final JFrame parent, final List<Player> players,
            final List<Court> courts, final List<Umpire> umpires) {
        super(parent, "Aggiungi Partita", true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(parent);

        this.mainPanel = new JPanel();
        this.mainPanel.setBorder(new EmptyBorder(20, 20, 10, 20));

        // Date picker
        final var dateSettings = getDatePickerSettings();
        this.datePicker = new DatePicker(dateSettings);
        this.datePicker.setDate(LocalDate.now());

        // Match type selector
        final var types = MatchType.getAllLabels();
        this.typeComboBox = new JComboBox<>(types.toArray(new String[0]));

        this.player1ComboBox = createPlayerComboBox(players);
        this.player2ComboBox = createPlayerComboBox(players);
        this.opponent1ComboBox = createPlayerComboBox(players);
        this.opponent2ComboBox = createPlayerComboBox(players);

        this.player1Label = new JLabel("Vincitore:");
        this.player2Label = new JLabel("Vincitore 2:");
        this.opponent1Label = new JLabel("Avversario:");
        this.opponent2Label = new JLabel("Avversario 2:");

        this.courtComboBox = createComboBox(courts);
        this.umpireComboBox = createComboBoxWithNull(umpires);
        this.resultField = new JTextField(15);

        this.saveButton = new JButton("Salva");
        this.cancelButton = new JButton("Annulla");

        final var buttonsPanel = createButtonsPanel();
        add(this.mainPanel, BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.SOUTH);
        
        // Initialize with singles layout by default
        updateLayout(MatchType.SINGOLARE);

        pack();
        setLocationRelativeTo(parent);
    }

    private void updateLayout(final MatchType type) {
        this.mainPanel.removeAll();
        final boolean isDoubles = type == MatchType.DOPPIO;
        final int rows = isDoubles ? DOUBLES_ROWS : SINGLES_ROWS;
        this.mainPanel.setLayout(new GridLayout(rows, 2, 10, 10));

        player1Label.setText(isDoubles ? "Vincitore 1:" : "Vincitore:");
        opponent1Label.setText(isDoubles ? "Avversario 1:" : "Avversario:");

        addRow("Data:", this.datePicker);
        addRow("Tipo:", typeComboBox);

        addRow(player1Label, player1ComboBox);
        if (isDoubles) {
            addRow(player2Label, player2ComboBox);
        }
        addRow(opponent1Label, opponent1ComboBox);
        if (isDoubles) {
            addRow(opponent2Label, opponent2ComboBox);
        }

        addRow("Campo:", courtComboBox);
        addRow("Arbitro:", umpireComboBox);
        addRow("Risultato:", resultField);

        this.mainPanel.revalidate();
        this.mainPanel.repaint();
        pack();
    }

    private void addRow(final String label, final JComponent component) {
        mainPanel.add(new JLabel(label));
        mainPanel.add(component);
    }

    private void addRow(final JLabel label, final JComponent component) {
        mainPanel.add(label);
        mainPanel.add(component);
    }

    private <T> JComboBox<T> createComboBox(final List<T> items) {
        final JComboBox<T> comboBox = new JComboBox<>();
        for (final T item : items) comboBox.addItem(item);
        return comboBox;
    }

    private <T> JComboBox<T> createComboBoxWithNull(final List<T> items) {
        final JComboBox<T> comboBox = new JComboBox<>();
        comboBox.addItem(null);
        for (final T item : items) comboBox.addItem(item);
        return comboBox;
    }

    private JComboBox<Player> createPlayerComboBox(final List<Player> players) {
        return createComboBox(players);
    }

    private JPanel createButtonsPanel() {
        final var panel = new JPanel(new FlowLayout());
        panel.setBorder(new EmptyBorder(10, 20, 20, 20));
        panel.add(this.saveButton);
        panel.add(this.cancelButton);
        return panel;
    }

    public String getMatchDate() {
        final var selectedDate = this.datePicker.getDate();
        return selectedDate != null ? selectedDate.format(CommonUtils.getYmdDateFormatter()) : "";
    }

    // Singles getters
    public Player getWinner() {
        return (Player) this.player1ComboBox.getSelectedItem();
    }

    public Player getOpponent() {
        return (Player) this.opponent1ComboBox.getSelectedItem();
    }
    
    // Doubles getters
    public Pair<Player, Player> getWinnerPair() {
        final var winner1 = (Player) this.player1ComboBox.getSelectedItem();
        final var winner2 = (Player) this.player2ComboBox.getSelectedItem();
        if (winner1 != null && winner2 != null) {
            return new Pair<>(winner1, winner2);
        }
        return null;
    }
    
    public Pair<Player, Player> getOpponentPair() {
        final var opponent1 = (Player) this.opponent1ComboBox.getSelectedItem();
        final var opponent2 = (Player) this.opponent2ComboBox.getSelectedItem();
        if (opponent1 != null && opponent2 != null) {
            return new Pair<>(opponent1, opponent2);
        }
        return null;
    }

    public Court getCourt() {
        return (Court) this.courtComboBox.getSelectedItem();
    }

    public Umpire getUmpire() {
        return (Umpire) umpireComboBox.getSelectedItem();
    }

    public String getResult() {
        return this.resultField.getText().trim();
    }
    
    public void setTournamentType(final MatchType type) {
        setMatchTypeField(type.getLabel());
        setMatchTypeEditable(false);
        updateLayout(type);
    }

    public void setDate(final LocalDate date) {
        this.datePicker.setDate(date);
    }

    public void setDateEditable(final boolean editable) {
        this.datePicker.setEnabled(editable);
    }

    public void setSaveButtonListener(final ActionListener listener) {
        this.saveButton.addActionListener(listener);
    }

    public void setCancelButtonListener(final ActionListener listener) {
        this.cancelButton.addActionListener(listener);
    }

    private void setMatchTypeField(final String type) {
        this.typeComboBox.setSelectedItem(type);
    }

    private void setMatchTypeEditable(final boolean editable) {
        this.typeComboBox.setEnabled(editable);
    }

    private DatePickerSettings getDatePickerSettings() {
        final var settings = new DatePickerSettings();
        settings.setFormatForDatesCommonEra("dd/MM/yyyy");
        return settings;
    }
}