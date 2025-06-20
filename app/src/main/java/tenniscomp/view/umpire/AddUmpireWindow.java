package tenniscomp.view.umpire;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.time.LocalDate;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.optionalusertools.DateVetoPolicy;

import tenniscomp.utils.CommonUtils;
import tenniscomp.utils.Gender;

public class AddUmpireWindow extends JDialog {

    private static final double WIDTH_RATIO = 0.25;
    private static final double HEIGHT_RATIO = 0.3;

    private final JTextField nameField = new JTextField();
    private final JTextField surnameField = new JTextField();
    private final JTextField emailField = new JTextField();
    private final JTextField phoneField = new JTextField();
    private final JComboBox<String> genderComboBox = new JComboBox<>(Gender.getAllLabels().toArray(new String[0]));
    private final JComboBox<String> titleComboBox =
            new JComboBox<>(new String[]{"ARBN2", "ARBN1", "ITF1", "ITF2", "ITF3"});

    private final JButton saveButton = new JButton("Salva");
    private final JButton cancelButton = new JButton("Annulla");

    private final DatePicker birthDatePicker;

    public AddUmpireWindow(final JFrame parent) {
        super(parent, "Aggiungi Arbitro", true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        final var panel = new JPanel(new GridLayout(8, 2, 2, 5));

        this.birthDatePicker = createDatePicker();

        panel.add(new JLabel("Cognome:"));
        panel.add(surnameField);
        panel.add(new JLabel("Nome:"));
        panel.add(nameField);
        panel.add(new JLabel("Email:"));
        panel.add(emailField);
        panel.add(new JLabel("Data di nascita:"));
        panel.add(this.birthDatePicker);
        panel.add(new JLabel("Sesso:"));
        panel.add(genderComboBox);
        panel.add(new JLabel("Telefono:"));
        panel.add(phoneField);
        panel.add(new JLabel("Qualifica:"));
        panel.add(titleComboBox);

        panel.add(this.cancelButton);
        panel.add(this.saveButton);
        
        final var screenSize = CommonUtils.getScreenSize();
        final int width = (int) (screenSize.width * WIDTH_RATIO);
        final int height = (int) (screenSize.height * HEIGHT_RATIO);
        panel.setPreferredSize(new Dimension(width, height));

        add(panel);
        getRootPane().setDefaultButton(this.saveButton);

        pack();
        setLocationRelativeTo(parent);
    }

    public String getSurname() {
        return this.surnameField.getText().trim();
    }

    public String getName() {
        return this.nameField.getText().trim();
    }

    public String getBirthDate() {
        final var selectedDate = this.birthDatePicker.getDate();
        if (selectedDate == null) {
            return null;
        }
        return selectedDate.format(CommonUtils.getYmdDateFormatter());
    }

    public String getGender() {
        final var gender = (String) genderComboBox.getSelectedItem();
        if(gender == null) {
            return null;
        }
        return gender.substring(0, 1);
    }

    public String getEmail() {
        return this.emailField.getText().trim();
    }

    public String getPhone() {
        return this.phoneField.getText().trim();
    }

    public String getTitle() {
        return (String) titleComboBox.getSelectedItem();
    }

    public void setSaveButtonListener(final ActionListener listener) {
        this.saveButton.addActionListener(listener);
    }

    public void setCancelButtonListener(final ActionListener listener) {
        this.cancelButton.addActionListener(listener);
    }

    public void showMessage(final String message, final int messageType) {
        JOptionPane.showMessageDialog(this, message, "Aggiungi Arbitro", messageType);
    }

    private DatePicker createDatePicker() {
        // Set date formats
        final var dateSettings = new DatePickerSettings();
        dateSettings.setFormatForDatesCommonEra("dd/MM/yyyy");
        dateSettings.setFormatForDatesBeforeCommonEra("dd/MM/yyyy");

        final var datePicker = new DatePicker(dateSettings);

        // Allow only past dates
        dateSettings.setVetoPolicy(new DateVetoPolicy() {
            @Override
            public boolean isDateAllowed(final LocalDate date) {
                return date.isBefore(LocalDate.now());
            }
        });

        return datePicker;
    }
}
