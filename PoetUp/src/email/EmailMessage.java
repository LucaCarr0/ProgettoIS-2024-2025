package email;

import java.util.List;

// È la mail da inviare con destinatario, oggetto e corpo
public class EmailMessage {
    private final List<String> recipients; // Contiene una lista di destinatari: se presenti più di uno, separati da virgola, questi vengono messi in CC (evitate plz)
    private final String subject;
    private final String body;

    public EmailMessage(List<String> recipients, String subject, String body) {
        this.recipients = recipients;
        this.subject = subject;
        this.body = body;
    }

    public List<String> getRecipients() {
        return recipients;
    }

    public String getSubject() {
        return subject;
    }

    public String getBody() {
        return body;
    }
}
