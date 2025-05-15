package tenniscomp.data;

public class TournamentMatch extends Match {
    private final int tournamentId;

    public TournamentMatch(final int matchId, final String type, final String winner, final String result,
                           final int courtId, final int refereeId, final int tournamentId) {
        super(matchId, type, winner, result, courtId, refereeId);
        this.tournamentId = tournamentId;
    }

    public int getTournamentId() {
        return tournamentId;
    }

    public final class DAO {

    }
}

