package boundary;

import boundary.theme.Theme;
import boundary.theme.ThemeManager;

import java.awt.*;
import javax.swing.*;

public class StatisticheForm extends JFrame {

	private Theme theme;
	
    public StatisticheForm(JFrame HomePage) {
        
    	this.theme = ThemeManager.getTheme(); 
    	
    	setTitle("Statistiche");
        setSize(700, 550);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(HomePage);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(theme.getBackgroundPrimary());
        setContentPane(mainPanel);

        // === Left Panel (Poesia) ===
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BorderLayout(10, 10));
        leftPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 15));
        leftPanel.setBackground(theme.getBackgroundPrimary());
        mainPanel.add(leftPanel, BorderLayout.CENTER);

        JLabel heading = new JLabel("Poesia più apprezzata", SwingConstants.CENTER);
        heading.setFont(new Font("Segoe UI", Font.BOLD, 22));
        heading.setForeground(theme.getTextPrimary());
        leftPanel.add(heading, BorderLayout.NORTH);

        JPanel poesiaWrapper = new JPanel(new GridBagLayout());
        poesiaWrapper.setBackground(theme.getBackgroundSecondary());
        poesiaWrapper.setBorder(BorderFactory.createLineBorder(theme.getBorderColor(), 1));
        poesiaWrapper.setPreferredSize(new Dimension(360, 250));

        JTextArea poesiaArea = new JTextArea("Qui va il testo della poesia più apprezzata...");
        poesiaArea.setWrapStyleWord(true);
        poesiaArea.setLineWrap(true);
        poesiaArea.setEditable(false);
        poesiaArea.setFont(new Font("Serif", Font.ITALIC, 16));
        poesiaArea.setMargin(new Insets(10, 10, 10, 10));
        poesiaArea.setBackground(theme.getBackgroundSecondary());
        poesiaArea.setForeground(theme.getTextPrimary());

        poesiaWrapper.add(poesiaArea, new GridBagConstraints());
        leftPanel.add(poesiaWrapper, BorderLayout.CENTER);

        // === Right Panel (Stat) ===
        JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setPreferredSize(new Dimension(260, 0));
        rightPanel.setBackground(theme.getBackgroundPrimary());
        rightPanel.setBorder(BorderFactory.createEmptyBorder(30, 15, 30, 30));
        mainPanel.add(rightPanel, BorderLayout.EAST);

        JPanel statBox = new JPanel(new GridLayout(2, 1, 20, 20));
        statBox.setBackground(theme.getBackgroundPrimary());
        statBox.setOpaque(false);

        statBox.add(createIconStatPanel("/res/like.png", "123", "Totale apprezzamenti", theme));
        statBox.add(createIconStatPanel("/res/commento.png", "45", "Totale commenti", theme));
        rightPanel.add(statBox);

        // === Bottom Home Button ===
        JButton homeButton = createCircleButton("/res/home.png", 48);
        JPanel bottomRightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 10));
        bottomRightPanel.setBackground(theme.getBackgroundPrimary());
        bottomRightPanel.add(homeButton);
        mainPanel.add(bottomRightPanel, BorderLayout.SOUTH);

        homeButton.addActionListener(e -> dispose());
    }

    private JPanel createIconStatPanel(String iconPath, String value, String labelText, Theme theme) {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(240, 110));
        panel.setBackground(theme.getBackgroundSecondary());
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
            return new ImageIcon(); // fallback
        }
        Image img = new ImageIcon(url).getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }
}
