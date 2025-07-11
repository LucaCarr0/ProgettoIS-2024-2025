package email;

import java.util.Properties;

// Classe per configurare i parametri di connessione SMTP e credenziali dell'account gmail

public class EmailConfig {
    
    private final String username; // Indirizzo email del mittente 
    private final String password; // UTILIZZARE APP PASSWORD DI GMAIL E NON LA PASSWORD DELL ACCOUNT

    public EmailConfig(String username, String password) {
        this.username = username;
        this.password = password;
    }
    
    // Restituisce le proprietà necessarie alla connessione SMTP
    // Con questa funzione si sta preparando le proprietà di connessione per inizializzare una sessione con il server SMTP di Gmail.
    public Properties getProperties() {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        return props;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
