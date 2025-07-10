package boundary;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ReportForm extends JFrame {

    private JPanel contentPane;
    private JTextArea reportArea;

    public ReportForm(JFrame parent) {
        setTitle("Report");
        setBounds(100, 100, 700, 550);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(parent);

        contentPane = new JPanel();
        contentPane.setLayout(null);
        contentPane.setBackground(new Color(0x15202B));
        setContentPane(contentPane);

        creaIntestazione();
        creaCampiEGenerazione();
        creaAreaReport();
        creaBottoneHome();
    }

    private void creaIntestazione() {
        JLabel titleLabel = new JLabel("Genera Report");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBounds(240, 20, 300, 40);
        contentPane.add(titleLabel);
    }

    private void creaCampiEGenerazione() {
        int fieldWidth = 120;
        int fieldHeight = 25;
        int yPos = 80;

        // Larghezza totale della riga "Da [campo] a [campo] [button]"
        int totalWidth = 30    // "Da"
                + 5
                + fieldWidth
                + 20   // spazio tra primo campo e "a"
                + 15   // "a"
                + 5
                + fieldWidth
                + 20   // spazio tra secondo campo e bottone
                + 150; // larghezza bottone

        int startX = (700 - totalWidth) / 2; // finestra 700px larghezza

        JLabel daLabel = new JLabel("Da");
        daLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        daLabel.setForeground(Color.WHITE);
        daLabel.setBounds(startX, yPos, 30, fieldHeight);
        contentPane.add(daLabel);

        JTextField dataInizioField = new JTextField();
        dataInizioField.setBounds(startX + 30 + 5, yPos, fieldWidth, fieldHeight);
        // Imposta testo e colore iniziali per placeholder
        dataInizioField.setForeground(Color.LIGHT_GRAY);
        dataInizioField.setText("gg/mm/aaaa");
        addPlaceholderBehavior(dataInizioField);
        contentPane.add(dataInizioField);
        

        JLabel aLabel = new JLabel("a");
        aLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        aLabel.setForeground(Color.WHITE);
        aLabel.setBounds(startX + 30 + 5 + fieldWidth + 20, yPos, 15, fieldHeight);
        contentPane.add(aLabel);

        JTextField dataFineField = new JTextField();
        dataFineField.setBounds(startX + 30 + 5 + fieldWidth + 20 + 15 + 5, yPos, fieldWidth, fieldHeight);
        dataFineField.setForeground(Color.LIGHT_GRAY);
        dataFineField.setText("gg/mm/aaaa");
        addPlaceholderBehavior(dataFineField);
        contentPane.add(dataFineField);
        
        
        JButton generaButton = new JButton("Genera Report");
        generaButton.setBounds(startX + 30 + 5 + fieldWidth + 20 + 15 + 5 + fieldWidth + 20, yPos, 150, fieldHeight);
        generaButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        contentPane.add(generaButton);

        generaButton.addActionListener(e -> {
            String inizio = dataInizioField.getText().trim();
            String fine = dataFineField.getText().trim();

            // Regex di base per gg/mm/aaaa con solo numeri e "/"
            if (!inizio.matches("\\d{2}/\\d{2}/\\d{4}") || !fine.matches("\\d{2}/\\d{2}/\\d{4}")) {
                JOptionPane.showMessageDialog(this, "Formato data non valido. Usa gg/mm/aaaa.", "Errore", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Impedisci caratteri non numerici o simboli diversi da /
            if (!inizio.matches("[0-9/]+") || !fine.matches("[0-9/]+")) {
                JOptionPane.showMessageDialog(this, "Sono ammessi solo numeri e il carattere '/'", "Errore", JOptionPane.ERROR_MESSAGE);
                return;
            }

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/uuuu")
                    .withResolverStyle(java.time.format.ResolverStyle.STRICT);
            LocalDate dataInizio, dataFine, oggi = LocalDate.now();

            try {
                dataInizio = LocalDate.parse(inizio, formatter);
                dataFine = LocalDate.parse(fine, formatter);
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(this, "Data non valida. Controlla giorno e mese.", "Errore", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Controllo: data futura
            if (dataInizio.isAfter(oggi) || dataFine.isAfter(oggi)) {
                JOptionPane.showMessageDialog(this, "Le date non possono essere nel futuro.", "Errore", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Controllo: fine < inizio
            if (dataFine.isBefore(dataInizio)) {
                JOptionPane.showMessageDialog(this, "La data fine non può essere precedente alla data inizio.", "Errore", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Tutto ok, genera il report
            reportArea.setText("Report generato da " + inizio + " a " + fine + ":\n\n- Voce 1\n- Voce 2\n- Voce 3");
        });
    }

    private void creaAreaReport() {
        reportArea = new JTextArea();
        reportArea.setEditable(false);
        reportArea.setBackground(new Color(0x1E2A36));
        reportArea.setForeground(Color.WHITE);
        reportArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        reportArea.setBorder(new LineBorder(Color.GRAY, 1));
        reportArea.setLineWrap(true);
        reportArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(reportArea);
        scrollPane.setBounds(40, 130, 600, 290);
        contentPane.add(scrollPane);
    }

    private void creaBottoneHome() {
        JButton homeButton = createCircleButton("/res/home.png", 48);
        homeButton.setBounds(622, 440, 48, 48);
        contentPane.add(homeButton);

        homeButton.addActionListener(e -> this.dispose());
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
    
    private void addPlaceholderBehavior(JTextField field) {
        field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (field.getText().equals("gg/mm/aaaa")) {
                    field.setText("");
                    field.setForeground(Color.BLACK); // Testo utente visibile
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (field.getText().isEmpty()) {
                    field.setForeground(Color.LIGHT_GRAY); // placeholder
                    field.setText("gg/mm/aaaa");
                } else {
                    field.setForeground(Color.BLACK); // conferma testo utente in nero
                }
            }
        });

        // Imposta subito colore corretto se il campo non è placeholder
        if (!field.getText().equals("gg/mm/aaaa")) {
            field.setForeground(Color.BLACK);
        }
    }
}
