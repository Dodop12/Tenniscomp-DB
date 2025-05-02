CREATE DATABASE IF NOT EXISTS tenniscomp;
USE tenniscomp;

DROP TABLE IF EXISTS giudice_arbitro;
CREATE TABLE giudice_arbitro (
    id_ga INT PRIMARY KEY AUTO_INCREMENT,
    cognome VARCHAR(50),
    nome VARCHAR(50),
    email VARCHAR(100) UNIQUE NOT NULL,
    data_nascita DATE,
    sesso CHAR(1),
    telefono VARCHAR(15),
    username VARCHAR(50) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    qualifica VARCHAR(10) DEFAULT 'GAQR'
);

DROP TABLE IF EXISTS arbitro;
CREATE TABLE arbitro (
    id_arbitro INT PRIMARY KEY AUTO_INCREMENT,
    cognome VARCHAR(50),
    nome VARCHAR(50),
    email VARCHAR(100) UNIQUE NOT NULL,
    data_nascita DATE,
    sesso CHAR(1),
    telefono VARCHAR(15),
    qualifica VARCHAR(10)
);

DROP TABLE IF EXISTS circolo;
CREATE TABLE circolo (
    id_circolo INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100),
    indirizzo VARCHAR(100),
    citta VARCHAR(50)
);

DROP TABLE IF EXISTS competizione;
CREATE TABLE competizione (
    id_competizione INT PRIMARY KEY AUTO_INCREMENT,
    id_ga INT,
    FOREIGN KEY (id_ga) REFERENCES giudice_arbitro(id_ga)
);

DROP TABLE IF EXISTS torneo;
CREATE TABLE torneo (
    id_competizione INT PRIMARY KEY,
    nome VARCHAR(100),
    data_inizio DATE,
    data_fine DATE,
    scadenza_iscrizioni DATE,
    tipo VARCHAR(50),
    limite_classifica VARCHAR(20),
    montepremi DECIMAL(10,2),
    id_circolo INT NOT NULL,
    FOREIGN KEY (id_competizione) REFERENCES competizione(id_competizione),
    FOREIGN KEY (id_circolo) REFERENCES circolo(id_circolo)
);

DROP TABLE IF EXISTS campionato;
CREATE TABLE campionato (
    id_competizione INT PRIMARY KEY,
    serie VARCHAR(20),
    categoria VARCHAR(20),
    sesso CHAR(1),
    anno INT,
    FOREIGN KEY (id_competizione) REFERENCES competizione(id_competizione)
);

DROP TABLE IF EXISTS premio;
CREATE TABLE premio (
    id_premio INT PRIMARY KEY AUTO_INCREMENT,
    posizione INT,
    valore DECIMAL(10,2),
    id_torneo INT NOT NULL,
    FOREIGN KEY (id_torneo) REFERENCES torneo(id_competizione)
);

DROP TABLE IF EXISTS campo;
CREATE TABLE campo (
    id_campo INT PRIMARY KEY AUTO_INCREMENT,
    numero INT,
    superficie VARCHAR(50),
    indoor BOOLEAN,
    id_circolo INT NOT NULL,
    FOREIGN KEY (id_circolo) REFERENCES circolo(id_circolo)
);

DROP TABLE IF EXISTS squadra;
CREATE TABLE squadra (
    id_squadra INT PRIMARY KEY AUTO_INCREMENT,
    id_circolo INT NOT NULL,
    FOREIGN KEY (id_circolo) REFERENCES circolo(id_circolo)
);

DROP TABLE IF EXISTS tessera;
CREATE TABLE tessera (
    id_tessera INT PRIMARY KEY,
    numero VARCHAR(20) UNIQUE NOT NULL,
    scadenza DATE,
);

DROP TABLE IF EXISTS giocatore;
CREATE TABLE giocatore (
    id_giocatore INT PRIMARY KEY AUTO_INCREMENT,
    cognome VARCHAR(50),
    nome VARCHAR(50),
    email VARCHAR(100) UNIQUE NOT NULL,
    data_nascita DATE,
    sesso CHAR(1),
    telefono VARCHAR(15),
    username VARCHAR(50) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    classifica VARCHAR(20) DEFAULT '4.NC',
    id_tessera INT UNIQUE,
    id_circolo INT,
    id_squadra INT,
    FOREIGN KEY (id_tessera) REFERENCES tessera(id_tessera),
    FOREIGN KEY (id_circolo) REFERENCES circolo(id_circolo),
    FOREIGN KEY (id_squadra) REFERENCES squadra(id_squadra)
);

DROP TABLE IF EXISTS partita;
CREATE TABLE partita (
    id_partita INT PRIMARY KEY AUTO_INCREMENT,
    tipo VARCHAR(20), -- "singolare" o "doppio"
    vincitore VARCHAR(100),
    risultato VARCHAR(20),
    id_competizione INT NOT NULL,
    id_campo INT NOT NULL,
    id_arbitro INT,
    FOREIGN KEY (id_competizione) REFERENCES competizione(id_competizione),
    FOREIGN KEY (id_campo) REFERENCES campo(id_campo),
    FOREIGN KEY (id_arbitro) REFERENCES arbitro(id_arbitro)
);

DROP TABLE IF EXISTS incontro_campionato;
CREATE TABLE incontro_campionato (
    id_incontro INT PRIMARY KEY AUTO_INCREMENT,
    data DATE,
    id_campionato INT,
    FOREIGN KEY (id_campionato) REFERENCES campionato(id_competizione)
);

DROP TABLE IF EXISTS iscrizione;
CREATE TABLE iscrizione (
    id_iscrizione INT PRIMARY KEY AUTO_INCREMENT,
    data DATE,
    id_giocatore INT NOT NULL,
    id_torneo INT NOT NULL,
    FOREIGN KEY (id_giocatore) REFERENCES giocatore(id_giocatore),
    FOREIGN KEY (id_torneo) REFERENCES torneo(id_competizione)
);