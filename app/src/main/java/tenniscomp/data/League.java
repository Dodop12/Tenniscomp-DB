package tenniscomp.data;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class League {
    private final int competitionId;
    private final String series;
    private final String category;
    private final String gender;
    private final int year;
    private final int refereeId;

    public League(final int competitionId, final String series, final String category, 
            final String gender, final int year, final int refereeId) {
        this.competitionId = competitionId;
        this.series = series;
        this.category = category;
        this.gender = gender;
        this.year = year;
        this.refereeId = refereeId;
    }

    public int getCompetitionId() {
        return competitionId;
    }

    public String getSeries() {
        return series;
    }

    public String getCategory() {
        return category;
    }

    public String getGender() {
        return gender;
    }

    public int getYear() {
        return year;
    }

    public int getRefereeId() {
        return refereeId;
    }

    public final class DAO {

        public static boolean insertLeague(final Connection connection, final String series, 
                final String category, final String gender, final int year, final int refereeId) {
            try (
                final var statement = DAOUtils.prepare(connection, Queries.ADD_LEAGUE, series, category,
                        gender, year, refereeId)
            ) {
                return statement.executeUpdate() > 0;
            } catch (final Exception e) {
                throw new DAOException(e);
            }
        }

        public static List<League> getLeaguesByReferee(final Connection connection, final int refereeId) {           
            try (
                var statement = connection.prepareStatement(Queries.GET_LEAGUES_BY_REFEREE);
                var resultSet = statement.executeQuery();
                ) {
                    final var leagues = new ArrayList<League>();
                    while(resultSet.next()) {
                        leagues.add(new League(
                            resultSet.getInt("id_campionato"),
                            resultSet.getString("serie"),
                            resultSet.getString("categoria"),
                            resultSet.getString("sesso"),
                            resultSet.getInt("anno"),
                            resultSet.getInt("id_ga")
                        ));
                    }
                    return leagues;
            } catch (final Exception e) {
                throw new DAOException(e);
            }
        }
        
    }
}
