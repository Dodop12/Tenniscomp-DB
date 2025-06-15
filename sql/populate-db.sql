INSERT INTO giudice_arbitro (cognome, nome, email, data_nascita, sesso, telefono, username, password_hash)
-- pw: admin
VALUES  ('Rossi', 'Mario', 'mrossi@mail.com', '1975-05-15', 'M', '333444444', 'admin', 'UUZZRTWXvQSsKaJz+IROEg==:ka4OYgbbh0LjqqC0u9flya9Qc47Ava5SWCyCAREIiSA=');

INSERT INTO arbitro (cognome, nome, email, data_nascita, sesso, telefono, qualifica)
VALUES  ('Ferrari', 'Luca', 'luca.ferrari@mail.it', '1985-05-12', 'M', '3337777888', 'ARBN2'),
        ('Romano', 'Giulia', 'giulia.romano@mail.it', '1990-09-30', 'F', '3334444555', 'ARBN2'),
        ('Conti', 'Marco', 'marco.conti@mail.it', '1988-01-18', 'M', '3332222333', 'ARBN1');

INSERT INTO circolo (nome, indirizzo, citta)
VALUES  ('Tennis Club Roma Nord', 'Via del Tennis 15', 'Roma'),
        ('Circolo Sportivo Milano', 'Via Buenos Aires 45', 'Milano'),
        ('Tennis Academy Napoli', 'Via Partenope 12', 'Napoli'),
        ('Club Torino Elite', 'Via Po 88', 'Torino'),
        ('Tennis Center Bologna', 'Via Indipendenza 33', 'Bologna'),
        ('Sporting Club Firenze', 'Viale Michelangelo 7', 'Firenze'),
        ('Tennis Village Palermo', 'Via Libertà 22', 'Palermo'),
        ('Racquet Club Venezia', 'Via dei Pini 5', 'Venezia');

INSERT INTO campo (numero, superficie, indoor, id_circolo) VALUES
-- Tennis Club Roma Nord (4 campi)
(1, 'Terra rossa', false, 1),
(2, 'Terra rossa', false, 1),
(3, 'Cemento', true, 1),
(4, 'Erba', false, 1),
-- Circolo Sportivo Milano (3 campi)
(1, 'Cemento', true, 2),
(2, 'Cemento', true, 2),
(3, 'Terra rossa', false, 2),
-- Tennis Academy Napoli (5 campi)
(1, 'Terra rossa', false, 3),
(2, 'Terra rossa', false, 3),
(3, 'Cemento', true, 3),
(4, 'Cemento', false, 3),
(5, 'Erba', false, 3),
-- Club Torino Elite (2 campi)
(1, 'Terra rossa', true, 4),
(2, 'Cemento', true, 4),
-- Tennis Center Bologna (3 campi)
(1, 'Terra rossa', false, 5),
(2, 'Cemento', false, 5),
(3, 'Cemento', true, 5),
-- Sporting Club Firenze (4 campi)
(1, 'Terra rossa', false, 6),
(2, 'Terra rossa', false, 6),
(3, 'Cemento', true, 6),
(4, 'Erba', false, 6),
-- Tennis Village Palermo (2 campi)
(1, 'Terra rossa', false, 7),
(2, 'Cemento', false, 7),
-- Racquet Club Venezia (3 campi)
(1, 'Cemento', true, 8),
(2, 'Terra rossa', false, 8),
(3, 'Erba', false, 8);

INSERT INTO tessera (numero, scadenza) VALUES
('2025001', '2025-12-31'),
('2025002', '2025-12-31'),
('2025003', '2025-12-31'),
('2025004', '2025-12-31'),
('2025005', '2025-12-31'),
('2025006', '2025-12-31'),
('2025007', '2025-12-31'),
('2025008', '2025-12-31'),
('2025009', '2025-12-31'),
('2025010', '2025-12-31'),
('2025011', '2025-12-31'),
('2025012', '2025-12-31'),
('2025013', '2025-12-31'),
('2025014', '2025-12-31'),
('2025015', '2025-12-31'),
('2025016', '2025-12-31'),
('2025017', '2025-12-31'),
('2025018', '2025-12-31'),
('2025019', '2025-12-31'),
('2025020', '2025-12-31'),
('2025021', '2025-12-31'),
('2025022', '2025-12-31'),
('2025023', '2025-12-31'),
('2025024', '2025-12-31'),
('2025025', '2025-12-31'),
('2025026', '2025-12-31'),
('2025027', '2025-12-31'),
('2025028', '2025-12-31'),
('2025029', '2025-12-31'),
('2025030', '2025-12-31'),
('2024999', '2024-12-31'); -- Expired card for testing

INSERT INTO squadra (id_circolo)
VALUES  (1), (2), (3), (4), (5), (6), (7), (8), -- M
        (1), (2), (3), (4), (5), (6), (7), (8); -- F

INSERT INTO giocatore (cognome, nome, email, data_nascita, sesso, telefono, username, password_hash, classifica, id_tessera, id_circolo, id_squadra)
VALUES  ('Rossi', 'Marco', 'marco.rossi@mail.it', '1990-03-12', 'M', '3339999999', 'mrossi', '0ONHYV9gtfq+a+8yx0Nmww==:Rr4Q9KDj0x6T5NMtWrDKrajCJqUsc7eRiZ0zTSyXFmE=', '3.2', 1, 1, 1), -- pw: pw1
        ('Bianchi', 'Luca', 'luca.bianchi@mail.it', '1991-07-25', 'M', '3330000000', 'lbianchi', '/xnTYcnzIswLZLhQc2yHPA==:D18gBXavASJhDeZQEAO+/Iu2QmTBmi96sKRFN8sOR/A=', '3.3', 2, 1, 1), -- pw: pw2
        ('Verdi', 'Alessandro', 'alessandro.verdi@mail.it', '1989-11-03', 'M', '3331010101', 'averdi', 'Rj4wP3zRsxUsU6YNif5VpQ==:SwRBBRlAFvdotJa+gcP1dDLV+snC5i6sLQG1gzk+Qks=', '3.4', 3, 2, 2), -- pw: pw3
        ('Ferrari', 'Giovanni', 'giovanni.ferrari@mail.it', '1992-01-18', 'M', '3332020202', 'gferrari', 'A4/x3rH8QAgpdW3mpVNrwA==:dPofjpVWC2WFE7Sy3qRz18iXBMUArHcWJQScOJgnzDY=', '3.5', 4, 2, NULL), -- pw: pw4
        ('Colombo', 'Matteo', 'matteo.colombo@mail.it', '1988-05-20', 'M', '3333030303', 'mcolombo', 'fARjYWl4tZVs19TebtGqzA==:QD7jljQVW5xmI1oGaSuj0vWYKZT1QAe0UPGPf/I7rEk=', '3.2', 5, 3, 3), -- pw: pw5
        ('Esposito', 'Antonio', 'antonio.esposito@mail.it', '1990-08-14', 'M', '3334040404', 'aesposito', 'A5Dyj+bqatIEdOeMDfBMGA==:+d49GLiF/og5zjFYBXTFWTmoJsJBctHw6jLC0B7/2sk=', '3.3', 6, 3, 3), -- pw: pw6
        ('Rizzo', 'Francesco', 'francesco.rizzo@mail.it', '1987-12-03', 'M', '3335050505', 'frizzo', '7fZRXLZCabhgRyopxB9N/A==:mrlEl5Xb1dM+4WY4mUGIa+cQ+Bav9v0YYBvFC/+7X6k=', '3.4', 7, 4, 4), -- pw: pw7
        ('Galli', 'Stefano', 'stefano.galli@mail.it', '1991-04-27', 'M', '3336060606', 'sgalli', '7VFCicpsVXa3JQnauZh8ww==:Ziw0cVo6k1kyH8vGl6E4tCbF2/0Twc6at5sBiITsWFI=', '3.5', 8, 5, 5), -- pw: pw8

        ('Romano', 'Elena', 'elena.romano@mail.it', '1993-05-30', 'F', '3337070707', 'eromano', '5OUGq4nhVwr5mpLBpgvCeg==:A5CMNIMd1kOyDX08UdWbReyEskp7YwVageTFklLfLik=', '3.2', 9, NULL, NULL), -- pw: pw9
        ('Conti', 'Francesca', 'francesca.conti@mail.it', '1990-12-14', 'F', '3338080808', 'fconti', 'clZxivPGSSsk4sUuo3ag9Q==:vvQIEsd9qHMlZDdW1GQEvkAtNwvXzSKZ+qplYdA1SiI=', '3.3', 10, 2, 10), -- pw: pw10
        ('Moretti', 'Giulia', 'giulia.moretti@mail.it', '1988-08-09', 'F', '3339090909', 'gmoretti', '9WbeG+wd5VSWWzyIBsh//w==:Uqx+W31AEhCinzGxEvQH6/hqZxm8/F834dObmarz5c4=', '3.4', 11, 3, 11), -- pw: pw11
        ('Ricci', 'Valentina', 'valentina.ricci@mail.it', '1991-10-22', 'F', '3330101010', 'vricci', 'AWckEr5JD6utx2IlT+c11Q==:8b694ZBebfCm/L+6+OZIuk4+93q35JEqFTP/E+BCKNY=', '3.5', 12, 4, NULL), -- pw: pw12
        ('Mancini', 'Laura', 'laura.mancini@mail.it', '1989-06-18', 'F', '3331111111', 'lmancini', '4HXvvPWF0mcVdLAW4ewh1Q==:KqxxpDptHzCUW4r/1+RH2RQdkUZL9n1fQl1Hf1hvX+U=', '3.2', 13, 5, 13), -- pw: pw13
        ('Santoro', 'Silvia', 'silvia.santoro@mail.it', '1992-09-25', 'F', '3332222222', 'ssantoro', '69D6SMSz4YNcczele6wQyA==:GEA0k6cc+TylEtH7V1c0eNvremsbEjdlpI8xMpRWmaA=', '3.3', 14, 6, 14), -- pw: pw14
        ('De Luca', 'Chiara', 'chiara.deluca@mail.it', '1990-02-11', 'F', '3333333333', 'cdeluca', 'unUKJroiYLZy4OvwYqBCbQ==:OLwCmWQWiuxgE8ty29wzCWXfDZ0UPwSwe6fhkHSWuhU=', '3.4', 15, 7, 15), -- pw: pw15
        ('Marini', 'Alessia', 'alessia.marini@mail.it', '1987-11-07', 'F', '3334444444', 'amarini', '1u1rj84PLQ9CgA0iIQemog==:Lb3HkpWxI92qFGKiDZ9X++ray6JThm5mGttTszAE0DA=', '3.5', 16, 8, 16), -- pw: pw16

        ('Fontana', 'Paolo', 'paolo.fontana@mail.it', '1985-02-28', 'M', '3335555555', 'pfontana', 'zvzbOG3Cw1t1tC2472jKSQ==:ej8ZDRO14C3wRGbFH/ZIWDdaACZvDQzEWTKniWXWzvE=', '4.1', 17, 1, 1), -- pw: pw17
        ('Martini', 'Stefano', 'stefano.martini@mail.it', '1987-06-17', 'M', '3336666666', 'smartini', 'A1vjgmiatcm2hSh/nOuCtQ==:oeqx8zBfXHkuqPqFtdPNap+vFHRxKGZZQPbA8DMAnPU=', '4.2', 18, 2, NULL), -- pw: pw18
        ('Greco', 'Roberto', 'roberto.greco@mail.it', '1986-09-05', 'M', '3337777777', 'rgreco', 'KEQ9oRK+Y+Pys4DaI+Vxug==:E9mubnxgj1snJVEz42h/urFiHeF6E4n9F4yXI8Ib/1Y=', '4.3', 19, 3, 3), -- pw: pw19
        ('Gallo', 'Antonio', 'antonio.gallo@mail.it', '1984-12-11', 'M', '3338888888', 'agallo', 'rpuwDuMKKIa1qwhHUWd/qQ==:evzArCdXJ7Lnn2vSGypumpJ31mlBZ0XHS2XaDYAxThY=', '4.NC', 20, 4, 4), -- pw: pw20
        ('Serra', 'Davide', 'davide.serra@mail.it', '1983-03-20', 'M', '3339999999', 'dserra', 'LA9wz2oEyVQ9XT0t0qUn7Q==:mhKcEDqGlCZiAuQX8+jgPwp/h2VIZOf9g0R1VZzxVm0=', '4.1', 21, 5, 5), -- pw: pw21
        ('Longo', 'Michele', 'michele.longo@mail.it', '1985-07-12', 'M', '3330000001', 'mlongo', 't/PdnpUMoj9U233RQBZDfA==:zDF/W2KjSammlKdJF6kBBDDlE8iH4EAvj+gcvNC+XHE=', '4.2', 22, 6, 6), -- pw: pw22
        ('Caruso', 'Valerio', 'valerio.caruso@mail.it', '1988-10-16', 'M', '3331111112', 'vcaruso', 'HFUt8/BwWOQR22cMYdcXow==:Sg4Kj2/nPw97BSiAFz2KOOB2O1OKmNqQZQoiX31dKuw=', '4.3', 23, NULL, NULL), -- pw: pw23
        ('Vitale', 'Simone', 'simone.vitale@mail.it', '1986-01-30', 'M', '3332222223', 'svitale', 'SGFac8LBfULozoE148m5Dg==:C4AIxGi6oWZij7sTI3JBc1c87XtDmjoOc7qY7aGw9sA=', '4.NC', 24, 8, 8), -- pw: pw24
        ('Bruno', 'Carla', 'carla.bruno@mail.it', '1988-04-03', 'F', '3333333334', 'cbruno', '8rseK0oAdwlt3BdR3EK6vA==:2O+UmxKvnA+lKegon0xUBhvj5ze6ASWhSyA29tUgqnI=', '4.1', 25, 1, 9), -- pw: pw25
        ('Leone', 'Laura', 'laura.leone@mail.it', '1989-11-29', 'F', '3334444445', 'lleone', 'BTkZSAMYAtASpJtseJFFiw==:EpAXBV9IeJvWTgpPEGL9amVumC+tnH57YchaUL8J0Uc=', '4.2', 26, 2, 10), -- pw: pw26
        ('Testa', 'Monica', 'monica.testa@mail.it', '1987-08-22', 'F', '3335555556', 'mtesta', 'YdY/yeh5CNQPmF5/r6+JQA==:63Y1CQFdTfaEBhT8EumQHzSj42IKgn9wnEbZRvMGvok=', '4.3', 27, 3, 11), -- pw: pw27
        ('Monti', 'Paola', 'paola.monti@mail.it', '1985-12-05', 'F', '3336666667', 'pmonti', 'NcPPkynw2XHING2ib5Vo5g==:04EH1hb3Hk4aSPEs9QehAj4mEo3JrrD0y9lFvGBlAmI=', '4.NC', 28, 4, 12), -- pw: pw28
        ('Sanna', 'Isabella', 'isabella.sanna@mail.it', '1990-05-14', 'F', '3337777778', 'isanna', 'sl3DO0O7hgcZHiUyU3ECaw==:JXN3KvDFuf+IBf8BdTkc0o3Gh7oDcyR+zz6/j2YeEiI=', '4.1', 29, NULL, NULL), -- pw: pw29
        ('Pellegrini', 'Anna', 'anna.pellegrini@mail.it', '1986-09-08', 'F', '3338888889', 'apellegrini', 'WX1BGUw8Ooe2qed6n1G/Fw==:ZnHshIpbfxRhjHxnOBo9fxysLPkkO+cb4/jUYmBJ6yU=', '4.2', 30, 6, 14), -- pw: pw30
        ('Orlando', 'Michela', 'michela.orlando@mail.it', '1991-03-17', 'F', '3339999990', 'morlando', 'zU+iQgmYlsXz5zBWdJk3zg==:R7kRKOA0vmwhU1S/pLoWlMFA4pjz49SCdFcB7nhGEG4=', '4.3', NULL, NULL, NULL), -- pw: pw31; no card
        ('Fabbri', 'Cristina', 'cristina.fabbri@mail.it', '1989-07-23', 'F', '3330000002', 'cfabbri', 'v/kDiMc8D+n3sYaWx3bwVg==:ZWaHO2WkpEc3DkS3YF55q6IS52euh9GJGyklM3YQQLQ=', '4.NC', 31, 8, NULL); -- pw: pw32; expired card

INSERT INTO torneo (nome, data_inizio, data_fine, scadenza_iscrizioni, tipo, sesso, limite_classifica, montepremi, id_ga, id_circolo) VALUES
('Open di Roma 2025', '2025-07-15', '2025-07-29', '2025-07-13', 'Singolare', 'M', '1', 5000.00, 1, 1),
('Torneo Femminile Milano', '2025-08-01', '2025-08-08', '2025-07-30', 'Singolare', 'F', '3.1', 4000.00, 1, 2),
('Doppio Maschile Napoli', '2025-08-15', '2025-08-22', '2025-08-13', 'Doppio', 'M', '2.8', 300.00, 1, 3),
('Campionato Regionale 3°-4° Categoria Torino', '2025-09-01', '2025-09-13', '2025-08-28', 'Singolare', 'M', '3.1', 2000.00, 1, 4),

('Open Estate 2024', '2024-06-15', '2024-06-22', '2024-06-01', 'Singolare', 'M', '1', 3500.00, 1, 1),
('Torneo Primavera 2024', '2024-05-01', '2024-05-08', '2024-04-15', 'Singolare', 'F', '3.1', 2800.00, 1, 2),

('Master 4° Categoria Bologna', '2025-06-20', '2025-06-25', '2025-06-05', 'Singolare', 'M', '4.1', 500.00, 1, 5);

INSERT INTO premio (posizione, valore, id_torneo) VALUES
-- Open di Roma 2025
(1, 2500.00, 1), (2, 1500.00, 1), (3, 500.00, 1),
-- Torneo Femminile Milano
(1, 2000.00, 2), (2, 1200.00, 2), (3, 400.00, 2),
-- Doppio Maschile Napoli
(1, 125.00, 3), (2, 75.00, 3), (3, 0.00, 3),
-- Campionato Regionale 3°-4° Categoria Torino
(1, 1000.00, 4), (2, 500.00, 4), (3, 250.00, 4),
-- Open Estate 2024
(1, 1750.00, 5), (2, 1050.00, 5), (3, 350.00, 5),
-- Torneo Primavera 2024
(1, 1400.00, 6), (2, 800.00, 6), (3, 300.00, 6),
-- Master 4° Categoria Bologna
(1, 310.00, 7), (2, 190.00, 7), (3, 0.00, 7);

INSERT INTO iscrizione_torneo (data, id_giocatore, id_torneo)
VALUES  ('2025-06-15', 1, 1), ('2025-06-16', 2, 1), ('2025-06-17', 3, 1), ('2025-06-18', 4, 1),
        ('2025-06-19', 5, 1), ('2025-06-20', 6, 1), ('2025-06-21', 7, 1), ('2025-06-22', 8, 1),

        ('2025-06-20', 9, 2), ('2025-06-21', 10, 2), ('2025-06-22', 11, 2), ('2025-06-23', 12, 2),
        ('2025-06-24', 13, 2), ('2025-06-25', 14, 2), ('2025-06-26', 15, 2), ('2025-06-27', 16, 2),

        ('2025-07-01', 1, 3), ('2025-07-02', 2, 3),
        ('2025-07-03', 3, 3), ('2025-07-04', 4, 3),

        ('2024-05-15', 1, 5), ('2024-05-16', 2, 5), ('2024-05-17', 3, 5), ('2024-05-18', 4, 5),
        ('2024-05-19', 5, 5), ('2024-05-20', 6, 5), ('2024-05-21', 7, 5), ('2024-05-22', 8, 5),
        ('2024-04-10', 9, 6), ('2024-04-11', 10, 6), ('2024-04-12', 11, 6), ('2024-04-13', 12, 6),
        ('2024-04-14', 13, 6), ('2024-04-15', 14, 6), ('2024-04-16', 15, 6), ('2024-04-17', 16, 6);

INSERT INTO partita_torneo (data, risultato, id_torneo, id_campo, id_arbitro)
VALUES  ('2024-06-16', '6/4 6/2', 5, 1, NULL), -- Open Estate 2024
        ('2024-06-17', '7/5 3/6 6/4', 5, 2, NULL),
        ('2024-06-18', '6/3 6/1', 5, 3, NULL),
        ('2024-06-19', '6/2 7/5', 5, 4, NULL),
        ('2024-06-20', '6/4 7/6', 5, 1, 1),
        ('2024-06-21', '7/5 6/3', 5, 2, 2),
        ('2024-06-22', '6/2 6/3', 5, 1, 1),
        ('2024-05-02', '6/2 6/4', 6, 7, NULL), -- Torneo Primavera 2024
        ('2024-05-03', '7/5 6/3', 6, 8, NULL),
        ('2024-05-04', '6/1 6/2', 6, 9, NULL),
        ('2024-05-05', '6/4 6/4', 6, 10, NULL),
        ('2024-05-06', '6/4 6/4', 6, 7, 2),
        ('2024-05-07', '7/6 6/2', 6, 8, 2),
        ('2024-05-08', '6/3 7/5', 6, 7, 1);

INSERT INTO giocatore_partita_torneo (id_giocatore, id_partita_torneo, vincitore)
VALUES  (1, 1, true), (2, 1, false), -- Open Estate 2024
        (3, 2, true), (4, 2, false),
        (5, 3, false), (6, 3, true),   
        (7, 4, false), (8, 4, true),   
        (1, 5, true), (3, 5, false), 
        (6, 6, false), (8, 6, true),
        (1, 7, true), (8, 7, false),

        (9, 8, true), (10, 8, false),  -- Torneo Primavera 2024
        (11, 9, false), (12, 9, true), 
        (13, 10, true), (14, 10, false), 
        (15, 11, false), (16, 11, true), 
        (9, 12, true), (12, 12, false), 
        (13, 13, false), (16, 13, true), 
        (9, 14, true), (16, 14, false);

INSERT INTO campionato (serie, categoria, sesso, anno, id_ga)
VALUES  ('B2', 'Open', 'M', 2025, 1);

INSERT INTO iscrizione_squadra_campionato (id_squadra, id_campionato)
VALUES  (1, 1), (3, 1), (4, 1), (5, 1);

INSERT INTO incontro_campionato (data, id_campionato, id_squadra_casa, id_squadra_ospite)
VALUES  ('2025-03-15', 1, 1, 3),
        ('2025-03-22', 1, 3, 4),
        ('2025-04-05', 1, 5, 1),
        ('2025-04-12', 1, 2, 3);

INSERT INTO partita_campionato (tipo, risultato, id_incontro, id_campo, id_arbitro)
VALUES  ('Singolare', '6/4 6/2', 1, 1, 1),
        ('Singolare', '7/5 3/6 6/4', 1, 2, 2),
        ('Doppio', '6/3 6/1', 1, 1, 2),

        ('Singolare', '6/2 7/5', 2, 8, 3),
        ('Singolare', '6/4 7/6', 2, 9, 1),
        ('Doppio', '7/5 6/3', 2, 9, 3),

        ('Singolare', '6/2 6/3', 3, 16, 2),
        ('Singolare', '6/1 6/4', 3, 17, 1),
        ('Doppio', '7/6 6/2', 3, 17, 1),

        ('Singolare', '6/4 6/3', 4, 5, 3),
        ('Singolare', '6/2 7/5', 4, 6, 2),
        ('Doppio', '7/5 6/4', 4, 5, 2);

INSERT INTO giocatore_partita_campionato (id_giocatore, id_partita_campionato, vincitore)
VALUES  (1, 1, true), (5, 1, false),
        (17, 2, false), (19, 2, true),
        (1, 3, true), (17, 3, true), (5, 3, false), (19, 3, false),

        (5, 4, true), (7, 4, false),
        (19, 5, false), (20, 5, true),
        (5, 6, false), (19, 6, false), (7, 6, true), (20, 6, true),

        (1, 7, false), (21, 7, true),
        (17, 8, true), (22, 8, false),
        (17, 9, false), (1, 9, false), (22, 9, true), (21, 9, true),

        (3, 10, true), (5, 10, false),
        (4, 11, false), (19, 11, true),
        (3, 12, true), (4, 12, true), (5, 12, false), (19, 12, false);
