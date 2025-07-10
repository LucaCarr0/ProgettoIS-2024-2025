package boundary;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.RenderingHints;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

public class StatisticheForm extends JFrame {

    public StatisticheForm(JFrame HomePage) {
        setTitle("Statistiche");
        setSize(700, 550);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(HomePage);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(0x15202B));
        setContentPane(mainPanel);

        // === Left Panel (Poesia) ===
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BorderLayout(10, 10));
        leftPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 15)); // left: 30
        leftPanel.setBackground(new Color(0x15202B));
        mainPanel.add(leftPanel, BorderLayout.CENTER);

        JLabel heading = new JLabel("Poesia più apprezzata", SwingConstants.CENTER);
        heading.setFont(new Font("Segoe UI", Font.BOLD, 22));
        heading.setForeground(Color.WHITE);
        leftPanel.add(heading, BorderLayout.NORTH);

        JPanel poesiaWrapper = new JPanel(new GridBagLayout());
        poesiaWrapper.setBackground(new Color(0x1E2A38));
        poesiaWrapper.setBorder(BorderFactory.createLineBorder(new Color(0x445566), 1));
        poesiaWrapper.setPreferredSize(new Dimension(360, 250)); // Ridotto leggermente

        JTextArea poesiaArea = new JTextArea("Qui va il testo della poesia più apprezzata...");
        poesiaArea.setWrapStyleWord(true);
        poesiaArea.setLineWrap(true);
        poesiaArea.setEditable(false);
        poesiaArea.setFont(new Font("Serif", Font.ITALIC, 16));
        poesiaArea.setMargin(new Insets(10, 10, 10, 10));
        poesiaArea.setBackground(new Color(0x1E2A38));
        poesiaArea.setForeground(Color.WHITE);

        poesiaWrapper.add(poesiaArea, new GridBagConstraints());
        leftPanel.add(poesiaWrapper, BorderLayout.CENTER);

        // === Right Panel (Stat) ===
        JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setPreferredSize(new Dimension(260, 0));
        rightPanel.setBackground(new Color(0x15202B));
        rightPanel.setBorder(BorderFactory.createEmptyBorder(30, 15, 30, 30)); // right: 30 (simmetrico con sinistra)
        mainPanel.add(rightPanel, BorderLayout.EAST);

        JPanel statBox = new JPanel(new GridLayout(2, 1, 20, 20));
        statBox.setBackground(new Color(0x15202B));
        statBox.setOpaque(false);

        statBox.add(createIconStatPanel("/res/like.png", "123", "Totale apprezzamenti"));
        statBox.add(createIconStatPanel("/res/commento.png", "45", "Totale commenti"));
        rightPanel.add(statBox);

        // === Bottom Home Button ===
        JButton homeButton = createCircleButton("/res/home.png", 48);
        JPanel bottomRightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 10));
        bottomRightPanel.setBackground(new Color(0x15202B));
        bottomRightPanel.add(homeButton);
        mainPanel.add(bottomRightPanel, BorderLayout.SOUTH);

        homeButton.addActionListener(e -> dispose());
    }

    private JPanel createIconStatPanel(String iconPath, String value, String labelText) {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(240, 110));
        panel.setBackground(new Color(0x1E2A38));
        panel.setBorder(BorderFactory.createLineBorder(new Color(0x445566), 1));
        panel.setLayout(new BorderLayout());

        // Icona a sinistra
        JLabel iconLabel = new JLabel(resizeIcon(iconPath, 40, 40));
        iconLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.add(iconLabel, BorderLayout.WEST);

        // Pannello Testo
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setOpaque(false);
        textPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 10));

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        label.setForeground(Color.LIGHT_GRAY);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Wrapper per centrare il numero
        JPanel numberWrapper = new JPanel(new GridBagLayout());
        numberWrapper.setOpaque(false);

        JLabel number = new JLabel(value);
        number.setFont(new Font("Segoe UI", Font.BOLD, 22));
        number.setForeground(Color.WHITE);

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
