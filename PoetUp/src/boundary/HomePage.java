package boundary;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.*;

import controller.ControllerPoetUp;
import dto.PoesiaDTO;

public class HomePage extends JFrame {

	private JPanel contentPane;
	private JPanel menuPanel;

	public HomePage() {
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 550);
		contentPane = new JPanel();
		contentPane.setLayout(null);
		contentPane.setBackground(new Color(0x15202B));
		setContentPane(contentPane);

        
		Color textColor = new Color(245, 248, 250);
        Color cardColor = Color.cyan;
        Color primaryColor = new Color(60, 164, 238);
        
        Font cardFont = new Font("Segoe UI", Font.PLAIN, 15);
		// === TITOLO ===
		JLabel titleLabel = new JLabel("POET UP - Home");
		titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
		titleLabel.setForeground(textColor);
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setBounds(200, 40, 300, 40);
		contentPane.add(titleLabel);

		// === PULSANTE RICERCA ===
		JButton searchButton = createCircleButton("/res/ricerca.png", 48);
		searchButton.setBounds(30, 440, 48, 48);
		contentPane.add(searchButton);

		// === PULSANTE AGGIUNGI POESIA ===
		JButton addPoetryButton = createCircleButton("/res/piuma.png", 64);
		addPoetryButton.setBounds(318, 432, 64, 64);
		addPoetryButton.setToolTipText("Aggiungi poesia");
		contentPane.add(addPoetryButton);

		// === PULSANTE MENU ===
		JButton menuButton = createCircleButton("/res/menu.png", 48);
		menuButton.setBounds(622, 440, 48, 48);
		contentPane.add(menuButton);

		// === MENU A TENDINA CON SOLO ICONE ===
		menuPanel = new JPanel();
		menuPanel.setLayout(new GridLayout(3, 1, 6, 6));
		menuPanel.setBounds(622, 284, 48, 144);
		menuPanel.setBackground(new Color(0x15202B));
		menuPanel.setVisible(false);
		contentPane.add(menuPanel);

		JButton icon1 = createCircleButton("/res/utente.png", 48);
		JButton icon2 = createCircleButton("/res/raccolta.png", 48);
		JButton icon3 = createCircleButton("/res/stat.png", 48);

		icon1.addActionListener(e -> new UtenteForm().setVisible(true));
		icon2.addActionListener(e -> new RaccolteFrame());
		icon3.addActionListener(e -> JOptionPane.showMessageDialog(this, "Stat cliccato"));

		menuPanel.add(icon1);
		menuPanel.add(icon2);
		menuPanel.add(icon3);

		menuButton.addActionListener(e -> menuPanel.setVisible(!menuPanel.isVisible()));
		addPoetryButton.addActionListener(e -> new PoesiaForm(this).setVisible(true));

		// === PANEL POESIE CENTRALE ===
		JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setBackground(new Color(0x15202B));
 
        JScrollPane scrollPane = new JScrollPane(listPanel);
        scrollPane.setBounds(100, 110, 480, 300);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        contentPane.add(scrollPane);
		// === CARICAMENTO POESIE DAL CONTROLLER ===
		ArrayList<PoesiaDTO> poesie = ControllerPoetUp.visualizzaFeed(); // Sostituisci con il tuo metodo
        /*
		List<PoesiaDTO> poesie = List.of(
                new PoesiaDTO("titolo","autore",3,"YYYY-MM-DD"),
                new PoesiaDTO("titolo","autore",3,"YYYY-MM-DD"),
                new PoesiaDTO("titolo","autore",3,"YYYY-MM-DD"),
                new PoesiaDTO("titolo","autore",3,"YYYY-MM-DD"),
                new PoesiaDTO("titolo","autore",3,"YYYY-MM-DD"),
                new PoesiaDTO("titolo","autore",3,"YYYY-MM-DD")
                
            );
     */

	// === CARD POESIA CON CLICK ===
        for (int i=0;i<5 && i<poesie.size();i++) {
        	PoesiaDTO poesia = poesie.get(i);
            JPanel card = new JPanel();
            card.setLayout(new BorderLayout());
            card.setMaximumSize(new Dimension(480, 80)); // Stessa larghezza dello scrollPane
            card.setBackground(cardColor);
            card.setBorder(BorderFactory.createLineBorder(primaryColor, 1));

            // ==== PANEL SINISTRO ====
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

            // ==== PANEL DESTRO ====
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

            JLabel likeLabel = new JLabel("❤ " + poesia.getLike());
            likeLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
            likeLabel.setForeground(new Color(255, 122, 89));
            likeLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
            rightPanel.add(Box.createVerticalStrut(10));
            rightPanel.add(likeLabel);

            card.add(leftPanel, BorderLayout.CENTER);
            card.add(rightPanel, BorderLayout.EAST);

            // === Mouse events ===
            card.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    JOptionPane.showMessageDialog(
                        card,
                        "Funzione non ancora implementata per: " + poesia.getTitolo(),
                        "Info",
                        JOptionPane.INFORMATION_MESSAGE
                    );
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

            listPanel.add(card);
            listPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        }
	}
	// === Utility per creare pulsante rotondo ===
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
