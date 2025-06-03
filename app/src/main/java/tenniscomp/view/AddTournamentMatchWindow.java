package tenniscomp.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
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
import tenniscomp.utils.CommonUtils;

public class AddTournamentMatchWindow extends JDialog {

    private final DatePicker datePicker;
    private final JComboBox<String> typeComboBox;
    private final JComboBox<Player> winnerComboBox;
    private final JComboBox<Player> opponentComboBox;
    private final JComboBox<Court> courtComboBox;
    private final JComboBox<Umpire> umpireComboBox;
    private final JTextField resultField;
    private final JButton saveButton;
    private final JButton cancelButton;

    public AddTournamentMatchWindow(final JFrame parent, final List<Player> registeredPlayers,
            final List<Court> courts, final List<Umpire> umpires) {
        super(parent, "Aggiungi Partita", true);
        setSize(450, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(parent);

        final var mainPanel = new JPanel(new GridLayout(7, 2, 10, 10));
        mainPanel.setBorder(new EmptyBorder(20, 20, 10, 20));

        // Date picker
        mainPanel.add(new JLabel("Data:"));
        final var dateSettings = getDatePickerSettings();
        this.datePicker = new DatePicker(dateSettings);
        this.datePicker.setDate(LocalDate.now());
        mainPanel.add(this.datePicker);

        // Match type selector
        mainPanel.add(new JLabel("Tipo:"));
        final var types = MatchType.getAllLabels();
        this.typeComboBox = new JComboBox<>(types.toArray(new String[0]));
        mainPanel.add(this.typeComboBox);

        // Winner selector
        mainPanel.add(new JLabel("Vincitore:"));
        this.winnerComboBox = new JComboBox<>();
        for (final var player : registeredPlayers) {
            this.winnerComboBox.addItem(player);
        }
        mainPanel.add(this.winnerComboBox);

        // Opponent selector  
        mainPanel.add(new JLabel("Avversario:"));
        this.opponentComboBox = new JComboBox<>();
        for (final var player : registeredPlayers) {
            this.opponentComboBox.addItem(player);
        }
        mainPanel.add(this.opponentComboBox);

        // Court selector
        mainPanel.add(new JLabel("Campo:"));
        this.courtComboBox = new JComboBox<>();
        for (final var court : courts) {
            this.courtComboBox.addItem(court);
        }
        mainPanel.add(this.courtComboBox);

        // Umpire selector
        mainPanel.add(new JLabel("Arbitro:"));
        this.umpireComboBox = new JComboBox<>();
        this.umpireComboBox.addItem(null); // null as default option (no umpire selected)
        for (final var umpire : umpires) {
            this.umpireComboBox.addItem(umpire);
        }
        mainPanel.add(this.umpireComboBox);

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
    }

    public String getMatchDate() {
        final var selectedDate = this.datePicker.getDate();
        return selectedDate != null ? selectedDate.format(CommonUtils.getYmdDateFormatter()) : "";
    }

    public Player getWinner() {
        return (Player) this.winnerComboBox.getSelectedItem();
    }

    public Player getOpponent() {
        return (Player) this.opponentComboBox.getSelectedItem();
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

    public void setMatchType(final String type) {
        this.typeComboBox.setSelectedItem(type);
    }

    public void setMatchTypeEditable(final boolean editable) {
        this.typeComboBox.setEnabled(editable);
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

    private DatePickerSettings getDatePickerSettings() {
        final var settings = new DatePickerSettings();
        settings.setFormatForDatesCommonEra("dd/MM/yyyy");
        return settings;
    }
}
