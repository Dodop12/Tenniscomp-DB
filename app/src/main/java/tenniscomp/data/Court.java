package tenniscomp.data;

import java.sql.Connection;

public class Court {
    private final int courtId;
    private final int number;
    private final String surface; // TODO: enum?
    private final boolean indoor;
    private final int clubId;

    public Court(final int courtId, final int number, final String surface, final boolean indoor, final int clubId) {
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

    public String getSurface() {
        return surface;
    }

    public boolean isIndoor() {
        return indoor;
    }

    public int getClubId() {
        return clubId;
    }

    public final class DAO {
        
        public static boolean insertCourt(final Connection connection, final int number, 
                final String surface, final boolean indoor, final int clubId) {
            try (
                var statement = DAOUtils.prepare(connection, Queries.ADD_COURT,
                    number, surface, indoor, clubId);
            ) {
                return statement.executeUpdate() > 0;
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
