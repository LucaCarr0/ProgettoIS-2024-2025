package boundary;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

import boundary.theme.Theme;
import boundary.theme.ThemeManager;
import controller.ControllerPoetUp;
import dto.PoesiaDTO;

public class RicercaForm extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
    private JTextField searchField;
    private JPanel resultsPanel;
    private JScrollPane scrollPane;
    private HomePage parentFrame;
    private String currentFilter = "";

    private Theme theme;
    private Color backgroundColor;
    private Color textColor;
    private Color cardColor;
    private Color primaryColor;
    private Font cardFont = new Font("Segoe UI", Font.PLAIN, 15);

    public RicercaForm(HomePage parent) {
    	Image icon = new ImageIcon(getClass().getResource("/res/logo.png")).getImage();
		setIconImage(icon);
        this.parentFrame = parent;
        this.theme = ThemeManager.getTheme();
        // Carica i colori dalla palette del tema
        this.textColor = theme.getPalette().get(0);    // esempio: colore testo
        this.primaryColor = theme.getPalette().get(1); // esempio: colore primario
        this.backgroundColor = theme.getPalette().get(2); // backgroundPrimary
        this.cardColor = theme.getPalette().get(4);      // colore card

        initializeComponents();
        setupLayout();
        setVisible(true);
    }

    private void initializeComponents() {
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 700, 550);
        setLocationRelativeTo(parentFrame);

        contentPane = new JPanel();
        contentPane.setLayout(null);
        contentPane.setBackground(backgroundColor);
        setContentPane(contentPane);
    }

    private void setupLayout() {
        JLabel titleLabel = new JLabel("POET UP - Ricerca");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
        titleLabel.setForeground(textColor);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBounds(200, 20, 300, 40);
        contentPane.add(titleLabel);

        searchField = new JTextField();
        searchField.setBounds(100, 80, 380, 35);
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        searchField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(primaryColor, 2),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        searchField.setBackground(Color.WHITE);
        searchField.addActionListener(e -> performSearch());
        contentPane.add(searchField);

        JButton searchButton = createStyledButton("Cerca");
        searchButton.setBounds(490, 80, 80, 35);
        searchButton.addActionListener(e -> performSearch());
        contentPane.add(searchButton);

        JPanel filterPanel = new JPanel();
        filterPanel.setLayout(new GridLayout(1, 3, 10, 0));
        filterPanel.setBounds(100, 130, 470, 35);
        filterPanel.setBackground(backgroundColor);

        JButton filterByTag = createFilterButton("Tag");
        JButton filterByTitle = createFilterButton("Titolo");
        JButton filterByText = createFilterButton("Testo");

        filterPanel.add(filterByTag);
        filterPanel.add(filterByTitle);
        filterPanel.add(filterByText);
        contentPane.add(filterPanel);

        resultsPanel = new JPanel();
        resultsPanel.setLayout(new BoxLayout(resultsPanel, BoxLayout.Y_AXIS));
        resultsPanel.setBackground(backgroundColor);

        scrollPane = new JScrollPane(resultsPanel);
        scrollPane.setBounds(100, 180, 480, 280);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        contentPane.add(scrollPane);

        JButton backButton = createCircleButton("/res/home.png", 48);
        backButton.setBounds(30, 440, 48, 48);
        backButton.setToolTipText("Torna alla home");
        backButton.addActionListener(e -> {
            parentFrame.setVisible(true);
            dispose();
        });
        contentPane.add(backButton);

        showInitialMessage();
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(primaryColor);
        button.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        button.setFocusPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(primaryColor.darker());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(primaryColor);
            }
        });

        return button;
    }

    private JButton createFilterButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        button.setForeground(textColor);
        button.setBackground(new Color(0x1E2732));
        button.setBorder(BorderFactory.createLineBorder(primaryColor, 1));
        button.setFocusPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        button.addActionListener(e -> {
            if (currentFilter.equals(text)) {
                currentFilter = "";
                button.setBackground(new Color(0x1E2732));
                button.setForeground(textColor);
            } else {
                Component[] components = ((JPanel) button.getParent()).getComponents();
                for (Component comp : components) {
                    if (comp instanceof JButton && comp != button) {
                        comp.setBackground(new Color(0x1E2732));
                        ((JButton) comp).setForeground(textColor);
                    }
                }

                currentFilter = text;
                button.setBackground(primaryColor);
                button.setForeground(Color.WHITE);
            }

            if (!searchField.getText().trim().isEmpty()) {
                performSearch();
            }
        });

        return button;
    }

    private void performSearch() {
        String searchTerm = searchField.getText().trim();

        if (searchTerm.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Inserisci un termine di ricerca",
                "Attenzione", JOptionPane.WARNING_MESSAGE);
            return;
        }

        resultsPanel.removeAll();

        ArrayList<PoesiaDTO> risultati = cercaPoesie(searchTerm);

        if (risultati.isEmpty()) {
            showNoResultsMessage();
        } else {
            displayResults(risultati);
        }

        resultsPanel.revalidate();
        resultsPanel.repaint();
    }

    private ArrayList<PoesiaDTO> cercaPoesie(String searchTerm) {
        try {
            return ControllerPoetUp.ricercaPoesie(searchTerm, currentFilter);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private void displayResults(ArrayList<PoesiaDTO> risultati) {
        for (PoesiaDTO poesia : risultati) {
            JPanel card = createPoetryCard(poesia);
            resultsPanel.add(card);
            resultsPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        }
    }

    private JPanel createPoetryCard(PoesiaDTO poesia) {
        JPanel card = new JPanel(new BorderLayout());
        card.setMaximumSize(new Dimension(480, 80));
        card.setBackground(cardColor);
        card.setBorder(BorderFactory.createLineBorder(primaryColor, 1));

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(cardColor);
        leftPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 10));

        JLabel titolo = new JLabel(poesia.getTitolo());
        titolo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titolo.setForeground(textColor);
        leftPanel.add(titolo);

        JLabel autore = new JLabel("<html><i>" + poesia.getAutore() + "</i></html>");
        autore.setFont(cardFont);
        autore.setForeground(textColor);
        leftPanel.add(autore);

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBackground(cardColor);
        rightPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 15));
        rightPanel.setPreferredSize(new Dimension(120, 60));

        JLabel dataLabel = new JLabel(poesia.getData());
        dataLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        dataLabel.setForeground(Color.GRAY);
        dataLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
        rightPanel.add(dataLabel);

        JLabel likeLabel = new JLabel("\u2665 " + poesia.getLike());
        likeLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        likeLabel.setForeground(new Color(255, 122, 89));
        likeLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
        rightPanel.add(Box.createVerticalStrut(10));
        rightPanel.add(likeLabel);

        card.add(leftPanel, BorderLayout.CENTER);
        card.add(rightPanel, BorderLayout.EAST);

        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new PoesiaFrame(RicercaForm.this,poesia.getId(), poesia.getAutore()).setVisible(true);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                card.setBackground(textColor.brighter());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                card.setBackground(cardColor);
            }
        });

        return card;
    }

    private void showInitialMessage() {
        JLabel messageLabel = new JLabel("<html><div style='text-align: center;'>" +
            "Inserisci un termine per iniziare la ricerca<br><br>" +
            "<small>Suggerimento: usa i filtri per cercare specificamente per Tag, Titolo o Testo</small>" +
            "</div></html>");
        messageLabel.setFont(new Font("Segoe UI", Font.ITALIC, 16));
        messageLabel.setForeground(Color.GRAY);
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        resultsPanel.add(Box.createVerticalStrut(50));
        resultsPanel.add(messageLabel);
    }

    private void showNoResultsMessage() {
        JLabel messageLabel = new JLabel("Nessun risultato trovato per la ricerca");
        messageLabel.setFont(new Font("Segoe UI", Font.ITALIC, 16));
        messageLabel.setForeground(Color.GRAY);
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        resultsPanel.add(Box.createVerticalStrut(50));
        resultsPanel.add(messageLabel);
    }

    private JButton createCircleButton(String resourcePath, int size) {
        JButton button = new JButton();
        button.setPreferredSize(new Dimension(size, size));
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setBorderPainted(false);

        ImageIcon icon = new ImageIcon(getClass().getResource(resourcePath));
        Image img = icon.getImage().getScaledInstance(size, size, Image.SCALE_SMOOTH);
        button.setIcon(new ImageIcon(img));

        return button;
    }
}
