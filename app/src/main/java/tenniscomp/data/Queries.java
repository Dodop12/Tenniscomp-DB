package tenniscomp.data;

public final class Queries {

    public static final String ADD_PLAYER =
        """
        INSERT INTO giocatore (cognome, nome, email, data_nascita, sesso, telefono, username, password_hash)
        VALUES (?, ?, ?, ?, ?, ?, ?, ?)
        """;
    
    public static final String CHECK_PLAYER_LOGIN =
        """
        SELECT *
        FROM giocatore
        WHERE username = ?
        AND password_hash = ?
        """;

    public static final String CHECK_REFEREE_LOGIN =
        """
        SELECT *
        FROM giudice_arbitro
        WHERE username = ?
        AND password_hash = ?
        """;

    public static final String GET_ALL_PLAYERS =
        """
        SELECT *
        FROM giocatore
        ORDER BY cognome, nome
        """;

    public static final String GET_PLAYER_BY_USERNAME =
        """
        SELECT *
        FROM giocatore
        WHERE username = ?
        """;

    public static final String GET_REFEREE_BY_USERNAME =
        """
        SELECT *
        FROM giudice_arbitro
        WHERE username = ?
        """;

    public static final String GET_ALL_CLUBS =
        """
        SELECT *
        FROM circolo
        ORDER BY nome
        """;

    public static final String ADD_CLUB =
        """
        INSERT INTO circolo (nome, indirizzo, citta)
        VALUES (?, ?, ?)
        """;

    public static final String GET_CARD_BY_ID =
        """
        SELECT *
        FROM tessera
        WHERE id_tessera = ?
        """;
}
