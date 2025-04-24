package tenniscomp.controller;

import java.sql.Connection;

import tenniscomp.model.ModelImpl;
import tenniscomp.view.LoginSelector;
import tenniscomp.view.LoginWindow;

public class MainControllerImpl implements MainController {

    private final Connection connection;

    public MainControllerImpl(final Connection connection) {
        this.connection = connection;
    }

    @Override
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
        //new LoginControllerImpl(loginView, model);
    }
    
}
