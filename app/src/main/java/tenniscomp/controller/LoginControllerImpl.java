package tenniscomp.controller;

import javax.swing.JOptionPane;

import tenniscomp.model.Model;
import tenniscomp.view.LoginWindow;
import tenniscomp.view.RegisterWindow;

public class LoginControllerImpl implements LoginController {

    private final LoginWindow view;
    private final Model model;

    public LoginControllerImpl(final LoginWindow view, final Model model) {
        this.view = view;
        this.model = model;

        this.view.setLoginListener(e -> executeLogin());
        this.view.setRegisterListener(e -> openRegisterWindow());
    }

    @Override
    public void executeLogin() {
        final String username = view.getUsername();
        final String password = view.getPassword();

        final boolean isAdmin = view.isAdmin();

        final boolean success = isAdmin
            ? model.loginReferee(username, password)
            : model.loginPlayer(username, password);

        if (success) {
            view.showMessage("Login effettuato con successo!", JOptionPane.INFORMATION_MESSAGE);
            view.dispose();
            // TODO: Open main window
        } else {
            view.showMessage("Credenziali non valide.", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void openRegisterWindow() {
        view.dispose();
        final var registerView = new RegisterWindow();
        new RegisterControllerImpl(registerView, model);
    }
    
}
