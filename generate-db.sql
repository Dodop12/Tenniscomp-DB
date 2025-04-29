CREATE TABLE GIUDICE_ARBITRO (
    id_ga INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(50),
    cognome VARCHAR(50),
    email VARCHAR(100) UNIQUE NOT NULL,
    data_nascita DATE,
    sesso CHAR(1),
    telefono VARCHAR(15),
    username VARCHAR(50) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    qualifica VARCHAR(10) DEFAULT 'GAQR'
);

CREATE TABLE ARBITRO (
    id_arbitro INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(50),
    cognome VARCHAR(50),
    email VARCHAR(100) UNIQUE NOT NULL,
    data_nascita DATE,
    sesso CHAR(1),
    telefono VARCHAR(15),
    qualifica VARCHAR(10)
);

CREATE TABLE COMPETIZIONE (
    id_competizione INT PRIMARY KEY AUTO_INCREMENT
    id_ga INT,
    FOREIGN KEY (id_ga) REFERENCES GIUDICE_ARBITRO(id_ga)
);

CREATE TABLE TORNEO (
    id_competizione INT PRIMARY KEY,
    nome VARCHAR(100),
    data_inizio DATE,
    data_fine DATE,
    scadenza_iscrizioni DATE,
    tipo VARCHAR(50),
    limite_classifica VARCHAR(20),
    montepremi DECIMAL(10,2),
    id_circolo INT NOT NULL,
    FOREIGN KEY (id_competizione) REFERENCES COMPETIZIONE(id_competizione),
    FOREIGN KEY (id_circolo) REFERENCES CIRCOLO(id_circolo)
);

CREATE TABLE CAMPIONATO (
    id_competizione INT PRIMARY KEY,
    serie VARCHAR(20),
    categoria VARCHAR(20),
    sesso CHAR(1),
    anno INT,
    FOREIGN KEY (id_competizione) REFERENCES COMPETIZIONE(id_competizione)
);

CREATE TABLE PREMIO (
    id_premio INT PRIMARY KEY AUTO_INCREMENT,
    posizione INT,
    valore DECIMAL(10,2),
    id_torneo INT NOT NULL,
    FOREIGN KEY (id_torneo) REFERENCES TORNEO(id_competizione)
);

CREATE TABLE CIRCOLO (
    id_circolo INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100),
    indirizzo VARCHAR(100),
    citta VARCHAR(50)
);

CREATE TABLE CAMPO (
    id_campo INT PRIMARY KEY AUTO_INCREMENT,
    numero INT,
    superficie VARCHAR(50),
    indoor BOOLEAN,
    id_circolo INT NOT NULL,
    FOREIGN KEY (id_circolo) REFERENCES CIRCOLO(id_circolo)
);

CREATE TABLE SQUADRA (
    id_squadra INT PRIMARY KEY AUTO_INCREMENT
    id_circolo INT NOT NULL,
    FOREIGN KEY (id_circolo) REFERENCES CIRCOLO(id_circolo)
);

CREATE TABLE GIOCATORE (
    id_giocatore INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(50),
    cognome VARCHAR(50),
    email VARCHAR(100) UNIQUE NOT NULL,
    data_nascita DATE,
    sesso CHAR(1),
    telefono VARCHAR(15),
    username VARCHAR(50) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    classifica VARCHAR(20) DEFAULT '4.NC',
    id_circolo INT,
    id_squadra INT,
    FOREIGN KEY (id_circolo) REFERENCES CIRCOLO(id_circolo),
    FOREIGN KEY (id_squadra) REFERENCES SQUADRA(id_squadra)
);

CREATE TABLE TESSERA (
    id_tessera INT PRIMARY KEY,
    numero VARCHAR(20) UNIQUE NOT NULL,
    scadenza DATE,
    id_giocatore INT UNIQUE NOT NULL,
    FOREIGN KEY (id_giocatore) REFERENCES GIOCATORE(id_giocatore)
);

CREATE TABLE PARTITA (
    id_partita INT PRIMARY KEY AUTO_INCREMENT,
    tipo VARCHAR(20), -- "singolare" o "doppio"
    vincitore VARCHAR(100),
    risultato VARCHAR(20),
    id_competizione INT NOT NULL,
    id_campo INT NOT NULL,
    id_arbitro INT,
    FOREIGN KEY (id_competizione) REFERENCES COMPETIZIONE(id_competizione),
    FOREIGN KEY (id_campo) REFERENCES CAMPO(id_campo),
    FOREIGN KEY (id_arbitro) REFERENCES ARBITRO(id_arbitro)
);

CREATE TABLE INCONTRO_CAMPIONATO (
    id_incontro INT PRIMARY KEY AUTO_INCREMENT,
    data DATE,
    id_campionato INT,
    FOREIGN KEY (id_campionato) REFERENCES CAMPIONATO(id_competizione)
);

CREATE TABLE ISCRIZIONE (
    id_iscrizione INT PRIMARY KEY AUTO_INCREMENT,
    data DATE,
    id_giocatore INT NOT NULL,
    id_torneo INT NOT NULL,
    FOREIGN KEY (id_giocatore) REFERENCES GIOCATORE(id_giocatore),
    FOREIGN KEY (id_torneo) REFERENCES TORNEO(id_competizione)
);