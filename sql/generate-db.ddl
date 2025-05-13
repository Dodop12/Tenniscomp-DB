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

CREATE TABLE competizione (
    id_competizione INT PRIMARY KEY AUTO_INCREMENT,
    tipo VARCHAR(20) NOT NULL CHECK (tipo IN ('torneo', 'campionato'))
);

CREATE TABLE torneo (
    id_competizione INT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    data_inizio DATE NOT NULL,
    data_fine DATE NOT NULL,
    scadenza_iscrizioni DATE NOT NULL,
    tipo VARCHAR(50) NOT NULL,
    limite_classifica VARCHAR(20) NOT NULL,
    montepremi DECIMAL(10,2) NOT NULL,
    id_ga INT NOT NULL,
    id_circolo INT NOT NULL,
    FOREIGN KEY (id_competizione) REFERENCES competizione(id_competizione) ON DELETE CASCADE,
    FOREIGN KEY (id_ga) REFERENCES giudice_arbitro(id_ga),
    FOREIGN KEY (id_circolo) REFERENCES circolo(id_circolo)
);

CREATE TABLE campionato (
    id_competizione INT PRIMARY KEY,
    serie VARCHAR(20) NOT NULL,
    categoria VARCHAR(20) NOT NULL,
    sesso CHAR(1) NOT NULL,
    anno INT NOT NULL,
    id_ga INT NOT NULL,
    FOREIGN KEY (id_competizione) REFERENCES competizione(id_competizione) ON DELETE CASCADE,
    FOREIGN KEY (id_ga) REFERENCES giudice_arbitro(id_ga)
);

CREATE TABLE premio (
    id_premio INT PRIMARY KEY AUTO_INCREMENT,
    posizione INT NOT NULL,
    valore DECIMAL(10,2) NOT NULL,
    id_torneo INT NOT NULL,
    FOREIGN KEY (id_torneo) REFERENCES torneo(id_competizione) ON DELETE CASCADE
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

CREATE TABLE partita (
    id_partita INT PRIMARY KEY AUTO_INCREMENT,
    tipo VARCHAR(20) NOT NULL, -- "singolare" o "doppio"
    vincitore VARCHAR(100) NOT NULL,
    risultato VARCHAR(20) NOT NULL,
    id_competizione INT NOT NULL,
    id_campo INT NOT NULL,
    id_arbitro INT NULL,
    FOREIGN KEY (id_competizione) REFERENCES competizione(id_competizione) ON DELETE CASCADE,
    FOREIGN KEY (id_campo) REFERENCES campo(id_campo),
    FOREIGN KEY (id_arbitro) REFERENCES arbitro(id_arbitro)
);

CREATE TABLE incontro_campionato (
    id_incontro INT PRIMARY KEY AUTO_INCREMENT,
    data DATE NOT NULL,
    id_competizione INT NOT NULL,
    FOREIGN KEY (id_competizione) REFERENCES campionato(id_competizione) ON DELETE CASCADE
);

CREATE TABLE iscrizione (
    id_iscrizione INT PRIMARY KEY AUTO_INCREMENT,
    data DATE NOT NULL,
    id_giocatore INT NOT NULL,
    id_torneo INT NOT NULL,
    FOREIGN KEY (id_giocatore) REFERENCES giocatore(id_giocatore) ON DELETE CASCADE,
    FOREIGN KEY (id_torneo) REFERENCES torneo(id_competizione) ON DELETE CASCADE
);
