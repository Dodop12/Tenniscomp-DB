package tenniscomp.controller;

import javax.swing.JOptionPane;

import tenniscomp.model.Model;
import tenniscomp.view.LoginWindow;
import tenniscomp.view.RegisterWindow;

public class RegisterControllerImpl implements RegisterController {

    private final RegisterWindow view;
    private final Model model;

    public RegisterControllerImpl(final RegisterWindow view, final Model model) {
        this.view = view;
        this.model = model;

        this.view.addRegisterListener(e -> executeRegistration());
    }

    @Override
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

        final boolean success = model.registerPlayer(surname, name, email, birthDate, gender, phone, username, password);

        if (success) {
            view.showMessage("Registrazione completata con successo!", JOptionPane.INFORMATION_MESSAGE);
            this.view.dispose();
            final var loginView = new LoginWindow(false);
            new LoginControllerImpl(loginView, model); // Create a controller for the new login view
        } else {
            view.showMessage("Errore nella registrazione.", JOptionPane.ERROR_MESSAGE);
        }
    }
    
}
