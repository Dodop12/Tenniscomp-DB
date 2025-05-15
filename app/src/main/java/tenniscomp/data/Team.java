package tenniscomp.data;

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
        
    }
}
