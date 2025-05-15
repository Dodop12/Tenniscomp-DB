package tenniscomp.data;

public class TournamentRegistration {
    private final int registrationId;
    private final String date;
    private final int playerId;
    private final int tournamentId;

    public TournamentRegistration(final int registrationId, final String date,
            final int playerId, final int tournamentId) {
        this.registrationId = registrationId;
        this.date = date;
        this.playerId = playerId;
        this.tournamentId = tournamentId;
    }

    public int getRegistrationId() {
        return registrationId;
    }

    public String getDate() {
        return date;
    }

    public int getPlayerId() {
        return playerId;
    }

    public int getTournamentId() {
        return tournamentId;
    }

    public final class DAO {

    }
}
