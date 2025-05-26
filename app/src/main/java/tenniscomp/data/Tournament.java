package tenniscomp.data;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

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
                final Ranking rankingLimit, final double prizeMoney, final int refereeId, final int clubId) {
            try (
                var statement = DAOUtils.prepare(connection, Queries.ADD_TOURNAMENT, name, startDate, endDate, 
                        registrationDeadline, type.getLabel(), gender.getCode(), rankingLimit.getLabel(),
                        prizeMoney, refereeId, clubId)
            ) {
                return statement.executeUpdate() > 0;
            } catch (final Exception e) {
                throw new DAOException(e);
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
                        playerGender, playerRanking.getNumericValue());
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
                                Gender.fromLabel(resultSet.getString("sesso")),
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
