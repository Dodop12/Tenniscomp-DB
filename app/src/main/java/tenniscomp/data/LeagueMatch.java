package tenniscomp.data;

public class LeagueMatch extends Match {
    private final String type;
    private final int tieId;

    public LeagueMatch(final int matchId, final String type, final String result,
            final int tieId, final int courtId, final int refereeId) {
        super(matchId, result, courtId, refereeId);
        this.type = type;
        this.tieId = tieId;
    }

    public String getType() {
        return type;
    }

    public int getTieId() {
        return tieId;
    }

    public final class DAO {
        
    }
}

