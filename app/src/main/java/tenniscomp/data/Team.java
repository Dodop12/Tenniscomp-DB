package tenniscomp.data;

import java.sql.Connection;
import java.sql.Statement;
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
                        resultSet.getInt("id_circolo")
                    ));
                }
                return teams;
            } catch (final Exception e) {
                throw new DAOException(e);
            }
        }

        public static boolean registerTeamForLeague(final Connection connection, final int clubId, final int leagueId,
                final List<Integer> playerIds) {
            try {
                connection.setAutoCommit(false);
            
                // Insert team
                int teamId;
                try (var addTeamStatement = connection.prepareStatement(Queries.ADD_TEAM, 
                        Statement.RETURN_GENERATED_KEYS)) {
                    addTeamStatement.setInt(1, clubId);
                    if (addTeamStatement.executeUpdate() == 0) {
                        connection.rollback();
                        return false;
                    }

                    // Get the generated match ID
                    try (var generatedKeys = addTeamStatement.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            teamId = generatedKeys.getInt(1);
                        } else {
                            connection.rollback();
                            return false;
                        }
                    }
                }

                // Register team for league
                try (var registerTeamStatement = DAOUtils.prepare(connection, Queries.REGISTER_TEAM_FOR_LEAGUE,
                        teamId, leagueId)) {
                    if (registerTeamStatement.executeUpdate() == 0) {
                        connection.rollback();
                        return false;
                    }
                }
                
                // Add players to the team
                for (final int playerId : playerIds) {
                    try (var playerStatement = DAOUtils.prepare(connection, Queries.UPDATE_PLAYER_TEAM,
                            teamId, playerId)) {
                        if (playerStatement.executeUpdate() == 0) {
                            connection.rollback();
                            return false;
                        }
                    }
                }
            
                connection.commit();
                return true;
            } catch (final Exception e) {
                try {
                    connection.rollback();
                } catch (final Exception rollbackEx) {
                    throw new DAOException(rollbackEx);
                }
                throw new DAOException(e);
            } finally {
                try {
                    connection.setAutoCommit(true);
                } catch (final Exception autoCommitEx) {
                    throw new DAOException(autoCommitEx);
                }
            }
        }
    }
}
