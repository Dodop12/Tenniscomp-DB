package tenniscomp.view.user;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.optionalusertools.DateVetoPolicy;

import tenniscomp.utils.CommonUtils;
import tenniscomp.utils.Gender;

public class RegisterWindow extends JFrame {

    private static final double WIDTH_RATIO = 0.34;
    private static final double HEIGHT_RATIO = 0.42;
    private final JTextField nameField = new JTextField();
    private final JTextField surnameField = new JTextField();
    private final JTextField emailField = new JTextField();
    private final JTextField phoneField = new JTextField();
    private final JTextField usernameField = new JTextField();
    private final JPasswordField passwordField = new JPasswordField();
    private final JPasswordField confirmField = new JPasswordField();
    private final JComboBox<String> genderComboBox = new JComboBox<>(Gender.getAllLabels().toArray(new String[0]));

    private final JButton registerButton = new JButton("Registrati");

    private final DatePicker birthDatePicker;

    public RegisterWindow() {
        setTitle("Registrazione Giocatore");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        final var screenSize = CommonUtils.getScreenSize();
        final int width = (int) (screenSize.width * WIDTH_RATIO);
        final int height = (int) (screenSize.height * HEIGHT_RATIO);
        setPreferredSize(new Dimension(width, height));

        final var panel = new JPanel(new GridLayout(10, 2, 2, 5));

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
        panel.add(new JLabel("Username:"));
        panel.add(usernameField);
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);
        panel.add(new JLabel("Conferma Password:"));
        panel.add(confirmField);

        panel.add(new JLabel());
        panel.add(this.registerButton);
        
        add(panel);
        getRootPane().setDefaultButton(this.registerButton);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public String getSurname() {
        return this.surnameField.getText().trim();
    }

    public String getName() {
        return this.nameField.getText().trim();
    }

    public String getBirthDate() {
        final var date = this.birthDatePicker.getDate();
        if (date == null) {
            return null;
        }
        return date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    public String getGender() {
        final var gender = (String) genderComboBox.getSelectedItem();
        if (gender == null) {
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

    public String getUsername() {
        return usernameField.getText().trim();
    }

    public String getPassword() {
        return new String(this.passwordField.getPassword());
    }

    public String getConfirmedPassword() {
        return new String(this.confirmField.getPassword());
    }

    public void addRegisterListener(final ActionListener listener) {
        this.registerButton.addActionListener(listener);
    }

    public void showMessage(final String message, final int messageType) {
        JOptionPane.showMessageDialog(this, message, "Registrazione", messageType);
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
