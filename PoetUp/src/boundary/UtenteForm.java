package boundary;
 
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
 
public class UtenteForm extends JFrame {
 
    private JPanel contentPane;
    private JLabel immagineProfiloLabel;
 
    public UtenteForm() {
        setTitle("Profilo");
        setBounds(100, 100, 700, 550);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
 
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
 
        // Azione sul bottone (esempio: chiude la pagina profilo)
        homeButton.addActionListener(e -> {
            this.dispose(); // o: new HomePage().setVisible(true);
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
 
        for (int i = 0; i < etichette.length; i++) {
            JLabel label = new JLabel(etichette[i]);
            label.setForeground(textColor);
            label.setFont(labelFont);
            label.setBounds(labelX, startY + i * spacing, 120, 25);
            contentPane.add(label);
 
            JTextField campo = new JTextField();
            campo.setBounds(fieldX, startY + i * spacing, 300, 25);
            contentPane.add(campo);
        }
 
        // Biografia (textarea più grande con scroll)
        JLabel bioLabel = new JLabel("Biografia:");
        bioLabel.setForeground(textColor);
        bioLabel.setFont(labelFont);
        bioLabel.setBounds(labelX, startY + etichette.length * spacing, 120, 25);
        contentPane.add(bioLabel);
 
        JTextArea bioArea = new JTextArea();
        bioArea.setLineWrap(true);
        bioArea.setWrapStyleWord(true);
        JScrollPane scroll = new JScrollPane(bioArea);
        scroll.setBounds(fieldX, startY + etichette.length * spacing, 300, 140);
        contentPane.add(scroll);
 
        // Pulsante Salva
        JButton salvaButton = new JButton("Salva");
        salvaButton.setBounds(fieldX, startY + etichette.length * spacing + 150, 100, 30);
        salvaButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        salvaButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Funzionalità di salvataggio non ancora implementata.");
        });
        contentPane.add(salvaButton);
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
 
 