package tenniscomp.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import tenniscomp.data.Club;
import tenniscomp.utils.PlayerUtils;

public class AddTournamentWindow extends JDialog {

    private final JTextField nameField;
    private final JTextField startDateField;
    private final JTextField endDateField;
    private final JTextField registrationDeadlineField;
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
        this.startDateField = new JTextField();
        formPanel.add(this.startDateField);

        formPanel.add(new JLabel("Data fine:"));
        this.endDateField = new JTextField();
        formPanel.add(this.endDateField);

        formPanel.add(new JLabel("Scadenza iscrizioni:"));
        this.registrationDeadlineField = new JTextField();
        formPanel.add(this.registrationDeadlineField);

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
        return this.startDateField.getText().trim();
    }

    public String getEndDate() {
        return this.endDateField.getText().trim();
    }

    public String getRegistrationDeadline() {
        return this.registrationDeadlineField.getText().trim();
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
}
