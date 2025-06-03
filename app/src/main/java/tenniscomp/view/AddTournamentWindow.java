package tenniscomp.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;

import tenniscomp.data.Club;
import tenniscomp.utils.CommonUtils;
import tenniscomp.utils.Gender;
import tenniscomp.utils.MatchType;
import tenniscomp.utils.Ranking;

public class AddTournamentWindow extends JDialog {

    private final JTextField nameField;
    private final DatePicker startDatePicker;
    private final DatePicker endDatePicker;
    private final DatePicker registrationDeadlinePicker;
    private final JComboBox<String> typeComboBox;
    private final JRadioButton maleRadio;
    private final JRadioButton femaleRadio;
    private final JComboBox<String> rankingLimitComboBox;
    private final JTextField prizeMoneyField;
    private final ClubSelector clubSelector;
    
    private final JButton configurePrizesButton;
    private final JButton saveButton;
    private final JButton cancelButton;
    
    private List<Double> prizeDistribution;

    public AddTournamentWindow(final JFrame parent, final List<Club> clubs) {
        super(parent, "Aggiungi Torneo", true);
        setSize(800, 600);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout(10, 10));

        final var formPanel = new JPanel(new GridLayout(9, 2, 10, 10));
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
        final var types = MatchType.getAllLabels();
        this.typeComboBox = new JComboBox<>(types.toArray(new String[0]));
        formPanel.add(this.typeComboBox);

        formPanel.add(new JLabel("Sesso:"));
        final var genderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        this.maleRadio = new JRadioButton(Gender.MALE.getCode());
        this.femaleRadio = new JRadioButton(Gender.FEMALE.getCode());

        final var genderGroup = new ButtonGroup();
        genderGroup.add(this.maleRadio);
        genderGroup.add(this.femaleRadio);
        this.maleRadio.setSelected(true);

        genderPanel.add(this.maleRadio);
        genderPanel.add(this.femaleRadio);
        formPanel.add(genderPanel);

        formPanel.add(new JLabel("Limite classifica:"));
        final var rankings = Ranking.getAllLabels();
        this.rankingLimitComboBox = new JComboBox<>(rankings.toArray(new String[0]));
        formPanel.add(this.rankingLimitComboBox);

        formPanel.add(new JLabel("Montepremi (€):"));
        this.prizeMoneyField = new JTextField();
        formPanel.add(this.prizeMoneyField);

        formPanel.add(new JLabel("Circolo:"));
        this.clubSelector = new ClubSelector(clubs);
        formPanel.add(this.clubSelector);

        add(formPanel, BorderLayout.CENTER);

        // Prize configuration panel
        final var prizePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        prizePanel.setBorder(BorderFactory.createTitledBorder("Configurazione Premi"));
        
        this.configurePrizesButton = new JButton("Configura Distribuzione Premi");
        this.configurePrizesButton.setEnabled(false); // Initially disabled
        prizePanel.add(this.configurePrizesButton);

        // Button panel
        final var buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        this.saveButton = new JButton("Salva");
        this.cancelButton = new JButton("Annulla");
        
        buttonsPanel.add(this.saveButton);
        buttonsPanel.add(this.cancelButton);
        
        // South panel containing prize config and buttons
        final var southPanel = new JPanel(new BorderLayout());
        southPanel.add(prizePanel, BorderLayout.CENTER);
        southPanel.add(buttonsPanel, BorderLayout.SOUTH);
        
        add(southPanel, BorderLayout.SOUTH);
        
        setupPrizeMoneyListener();
    }
    
    private void setupPrizeMoneyListener() {
        this.prizeMoneyField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(final DocumentEvent e) {
                updateConfigurePrizesButtonState();
            }
            
            @Override
            public void removeUpdate(final DocumentEvent e) {
                updateConfigurePrizesButtonState();
            }
            
            @Override
            public void changedUpdate(final DocumentEvent e) {
                updateConfigurePrizesButtonState();
            }
        });
    }
    
    private void updateConfigurePrizesButtonState() {
        try {
            final String prizeMoneyText = this.prizeMoneyField.getText().trim();
            if (prizeMoneyText.isEmpty()) {
                this.configurePrizesButton.setEnabled(false);
                return;
            }
            
            final double prizeMoney = Double.parseDouble(prizeMoneyText);
            this.configurePrizesButton.setEnabled(prizeMoney > 0);
            
        } catch (final NumberFormatException e) {
            this.configurePrizesButton.setEnabled(false);
        }
    }

    public String getTournamentName() {
        return this.nameField.getText().trim();
    }

    public String getStartDate() {
        final var selectedDate = this.startDatePicker.getDate();
        if (selectedDate == null) {
            throw new IllegalArgumentException("Data di nascita non specificata");
        }
        return selectedDate.format(CommonUtils.getYmdDateFormatter());
    }

    public String getEndDate() {
        final var selectedDate = this.endDatePicker.getDate();
        if (selectedDate == null) {
            throw new IllegalArgumentException("Data di nascita non specificata");
        }
        return selectedDate.format(CommonUtils.getYmdDateFormatter());
    }

    public String getRegistrationDeadline() {
        final var selectedDate = this.registrationDeadlinePicker.getDate();
        if (selectedDate == null) {
            throw new IllegalArgumentException("Data di nascita non specificata");
        }
        return selectedDate.format(CommonUtils.getYmdDateFormatter());
    }

    public String getTournamentType() {
        return (String) this.typeComboBox.getSelectedItem();
    }

    public String getGender() {
        return this.maleRadio.isSelected() ? "M" : "F";
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
    
    public List<Double> getPrizeDistribution() {
        return new ArrayList<>(this.prizeDistribution);
    }

    public void setPrizeDistribution(final List<Double> prizeDistribution) {
        this.prizeDistribution = new ArrayList<>(prizeDistribution);
        if (!this.prizeDistribution.isEmpty()) {
            this.configurePrizesButton.setText("Modifica Distribuzione Premi");
        } else {
            this.configurePrizesButton.setText("Configura Distribuzione Premi");
        }
    }
    
    public boolean hasPrizeDistribution() {
        return !this.prizeDistribution.isEmpty();
    }

    public void setConfigurePrizesButtonListener(final ActionListener listener) {
        this.configurePrizesButton.addActionListener(listener);
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