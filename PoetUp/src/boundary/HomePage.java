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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import boundary.theme.Theme;
import boundary.theme.ThemeManager;
import boundary.theme.ThemeType;
import controller.ControllerPoetUp;
import dto.PoesiaDTO;
import session.SessioneUtente;

public class HomePage extends JFrame {

	private JPanel contentPane;
	private JPanel menuPanel;
	private JPanel listPanel;  


	public HomePage() {
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 550);
		contentPane = new JPanel();
		contentPane.setLayout(null);

		Theme theme = ThemeManager.getTheme();
		// mappa colori: palette index:
		// 0 = textPrimary
		// 1 = primary
		// 2 = backgroundPrimary
		// 3 = accent1 (per like)
		// 4 = secondary background / cards

		Color textColor = theme.getPalette().get(0);       // prima usavi Color(245, 248, 250)
		Color primaryColor = theme.getPalette().get(1);    // prima usavi Color(60, 164, 238)
		Color cardColor = theme.getPalette().get(4);       // sostituisce Color.cyan

		contentPane.setBackground(theme.getPalette().get(2)); // backgroundPrimary
		setContentPane(contentPane);
		setLocationRelativeTo(null);

		Font cardFont = new Font("Segoe UI", Font.PLAIN, 15);

		JLabel titleLabel = new JLabel("POET UP - Home");
		titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
		titleLabel.setForeground(textColor);
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setBounds(200, 40, 300, 40);
		contentPane.add(titleLabel);

		JButton searchButton = createCircleButton("/res/ricerca.png", 48);
		searchButton.setBounds(30, 440, 48, 48);
		contentPane.add(searchButton);
		searchButton.addActionListener(e -> {
			new RicercaForm(this);
			setVisible(false);
		});

		JButton addPoetryButton = createCircleButton("/res/piuma.png", 64);
		addPoetryButton.setBounds(318, 432, 64, 64);
		addPoetryButton.setToolTipText("Aggiungi poesia");
		contentPane.add(addPoetryButton);

		JButton menuButton = createCircleButton("/res/menu.png", 48);
		menuButton.setBounds(622, 440, 48, 48);
		contentPane.add(menuButton);

		menuPanel = new JPanel();
		int numButtons = 4;
		if (SessioneUtente.isAmministratore()) {
			numButtons++;
		}
		menuPanel.setLayout(new GridLayout(numButtons, 1, 6, 6));
		menuPanel.setBounds(622, 236, 48, 192);
		menuPanel.setBackground(theme.getPalette().get(2));
		menuPanel.setVisible(false);
		contentPane.add(menuPanel);

		JButton icon1 = createCircleButton("/res/utente.png", 48);
		JButton icon2 = createCircleButton("/res/raccolta.png", 48);
		JButton icon3 = createCircleButton("/res/stat.png", 48);
		JButton icon4 = createCircleButton("/res/report.png", 48);
		JButton icon5 = createCircleButton("/res/tema.png", 48);

		icon1.addActionListener(e -> new UtenteForm(this).setVisible(true));
		icon2.addActionListener(e -> new RaccolteFrame(this));
		icon3.addActionListener(e -> new StatisticheForm(this).setVisible(true));
		icon4.addActionListener(e -> new ReportForm(this).setVisible(true));
		icon5.addActionListener(e -> {
			if (ThemeManager.getCurrentType() == ThemeType.CHIARO) {
				ThemeManager.setTheme(ThemeType.SCURO);
			} else {
				ThemeManager.setTheme(ThemeType.CHIARO);
			}
			applyTheme();
		});

		if (SessioneUtente.isAmministratore()) {
			menuPanel.add(icon4);
		}
		menuPanel.add(icon1);
		menuPanel.add(icon2);
		menuPanel.add(icon3);
		menuPanel.add(icon5);

		menuButton.addActionListener(e -> menuPanel.setVisible(!menuPanel.isVisible()));
		addPoetryButton.addActionListener(e -> new PoesiaForm(this).setVisible(true));

		listPanel = new JPanel();
		listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
		listPanel.setBackground(theme.getPalette().get(2));

		JScrollPane scrollPane = new JScrollPane(listPanel);
		scrollPane.setBounds(100, 110, 480, 300);
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
		contentPane.add(scrollPane);

		this.popolaFeed();
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

	private void applyTheme() {
		Theme theme = ThemeManager.getTheme();
		contentPane.setBackground(theme.getPalette().get(2));
		menuPanel.setBackground(theme.getPalette().get(2));
		getContentPane().setBackground(theme.getPalette().get(2));

		for (Component c : contentPane.getComponents()) {
			if (c instanceof JLabel label) {
				label.setForeground(theme.getPalette().get(0));
			}
		}
		
		repaint();
		popolaFeed();
	
	}
	
	public void popolaFeed() {
	    listPanel.removeAll();  // pulisci il pannello

	    ArrayList<PoesiaDTO> poesie = ControllerPoetUp.visualizzaFeed();
		Theme theme = ThemeManager.getTheme();

	    
	    // Prendi i colori e font dal tema corrente
		Color cardColor = theme.getPalette().get(3);   // prova 3
		Color primaryColor = theme.getPalette().get(1);
		Color textColor = theme.getPalette().get(5);   
		Color dateColor = theme.getPalette().get(5);
		Color likeColor = theme.getPalette().get(5);   
		Color hoverColor = theme.getPalette().get(6);  
		

	    Font cardFont = new Font("Segoe UI", Font.PLAIN, 14);

	    for (int i = 0; i < 5 && i < poesie.size(); i++) {
	        PoesiaDTO poesia = poesie.get(i);
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
	        dataLabel.setForeground(dateColor);
	        dataLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
	        rightPanel.add(dataLabel);

	        JLabel likeLabel = new JLabel("\u2665 " + poesia.getLike());
	        likeLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
	        likeLabel.setForeground(likeColor);
	        likeLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
	        rightPanel.add(Box.createVerticalStrut(10));
	        rightPanel.add(likeLabel);

	        card.add(leftPanel, BorderLayout.CENTER);
	        card.add(rightPanel, BorderLayout.EAST);

	        card.addMouseListener(new MouseAdapter() {
	            @Override
	            public void mouseClicked(MouseEvent e) {
	                new PoesiaFrame(HomePage.this,poesia.getId(), poesia.getAutore());
	            }

	            @Override
	            public void mouseEntered(MouseEvent e) {
	                card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	                card.setBackground(hoverColor);
	            }

	            @Override
	            public void mouseExited(MouseEvent e) {
	                card.setBackground(cardColor);
	            }
	        });

	        listPanel.add(card);
	        listPanel.add(Box.createRigidArea(new Dimension(0, 10)));
			listPanel.setBackground(theme.getPalette().get(2));
	    }

	    listPanel.revalidate();
	    listPanel.repaint();
	}



}
