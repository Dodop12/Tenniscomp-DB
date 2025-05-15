package tenniscomp.data;

public class LeagueMatch extends Match {
    private final int leagueId;

    public LeagueMatch(final int matchId, final String type, final String winner, final String result,
                       final int courtId, final int refereeId, final int leagueId) {
        super(matchId, type, winner, result, courtId, refereeId);
        this.leagueId = leagueId;
    }

    public int getLeagueId() {
        return leagueId;
    }

    public final class DAO {
        
    }
}

