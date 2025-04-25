package tenniscomp.data;

public final class Queries {

    public static final String ADD_PLAYER =
        """
        INSERT INTO giocatore (nome, cognome, email, data_nascita, sesso, telefono, username, password_hash)
        VALUES (?, ?, ?, ?, ?, ?, ?, ?)
        """;
    
    public static final String CHECK_PLAYER_LOGIN =
        """
        SELECT *
        FROM giocatore
        WHERE username = ?
        AND password_hash = ?
        """;

    public static final String CHECK_GA_LOGIN =
        """
        SELECT *
        FROM giudice_arbitro
        WHERE username = ?
        AND password_hash = ?
        """;

    public static final String GET_PLAYER_BY_USERNAME =
        """
        SELECT *
        FROM giocatore
        WHERE username = ?
        """;
}
