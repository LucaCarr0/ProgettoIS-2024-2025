package email;

import java.util.List;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

// Classe-Servizio che si occupa dell'invio effettivo delle email
public class EmailService {
    private final EmailConfig config;

    public EmailService(EmailConfig config) {
        this.config = config;
    }

    // Metodo per mandare una mail data una EmailMessage
    public void sendEmail(EmailMessage message) throws MessagingException {
        // Crea una nuova sessione smtp (con autenticazione) per l'invio della mail
        // Per farlo viene fornito un Authenticator personalizzato che ridefinisce il metodo getPasswordAuthentication().
        // Questo metodo viene chiamato automaticamente quando il server SMTP richiederà
        // le credenziali. Se non lo avessi ridefinito la Session non saprebbe con che credenziali autenticarsi,
        // e l'invio della mail fallirebbe (il metodo standard non ridefinito ritorna null smpre).
        Session session = Session.getInstance(config.getProperties(), new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(config.getUsername(), config.getPassword());
            }
        });

        // Dopo aver creato la session crea il messaggio email
        Message mimeMessage = new MimeMessage(session);
        mimeMessage.setFrom(new InternetAddress(config.getUsername()));

        List<String> recipients = message.getRecipients();
        // Unisce i destinatari in una singola stringa separata da virgole
        String recipientString = String.join(",", recipients);
        mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientString));

        mimeMessage.setSubject(message.getSubject());
        mimeMessage.setText(message.getBody());

        // Invia il messaggio
        Transport.send(mimeMessage);
    }
}
