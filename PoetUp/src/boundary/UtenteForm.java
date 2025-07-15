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
import java.sql.Date;

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

import boundary.theme.Theme;
import boundary.theme.ThemeManager;
import controller.ControllerPoetUp;
import dto.ProfiloPersonaleDTO;

public class UtenteForm extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
    private JLabel immagineProfiloLabel;

    private JTextField campoNome;
    private JTextField campoCognome;
    private JTextField campoData;
    private JTextField campoNickname;
    private JTextArea bioArea;
    private Theme theme;

    private ProfiloPersonaleDTO profilo;

    public UtenteForm(JFrame HomePage) {
    	Image icon = new ImageIcon(getClass().getResource("/res/logo.png")).getImage();
		setIconImage(icon);
        this.profilo = ControllerPoetUp.getProfiloUtente();

        this.theme = ThemeManager.getTheme();

        setTitle("Profilo");
        setBounds(100, 100, 700, 550);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(HomePage);

        contentPane = new JPanel();
        contentPane.setLayout(null);
        // Sfondo con metodo dedicato
        contentPane.setBackground(theme.getBackgroundPrimary());
        setContentPane(contentPane);

        creaBoxImmagineProfilo();
        creaCampiProfilo();

        JButton homeButton = createCircleButton("/res/home.png", 48);
        homeButton.setBounds(622, 440, 48, 48);
        homeButton.addActionListener(e -> this.dispose());
        contentPane.add(homeButton);
    }

    private void creaBoxImmagineProfilo() {
        String pathImg = profilo.getImmagineProfilo();
        if (pathImg == null || pathImg.isEmpty()) {
            pathImg = "/res/utente.png";
        }
        ImageIcon iconaProfilo = resizeIcon(pathImg, 120, 120);
        immagineProfiloLabel = new JLabel(iconaProfilo);
        immagineProfiloLabel.setBounds(30, 30, 120, 120);
        immagineProfiloLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        immagineProfiloLabel.setOpaque(true);
        // Colore sfondo e bordo da theme con metodi dedicati
        immagineProfiloLabel.setBackground(theme.getBackgroundSecondary());
        immagineProfiloLabel.setBorder(BorderFactory.createLineBorder(theme.getBackgroundTertiary(), 1));
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

    private void creaCampiProfilo() {
        Font labelFont = new Font("Segoe UI", Font.PLAIN, 14);
        Color textColor = theme.getTextPrimary();

        int labelX = 170;
        int fieldX = 290;
        int startY = 30;
        int spacing = 50;

        String[] etichette = { "Nome:", "Cognome:", "Data di Nascita:", "Nickname:" };

        campoNome = new JTextField();
        campoCognome = new JTextField();
        campoData = new JTextField();
        campoNickname = new JTextField();
        bioArea = new JTextArea();

        JTextField[] campi = { campoNome, campoCognome, campoData, campoNickname };

        for (int i = 0; i < etichette.length; i++) {
            JLabel label = new JLabel(etichette[i]);
            label.setForeground(textColor);
            label.setFont(labelFont);
            label.setBounds(labelX, startY + i * spacing, 120, 25);
            contentPane.add(label);

            campi[i].setBounds(fieldX, startY + i * spacing, 300, 25);
            contentPane.add(campi[i]);
        }

        // Placeholder campoData
        campoData.setForeground(theme.getBackgroundTertiary());
        campoData.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                if (campoData.getText().equals("yyyy-MM-dd")) {
                    campoData.setText("");
                    campoData.setForeground(theme.getBackgroundPrimary());
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                if (campoData.getText().isEmpty()) {
                    campoData.setText("yyyy-MM-dd");
                    campoData.setForeground(theme.getBackgroundTertiary());
                }
            }
        });

        // Biografia
        JLabel bioLabel = new JLabel("Biografia:");
        bioLabel.setForeground(textColor);
        bioLabel.setFont(labelFont);
        bioLabel.setBounds(labelX, startY + etichette.length * spacing, 120, 25);
        contentPane.add(bioLabel);

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

        // Imposta valori dal DTO
        campoNome.setText(profilo.getNome());
        campoCognome.setText(profilo.getCognome());
        if (profilo.getDataNascita() != null) {
            campoData.setText(profilo.getDataNascita().toString());
            campoData.setForeground(Color.BLACK);
        } else {
            campoData.setText("yyyy-MM-dd");
            campoData.setForeground(Color.BLACK);
        }
        campoNickname.setText(profilo.getNickname());
        campoNickname.setEditable(false);
        bioArea.setText(profilo.getBio());
    }

    private void apriPopupModificaImmagine() {
        SelettoreImmagineProfilo dialog = new SelettoreImmagineProfilo(this);
        dialog.setVisible(true);

        String pathSelezionato = dialog.getImmagineSelezionata();
        if (pathSelezionato != null) {
            ImageIcon nuovaIcona = resizeIcon(pathSelezionato, 120, 120);
            immagineProfiloLabel.setIcon(nuovaIcona);
            profilo.setImmagineProfilo(pathSelezionato);
        }
    }

    private void salvaProfilo() {
        String nome = campoNome.getText().trim();
        String cognome = campoCognome.getText().trim();
        String dataNascitaStr = campoData.getText().trim();
        String bio = bioArea.getText().trim();
        String immaginePath = profilo.getImmagineProfilo();

        // Controllo nome e cognome
    	if (nome.length() > 40 || cognome.length() > 40 ||
        	!nome.matches("^[a-zA-ZàèéìòùÀÈÉÌÒÙ\\s]+$") ||
        	!cognome.matches("^[a-zA-ZàèéìòùÀÈÉÌÒÙ\\s]+$")) {

        JOptionPane.showMessageDialog(this, "Nome e cognome devono contenere solo lettere (massimo 40 caratteri).");
        return;
    	}
        if (bio.length()>500) {
            JOptionPane.showMessageDialog(this, "Biografia troppo lunga!");
            return;
        }

        if (dataNascitaStr.equals("yyyy-MM-dd")) {
            JOptionPane.showMessageDialog(this, "Inserire una data di nascita valida.");
            return;
        }

        if (!dataNascitaStr.matches("\\d{4}-\\d{2}-\\d{2}")) {
            JOptionPane.showMessageDialog(this, "Formato data non valido. Usa yyyy-MM-dd.");
            return;
        }

        Date dataNascita;
        try {
            dataNascita = Date.valueOf(dataNascitaStr);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, "Errore: data non valida.");
            return;
        }

        String messaggio = ControllerPoetUp.modificaProfilo(nome, cognome, dataNascita, bio, immaginePath);
        JOptionPane.showMessageDialog(this, messaggio);
        if (messaggio.equals("Profilo aggiornato con successo!")) {
            this.dispose();
        }
    }

    private String pathImmagine;

    public String getPathImmagine() {
        return pathImmagine;
    }

    public void setPathImmagine(String pathImmagine) {
        this.pathImmagine = pathImmagine;
    }

    private JButton createCircleButton(String iconPath, int size) {
        JButton button = new JButton();
        button.setPreferredSize(new Dimension(size, size));
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setOpaque(false);
        button.setIcon(resizeIcon(iconPath, size - 8, size - 8));

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

    private ImageIcon resizeIcon(String resourcePath, int width, int height) {
        java.net.URL url = getClass().getResource(resourcePath);
        if (url == null) {
            System.err.println("Icona non trovata: " + resourcePath);
            return new ImageIcon();
        }
        ImageIcon icon = new ImageIcon(url);
        Image image = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(image);
    }
}
