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

    public static final String GET_PLAYER_BY_ID =
        """
        SELECT *
        FROM giocatore
        WHERE id_giocatore = ?
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

    public static final String ADD_CLUB =
        """
        INSERT INTO circolo (nome, indirizzo, citta)
        VALUES (?, ?, ?)
        """;

    public static final String GET_CLUB_BY_ID =
        """
        SELECT *
        FROM circolo
        WHERE id_circolo = ?
        """;

    public static final String GET_ALL_CLUBS =
        """
        SELECT *
        FROM circolo
        ORDER BY citta, nome
        """;

    public static final String ADD_CARD =
        """
        INSERT INTO tessera (numero, scadenza)
        VALUES (?, ?)
        """;

    public static final String GET_CARD_BY_ID =
        """
        SELECT *
        FROM tessera
        WHERE id_tessera = ?
        """;

    public static final String GET_CARD_BY_NUMBER =
        """
        SELECT *
        FROM tessera
        WHERE numero = ?
        """;

    public static final String UPDATE_PLAYER_RANKING =
        """
        UPDATE giocatore
        SET classifica = ?
        WHERE id_giocatore = ?
        """;

    public static final String UPDATE_PLAYER_CARD =
        """
        UPDATE giocatore
        SET id_tessera = ?
        WHERE id_giocatore = ?
        """;

    public static final String UPDATE_PLAYER_CLUB =
        """
        UPDATE giocatore
        SET id_circolo = ?
        WHERE id_giocatore = ?
        """;

    public static final String CHECK_CARD_NUMBER_EXISTS =
        """
        SELECT EXISTS(
            SELECT 1
            FROM tessera
            WHERE numero = ?
        )
        """;

    public static final String UPDATE_CARD_EXPIRY_DATE =
        """
        UPDATE tessera
        SET scadenza = ?
        WHERE id_tessera = ?
        """;

    public static final String ADD_TOURNAMENT =
        """
        INSERT INTO torneo (nome, data_inizio, data_fine, scadenza_iscrizioni,
                tipo, limite_classifica, montepremi, id_ga, id_circolo)
        VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;

    public static final String GET_TOURNAMENTS_BY_REFEREE =
        """
        SELECT *
        FROM torneo
        WHERE id_ga = ?
        ORDER BY data_inizio DESC
        """;

    public static final String ADD_LEAGUE =
        """
        INSERT INTO campionato (serie, categoria, sesso, anno, id_ga)
        VALUES (?, ?, ?, ?, ?)
        """;

    public static final String GET_LEAGUES_BY_REFEREE =
        """
        SELECT *
        FROM campionato
        WHERE id_ga = ?
        ORDER BY anno DESC, serie
        """;
}
