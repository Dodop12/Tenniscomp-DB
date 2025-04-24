package tenniscomp;

import java.sql.SQLException;

import tenniscomp.data.DAOUtils;

public class App {

    public static void main(String[] args) throws SQLException {
        final var connection = DAOUtils.localMySQLConnection("tenniscomp", "root", "");
    }
}
