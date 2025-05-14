package tenniscomp.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;

import tenniscomp.data.Club;
import tenniscomp.utils.PlayerUtils;

public class AddTournamentWindow extends JDialog {

    private final JTextField nameField;
    private final DatePicker startDatePicker;
    private final DatePicker endDatePicker;
    private final DatePicker registrationDeadlinePicker;
    private final JComboBox<String> typeComboBox;
    private final JComboBox<String> rankingLimitComboBox;
    private final JTextField prizeMoneyField;
    private final ClubSelector clubSelector;
    
    private final JButton saveButton;
    private final JButton cancelButton;

    public AddTournamentWindow(final JFrame parent, final List<Club> clubs) {
        super(parent, "Aggiungi Torneo", true);
        setSize(800, 500);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout(10, 10));

        final var formPanel = new JPanel(new GridLayout(8, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        formPanel.add(new JLabel("Nome:"));
        this.nameField = new JTextField();
        formPanel.add(this.nameField);

        formPanel.add(new JLabel("Data inizio:"));
        this.startDatePicker = new DatePicker(getDatePickerSettings());
        formPanel.add(this.startDatePicker);

        formPanel.add(new JLabel("Data fine:"));
        this.endDatePicker = new DatePicker(getDatePickerSettings());
        formPanel.add(this.endDatePicker);

        formPanel.add(new JLabel("Scadenza iscrizioni:"));
        this.registrationDeadlinePicker = new DatePicker(getDatePickerSettings());
        formPanel.add(this.registrationDeadlinePicker);

        // Singles or doubles
        formPanel.add(new JLabel("Tipo:"));
        final var types = PlayerUtils.getMatchTypes();
        this.typeComboBox = new JComboBox<>(types.toArray(new String[0]));
        formPanel.add(this.typeComboBox);

        formPanel.add(new JLabel("Limite classifica:"));
        this.rankingLimitComboBox = new JComboBox<>(PlayerUtils.getAllRankings().toArray(new String[0]));
        formPanel.add(this.rankingLimitComboBox);

        formPanel.add(new JLabel("Montepremi (€):"));
        this.prizeMoneyField = new JTextField();
        formPanel.add(this.prizeMoneyField);

        formPanel.add(new JLabel("Circolo:"));
        this.clubSelector = new ClubSelector(clubs);
        formPanel.add(this.clubSelector);

        add(formPanel, BorderLayout.CENTER);

        final var buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        this.saveButton = new JButton("Salva");
        this.cancelButton = new JButton("Annulla");
        
        buttonsPanel.add(this.saveButton);
        buttonsPanel.add(this.cancelButton);
        
        add(buttonsPanel, BorderLayout.SOUTH);
    }

    public String getTournamentName() {
        return this.nameField.getText().trim();
    }

   public String getStartDate() {
        final LocalDate selectedDate = this.startDatePicker.getDate();
        if (selectedDate != null) {
            return selectedDate.format(PlayerUtils.getYmdDateFormatter());
        }
        return "";
    }

    public String getEndDate() {
        final LocalDate selectedDate = this.endDatePicker.getDate();
        if (selectedDate != null) {
            return selectedDate.format(PlayerUtils.getYmdDateFormatter());
        }
        return "";
    }

    public String getRegistrationDeadline() {
        final LocalDate selectedDate = this.registrationDeadlinePicker.getDate();
        if (selectedDate != null) {
            return selectedDate.format(PlayerUtils.getYmdDateFormatter());
        }
        return "";
    }

    public String getTournamentType() {
        return (String) this.typeComboBox.getSelectedItem();
    }

    public String getRankingLimit() {
        return (String) this.rankingLimitComboBox.getSelectedItem();
    }

    public String getPrizeMoney() {
        return this.prizeMoneyField.getText().trim();
    }

    public Club getSelectedClub() {
        return this.clubSelector.getSelectedClub();
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
        settings.setAllowEmptyDates(true);
        return settings;
    }
}
