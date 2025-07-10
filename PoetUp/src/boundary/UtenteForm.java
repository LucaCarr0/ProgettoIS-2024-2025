package boundary;
 
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
 
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import java.sql.Date;
import controller.ControllerPoetUp;
 
public class UtenteForm extends JFrame {
 
    private JPanel contentPane;
    private JLabel immagineProfiloLabel;
    // Campi di classe per accedere ai valori
    private JTextField campoNome;
    private JTextField campoCognome;
    private JTextField campoData;
    private JTextField campoNickname;
    private JTextArea bioArea;
 
    public UtenteForm(JFrame HomePage) {
        setTitle("Profilo");
        setBounds(100, 100, 700, 550);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(HomePage);
 
        contentPane = new JPanel();
        contentPane.setLayout(null);
        contentPane.setBackground(new Color(0x15202B));
        setContentPane(contentPane);
 
        creaBoxImmagineProfilo();
        creaCampiProfilo();
 
        // === Pulsante Home in basso a destra ===
        JButton homeButton = createCircleButton("/res/home.png", 48);
        homeButton.setBounds(622, 440, 48, 48);
        contentPane.add(homeButton);
 
        // Azione sul bottone
        homeButton.addActionListener(e -> {
            this.dispose();
        });
    }
 
    // === Crea immagine profilo cliccabile (120x120) ===
    private void creaBoxImmagineProfilo() {
        ImageIcon iconaProfilo = resizeIcon("/res/utente.png", 120, 120);
        immagineProfiloLabel = new JLabel(iconaProfilo);
        immagineProfiloLabel.setBounds(30, 30, 120, 120);
        immagineProfiloLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        immagineProfiloLabel.setToolTipText("Modifica immagine profilo (da implementare)");
        immagineProfiloLabel.setOpaque(true);
        immagineProfiloLabel.setBackground(new Color(0x1E2A36));
        immagineProfiloLabel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        immagineProfiloLabel.setHorizontalAlignment(SwingConstants.CENTER);
        immagineProfiloLabel.setVerticalAlignment(SwingConstants.CENTER);
 
        immagineProfiloLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                apriPopupModificaImmagine();
            }
        });
 
        contentPane.add(immagineProfiloLabel);
    }
 
    // === Crea campi testo modificabili e pulsante Salva ===
    private void creaCampiProfilo() {
        Font labelFont = new Font("Segoe UI", Font.PLAIN, 14);
        Color textColor = Color.WHITE;
 
        int labelX = 170;
        int fieldX = 290;
        int startY = 30;
        int spacing = 50;
 
        String[] etichette = { "Nome:", "Cognome:", "Data di Nascita:", "Nickname:" };
        // Inizializza i campi di classe
        campoNome = new JTextField();
        campoCognome = new JTextField();
        campoData = new JTextField();
        campoNickname = new JTextField();
        bioArea = new JTextArea();
 
        // Array dei campi per associarli alle etichette
        JTextField[] campi = { campoNome, campoCognome, campoData, campoNickname };
 
        // Crea etichette e campi
        for (int i = 0; i < etichette.length; i++) {
            JLabel label = new JLabel(etichette[i]);
            label.setForeground(textColor);
            label.setFont(labelFont);
            label.setBounds(labelX, startY + i * spacing, 120, 25);
            contentPane.add(label);
 
            // Usa i campi di classe invece di creare nuovi JTextField
            campi[i].setBounds(fieldX, startY + i * spacing, 300, 25);
            contentPane.add(campi[i]);
        }
 
        // Placeholder per il campo data
        campoData.setText("yyyy-MM-dd");
        campoData.setForeground(Color.GRAY);
        campoData.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                if (campoData.getText().equals("yyyy-MM-dd")) {
                    campoData.setText("");
                    campoData.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                if (campoData.getText().isEmpty()) {
                    campoData.setText("yyyy-MM-dd");
                    campoData.setForeground(Color.GRAY);
                }
            }
        });
 
        // Biografia (textarea più grande con scroll)
        JLabel bioLabel = new JLabel("Biografia:");
        bioLabel.setForeground(textColor);
        bioLabel.setFont(labelFont);
        bioLabel.setBounds(labelX, startY + etichette.length * spacing, 120, 25);
        contentPane.add(bioLabel);
 
        // Usa il campo di classe bioArea
        bioArea.setLineWrap(true);
        bioArea.setWrapStyleWord(true);
        JScrollPane scroll = new JScrollPane(bioArea);
        scroll.setBounds(fieldX, startY + etichette.length * spacing, 300, 140);
        contentPane.add(scroll);
 
        // Pulsante Salva
        JButton salvaButton = new JButton("Salva");
        salvaButton.setBounds(fieldX, startY + etichette.length * spacing + 150, 100, 30);
        salvaButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        salvaButton.addActionListener(e -> salvaProfilo());
        contentPane.add(salvaButton);
    }
 
    // === Metodo separato per gestire il salvataggio ===
    private void salvaProfilo() {
        try {
            String nome = campoNome.getText().trim();
            String cognome = campoCognome.getText().trim();
            String dataNascitaStr = campoData.getText().trim();
            String nickname = campoNickname.getText().trim();
            String bio = bioArea.getText().trim();
 
            // Validazione campi obbligatori
            if (nome.isEmpty() || cognome.isEmpty() || nickname.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nome, Cognome e Nickname sono obbligatori.");
                return;
            }
 
            // Controllo se il campo data è ancora il placeholder
            if (dataNascitaStr.equals("yyyy-MM-dd") || dataNascitaStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Inserire una data di nascita valida.");
                return;
            }
 
            // Regex check per assicurarsi che la data sia nel formato corretto
            if (!dataNascitaStr.matches("\\d{4}-\\d{2}-\\d{2}")) {
                JOptionPane.showMessageDialog(this, "Formato data non valido. Usa yyyy-MM-dd.");
                return;
            }
 
            // Conversione diretta con java.sql.Date.valueOf()
            java.sql.Date dataNascita = java.sql.Date.valueOf(dataNascitaStr);
 
            String messaggio = ControllerPoetUp.modificaProfilo(nome, cognome, dataNascita, bio);
            JOptionPane.showMessageDialog(this, messaggio);
 
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, "Errore: data non valida. Usa il formato yyyy-MM-dd.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Errore durante il salvataggio: " + ex.getMessage());
        }
    }
 
    // === Placeholder popup (da implementare) ===
    private void apriPopupModificaImmagine() {
        JOptionPane.showMessageDialog(this, "Popup modifica immagine da implementare.");
    }
 
    // === Utility per creare un pulsante rotondo con icona ===
    private JButton createCircleButton(String iconPath, int size) {
        JButton button = new JButton();
        button.setPreferredSize(new Dimension(size, size));
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setOpaque(false);
        button.setIcon(resizeIcon(iconPath, size - 8, size - 8));
 
        // Rende il bottone rotondo
        button.setUI(new javax.swing.plaf.basic.BasicButtonUI() {
            @Override
            public void paint(Graphics g, JComponent c) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                super.paint(g, c);
            }
        });
        return button;
    }
 
    // === Ridimensiona icone ===
    private ImageIcon resizeIcon(String resourcePath, int width, int height) {
        java.net.URL url = getClass().getResource(resourcePath);
        if (url == null) {
            System.err.println("Icona non trovata: " + resourcePath);
            return new ImageIcon(); // icona vuota
        }
        ImageIcon icon = new ImageIcon(url);
        Image image = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(image);
    }
}