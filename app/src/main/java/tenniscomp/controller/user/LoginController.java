package tenniscomp.controller.user;

import java.sql.Connection;

import javax.swing.JOptionPane;

import tenniscomp.controller.player.PlayerDashboardController;
import tenniscomp.controller.referee.RefereeDashboardController;
import tenniscomp.model.Model;
import tenniscomp.view.player.PlayerDashboard;
import tenniscomp.view.referee.RefereeDashboard;
import tenniscomp.view.user.LoginWindow;
import tenniscomp.view.user.RegisterWindow;

public class LoginController {

    private final Connection connection;
    private final LoginWindow view;
    private final Model model;

    public LoginController(final Connection connection, final LoginWindow view, final Model model) {
        this.connection = connection;
        this.view = view;
        this.model = model;

        this.view.setLoginListener(e -> executeLogin());
        this.view.setRegisterListener(e -> openRegisterWindow());
    }

    public void executeLogin() {
        final String username = view.getUsername();
        final String password = view.getPassword();

        final boolean isAdmin = view.isAdmin();

        final boolean success = isAdmin
            ? model.loginReferee(username, password)
            : model.loginPlayer(username, password);

        if (success) {
            view.dispose();
            
            if (!isAdmin) {
                final var player = model.getPlayerByUsername(username);
                if (player == null) {
                    view.showMessage("Errore durante il recupero dei dati del giocatore.", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                final var dashboard = new PlayerDashboard();
                dashboard.display();
                new PlayerDashboardController(connection, dashboard, model, player);
            } else {
                final var referee = model.getRefereeByUsername(username);
                if (referee == null) {
                    view.showMessage("Errore durante il recupero dei dati del giudice arbitro.", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                final var dashboard = new RefereeDashboard();
                dashboard.display();
                new RefereeDashboardController(connection, dashboard, model, referee);
            }
        } else {
            view.showMessage("Credenziali non valide.", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void openRegisterWindow() {
        view.dispose();
        final var registerView = new RegisterWindow();
        new RegisterController(connection, registerView, model);
    }
    
}
