Guida all’uso del servizio di invio email in PoetUp

Questo progetto include un piccolo servizio che permette di inviare email, utile ad esempio per:

confermare la registrazione di un utente

notificare un nuovo like o commento ricevuto

inviare un report da parte dell’amministratore

1. Dove si trovano le classi?
Il servizio si trova nel package:

cpp
Copia
Modifica
mail
├── EmailMessage.java       // semplice classe che incapsula il messaggio
└── EmailService.java       // classe che gestisce l’invio dell’email
2. Come si usa?
Nel tuo codice, importa il servizio:

java
Copia
Modifica
import mail.EmailService;
import mail.EmailMessage;
Poi, istanzia il servizio:

java
Copia
Modifica
EmailService emailService = new EmailService();
E crea un messaggio da inviare:

java
Copia
Modifica
EmailMessage msg = new EmailMessage(
    List.of("destinatario@example.com"),     // uno o più destinatari
    "Oggetto dell'email",                    // subject
    "Testo dell'email"                       // body del messaggio
);
Infine, invialo:

java
Copia
Modifica
emailService.sendEmail(msg);
3. Esempi pratici
Email di benvenuto dopo registrazione

java
Copia
Modifica
EmailMessage msg = new EmailMessage(
    List.of(nuovoUtente.getEmail()),
    "Benvenuto su PoetUp!",
    "Ciao " + nuovoUtente.getNome() + ", grazie per esserti registrato!"
);
emailService.sendEmail(msg);
Notifica di like

java
Copia
Modifica
EmailMessage msg = new EmailMessage(
    List.of(autorePoesia.getEmail()),
    "Hai ricevuto un like!",
    "La tua poesia \"" + poesia.getTitolo() + "\" ha ricevuto un like da " + utenteCheHaMessoLike.getNome() + "."
);
emailService.sendEmail(msg);
Notifica di commento

java
Copia
Modifica
EmailMessage msg = new EmailMessage(
    List.of(autorePoesia.getEmail()),
    "Hai ricevuto un nuovo commento",
    utenteCommentatore.getNome() + " ha scritto:\n\n" + commento.getTesto()
);
emailService.sendEmail(msg);
Email con report dell’amministratore

java
Copia
Modifica
EmailMessage msg = new EmailMessage(
    List.of("admin@poetup.com"),
    "Report settimanale",
    reportGeneratoTestuale
);
emailService.sendEmail(msg);
4. Requisiti
Assicurati che nel progetto siano incluse le librerie Jakarta Mail (o Jakarta Activation)

È necessario configurare un account Gmail (es. poetup.noreply@gmail.com) e generare una App Password

L’invio usa smtp.gmail.com con autenticazione e TLS