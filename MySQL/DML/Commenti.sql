INSERT INTO Commenti (id, testo, data, poesia, autore) VALUES
-- Utente 1 commenta poesia id 1
(1, 'Bellissima poesia, mi ha emozionato davvero.', '2023-07-01', 1, 1),
(2, 'Mi sono ritrovato in queste parole.', '2023-07-02', 1, 1),

-- Utente 2 commenta poesia id 4
(3, 'La musica delle parole è perfetta.', '2023-07-03', 4, 2),

-- Utente 3 commenta poesia id 6
(4, 'Molto potente, complimenti.', '2023-07-04', 6, 3),
(5, 'Mi piace come hai espresso la forza.', '2023-07-05', 6, 3),

-- Utente 4 commenta poesia id 9
(6, 'Le parole pungenti come spine, molto vero.', '2023-07-06', 9, 4),

-- Utente 5 (hater) commenti negativi su poesia id 13
(7, 'Questa poesia mi ricorda solo delusione e rabbia.', '2023-07-07', 13, 5),
(8, 'Non capisco chi possa apprezzare queste parole vuote.', '2023-07-08', 13, 5),
(9, 'Mi fa solo arrabbiare, parla di cose che vorrei dimenticare.', '2023-07-09', 13, 5),

-- Utente 6 commenta poesia id 15
(10, 'I colori e i versi si sposano bene, ottimo lavoro.', '2023-07-10', 15, 6);
