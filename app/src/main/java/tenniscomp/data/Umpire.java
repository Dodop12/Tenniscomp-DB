package tenniscomp.data;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import tenniscomp.data.sql.DAOException;
import tenniscomp.data.sql.DAOUtils;
import tenniscomp.data.sql.Queries;
import tenniscomp.utils.Gender;

public class Umpire {
    private final int umpireId;
    private final String surname;
    private final String name;
    private final String email;
    private final String birthDate;
    private final Gender gender;
    private final String phone;
    private final String title;
    
    public Umpire(final int umpireId, final String surname, final String name, 
            final String email, final String birthDate, final Gender gender, 
            final String phone, final String title) {
        this.umpireId = umpireId;
        this.surname = surname;
        this.name = name;
        this.email = email;
        this.birthDate = birthDate;
        this.gender = gender;
        this.phone = phone;
        this.title = title;
    }
    
    public int getUmpireId() {
        return umpireId;
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
    
    public Gender getGender() {
        return gender;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public String getTitle() {
        return title;
    }
    
    @Override
    public String toString() {
        return getSurname() + " " + getName() + " (" + title + ")";
    }

    public final class DAO {

        public static boolean insertUmpire(final Connection connection, final String surname,
                final String name, final String email, final String birthDate,
                final Gender gender, final String phone, final String title) {
            try (
                var statement = DAOUtils.prepare(connection, Queries.ADD_UMPIRE, surname, name, email,
                        birthDate, gender.getCode(), phone, title);
            ) {
                return statement.executeUpdate() > 0;
            } catch (final Exception e) {
                throw new DAOException(e);
            }
        }

        public static Umpire getUmpireById(final Connection connection, final int umpireId) {
            try (
                var statement = DAOUtils.prepare(connection, Queries.GET_UMPIRE_BY_ID, umpireId);
                var resultSet = statement.executeQuery();
            ) {
                if (resultSet.next()) {
                    return new Umpire(
                        resultSet.getInt("id_arbitro"),
                        resultSet.getString("cognome"),
                        resultSet.getString("nome"),
                        resultSet.getString("email"),
                        resultSet.getString("data_nascita"),
                        Gender.fromCode(resultSet.getString("sesso")),
                        resultSet.getString("telefono"),
                        resultSet.getString("qualifica")
                    );
                } else {
                    return null;
                }
            } catch (final Exception e) {
                throw new DAOException(e);
            }
        }

        public static List<Umpire> getAllUmpires(final Connection connection) {
            try (
                var statement = DAOUtils.prepare(connection, Queries.GET_ALL_UMPIRES);
                var resultSet = statement.executeQuery();
            ) {
                final var umpires = new ArrayList<Umpire>();
                while (resultSet.next()) {
                    umpires.add(new Umpire(
                        resultSet.getInt("id_arbitro"),
                        resultSet.getString("cognome"),
                        resultSet.getString("nome"),
                        resultSet.getString("email"),
                        resultSet.getString("data_nascita"),
                        Gender.fromCode(resultSet.getString("sesso")),
                        resultSet.getString("telefono"),
                        resultSet.getString("qualifica")
                    ));
                }
                return umpires;
            } catch (final Exception e) {
                throw new DAOException(e);
            }
        }
    }
}
