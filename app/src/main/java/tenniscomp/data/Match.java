package tenniscomp.data;

public abstract class Match {
    protected final int matchId;
    protected final String result;
    protected final int courtId;
    protected final Integer umpireId;

    public Match(final int matchId, final String result, final int courtId, final Integer umpireId) {
        this.matchId = matchId;
        this.result = result;
        this.courtId = courtId;
        this.umpireId = umpireId;
    }

    public int getMatchId() {
        return matchId;
    }

    public String getResult() {
        return result;
    }

    public int getCourtId() {
        return courtId;
    }

    public Integer getUmpireId() {
        return umpireId;
    }

}
