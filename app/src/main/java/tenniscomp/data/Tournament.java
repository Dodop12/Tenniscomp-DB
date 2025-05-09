package tenniscomp.data;

public class Tournament {
    private final int tournamentId;
    private final String name;
    private final String startDate;
    private final String endDate;
    private final String registrationDeadline;
    private final String type;
    private final String rankingLimit;
    private final double prizeMoney;
    private final int refereeId;
    private final int clubId;

    public Tournament(final int competitionId, final String name, final String startDate, final String endDate,
            final String registrationDeadline, final String type, final String rankingLimit,
            final double prizeMoney, final int refereeId, final int clubId) {
        this.tournamentId = competitionId;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.registrationDeadline = registrationDeadline;
        this.type = type;
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

    public String getType() {
        return type;
    }

    public String getRankingLimit() {
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
        
    }
}
