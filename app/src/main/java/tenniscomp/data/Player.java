package tenniscomp.data;

import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Player {
    private final int playerId;
    private final String name;
    private final String surname;
    private final String email;
    private final String birthDate;
    private final String gender;
    private final String phone;
    private final String username;
    private final String password;

    public Player(final int playerId, final String name, final String surname,
            final String email, final String birthDate, final String gender,
            final String phone, final String username, final String password) {
        this.playerId = playerId;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.birthDate = birthDate;
        this.gender = gender;
        this.phone = phone;
        this.username = username;
        this.password = password;
    }

    public int getPlayerId() {
        return playerId;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public String getGender() {
        return gender;
    }

    public String getPhone() {
        return phone;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public final class DAO {

        public static int insertPlayer(final Connection connection, final String name, final String surname,
                final String email, final String birthDate, final String gender,
                final String phone, final String username, final String password) {
            final var convertedDate = getConvertedDateFormat(birthDate);
            try ( 
                var statement = DAOUtils.prepare(connection, Queries.ADD_PLAYER, name, surname, email,
                    convertedDate, gender, phone, username, password);
            ) {
                return statement.executeUpdate();
            } catch (final Exception e) {
                throw new DAOException(e);
            }
        }

        public static boolean checkLogin(final Connection connection, final String username, final String password) {
            try (
                var statement = DAOUtils.prepare(connection, Queries.CHECK_PLAYER_LOGIN, username, password);
                var resultSet = statement.executeQuery();
            ) {
                return resultSet.next();
            } catch (final Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        private static String getConvertedDateFormat(final String date) {
            try {
                final var oldFormat = new SimpleDateFormat("dd/MM/yyyy");
                final var newFormat = new SimpleDateFormat("yyyy-MM-dd");
                final var parsedDate = oldFormat.parse(date);
                return newFormat.format(parsedDate);
            } catch (final ParseException e) {
                throw new DAOException("Errore nella conversione della data: " + e.getMessage(), e);
            }
        }

    }
    
}
