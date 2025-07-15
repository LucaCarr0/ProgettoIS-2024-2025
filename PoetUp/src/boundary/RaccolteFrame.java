package boundary;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import boundary.theme.Theme;
import boundary.theme.ThemeManager;
import controller.ControllerUtenti;
import dto.RaccoltaDTO;

public class RaccolteFrame extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Theme theme;

    public RaccolteFrame(JFrame parentFrame) {
    	Image icon = new ImageIcon(getClass().getResource("/res/logo.png")).getImage();
		setIconImage(icon);
		ThemeManager tema_app=ThemeManager.getInstance();
		this.theme = tema_app.getTheme();
    	
    	
        setTitle("Raccolte");
        setSize(900, 600);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(parentFrame);
        
       // mappa colori: palette index:
     		// 0 = textPrimary
     		// 1 = primary
     		// 2 = backgroundPrimary
     		// 3 = accent1 (per like)
     		// 4 = secondary background / cards
        // Colori
        Color primaryColor = theme.getPalette().get(1);
        Color accentColor = theme.getPalette().get(4);
        Color bgColor = theme.getPalette().get(2);
        Color cardColor = theme.getPalette().get(3);
        //Color textColor = theme.getPalette().get(0);

        Font titleFont = new Font("Segoe UI", Font.BOLD, 24);
        Font cardFont = new Font("Segoe UI", Font.PLAIN, 15);

        JPanel contentPane = new JPanel();
        contentPane.setBackground(bgColor);
        contentPane.setLayout(null);
        setContentPane(contentPane);

        JLabel titleLabel = new JLabel("Raccolte");
        titleLabel.setFont(titleFont);
        titleLabel.setForeground(accentColor);
        titleLabel.setBounds(0, 10, getWidth(), 40);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        contentPane.add(titleLabel);

        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setBackground(bgColor);

        JScrollPane scrollPane = new JScrollPane(listPanel);
        scrollPane.setBounds(100, 60, 700, 420);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        contentPane.add(scrollPane);

        

        ArrayList<RaccoltaDTO> raccolte = ControllerUtenti.getRaccolteByUtente();

        for (RaccoltaDTO raccolta : raccolte) {
            JPanel card = new JPanel();
            card.setLayout(null);
            card.setPreferredSize(new Dimension(680, 80));
            card.setBackground(cardColor);
            card.setBorder(BorderFactory.createLineBorder(primaryColor, 1));

            JLabel titolo = new JLabel(raccolta.getTitolo());
            titolo.setFont(new Font("Segoe UI", Font.BOLD, 16));
            titolo.setBounds(20, 10, 400, 20);
            card.add(titolo);

            JLabel descrizione = new JLabel("<html><i>" + raccolta.getDescrizione() + "</i></html>");
            descrizione.setFont(cardFont);
            descrizione.setBounds(20, 35, 450, 20);
            card.add(descrizione);



            // Azione click sulla card → apre PoesieFrame
            card.addMouseListener(new MouseAdapter() {
            		@Override
                    public void mouseClicked(MouseEvent e) {
                        new PoesieRaccoltaFrame(RaccolteFrame.this, raccolta.getId(), raccolta.getTitolo());


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



            JButton modificaBtn = new JButton("Modifica");
            modificaBtn.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            modificaBtn.setBounds(500, 15, 90, 25);
            modificaBtn.setBackground(new Color(255, 200, 100));
            modificaBtn.setFocusPainted(false);
            card.add(modificaBtn);

            JButton eliminaBtn = new JButton("Elimina");
            eliminaBtn.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            eliminaBtn.setBounds(500, 45, 90, 25);
            eliminaBtn.setBackground(new Color(230, 90, 90));
            eliminaBtn.setForeground(Color.WHITE);
            eliminaBtn.setFocusPainted(false);
            card.add(eliminaBtn);

           
            modificaBtn.addActionListener(e -> {
                JFrame modificaFrame = new JFrame("Modifica Raccolta");
                modificaFrame.setSize(400, 250);
                modificaFrame.setLocationRelativeTo(this);
                modificaFrame.setLayout(null);

                JLabel titoloLabel = new JLabel("Titolo:");
                titoloLabel.setBounds(30, 30, 80, 25);
                modificaFrame.add(titoloLabel);

                javax.swing.JTextField campoTitoloInput = new javax.swing.JTextField(raccolta.getTitolo());
                campoTitoloInput.setBounds(120, 30, 220, 25);
                modificaFrame.add(campoTitoloInput);

                JLabel descLabel = new JLabel("Descrizione:");
                descLabel.setBounds(30, 70, 80, 25);
                modificaFrame.add(descLabel);

                javax.swing.JTextArea campoDescrizioneInput = new javax.swing.JTextArea(raccolta.getDescrizione());
                campoDescrizioneInput.setLineWrap(true);
                campoDescrizioneInput.setWrapStyleWord(true);
                JScrollPane scrollDesc = new JScrollPane(campoDescrizioneInput);
                scrollDesc.setBounds(120, 70, 220, 80);
                modificaFrame.add(scrollDesc);

                JButton salvaBtn = new JButton("Salva");
                salvaBtn.setBounds(150, 170, 100, 30);
                modificaFrame.add(salvaBtn);

                salvaBtn.addActionListener(ev -> {
                    String titoloInput = campoTitoloInput.getText().trim();
                    String descrizioneInput = campoDescrizioneInput.getText().trim();

                    //VALIDAZIONE INPUT
                    if (titoloInput.isEmpty()) {
                        JOptionPane.showMessageDialog(modificaFrame, "Il titolo non può essere vuoto.");
                        return;
                    }

                    if (titoloInput.length() > 25) {
                        JOptionPane.showMessageDialog(modificaFrame, "Il titolo non può superare i 25 caratteri.");
                        return;
                    }

                    if (!titoloInput.matches("^[a-zA-Z0-9\\s]+$")) {
                        JOptionPane.showMessageDialog(modificaFrame, "Il titolo non può contenere caratteri speciali.");
                        return;
                    }

                   
                    if (descrizioneInput.length() > 255) {
                        JOptionPane.showMessageDialog(modificaFrame, "La descrizione non può superare i 255 caratteri.");
                        return;
                    }

                    
                    String messaggio = ControllerUtenti.modificaRaccolta(titoloInput, descrizioneInput, raccolta.getId());
                    JOptionPane.showMessageDialog(modificaFrame, messaggio);

                    if (messaggio.equals("Raccolta aggiornata con successo!")) {
                        modificaFrame.dispose();
                        this.dispose();
                        new RaccolteFrame(parentFrame); 
                    }
                });

                modificaFrame.setVisible(true);
            });

            eliminaBtn.addActionListener(e -> {
                int conferma = JOptionPane.showConfirmDialog(this, "Eliminare la raccolta '" + raccolta.getTitolo() + "'?", "Conferma", JOptionPane.YES_NO_OPTION);
                if (conferma == JOptionPane.YES_OPTION) {
                    String risultato = ControllerUtenti.eliminaRaccolta(raccolta.getId());
                    JOptionPane.showMessageDialog(this, risultato);

                    if (risultato.equals("Raccolta eliminata con successo!")) {

                        parentFrame.dispose();         // chiude la vecchia home
                        HomePage nuovaHome = new HomePage();
                        nuovaHome.setLocationRelativeTo(this);
                        nuovaHome.setVisible(true);    // mostra la nuova home
                        this.dispose();
                        new RaccolteFrame(nuovaHome); // Ricarica finestra aggiornata
                    }
                }
            });


            card.setAlignmentX(Component.LEFT_ALIGNMENT);
            listPanel.add(card);
            listPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        }

       
        JButton addBtn = new JButton("+");
        addBtn.setFont(new Font("Segoe UI", Font.BOLD, 28));
        addBtn.setForeground(Color.WHITE);
        addBtn.setBackground(accentColor);
        addBtn.setBorderPainted(false);
        addBtn.setBounds(410, 500, 60, 50);
        addBtn.setFocusPainted(false);
        addBtn.addActionListener(e -> new RaccoltaForm(this,parentFrame));
        contentPane.add(addBtn);

        setVisible(true);
        
        
    }
    
    
}
