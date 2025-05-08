INSERT INTO giocatore (cognome, nome, email, data_nascita, sesso, telefono, username, password_hash)
VALUES ('Pino', 'Gino', 'gpino@mail.com', '1990-01-01', 'M', '333333333', 'gpino', 'ciao');

INSERT INTO giudice_arbitro (cognome, nome, email, data_nascita, sesso, telefono, username, password_hash)
VALUES ('Rossi', 'Mario', 'mrossi@mail.com', '1975-05-15', 'M', '333444444', 'admin', 'admin');

INSERT INTO circolo (nome, indirizzo, citta)
VALUES ('Circolo Tennis Roma', 'Via Bologna 1', 'Roma'),
       ('Circolo Tennis Milano', 'Via Rimini 24', 'Milano'),
       ('Circolo Tennis Napoli', 'Via Vesuviana 51', 'Napoli');