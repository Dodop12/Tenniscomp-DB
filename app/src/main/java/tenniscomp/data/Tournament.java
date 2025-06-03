package tenniscomp.data;

import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import tenniscomp.data.sql.DAOException;
import tenniscomp.data.sql.DAOUtils;
import tenniscomp.data.sql.Queries;
import tenniscomp.utils.Gender;
import tenniscomp.utils.MatchType;
import tenniscomp.utils.Ranking;

public class Tournament {
    private final int tournamentId;
    private final String name;
    private final String startDate;
    private final String endDate;
    private final String registrationDeadline;
    private final MatchType type;
    private final Gender gender;
    private final Ranking rankingLimit;
    private final double prizeMoney;
    private final int refereeId;
    private final int clubId;

    public Tournament(final int competitionId, final String name, final String startDate, final String endDate,
            final String registrationDeadline, final MatchType type, final Gender gender, final Ranking rankingLimit,
            final double prizeMoney, final int refereeId, final int clubId) {
        this.tournamentId = competitionId;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.registrationDeadline = registrationDeadline;
        this.type = type;
        this.gender = gender;
        this.rankingLimit = rankingLimit;
        this.prizeMoney = prizeMoney;
        this.refereeId = refereeId;
        this.clubId = clubId;
    }

    public int getTournamentId() {
        return tournamentId;
    }

    public String getName() {
        return name;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getRegistrationDeadline() {
        return registrationDeadline;
    }

    public MatchType getType() {
        return type;
    }

    public Gender getGender() {
        return gender;
    }

    public Ranking getRankingLimit() {
        return rankingLimit;
    }

    public double getPrizeMoney() {
        return prizeMoney;
    }

    public int getRefereeId() {
        return refereeId;
    }

    public int getClubId() {
        return clubId;
    }

    public final class DAO {

        public static boolean insertTournament(final Connection connection, final String name, final String startDate, 
                final String endDate, final String registrationDeadline, final MatchType type, final Gender gender,
                final Ranking rankingLimit, final double prizeMoney, final List<Double> prizeDistribution, 
                final int refereeId, final int clubId) {
            try {
                connection.setAutoCommit(false);
                
                // Insert tournament
                int tournamentId;
                try (var statement = connection.prepareStatement(Queries.ADD_TOURNAMENT, 
                        Statement.RETURN_GENERATED_KEYS)) {
                    statement.setString(1, name);
                    statement.setString(2, startDate);
                    statement.setString(3, endDate);
                    statement.setString(4, registrationDeadline);
                    statement.setString(5, type.getLabel());
                    statement.setString(6, gender.getCode());
                    statement.setString(7, rankingLimit.getLabel());
                    statement.setDouble(8, prizeMoney);
                    statement.setInt(9, refereeId);
                    statement.setInt(10, clubId);
                    if (statement.executeUpdate() == 0) {
                        connection.rollback();
                        return false;
                    }
                    
                    // Get the generated tournament ID
                    try (var generatedKeys = statement.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            tournamentId = generatedKeys.getInt(1);
                        } else {
                            connection.rollback();
                            return false;
                        }
                    }
                }
                
                // Insert prizes if distribution is provided
                if (prizeDistribution != null && !prizeDistribution.isEmpty()) {
                    for (int i = 0; i < prizeDistribution.size(); i++) {
                        final double prizeValue = prizeDistribution.get(i);
                        if (prizeValue > 0) { // Only insert non-zero prize values
                            final int position = i + 1; // Position starts from 1
                            if (!Prize.DAO.insertPrize(connection, position, prizeValue, tournamentId)) {
                                connection.rollback();
                                return false;
                            }
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

        public static Tournament getTournamentById(final Connection connection, final int tournamentId) {
            try (
                var statement = DAOUtils.prepare(connection, Queries.GET_TOURNAMENT_BY_ID, tournamentId);
                var resultSet = statement.executeQuery();
            ) {
                if (resultSet.next()) {
                    return new Tournament(
                            resultSet.getInt("id_torneo"),
                            resultSet.getString("nome"),
                            resultSet.getString("data_inizio"),
                            resultSet.getString("data_fine"),
                            resultSet.getString("scadenza_iscrizioni"),
                            MatchType.fromLabel(resultSet.getString("tipo")),
                            Gender.fromCode(resultSet.getString("sesso")),
                            Ranking.fromLabel(resultSet.getString("limite_classifica")),
                            resultSet.getDouble("montepremi"),
                            resultSet.getInt("id_ga"),
                            resultSet.getInt("id_circolo")
                    );
                }
                return null;
            } catch (final Exception e) {
                throw new DAOException(e);
            }
        }

        public static List<Tournament> getTournamentsByReferee(final Connection connection, final int refereeId) {           
            try (
                var statement = DAOUtils.prepare(connection, Queries.GET_TOURNAMENTS_BY_REFEREE, refereeId);
                var resultSet = statement.executeQuery();
                ) {
                    final var tournaments = new ArrayList<Tournament>();
                    while (resultSet.next()) {
                        tournaments.add(new Tournament(
                                resultSet.getInt("id_torneo"),
                                resultSet.getString("nome"),
                                resultSet.getString("data_inizio"),
                                resultSet.getString("data_fine"),
                                resultSet.getString("scadenza_iscrizioni"),
                                MatchType.fromLabel(resultSet.getString("tipo")),
                                Gender.fromCode(resultSet.getString("sesso")),
                                Ranking.fromLabel(resultSet.getString("limite_classifica")),
                                resultSet.getDouble("montepremi"),
                                resultSet.getInt("id_ga"),
                                resultSet.getInt("id_circolo")
                        ));
                    }
                    return tournaments;
            } catch (final Exception e) {
                throw new DAOException(e);
            }
        }

        public static List<Tournament> getEligibleTournaments(final Connection connection,
                final Ranking playerRanking, final Gender playerGender) {
            try (
                var statement = DAOUtils.prepare(connection, Queries.GET_ELIGIBLE_TOURNAMENTS,
                        playerGender.getCode(), playerRanking.getNumericValue());
                var resultSet = statement.executeQuery();
                ) {
                    final var tournaments = new ArrayList<Tournament>();
                    while (resultSet.next()) {
                        tournaments.add(new Tournament(
                                resultSet.getInt("id_torneo"),
                                resultSet.getString("nome"),
                                resultSet.getString("data_inizio"),
                                resultSet.getString("data_fine"),
                                resultSet.getString("scadenza_iscrizioni"),
                                MatchType.fromLabel(resultSet.getString("tipo")),
                                Gender.fromCode(resultSet.getString("sesso")),
                                Ranking.fromLabel(resultSet.getString("limite_classifica")),
                                resultSet.getDouble("montepremi"),
                                resultSet.getInt("id_ga"),
                                resultSet.getInt("id_circolo")
                        ));
                    }
                    return tournaments;
            } catch (final Exception e) {
                throw new DAOException(e);
            }
        }

        public static boolean registerPlayerForTournament(final Connection connection, final int playerId, final int tournamentId) {
            try (
                var statement = DAOUtils.prepare(connection, Queries.REGISTER_PLAYER_FOR_TOURNAMENT, playerId, tournamentId);
            ) {
                return statement.executeUpdate() > 0;
            } catch (final Exception e) {
                throw new DAOException(e);
            }
        }

        public static boolean isPlayerRegisteredForTournament(final Connection connection, final int playerId, final int tournamentId) {
            try (
                var statement = DAOUtils.prepare(connection, Queries.IS_PLAYER_REGISTERED_FOR_TOURNAMENT, playerId, tournamentId);
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
