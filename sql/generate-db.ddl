CREATE DATABASE IF NOT EXISTS tenniscomp;
USE tenniscomp;

CREATE TABLE giudice_arbitro (
    id_ga INT PRIMARY KEY AUTO_INCREMENT,
    cognome VARCHAR(50) NOT NULL,
    nome VARCHAR(50) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    data_nascita DATE NOT NULL,
    sesso CHAR(1) NOT NULL,
    telefono VARCHAR(15) NOT NULL,
    username VARCHAR(50) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    qualifica VARCHAR(10) DEFAULT 'GAQR'
);

CREATE TABLE arbitro (
    id_arbitro INT PRIMARY KEY AUTO_INCREMENT,
    cognome VARCHAR(50) NOT NULL,
    nome VARCHAR(50) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    data_nascita DATE NOT NULL,
    sesso CHAR(1) NOT NULL,
    telefono VARCHAR(15) NOT NULL,
    qualifica VARCHAR(10) NOT NULL
);

CREATE TABLE circolo (
    id_circolo INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    indirizzo VARCHAR(100) NOT NULL,
    citta VARCHAR(50) NOT NULL
);

CREATE TABLE torneo (
    id_torneo INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    data_inizio DATE NOT NULL,
    data_fine DATE NOT NULL,
    scadenza_iscrizioni DATE NOT NULL,
    tipo VARCHAR(50) NOT NULL,
    sesso CHAR(1) NOT NULL,
    limite_classifica VARCHAR(20) NOT NULL,
    montepremi DECIMAL(10,2) NOT NULL,
    id_ga INT NOT NULL,
    id_circolo INT NOT NULL,
    FOREIGN KEY (id_ga) REFERENCES giudice_arbitro(id_ga),
    FOREIGN KEY (id_circolo) REFERENCES circolo(id_circolo) ON DELETE CASCADE
);

CREATE TABLE campionato (
    id_campionato INT PRIMARY KEY AUTO_INCREMENT,
    serie VARCHAR(20) NOT NULL,
    categoria VARCHAR(20) NOT NULL,
    sesso CHAR(1) NOT NULL,
    anno INT NOT NULL,
    id_ga INT NOT NULL,
    FOREIGN KEY (id_ga) REFERENCES giudice_arbitro(id_ga)
);

CREATE TABLE premio (
    id_premio INT PRIMARY KEY AUTO_INCREMENT,
    posizione INT NOT NULL,
    valore DECIMAL(10,2) NOT NULL,
    id_torneo INT NOT NULL,
    FOREIGN KEY (id_torneo) REFERENCES torneo(id_torneo) ON DELETE CASCADE
);

CREATE TABLE campo (
    id_campo INT PRIMARY KEY AUTO_INCREMENT,
    numero INT NOT NULL,
    superficie VARCHAR(50) NOT NULL,
    indoor BOOLEAN NOT NULL,
    id_circolo INT NOT NULL,
    FOREIGN KEY (id_circolo) REFERENCES circolo(id_circolo) ON DELETE CASCADE
);

CREATE TABLE squadra (
    id_squadra INT PRIMARY KEY AUTO_INCREMENT,
    id_circolo INT NOT NULL,
    FOREIGN KEY (id_circolo) REFERENCES circolo(id_circolo) ON DELETE CASCADE
);

CREATE TABLE tessera (
    id_tessera INT PRIMARY KEY AUTO_INCREMENT,
    numero VARCHAR(7) UNIQUE NOT NULL,
    scadenza DATE NOT NULL
);

CREATE TABLE giocatore (
    id_giocatore INT PRIMARY KEY AUTO_INCREMENT,
    cognome VARCHAR(50) NOT NULL,
    nome VARCHAR(50) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    data_nascita DATE NOT NULL,
    sesso CHAR(1) NOT NULL,
    telefono VARCHAR(15) NOT NULL,
    username VARCHAR(50) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    classifica VARCHAR(20) NOT NULL DEFAULT '4.NC',
    id_tessera INT UNIQUE NULL,
    id_circolo INT NULL,
    id_squadra INT NULL,
    FOREIGN KEY (id_tessera) REFERENCES tessera(id_tessera),
    FOREIGN KEY (id_circolo) REFERENCES circolo(id_circolo) ON DELETE SET NULL,
    FOREIGN KEY (id_squadra) REFERENCES squadra(id_squadra) ON DELETE SET NULL
);

CREATE TABLE iscrizione_torneo (
    id_iscrizione INT PRIMARY KEY AUTO_INCREMENT,
    data DATE NOT NULL,
    id_giocatore INT NOT NULL,
    id_torneo INT NOT NULL,
    FOREIGN KEY (id_giocatore) REFERENCES giocatore(id_giocatore) ON DELETE CASCADE,
    FOREIGN KEY (id_torneo) REFERENCES torneo(id_torneo) ON DELETE CASCADE
);

CREATE TABLE partita_torneo (
    id_partita_torneo INT PRIMARY KEY AUTO_INCREMENT,
    data DATE NOT NULL,
    risultato VARCHAR(20) NOT NULL,
    id_torneo INT NOT NULL,
    id_campo INT NOT NULL,
    id_arbitro INT NULL,
    FOREIGN KEY (id_torneo) REFERENCES torneo(id_torneo) ON DELETE CASCADE,
    FOREIGN KEY (id_campo) REFERENCES campo(id_campo),
    FOREIGN KEY (id_arbitro) REFERENCES arbitro(id_arbitro)
);

CREATE TABLE iscrizione_squadra_campionato (
    id_squadra INT NOT NULL,
    id_campionato INT NOT NULL,
    PRIMARY KEY (id_squadra, id_campionato),
    FOREIGN KEY (id_squadra) REFERENCES squadra(id_squadra) ON DELETE CASCADE,
    FOREIGN KEY (id_campionato) REFERENCES campionato(id_campionato) ON DELETE CASCADE
);

CREATE TABLE incontro_campionato (
    id_incontro INT PRIMARY KEY AUTO_INCREMENT,
    data DATE NOT NULL,
    risultato VARCHAR(10) NULL,
    id_campionato INT NOT NULL,
    id_squadra_casa INT NOT NULL,
    id_squadra_ospite INT NOT NULL,
    FOREIGN KEY (id_campionato) REFERENCES campionato(id_campionato) ON DELETE CASCADE,
    FOREIGN KEY (id_squadra_casa) REFERENCES squadra(id_squadra),
    FOREIGN KEY (id_squadra_ospite) REFERENCES squadra(id_squadra),
    CHECK (id_squadra_casa <> id_squadra_ospite)
);

CREATE TABLE partita_campionato (
    id_partita_campionato INT PRIMARY KEY AUTO_INCREMENT,
    tipo VARCHAR(20) NOT NULL,
    risultato VARCHAR(20) NOT NULL,
    id_incontro INT NOT NULL,
    id_campo INT NOT NULL,
    id_arbitro INT NULL,
    FOREIGN KEY (id_incontro) REFERENCES incontro_campionato(id_incontro) ON DELETE CASCADE,
    FOREIGN KEY (id_campo) REFERENCES campo(id_campo),
    FOREIGN KEY (id_arbitro) REFERENCES arbitro(id_arbitro)
);

CREATE TABLE giocatore_partita_torneo (
    id_giocatore INT NOT NULL,
    id_partita_torneo INT NOT NULL,
    vincitore BOOLEAN NOT NULL,
    PRIMARY KEY (id_giocatore, id_partita_torneo),
    FOREIGN KEY (id_giocatore) REFERENCES giocatore(id_giocatore) ON DELETE CASCADE,
    FOREIGN KEY (id_partita_torneo) REFERENCES partita_torneo(id_partita_torneo) ON DELETE CASCADE
);

CREATE TABLE giocatore_partita_campionato (
    id_giocatore INT NOT NULL,
    id_partita_campionato INT NOT NULL,
    vincitore BOOLEAN NOT NULL,
    PRIMARY KEY (id_giocatore, id_partita_campionato),
    FOREIGN KEY (id_giocatore) REFERENCES giocatore(id_giocatore) ON DELETE CASCADE,
    FOREIGN KEY (id_partita_campionato) REFERENCES partita_campionato(id_partita_campionato) ON DELETE CASCADE
);
