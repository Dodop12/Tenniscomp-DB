package tenniscomp.data;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class Club {
    private final int clubId;
    private final String name;
    private final String address;
    private final String city;

    public Club(final int clubId, final String name, final String address, final String city) {
        this.clubId = clubId;
        this.name = name;
        this.address = address;
        this.city = city;
    }

    public int getClubId() {
        return clubId;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String toString() {
        return name + " - " + city;
    }

    public final class DAO {
        
        public static boolean insertClub(final Connection connection, final String name, 
                final String address, final String city) {
            try (
                var statement = DAOUtils.prepare(connection, Queries.ADD_CLUB,
                    name, address, city);
            ) {
                return statement.executeUpdate() == 1;
            } catch (final Exception e) {
                throw new DAOException(e);
            }
        }

        public static Club getClubById(final Connection connection, final int clubId) {
            try (
                var statement = DAOUtils.prepare(connection, Queries.GET_CLUB_BY_ID, clubId);
                var resultSet = statement.executeQuery();
            ) {
                if (resultSet.next()) {
                    return new Club(
                        resultSet.getInt("id_circolo"),
                        resultSet.getString("nome"),
                        resultSet.getString("indirizzo"),
                        resultSet.getString("citta")
                    );
                }
                return null;
            } catch (final Exception e) {
                throw new DAOException(e);
            }
        }

        public static Club getClubByTeamId(final Connection connection, final int teamId) {
            try (
                var statement = DAOUtils.prepare(connection, Queries.GET_CLUB_BY_TEAM_ID, teamId);
                var resultSet = statement.executeQuery();
            ) {
                if (resultSet.next()) {
                    return new Club(
                        resultSet.getInt("id_circolo"),
                        resultSet.getString("nome"),
                        resultSet.getString("indirizzo"),
                        resultSet.getString("citta")
                    );
                }
                return null;
            } catch (final Exception e) {
                throw new DAOException(e);
            }
        }
        
        public static List<Club> getAllClubs(final Connection connection) {
            try (
                var statement = DAOUtils.prepare(connection, Queries.GET_ALL_CLUBS);
                var resultSet = statement.executeQuery();
            ) {
                final var clubs = new ArrayList<Club>();
                while (resultSet.next()) {
                    clubs.add(new Club(
                        resultSet.getInt("id_circolo"),
                        resultSet.getString("nome"),
                        resultSet.getString("indirizzo"),
                        resultSet.getString("citta")
                    ));
                }
                return clubs;
            } catch (final Exception e) {
                throw new DAOException(e);
            }
        }

    }
}
