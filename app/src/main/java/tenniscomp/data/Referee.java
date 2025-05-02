package tenniscomp.data;

import java.sql.Connection;

public class Referee {
    private final int refereeId;
    private final String name;
    private final String surname;
    private final String email;
    private final String birthDate;
    private final String gender;
    private final String phone;
    private final String username;
    private final String password;
    private final String title;

    public Referee(final int refereeId, final String surname, final String name,
            final String email, final String birthDate, final String gender,
            final String phone, final String username, final String password, final String title) {
        this.refereeId = refereeId;
        this.surname = surname;
        this.name = name;
        this.email = email;
        this.birthDate = birthDate;
        this.gender = gender;
        this.phone = phone;
        this.username = username;
        this.password = password;
        this.title = title;
    }

    public int getRefereeId() {
        return refereeId;
    }

    public String getSurname() {
        return surname;
    }

    public String getName() {
        return name;
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

    public String getTitle() {
        return title;
    }

    public final class DAO {

        public static boolean checkLogin(final Connection connection, final String username, final String password) {
            try (
                var statement = DAOUtils.prepare(connection, Queries.CHECK_REFEREE_LOGIN, username, password);
                var resultSet = statement.executeQuery();
            ) {
                return resultSet.next();
            } catch (final Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        public static Referee getRefereeByUsername(final Connection connection, final String username) {
            try (
                var statement = DAOUtils.prepare(connection, Queries.GET_REFEREE_BY_USERNAME, username);
                var resultSet = statement.executeQuery();
            ) {
                if (resultSet.next()) {
                    return new Referee(
                        resultSet.getInt("id_ga"),
                        resultSet.getString("cognome"),
                        resultSet.getString("nome"),
                        resultSet.getString("email"),
                        resultSet.getString("data_nascita"),
                        resultSet.getString("sesso"),
                        resultSet.getString("telefono"),
                        resultSet.getString("username"),
                        resultSet.getString("password_hash"),
                        resultSet.getString("qualifica")
                    );
                }
                return null;
            } catch (final Exception e) {
                throw new DAOException(e);
            }
        }
    }
    
}
