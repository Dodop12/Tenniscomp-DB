package tenniscomp.data;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import tenniscomp.data.sql.DAOException;
import tenniscomp.data.sql.DAOUtils;
import tenniscomp.data.sql.Queries;
import tenniscomp.utils.Surface;

public class Court {
    private final int courtId;
    private final int number;
    private final Surface surface;
    private final boolean indoor;
    private final int clubId;

    public Court(final int courtId, final int number, final Surface surface, final boolean indoor, final int clubId) {
        this.courtId = courtId;
        this.number = number;
        this.surface = surface;
        this.indoor = indoor;
        this.clubId = clubId;
    }

    public int getCourtId() {
        return courtId;
    }

    public int getNumber() {
        return number;
    }

    public Surface getSurface() {
        return surface;
    }

    public boolean isIndoor() {
        return indoor;
    }

    public int getClubId() {
        return clubId;
    }

    public String toString() {
        return "Campo " + number;
    }

    public final class DAO {
        
        public static boolean insertCourt(final Connection connection, final int number, 
                final Surface surface, final boolean indoor, final int clubId) {
            try (
                var statement = DAOUtils.prepare(connection, Queries.ADD_COURT,
                    number, surface.getLabel(), indoor, clubId);
            ) {
                return statement.executeUpdate() > 0;
            } catch (final Exception e) {
                throw new DAOException(e);
            }
        }

        public static Court getCourtById(final Connection connection, final int courtId) {
            try (
                var statement = DAOUtils.prepare(connection, Queries.GET_COURT_BY_ID, courtId);
                var resultSet = statement.executeQuery();
            ) {
                if (resultSet.next()) {
                    return new Court(
                        resultSet.getInt("id_campo"),
                        resultSet.getInt("numero"),
                        Surface.fromLabel(resultSet.getString("superficie")),
                        resultSet.getBoolean("indoor"),
                        resultSet.getInt("id_circolo")
                    );
                }
                return null;
            } catch (final Exception e) {
                throw new DAOException(e);
            }
        }

        public static int getCourtCountByClub(final Connection connection, final int clubId) {
            try (
                var statement = DAOUtils.prepare(connection, Queries.COUNT_COURTS_BY_CLUB, clubId);
                var resultSet = statement.executeQuery();
            ) {
                if (resultSet.next()) {
                    return resultSet.getInt(1);
                }
                return 0;
            } catch (final Exception e) {
                throw new DAOException(e);
            }
        }

        public static List<Court> getCourtsByClub(final Connection connection, final int clubId) {
        try (
            var statement = DAOUtils.prepare(connection, Queries.GET_COURTS_BY_CLUB, clubId);
            var resultSet = statement.executeQuery();
        ) {
            final var courts = new ArrayList<Court>();
            while (resultSet.next()) {
                courts.add(new Court(
                    resultSet.getInt("id_campo"),
                    resultSet.getInt("numero"),
                    Surface.fromLabel(resultSet.getString("superficie")),
                    resultSet.getBoolean("indoor"),
                    resultSet.getInt("id_circolo")
                ));
            }
            return courts;
        } catch (final Exception e) {
            throw new DAOException(e);
        }
    }

        public static boolean courtNumberExists(final Connection connection, final int number, final int clubId) {
            try (
                var statement = DAOUtils.prepare(connection, Queries.CHECK_COURT_NUMBER_EXISTS, number, clubId);
                var resultSet = statement.executeQuery();
            ) {
                if (resultSet.next()) {
                    return resultSet.getBoolean(1);
                }
                return false;
            } catch (final Exception e) {
                throw new DAOException(e);
            }
        }
    }

}
