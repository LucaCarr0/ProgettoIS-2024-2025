package boundary;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import controller.ControllerPoetUp;

public class PoesiaForm extends JFrame {

    private JPanel contentPane;
    private JTextField titoloField;
    private JTextArea testoArea;
    private JTextField tagField;
    private JTextField raccoltaField;

    private boolean visibilita = true; // true = pubblica, false = privata
    private JButton lockButton;
    private boolean locked = false;

    public PoesiaForm() {
        setTitle("Pubblica Poesia");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 600, 520);
        setResizable(false);

        contentPane = new JPanel();
        contentPane.setLayout(null);
        contentPane.setBackground(new Color(0x1E2A38));  // sfondo principale
        setContentPane(contentPane);

        // Pannelli laterali contrastati
        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(new Color(0x243447));
        leftPanel.setBounds(0, 0, 40, 520);
        contentPane.add(leftPanel);

        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(new Color(0x2A3C4F));
        rightPanel.setBounds(560, 0, 40, 520);
        contentPane.add(rightPanel);

        JLabel titleLabel = new JLabel("Pubblica una poesia");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBounds(180, 20, 300, 30);
        contentPane.add(titleLabel);

        JLabel lblTitolo = new JLabel("Titolo:");
        lblTitolo.setForeground(Color.WHITE);
        lblTitolo.setBounds(70, 70, 100, 20);
        contentPane.add(lblTitolo);

        titoloField = new JTextField();
        titoloField.setBounds(70, 90, 460, 28);
        contentPane.add(titoloField);

        JLabel lblTesto = new JLabel("Testo:");
        lblTesto.setForeground(Color.WHITE);
        lblTesto.setBounds(70, 130, 100, 20);
        contentPane.add(lblTesto);

        testoArea = new JTextArea();
        testoArea.setLineWrap(true);
        testoArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(testoArea);
        scrollPane.setBounds(70, 150, 460, 120);
        contentPane.add(scrollPane);

        JLabel lblTag = new JLabel("Tag:");
        lblTag.setForeground(Color.WHITE);
        lblTag.setBounds(70, 285, 100, 20);
        contentPane.add(lblTag);

        tagField = new JTextField();
        tagField.setBounds(70, 305, 460, 28);
        contentPane.add(tagField);

        JLabel lblRaccolta = new JLabel("Raccolta:");
        lblRaccolta.setForeground(Color.WHITE);
        lblRaccolta.setBounds(70, 345, 100, 20);
        contentPane.add(lblRaccolta);

        raccoltaField = new JTextField();
        raccoltaField.setBounds(70, 365, 460, 28);
        contentPane.add(raccoltaField);

        // Bottone "Annulla"
        JButton annullaButton = new JButton("Annulla");
        annullaButton.setBackground(new Color(0x6C757D));
        annullaButton.setForeground(Color.WHITE);
        annullaButton.setBounds(240, 420, 130, 32);
        contentPane.add(annullaButton);
        annullaButton.addActionListener(e -> dispose());

        // Bottone "Pubblica"
        JButton pubblicaButton = new JButton("Pubblica");
        pubblicaButton.setBackground(new Color(0x3A506B));
        pubblicaButton.setForeground(Color.WHITE);
        pubblicaButton.setBounds(390, 420, 140, 32);
        contentPane.add(pubblicaButton);

        pubblicaButton.addActionListener(e -> {
            if (validateFields()) {
                String titolo = titoloField.getText().trim();
                String testo = testoArea.getText().trim();
                String tag = tagField.getText().trim();
                String raccolta = raccoltaField.getText().trim();

                String esito = ControllerPoetUp.pubblicazionePoesia(titolo, testo, tag, raccolta, visibilita);
                if (esito.equals("Poesia Pubblicata!")) {
                    JOptionPane.showMessageDialog(this, esito);
                    this.dispose();
                } else {
                    JOptionPane.showMessageDialog(this, esito, "Errore", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Bottone lucchetto (visibilità)
        lockButton = createIconButton("/res/lockAperto.png");
        lockButton.setBounds(70, 420, 32, 32);
        lockButton.setToolTipText("Rendi privata");
        contentPane.add(lockButton);

        lockButton.addActionListener(e -> {
            locked = !locked;
            visibilita = !locked;
            String iconPath = locked ? "/res/lockChiuso.png" : "/res/lockAperto.png";
            URL url = getClass().getResource(iconPath);
            if (url != null) {
                ImageIcon icon = new ImageIcon(url);
                Image img = icon.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
                icon = new ImageIcon(img);
                lockButton.setIcon(icon);
            } else {
                System.err.println("Icona non trovata: " + iconPath);
            }
            lockButton.setToolTipText(locked ? "Privata" : "Pubblica");
        });
    }

    private boolean validateFields() {
        String titolo = titoloField.getText().trim();
        String testo = testoArea.getText().trim();
        String tag = tagField.getText().trim();
        String raccolta = raccoltaField.getText().trim();

        // --- CAMPI VUOTI ---
        if (titolo.isEmpty()) {
            showError("Il campo Titolo è obbligatorio.");
            return false;
        }

        if (testo.isEmpty()) {
            showError("Il campo Testo è obbligatorio.");
            return false;
        }

        if (tag.isEmpty()) {
            showError("Il campo Tag è obbligatorio.");
            return false;
        }

        if (raccolta.isEmpty()) {
            showError("Il campo Raccolta è obbligatorio.");
            return false;
        }

        // --- VALIDAZIONE FORMATO ---
        if (!titolo.matches("^[a-zA-ZÀ-ÿ\\s]{1,25}$")) {
            showError("Il titolo può contenere solo lettere e spazi, massimo 25 caratteri.");
            return false;
        }

        if (testo.length() > 500 || !testo.matches("^[\\p{L}\\p{N}\\s,\\.\\!\\?\\:\\;]+$")) {
            showError("Il testo può contenere massimo 500 caratteri e solo lettere, numeri, spazi e i seguenti simboli: , . ! ? : ;");
            return false;
        }

        if (!tag.matches("^(#[^\\s#]+)+$")) {
            showError("Il campo Tag deve contenere uno o più tag, ognuno iniziando con '#' e senza spazi o simboli '#' consecutivi.");
            return false;
        }

        if (!raccolta.matches("^[a-zA-ZÀ-ÿ0-9 ]+$")) {
            showError("Il campo Raccolta può contenere solo lettere, numeri e spazi.");
            return false;
        }

        return true;
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Errore di validazione", JOptionPane.ERROR_MESSAGE);
    }

    private JButton createIconButton(String path) {
        URL url = getClass().getResource(path);
        if (url == null) {
            throw new RuntimeException("Icona non trovata: " + path);
        }
        ImageIcon icon = new ImageIcon(url);
        Image img = icon.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
        icon = new ImageIcon(img);

        JButton button = new JButton(icon);
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        return button;
    }
}
