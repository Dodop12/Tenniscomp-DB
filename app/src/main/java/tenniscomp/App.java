package tenniscomp;

import java.sql.SQLException;

import tenniscomp.controller.MainControllerImpl;
import tenniscomp.data.DAOUtils;

public class App {

    public static void main(final String[] args) throws SQLException {
        final var connection = DAOUtils.localMySQLConnection("tenniscomp", "root", "");
        new MainControllerImpl(connection).start();
    }
}
