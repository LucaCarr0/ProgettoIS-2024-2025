package boundary;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.RenderingHints;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

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
		menuPanel.setBounds(622, 284, 48, 144); // 3 bottoni da 48x48 + spazi
		menuPanel.setBackground(new Color(0x15202B));
		menuPanel.setVisible(false);
		contentPane.add(menuPanel);

		// === ICON BUTTONS ===
		JButton icon1 = createCircleButton("/res/utente.png", 48);
		JButton icon2 = createCircleButton("/res/raccolta.png", 48);
		JButton icon3 = createCircleButton("/res/stat.png", 48);

		// Azioni esempio
		icon1.addActionListener(e -> {
		    new UtenteForm().setVisible(true);
		});
		icon2.addActionListener(e -> {
		    new RaccolteFrame();
		});
		icon3.addActionListener(e -> JOptionPane.showMessageDialog(this, "Stat cliccato"));

		menuPanel.add(icon1);
		menuPanel.add(icon2);
		menuPanel.add(icon3);

		menuButton.addActionListener(e -> {
			menuPanel.setVisible(!menuPanel.isVisible());
		});

		addPoetryButton.addActionListener(e -> {
		    new PoesiaForm().setVisible(true);
		});

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
