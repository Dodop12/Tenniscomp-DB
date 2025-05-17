package tenniscomp.data;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class Team {
    private final int teamId;
    private final int clubId;

    public Team(final int teamId, final int clubId) {
        this.teamId = teamId;
        this.clubId = clubId;
    }

    public int getTeamId() {
        return teamId;
    }

    public int getClubId() {
        return clubId;
    }

    public final class DAO {
        
        public static List<Team> getTeamsByLeague(final Connection connection, final int leagueId) {
            try (
                var statement = DAOUtils.prepare(connection, Queries.GET_LEAGUE_TEAMS, leagueId);
                var resultSet = statement.executeQuery();
            ) {
                final var teams = new ArrayList<Team>();
                while (resultSet.next()) {
                    teams.add(new Team(
                        resultSet.getInt("id_squadra"),
                        resultSet.getInt("id_club")
                    ));
                }
                return teams;
            } catch (final Exception e) {
                throw new DAOException(e);
            }
        }
    }
}
