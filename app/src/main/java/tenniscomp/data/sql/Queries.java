package tenniscomp.data.sql;

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

    public static final String GET_PLAYERS_BY_GENDER_AND_BIRTH =
        """
        SELECT *
        FROM giocatore
        WHERE data_nascita BETWEEN ? AND ?
        AND sesso = ?
        ORDER BY cognome, nome
        """;

    public static final String GET_REFEREE_BY_USERNAME =
        """
        SELECT *
        FROM giudice_arbitro
        WHERE username = ?
        """;

    public static final String ADD_UMPIRE =
        """
        INSERT INTO arbitro (cognome, nome, email, data_nascita, sesso, telefono, qualifica)
        VALUES (?, ?, ?, ?, ?, ?, ?)
        """;

    public static final String GET_UMPIRE_BY_ID =
        """
        SELECT *
        FROM arbitro
        WHERE id_arbitro = ?
        """;

    public static final String GET_ALL_UMPIRES =
        """
        SELECT *
        FROM arbitro
        ORDER BY cognome, nome
        """;

    public static final String GET_ALL_PLAYERS =
        """
        SELECT *
        FROM giocatore
        ORDER BY cognome, nome
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
    
    public static final String ADD_COURT =
        """
        INSERT INTO campo (numero, superficie, indoor, id_circolo)
        VALUES (?, ?, ?, ?)
        """;

    public static final String GET_COURT_BY_ID =
        """
        SELECT *
        FROM campo
        WHERE id_campo = ?
        """;

    public static final String COUNT_COURTS_BY_CLUB =
        """
        SELECT COUNT(*)
        FROM campo
        WHERE id_circolo = ?
        """;

    public static final String GET_COURTS_BY_CLUB = 
        """
        SELECT *
        FROM campo
        WHERE id_circolo = ?
        ORDER BY numero
        """;

    public static final String CHECK_COURT_NUMBER_EXISTS =
        """
        SELECT EXISTS(
            SELECT 1
            FROM campo
            WHERE numero = ?
            AND id_circolo = ?
        )
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

    public static final String ADD_PRIZE = 
        """
        INSERT INTO premio (posizione, valore, id_torneo) 
        VALUES (?, ?, ?)
        """;

    public static final String ADD_TOURNAMENT =
        """
        INSERT INTO torneo (nome, data_inizio, data_fine, scadenza_iscrizioni, tipo,
                sesso, limite_classifica, montepremi, id_ga, id_circolo)
        VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;

    public static final String GET_TOURNAMENT_BY_ID =
        """
        SELECT *
        FROM torneo
        WHERE id_torneo = ?
        """;

    public static final String GET_TOURNAMENT_REGISTRATIONS =
        """
        SELECT *
        FROM iscrizione_torneo
        WHERE id_torneo = ?
        """;

    public static final String GET_TOURNAMENT_PLAYERS =
        """
        SELECT g.*
        FROM giocatore g
        JOIN iscrizione_torneo i ON g.id_giocatore = i.id_giocatore
        WHERE i.id_torneo = ?
        """;

    public static final String GET_TOURNAMENTS_BY_REFEREE =
        """
        SELECT *
        FROM torneo
        WHERE id_ga = ?
        ORDER BY data_inizio DESC
        """;

    public static final String GET_ELIGIBLE_TOURNAMENTS =
        """
        SELECT *
        FROM torneo
        WHERE sesso = ?
        AND CAST(REPLACE(limite_classifica, '.', '') AS UNSIGNED) <= ?
        ORDER BY data_inizio ASC
        """;

    public static final String REGISTER_PLAYER_FOR_TOURNAMENT =
        """
        INSERT INTO iscrizione_torneo (data, id_giocatore, id_torneo)
        VALUES (CURDATE(), ?, ?)
        """;

    public static final String IS_PLAYER_REGISTERED_FOR_TOURNAMENT =
        """
        SELECT EXISTS(
            SELECT 1
            FROM iscrizione_torneo
            WHERE id_giocatore = ?
            AND id_torneo = ?
        )
        """;

    public static final String ADD_TOURNAMENT_MATCH_PLAYER =
        """
        INSERT INTO giocatore_partita_torneo (id_giocatore, id_partita_torneo, vincitore)
        VALUES (?, ?, ?)
        """;

    public static final String ADD_TOURNAMENT_MATCH =
        """
        INSERT INTO partita_torneo (data, risultato, id_torneo, id_campo, id_arbitro)
        VALUES (?, ?, ?, ?, ?)
        """;

    public static final String GET_TOURNAMENT_MATCHES =
        """
        SELECT *
        FROM partita_torneo
        WHERE id_torneo = ?
        """;

    public static final String GET_TOURNAMENT_MATCHES_BY_PLAYER =
        """
        SELECT pt.*
        FROM partita_torneo pt
        JOIN giocatore_partita_torneo gpt ON pt.id_partita_torneo = gpt.id_partita_torneo
        WHERE gpt.id_giocatore = ?
        """;

    public static final String GET_PLAYERS_BY_TOURNAMENT_MATCH =
        """
        SELECT g.*
        FROM giocatore g
        JOIN giocatore_partita_torneo gpt ON g.id_giocatore = gpt.id_giocatore
        WHERE gpt.id_partita_torneo = ?;
        """;

    public static final String CHECK_PLAYER_WON_TOURNAMENT_MATCH =
        """
        SELECT vincitore
        FROM giocatore_partita_torneo
        WHERE id_giocatore = ?
        AND id_partita_torneo = ?
        """;

    public static final String ADD_LEAGUE =
        """
        INSERT INTO campionato (serie, categoria, sesso, anno, id_ga)
        VALUES (?, ?, ?, ?, ?)
        """;

    public static final String GET_LEAGUE_BY_ID =
        """
        SELECT *
        FROM campionato
        WHERE id_campionato = ?
        """;

    public static final String ADD_TEAM = 
        """
        INSERT INTO squadra (id_circolo)
        VALUES (?)
        """;

    public static final String CHECK_PLAYER_IN_LEAGUE =
        """
        SELECT EXISTS(
            SELECT 1
            FROM giocatore g
            JOIN iscrizione_squadra_campionato isc ON g.id_squadra = isc.id_squadra
            WHERE g.id_giocatore = ? 
            AND isc.id_campionato = ?
        )
        """;

    public static final String GET_LEAGUES_BY_REFEREE =
        """
        SELECT *
        FROM campionato
        WHERE id_ga = ?
        ORDER BY anno DESC, serie
        """;

    public static final String GET_PLAYERS_BY_TEAM =
        """
        SELECT *
        FROM giocatore
        WHERE id_squadra = ?
        ORDER BY cognome, nome
        """;

    public static final String GET_TEAM_PLAYER_COUNT =
        """
        SELECT COUNT(*)
        FROM giocatore
        WHERE id_squadra = ?
        """;

    public static final String UPDATE_PLAYER_TEAM =
        """
        UPDATE giocatore
        SET id_squadra = ?
        WHERE id_giocatore = ?
        """;

    public static final String REGISTER_TEAM_FOR_LEAGUE =
        """
        INSERT INTO iscrizione_squadra_campionato (id_squadra, id_campionato)
        VALUES (?, ?)
        """;

    public static final String GET_LEAGUE_TEAMS =
        """
        SELECT s.*
        FROM squadra s
        JOIN iscrizione_squadra_campionato i ON s.id_squadra = i.id_squadra
        WHERE i.id_campionato = ?
        """;

    public static final String ADD_LEAGUE_TIE = 
        """
        INSERT INTO incontro_campionato (data, id_campionato, id_squadra_casa, id_squadra_ospite)
        VALUES (?, ?, ?, ?)
        """;

    public static final String GET_LEAGUE_TIE_BY_ID =
        """
        SELECT *
        FROM incontro_campionato
        WHERE id_incontro = ?
        """;

    public static final String GET_LEAGUE_TIES =
        """
        SELECT *
        FROM incontro_campionato
        WHERE id_campionato = ?
        """;

    public static final String UPDATE_LEAGUE_TIE_RESULT =
        """
        UPDATE incontro_campionato
        SET risultato = ?
        WHERE id_incontro = ?
        """;

    public static final String ADD_LEAGUE_MATCH_PLAYER =
        """
        INSERT INTO giocatore_partita_campionato (id_giocatore, id_partita_campionato, vincitore)
        VALUES (?, ?, ?)
        """;

    public static final String ADD_LEAGUE_MATCH =
        """
        INSERT INTO partita_campionato (tipo, risultato, id_incontro, id_campo, id_arbitro)
        VALUES (?, ?, ?, ?, ?)
        """;

    public static final String GET_MATCHES_BY_TIE_ID =
        """
        SELECT *
        FROM partita_campionato
        WHERE id_incontro = ?
        """;

    public static final String GET_CLUB_BY_TEAM_ID =
        """
        SELECT c.*
        FROM circolo c
        JOIN squadra s ON c.id_circolo = s.id_circolo
        WHERE s.id_squadra = ?
        """;

    public static final String GET_LEAGUE_MATCHES_BY_PLAYER =
        """
        SELECT pc.*
        FROM partita_campionato pc
        JOIN giocatore_partita_campionato gpc ON pc.id_partita_campionato = gpc.id_partita_campionato
        WHERE gpc.id_giocatore = ?
        """;

    public static final String GET_PLAYERS_BY_LEAGUE_MATCH =
        """
        SELECT g.*
        FROM giocatore g
        JOIN giocatore_partita_campionato gpc ON g.id_giocatore = gpc.id_giocatore
        WHERE gpc.id_partita_campionato = ?
        """;

    public static final String GET_LEAGUE_BY_MATCH =
        """
        SELECT c.*
        FROM campionato c
        JOIN incontro_campionato ic ON c.id_campionato = ic.id_campionato
        JOIN partita_campionato pc ON ic.id_incontro = pc.id_incontro
        WHERE pc.id_partita_campionato = ?
        """;

    public static final String CHECK_PLAYER_WON_LEAGUE_MATCH =
        """
        SELECT vincitore
        FROM giocatore_partita_campionato
        WHERE id_giocatore = ?
        AND id_partita_campionato = ?
        """;

    public static final String GET_TIE_MATCH_WINS_BY_TEAM =
        """
        SELECT COUNT(DISTINCT pc.id_partita_campionato)
        FROM partita_campionato pc
        JOIN giocatore_partita_campionato gpc ON pc.id_partita_campionato = gpc.id_partita_campionato
        WHERE gpc.id_giocatore IN (
            SELECT id_giocatore
            FROM giocatore
            WHERE id_squadra = ?
        )
        AND pc.id_incontro = ?
        AND gpc.vincitore = TRUE
        """;
}
