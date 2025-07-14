INSERT INTO Utenti (id, email, password, amministratore) VALUES

(1, 'luca.carraturo8998@gmail.com', 'lucaadmin1.', 1),

(2, 'bob@example.com', 'bobStandard123!', 0),

(3, 'carla@example.com', 'carlaStandard123!', 0),

(4, 'daniel@example.com', 'danielStandard123!', 0),

(5, 'hater@example.com', 'haterStandard123!', 0),

(6, 'alice@example.com', 'aliceStandard123!', 0);
 
INSERT INTO ProfiliPersonali (nome, cognome, immagineProfilo, biografia, nickname, dataNascita, utente) VALUES

('Luca', 'Carraturo', NULL, 'Appassionato di poesia e scrittura creativa.', 'Lucol8', '2003-11-21', 1),

('Bob', 'Smith', NULL, 'Amante della musica e della letteratura.', 'BobS', '1990-05-22', 2),

('Carla', 'Bianchi', NULL, 'Scrittrice emergente e lettrice instancabile.', 'CarlaB', '1992-11-10', 3),

('Daniel', 'Rossi', NULL, 'Critico letterario e appassionato di teatro.', 'DanielR', '1985-03-30', 4),

('Hater', 'Nero', NULL, 'Sempre pronto a criticare, ma con passione.', 'HaterN', '1995-07-07', 5),

('Alice', 'Verdi', NULL, 'Amante delle arti visive e della poesia moderna.', 'AliceV', '1988-12-01', 6);
 
INSERT INTO Raccolte (id, titolo, descrizione, utente) VALUES

(1, 'Poesie d\'amore', 'Una raccolta di poesie romantiche.', 1),

(2, 'Racconti brevi', 'Storie e racconti di fantasia.', 1),

(3, 'Pensieri sparsi', 'Frammenti di pensieri e riflessioni.', 1),

(4, 'Musica e parole', 'Testi ispirati alla musica.', 2),

(5, 'Viaggi nel tempo', 'Raccolta di storie fantascientifiche.', 2),

(6, 'Emozioni forti', 'Poesie e racconti intensi.', 2),

(7, 'Voci di donne', 'Raccolta di opere femminili.', 3),

(8, 'Sogni e realtà', 'Riflessioni tra sogno e vita reale.', 3),

(9, 'Parole leggere', 'Poesie brevi e delicate.', 3),

(10, 'Critiche letterarie', 'Analisi e recensioni di libri.', 4),

(11, 'Teatro e poesia', 'Spunti e testi teatrali.', 4),

(12, 'Racconti oscuri', 'Storie di mistero e suspense.', 4),

(13, 'Parole taglienti', 'Raccolta di poesie polemiche.', 5),

(14, 'Riflessioni amare', 'Pensieri critici e provocatori.', 5),

(15, 'Voci contro', 'Scritti di contestazione.', 5),

(16, 'Arte e poesia', 'Incontri tra arte visiva e versi.', 6),

(17, 'Poesia moderna', 'Testi contemporanei e innovativi.', 6),

(18, 'Colori e parole', 'Raccolte ispirate ai colori.', 6);
 
INSERT INTO Poesie (id, titolo, tag, body, visibilita, dataPubblicazione, contatoreLike, autore, raccolta) VALUES

(1, 'Amore nascosto', 'amore,romantico', 'Nel silenzio della notte il cuore parla piano.', 1, '2023-05-01', 2, 1, 1),

(2, 'Luna piena', 'notte,luna', 'La luna illumina i sogni mai detti.', 1, '2023-05-10', 2, 1, 2),

(3, 'Cuori lontani', 'amore,distanza', 'Non importa la distanza quando il cuore è vicino.', 1, '2023-06-05', 4, 1, 1),

(4, 'Melodia perduta', 'musica,tristezza', 'Note smarrite nel vento dell’anima.', 1, '2023-04-20', 3, 2, 4),

(5, 'Oltre il tempo', 'tempo,viaggio', 'Viaggio nel tempo dei ricordi perduti.', 1, '2023-04-28', 1, 2, 5),

(6, 'Voce femminile', 'donne,forza', 'La voce delle donne è vento che non si placa.', 1, '2023-03-15', 1, 3, 7),

(7, 'Sogni di carta', 'sogni,leggerezza', 'Sogni piegati come fogli nel vento.', 1, '2023-03-22', 2, 3, 8),

(8, 'Leggerezza', 'poesia,bellezza', 'La bellezza sta nella leggerezza dell’anima.', 1, '2023-03-30', 4, 3, 7),

(9, 'Critica amara', 'critica,letteratura', 'Parole pungenti come spine nascoste.', 1, '2023-02-05', 4, 4, 10),

(10, 'Sogno teatrale', 'teatro,sogno', 'Sul palco della vita recitiamo i nostri sogni.', 1, '2023-02-15', 4, 4, 11),

(11, 'Oscurità', 'mistero,oscurità', 'Nel buio troviamo la vera luce.', 1, '2023-02-25', 1, 4, 10),

(12, 'Scena finale', 'teatro,emozione', 'L’ultimo atto svela verità nascoste.', 1, '2023-03-05', 4, 4, 11),

(13, 'Parole taglienti', 'polemica,critica', 'Le parole sono spade affilate.', 1, '2023-01-10', 3, 5, 13),

(14, 'Amare riflessioni', 'riflessione,tristezza', 'Pensieri amari in notti insonni.', 1, '2023-01-20', 5, 5, 14),

(15, 'Colori e versi', 'arte,poesia', 'I colori dipingono versi sulla tela dell’anima.', 1, '2023-06-01', 4, 6, 16),

(16, 'Poesia moderna', 'contemporaneo,poesia', 'Rime libere in un mondo caotico.', 1, '2023-06-10', 3, 6, 17),

(17, 'Versi leggeri', 'poesia,leggerezza', 'Leggerezza che vola con il vento.', 1, '2023-06-15', 3, 6, 16);
 
INSERT INTO Commenti (id, testo, data, poesia, autore) VALUES

(1, 'Bellissima poesia, mi ha emozionato davvero.', '2023-07-01', 1, 1),

(2, 'Mi sono ritrovato in queste parole.', '2023-07-02', 1, 1),

(3, 'La musica delle parole è perfetta.', '2023-07-03', 4, 2),

(4, 'Molto potente, complimenti.', '2023-07-04', 6, 3),

(5, 'Mi piace come hai espresso la forza.', '2023-07-05', 6, 3),

(6, 'Le parole pungenti come spine, molto vero.', '2023-07-06', 9, 4),

(7, 'Questa poesia mi ricorda solo delusione e rabbia.', '2023-07-07', 13, 5),

(8, 'Non capisco chi possa apprezzare queste parole vuote.', '2023-07-08', 13, 5),

(9, 'Mi fa solo arrabbiare, parla di cose che vorrei dimenticare.', '2023-07-09', 13, 5),

(10, 'I colori e i versi si sposano bene, ottimo lavoro.', '2023-07-10', 15, 6);
INSERT INTO Apprezzamenti (id, autore, poesia) VALUES

(1, 1, 2),

(2, 1, 3),

(3, 1, 4),

(4, 1, 8),

(5, 1, 9),

(6, 1, 10),

(7, 1, 12),

(8, 1, 13),

(9, 1, 14),

(10, 1, 15),

(11, 1, 16),

(12, 1, 17),

(13, 2, 3),

(14, 2, 6),

(15, 2, 10),

(16, 2, 13),

(17, 2, 14),

(18, 2, 15),

(19, 2, 16),

(20, 3, 7),

(21, 3, 8),

(22, 3, 9),

(23, 3, 14),

(24, 4, 1),

(25, 4, 4),

(26, 4, 5),

(27, 4, 8),

(28, 4, 9),

(29, 4, 10),

(30, 4, 12),

(31, 4, 14),

(32, 4, 15),

(33, 4, 16),

(34, 4, 17),

(35, 5, 1),

(36, 5, 2),

(37, 5, 3),

(38, 5, 4),

(39, 5, 7),

(40, 5, 8),

(41, 5, 9),

(42, 5, 10),

(43, 5, 12),

(44, 5, 14),

(45, 5, 17),

(46, 6, 3),

(47, 6, 11),

(48, 6, 12),

(49, 6, 13),

(50, 6, 15);
