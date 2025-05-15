package tenniscomp.data;

public abstract class Match {
    protected final int matchId;
    protected final String type;
    protected final String winner;
    protected final String result;
    protected final int courtId;
    protected final Integer refereeId;

    public Match(int matchId, String type, String winner, String result, int courtId, Integer refereeId) {
        this.matchId = matchId;
        this.type = type;
        this.winner = winner;
        this.result = result;
        this.courtId = courtId;
        this.refereeId = refereeId;
    }

    public int getMatchId() {
        return matchId;
    }

    public String getType() {
        return type;
    }

    public String getWinner() {
        return winner;
    }

    public String getResult() {
        return result;
    }

    public int getCourtId() {
        return courtId;
    }

    public Integer getRefereeId() {
        return refereeId;
    }

}
