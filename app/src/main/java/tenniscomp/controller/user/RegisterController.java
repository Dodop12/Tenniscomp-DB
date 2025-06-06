package tenniscomp.controller.user;

import java.sql.Connection;

import javax.swing.JOptionPane;

import tenniscomp.model.Model;
import tenniscomp.utils.Gender;
import tenniscomp.utils.PasswordUtils;
import tenniscomp.view.user.LoginWindow;
import tenniscomp.view.user.RegisterWindow;

public class RegisterController {

    private final Connection connection;
    private final RegisterWindow view;
    private final Model model;

    public RegisterController(final Connection connection, final RegisterWindow view, final Model model) {
        this.connection = connection;
        this.view = view;
        this.model = model;

        this.view.addRegisterListener(e -> executeRegistration());
    }

    public void executeRegistration() {
        final String surname = this.view.getSurname();
        final String name = this.view.getName();
        final String email = this.view.getEmail();
        final String birthDate = this.view.getBirthDate();
        final String username = this.view.getUsername();
        final String gender = this.view.getGender();
        final String phone = this.view.getPhone();

        final String password = this.view.getPassword();
        final String confirm = this.view.getConfirmedPassword();

        if (!password.equals(confirm)) {
            view.showMessage("Le password non corrispondono.", JOptionPane.WARNING_MESSAGE);
            return;
        }

        final String hashedPassword = PasswordUtils.hashPasswordWithSalt(password);
        final boolean success = model.registerPlayer(surname, name, email, birthDate,
                Gender.fromCode(gender), phone, username, hashedPassword);

        if (success) {
            view.showMessage("Registrazione completata con successo!", JOptionPane.INFORMATION_MESSAGE);
            this.view.dispose();
            final var loginView = new LoginWindow(false);
            new LoginController(connection, loginView, model); // Create a controller for the new login view
        } else {
            view.showMessage("Errore nella registrazione.", JOptionPane.ERROR_MESSAGE);
        }
    }
    
}
