package tenniscomp.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public final class DAOUtils {

    // Establishes a connection to a MySQL daemon running locally at port 3306.
    public static Connection localMySQLConnection(final String database, final String username, final String password) {
        try {
            final var host = "localhost";
            final var port = "3306";
            final var connectionString = "jdbc:mysql://" + host + ":" + port + "/" + database;
            return DriverManager.getConnection(connectionString, username, password);
        } catch (final Exception e) {
            throw new DAOException(e);
        }
    }

    public static PreparedStatement prepare(final Connection connection, final String query, final Object... values) throws SQLException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(query);
            for (int i = 0; i < values.length; i++) {
                statement.setObject(i + 1, values[i]);
            }
            return statement;
        } catch (final Exception e) {
            if (statement != null) {
                statement.close();
            }
            throw e;
        }
    }
}
