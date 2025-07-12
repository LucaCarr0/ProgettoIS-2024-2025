package boundary;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import controller.ControllerPoetUp;
import dto.PoesiaDTO;

public class RicercaForm extends JFrame {

    private JPanel contentPane;
    private JTextField searchField;
    private JPanel resultsPanel;
    private JScrollPane scrollPane;
    private HomePage parentFrame;
    private String currentFilter = ""; // Filtro attualmente selezionato

    // Colori del tema
    private Color backgroundColor = new Color(0x15202B);
    private Color textColor = new Color(245, 248, 250);
    private Color cardColor = Color.cyan;
    private Color primaryColor = new Color(60, 164, 238);
    private Font cardFont = new Font("Segoe UI", Font.PLAIN, 15);

    public RicercaForm(HomePage parent) {
        this.parentFrame = parent;
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
        // === TITOLO ===
        JLabel titleLabel = new JLabel("POET UP - Ricerca");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
        titleLabel.setForeground(textColor);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBounds(200, 20, 300, 40);
        contentPane.add(titleLabel);

        // === CAMPO DI RICERCA ===
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

        // === PULSANTE RICERCA ===
        JButton searchButton = createStyledButton("Cerca");
        searchButton.setBounds(490, 80, 80, 35);
        searchButton.addActionListener(e -> performSearch());
        contentPane.add(searchButton);

        // === FILTRI ===
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

        // === PANEL RISULTATI ===
        resultsPanel = new JPanel();
        resultsPanel.setLayout(new BoxLayout(resultsPanel, BoxLayout.Y_AXIS));
        resultsPanel.setBackground(backgroundColor);

        scrollPane = new JScrollPane(resultsPanel);
        scrollPane.setBounds(100, 180, 480, 280);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        contentPane.add(scrollPane);

        // === PULSANTE INDIETRO ===
        JButton backButton = createCircleButton("/res/home.png", 48); // Assumo che esista questa icona
        backButton.setBounds(30, 440, 48, 48);
        backButton.setToolTipText("Torna alla home");
        backButton.addActionListener(e -> {
            parentFrame.setVisible(true);
            dispose();
        });
        contentPane.add(backButton);

        // === MESSAGGIO INIZIALE ===
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
            // Aggiorna il filtro corrente
            if (currentFilter.equals(text)) {
                // Se è già selezionato, deseleziona
                currentFilter = "";
                button.setBackground(new Color(0x1E2732));
                button.setForeground(textColor);
            } else {
                // Deseleziona tutti gli altri filtri
                Component[] components = button.getParent().getComponents();
                for (Component comp : components) {
                    if (comp instanceof JButton && comp != button) {
                        comp.setBackground(new Color(0x1E2732));
                        ((JButton) comp).setForeground(textColor);
                    }
                }

                // Seleziona questo filtro
                currentFilter = text;
                button.setBackground(primaryColor);
                button.setForeground(Color.WHITE);
            }

            // Rieffettua la ricerca con il nuovo filtro
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

        // Pulisci i risultati precedenti
        resultsPanel.removeAll();

        // Simula la ricerca tramite controller
        ArrayList<PoesiaDTO> risultati = cercaPoesie(searchTerm);

        if (risultati.isEmpty()) {
            showNoResultsMessage();
        } else {
            displayResults(risultati);
        }

        // Aggiorna la visualizzazione
        resultsPanel.revalidate();
        resultsPanel.repaint();
    }

    private ArrayList<PoesiaDTO> cercaPoesie(String searchTerm) {
        try {
            // Usa il controller per la ricerca
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
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout());
        card.setMaximumSize(new Dimension(480, 80));
        card.setBackground(cardColor);
        card.setBorder(BorderFactory.createLineBorder(primaryColor, 1));

        // Panel sinistro
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(cardColor);
        leftPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 10));

        JLabel titolo = new JLabel(poesia.getTitolo());
        titolo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        leftPanel.add(titolo);

        JLabel autore = new JLabel("<html><i>" + poesia.getAutore() + "</i></html>");
        autore.setFont(cardFont);
        leftPanel.add(autore);

        // Panel destro
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

        // Mouse events
        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Apri la visualizzazione dettagliata della poesia
                new PoesiaFrame(poesia.getId(), poesia.getAutore()).setVisible(true);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                card.setBackground(new Color(245, 245, 255));
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

    // Utility methods (stessi della HomePage)
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