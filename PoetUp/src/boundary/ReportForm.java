package boundary;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

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
import javax.swing.WindowConstants;
import javax.swing.border.LineBorder;

import boundary.theme.Theme;
import boundary.theme.ThemeManager;
import controller.ControllerUtenti;

public class ReportForm extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
    private JTextArea reportArea;
    private Theme theme;

    public ReportForm(JFrame parent) {
    	Image icon = new ImageIcon(getClass().getResource("/res/logo.png")).getImage();
		setIconImage(icon);
		ThemeManager tema_app=ThemeManager.getInstance();
		this.theme=tema_app.getTheme();
        
        
        setTitle("Report");
        setBounds(100, 100, 700, 550);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(parent);

        contentPane = new JPanel();
        contentPane.setLayout(null);
        contentPane.setBackground(theme.getBackgroundPrimary());
        setContentPane(contentPane);

        creaIntestazione();
        creaCampiEGenerazione();
        creaAreaReport();
        creaBottoneHome();
    }

    private void creaIntestazione() {
        JLabel titleLabel = new JLabel("Genera Report");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
        titleLabel.setForeground(theme.getTextPrimary());
        titleLabel.setBounds(240, 20, 300, 40);
        contentPane.add(titleLabel);
    }

    private void creaCampiEGenerazione() {
        int fieldWidth = 120;
        int fieldHeight = 25;
        int yPos = 80;

        int totalWidth = 30 + 5 + fieldWidth + 20 + 15 + 5 + fieldWidth + 20 + 150;
        int startX = (700 - totalWidth) / 2;

        JLabel daLabel = new JLabel("Da");
        daLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        daLabel.setForeground(theme.getTextPrimary());
        daLabel.setBounds(startX, yPos, 30, fieldHeight);
        contentPane.add(daLabel);

        JTextField dataInizioField = new JTextField();
        dataInizioField.setBounds(startX + 30 + 5, yPos, fieldWidth, fieldHeight);
        dataInizioField.setForeground(theme.getTextPrimary());
        dataInizioField.setText("yyyy-mm-dd");
        addPlaceholderBehavior(dataInizioField);
        contentPane.add(dataInizioField);

        JLabel aLabel = new JLabel("a");
        aLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        aLabel.setForeground(theme.getTextPrimary());
        aLabel.setBounds(startX + 30 + 5 + fieldWidth + 20, yPos, 15, fieldHeight);
        contentPane.add(aLabel);

        JTextField dataFineField = new JTextField();
        dataFineField.setBounds(startX + 30 + 5 + fieldWidth + 20 + 15 + 5, yPos, fieldWidth, fieldHeight);
        dataFineField.setForeground(theme.getTextPrimary());
        dataFineField.setText("yyyy-mm-dd");
        addPlaceholderBehavior(dataFineField);
        contentPane.add(dataFineField);

        JButton generaButton = new JButton("Genera Report");
        generaButton.setBounds(startX + 30 + 5 + fieldWidth + 20 + 15 + 5 + fieldWidth + 20, yPos, 150, fieldHeight);
        generaButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        contentPane.add(generaButton);

        generaButton.addActionListener(e -> {
            String inizio = dataInizioField.getText().trim();
            String fine = dataFineField.getText().trim();
	    //VALIDAZIONE INPUT
            if (!inizio.matches("\\d{4}-\\d{2}-\\d{2}") || !fine.matches("\\d{4}-\\d{2}-\\d{2}")) {
                JOptionPane.showMessageDialog(this, "Il formato deve essere yyyy-MM-dd, con solo numeri e trattini.", "Errore", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!inizio.matches("[0-9\\-]+") || !fine.matches("[0-9\\-]+")) {
                JOptionPane.showMessageDialog(this, "Sono ammessi solo numeri e il carattere '-'.", "Errore", JOptionPane.ERROR_MESSAGE);
                return;
            }

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu-MM-dd")
                    .withResolverStyle(java.time.format.ResolverStyle.STRICT);

            try {
                LocalDate dataInizioLD = LocalDate.parse(inizio, formatter);
                LocalDate dataFineLD = LocalDate.parse(fine, formatter);
                LocalDate oggi = LocalDate.now();

                if (dataInizioLD.isAfter(oggi) || dataFineLD.isAfter(oggi)) {
                    JOptionPane.showMessageDialog(this, "Le date non possono essere nel futuro.", "Errore", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (dataFineLD.isBefore(dataInizioLD)) {
                    JOptionPane.showMessageDialog(this, "La data di fine non può essere precedente alla data di inizio.", "Errore", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                java.sql.Date dataInizio = java.sql.Date.valueOf(dataInizioLD);
                java.sql.Date dataFine = java.sql.Date.valueOf(dataFineLD);

                reportArea.setText(ControllerUtenti.generaReport(dataInizio,dataFine));

            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(this, "Data non valida. Controlla giorno, mese e anno.", "Errore", JOptionPane.ERROR_MESSAGE);
            }

            
        });
    }

    private void creaAreaReport() {
        reportArea = new JTextArea();
        reportArea.setEditable(false);
        reportArea.setBackground(theme.getBackgroundSecondary());
        reportArea.setForeground(theme.getTextPrimary());
        reportArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        reportArea.setBorder(new LineBorder(theme.getBorderColor(), 1));
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
                if (field.getText().equals("yyyy-mm-dd")) {
                    field.setText("");
                    field.setForeground(theme.getTextPrimary());
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (field.getText().isEmpty()) {
                    field.setForeground(theme.getTextSecondary());
                    field.setText("yyyy-mm-dd");
                } else {
                    field.setForeground(theme.getTextPrimary());
                }
            }
        });

        if (!field.getText().equals("yyyy-mm-dd")) {
            field.setForeground(theme.getTextPrimary());
        }
    }
}
