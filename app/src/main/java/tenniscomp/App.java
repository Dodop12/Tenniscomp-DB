package tenniscomp;

import java.sql.SQLException;

import tenniscomp.controller.MainController;
import tenniscomp.data.sql.DAOUtils;

public class App {

    public static void main(final String[] args) throws SQLException {
        final var connection = DAOUtils.localMySQLConnection("tenniscomp", "root", "");
        new MainController(connection).start();
    }
}
