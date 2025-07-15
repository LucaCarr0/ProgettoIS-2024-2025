package boundary;

import java.awt.*;
import javax.swing.*;

import boundary.theme.Theme;
import boundary.theme.ThemeManager;
import dto.StatisticheDTO;
import facade.FacadeUtenti;

public class StatisticheForm extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Theme theme;
    private StatisticheDTO statistiche;

    public StatisticheForm(JFrame HomePage) {
    	Image icon = new ImageIcon(getClass().getResource("/res/logo.png")).getImage();
		setIconImage(icon);
        caricaStatistiche();
        ThemeManager tema_app=ThemeManager.getInstance();
		this.theme= tema_app.getTheme();
        

        setTitle("Statistiche");
        setSize(700, 550);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(HomePage);

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(theme.getBackgroundPrimary());
        setContentPane(mainPanel);
        mainPanel.setLayout(null);

       
        JPanel leftPanel = new JPanel();
        leftPanel.setBounds(0, 0, 440, 523);
        leftPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 15));
        leftPanel.setBackground(theme.getBackgroundPrimary());
        mainPanel.add(leftPanel);
        leftPanel.setLayout(null);

        JLabel heading = new JLabel("Poesia più apprezzata", SwingConstants.CENTER);
        heading.setBounds(30, 30, 395, 27);
        heading.setFont(new Font("Segoe UI", Font.BOLD, 22));
        heading.setForeground(theme.getTextPrimary());
        leftPanel.add(heading);

        JPanel poesiaWrapper = new JPanel();
        poesiaWrapper.setBounds(30, 94, 395, 348);
        poesiaWrapper.setBackground(theme.getBackgroundSecondary());
        poesiaWrapper.setBorder(BorderFactory.createLineBorder(theme.getBorderColor(), 1));
        poesiaWrapper.setPreferredSize(new Dimension(360, 250));
        poesiaWrapper.setLayout(null);

        JPanel poesiaContent = new JPanel();
        poesiaContent.setBounds(37, 69, 319, 185);
        poesiaContent.setLayout(new BoxLayout(poesiaContent, BoxLayout.Y_AXIS));
        poesiaContent.setBackground(theme.getBackgroundSecondary());
        poesiaContent.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titoloLabel = new JLabel(statistiche.getTitoloPoesiaPiuApprezzata());
        titoloLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titoloLabel.setForeground(theme.getAccentColor());
        titoloLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        poesiaContent.add(titoloLabel);
        poesiaContent.add(Box.createVerticalStrut(10));

        JTextArea poesiaArea = new JTextArea(statistiche.getTestoPoesiaPiuApprezzata());
        poesiaArea.setWrapStyleWord(true);
        poesiaArea.setLineWrap(true);
        poesiaArea.setEditable(false);
        poesiaArea.setFont(new Font("Serif", Font.ITALIC, 14));
        poesiaArea.setForeground(theme.getTextPrimary());
        poesiaArea.setBackground(theme.getBackgroundSecondary());
        poesiaArea.setOpaque(false);
        poesiaContent.add(poesiaArea);

        poesiaContent.add(Box.createVerticalStrut(10));

        JLabel likeLabel = new JLabel("♥ " + statistiche.getLikePoesiaPiuApprezzata() + " apprezzamenti");
        likeLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        likeLabel.setForeground(theme.getHighlightColor());
        likeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        poesiaContent.add(likeLabel);
        poesiaWrapper.add(poesiaContent);
        leftPanel.add(poesiaWrapper);

       
        JPanel rightPanel = new JPanel();
        rightPanel.setBounds(440, 0, 260, 445);
        rightPanel.setPreferredSize(new Dimension(260, 0));
        rightPanel.setBackground(theme.getBackgroundSecondary());
        rightPanel.setBorder(BorderFactory.createEmptyBorder(30, 15, 30, 30));
        mainPanel.add(rightPanel);
        rightPanel.setLayout(null);

        JPanel statBox = new JPanel(new GridLayout(2, 1, 50, 20));
        statBox.setBounds(22, 162, 210, 194);
        statBox.setBackground(theme.getBackgroundSecondary());
        statBox.setOpaque(false);

        statBox.add(createIconStatPanel("/res/like.png", String.valueOf(statistiche.getTotaleApprezzamenti()), "Totale apprezzamenti"));
        statBox.add(createIconStatPanel("/res/commento.png", String.valueOf(statistiche.getTotaleCommenti()), "Totale commenti"));
        rightPanel.add(statBox);

        
        JButton homeButton = createCircleButton("/res/home.png", 48);
        JPanel bottomRightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 10));
        bottomRightPanel.setBounds(0, 445, 700, 68);
        bottomRightPanel.setBackground(theme.getBackgroundSecondary());
        bottomRightPanel.add(homeButton);
        
        mainPanel.add(bottomRightPanel);

        homeButton.addActionListener(e -> dispose());
    }

    private void caricaStatistiche() {
        try {
            statistiche = FacadeUtenti.getStatistiche();
        } catch (Exception e) {
            e.printStackTrace();
            statistiche = new StatisticheDTO(0, 0, "Errore nel caricamento", "Impossibile caricare le statistiche", 0);
        }
    }

    private JPanel createIconStatPanel(String iconPath, String value, String labelText) {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(240, 110));
        panel.setBackground(theme.getBackgroundTertiary());
        panel.setBorder(BorderFactory.createLineBorder(theme.getBorderColor(), 1));
        panel.setLayout(new BorderLayout());

        JLabel iconLabel = new JLabel(resizeIcon(iconPath, 40, 40));
        iconLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.add(iconLabel, BorderLayout.WEST);

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setOpaque(false);
        textPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 10));

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        label.setForeground(theme.getTextSecondary());
        label.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel numberWrapper = new JPanel(new GridBagLayout());
        numberWrapper.setOpaque(false);

        JLabel number = new JLabel(value);
        number.setFont(new Font("Segoe UI", Font.BOLD, 22));
        number.setForeground(theme.getTextPrimary());

        numberWrapper.add(number);

        textPanel.add(label);
        textPanel.add(Box.createVerticalStrut(6));
        textPanel.add(numberWrapper);

        panel.add(textPanel, BorderLayout.CENTER);

        return panel;
    }

    private JButton createCircleButton(String iconPath, int size) {
        JButton button = new JButton();
        button.setPreferredSize(new Dimension(size, size));
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setOpaque(false);
        button.setIcon(resizeIcon(iconPath, size - 8, size - 8));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
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
        Image img = new ImageIcon(url).getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }
}
