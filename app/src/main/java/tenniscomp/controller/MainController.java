package tenniscomp.controller;

import java.sql.Connection;

import tenniscomp.controller.user.LoginController;
import tenniscomp.model.ModelImpl;
import tenniscomp.view.user.LoginSelector;
import tenniscomp.view.user.LoginWindow;

public class MainController {

    private final Connection connection;

    public MainController(final Connection connection) {
        this.connection = connection;
    }

    public void start() {
        final var loginSelector = new LoginSelector();
        loginSelector.setPlayerListener(e -> initializeLogin(false, loginSelector));
        loginSelector.setRefereeListener(e -> initializeLogin(true, loginSelector));
        loginSelector.display();
    }

    private void initializeLogin(final boolean isReferee, final LoginSelector loginSelector) {
        loginSelector.dispose();
        final var model = new ModelImpl(connection);
        final var loginView = new LoginWindow(isReferee);
        new LoginController(connection, loginView, model);
    }
    
}
