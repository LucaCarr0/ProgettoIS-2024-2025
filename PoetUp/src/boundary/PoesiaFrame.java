package boundary;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import controller.ControllerPoetUp;
import dto.CommentoDTO;
import dto.PoesiaCompletaDTO;

public class PoesiaFrame extends JFrame {

    public PoesiaFrame(int id_poesia,String autore) {
        // === Recupero dati ===
        PoesiaCompletaDTO poesia = ControllerPoetUp.visualizzaPoesia(id_poesia,autore);
        System.out.println(poesia.isAlreadyLiked());

        if (poesia == null) {
            JOptionPane.showMessageDialog(this, "Errore nel caricamento della poesia.", "Errore", JOptionPane.ERROR_MESSAGE);
            dispose();
            return;
        }

        setTitle(poesia.getTitolo());
        setSize(700, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setAlwaysOnTop(true); // copre la finestra precedente

        // Colori
        Color bgColor = new Color(245, 248, 255);
        Color textColor = new Color(40, 40, 40);
        Color accentColor = new Color(60, 130, 210);
        Color likeColor = new Color(220, 50, 50);
        Color tagColor = new Color(220, 230, 255);

        Font titleFont = new Font("Segoe UI", Font.BOLD, 26);
        Font subtitleFont = new Font("Segoe UI", Font.ITALIC, 14);
        Font bodyFont = new Font("Segoe UI", Font.PLAIN, 16);

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(bgColor);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));

        // Titolo
        JLabel titleLabel = new JLabel(poesia.getTitolo(), SwingConstants.CENTER);
        titleLabel.setFont(titleFont);
        titleLabel.setForeground(accentColor);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(titleLabel);

        // Autore + Stato
        JLabel autoreLabel = new JLabel("di " + poesia.getAutore() + " • Stato: " + poesia.getStato(), SwingConstants.CENTER);
        autoreLabel.setFont(subtitleFont);
        autoreLabel.setForeground(new Color(90, 90, 90));
        autoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(Box.createVerticalStrut(4));
        mainPanel.add(autoreLabel);

     // Tags
        JPanel tagPanel = new JPanel();
        tagPanel.setBackground(bgColor);
        tagPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 8, 5));

        // Estrai e filtra i tag
        String[] tags = poesia.getTag().split("#");
        for (String rawTag : tags) {
            if (!rawTag.isBlank()) {
                JLabel tagLabel = new JLabel("#" + rawTag.trim());
                tagLabel.setOpaque(true);
                tagLabel.setBackground(tagColor);
                tagLabel.setForeground(accentColor);
                tagLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
                tagLabel.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
                tagPanel.add(tagLabel);
            }
        }

        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(tagPanel);

        // Testo
        JTextArea testoArea = new JTextArea(poesia.getTesto());
        testoArea.setFont(bodyFont);
        testoArea.setForeground(textColor);
        testoArea.setBackground(Color.WHITE);
        testoArea.setLineWrap(true);
        testoArea.setWrapStyleWord(true);
        testoArea.setEditable(false);
        testoArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JScrollPane testoScroll = new JScrollPane(testoArea);
        testoScroll.setPreferredSize(new Dimension(650, 250));
        testoScroll.setBorder(BorderFactory.createLineBorder(accentColor, 1));
        mainPanel.add(Box.createVerticalStrut(15));
        mainPanel.add(testoScroll);

        // Cuore + like
        JPanel likePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        likePanel.setBackground(bgColor);

     // Cuore e likeCount dichiarati fuori per poter aggiornare dall'evento
     // Stato iniziale del cuore (può essere calcolato in base a dati del DTO se disponibili)
     // Stato iniziale del cuore: vuoto (♡)
     // Stato iniziale: verifica se like esiste già per questa poesia
     // Stato locale mutabile del like
        final boolean[] liked = {poesia.isAlreadyLiked()};
        System.out.println("INIT: liked = " + liked[0]);

        JLabel cuore = new JLabel(liked[0] ? "\u2665" : "\u2661"); // ♥ pieno o ♡ vuoto
        cuore.setFont(new Font("Segoe UI", Font.PLAIN, 24));
        cuore.setForeground(liked[0] ? likeColor : Color.GRAY);
        cuore.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        // Contatore like da DTO
        int[] conteggio = {poesia.getContatoreLike()};
        JLabel likeCount = new JLabel(String.valueOf(conteggio[0]));
        likeCount.setFont(new Font("Segoe UI", Font.BOLD, 16));
        likeCount.setForeground(likeColor);

        // Evento click sul cuore
        cuore.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                System.out.println("CLICK: liked prima = " + liked[0]);

                boolean successo = ControllerPoetUp.like(liked[0], poesia.getId());

                if (successo) {
                    // Inverti lo stato
                    liked[0] = !liked[0];
                    poesia.setAlreadyLiked(liked[0]);

                    if (liked[0]) {
                        cuore.setText("\u2665"); // ♥ pieno
                        cuore.setForeground(likeColor);
                        conteggio[0]++;
                    } else {
                        cuore.setText("\u2661"); // ♡ vuoto
                        cuore.setForeground(Color.GRAY);
                        if (conteggio[0] > 0) conteggio[0]--;
                    }

                    likeCount.setText(String.valueOf(conteggio[0]));

                } else {
                    // Se errore → forzo stato a "like rimosso"
                    liked[0] = false;
                    poesia.setAlreadyLiked(false);

                    cuore.setText("\u2661"); // ♡ vuoto
                    cuore.setForeground(Color.GRAY);
                    if (conteggio[0] > 0) conteggio[0]--;
                    likeCount.setText(String.valueOf(conteggio[0]));

                    System.out.println("ERRORE: like fallito → stato forzato a liked = false");

                }
            }
        });

        likePanel.add(cuore);
        likePanel.add(likeCount);





        
        System.out.println(poesia.getId());

        likePanel.add(cuore);
        likePanel.add(likeCount);




        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(likePanel);

        // Commenti
        JPanel commentiPanel = new JPanel();
        commentiPanel.setLayout(new BoxLayout(commentiPanel, BoxLayout.Y_AXIS));
        commentiPanel.setBackground(bgColor);
        commentiPanel.setBorder(BorderFactory.createTitledBorder("Ultimi commenti"));

        ArrayList<CommentoDTO> commenti = poesia.getUltimiCommenti(); // max 3
        if (commenti.isEmpty()) {
            JLabel noComment = new JLabel("Nessun commento disponibile.");
            noComment.setFont(bodyFont);
            noComment.setForeground(new Color(120, 120, 120));
            commentiPanel.add(noComment);
        } else {
        	for (CommentoDTO commento : commenti) {
        	    JPanel commentoBox = new JPanel();
        	    commentoBox.setLayout(new BoxLayout(commentoBox, BoxLayout.Y_AXIS));
        	    commentoBox.setBackground(Color.WHITE);
        	    commentoBox.setBorder(BorderFactory.createCompoundBorder(
        	            BorderFactory.createLineBorder(new Color(200, 200, 200)),
        	            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        	    ));
        	    commentoBox.setMaximumSize(new Dimension(600, 100));

        	    // Autore + Data
        	    JLabel autoreDataLabel = new JLabel(commento.getAutore() + " • " + commento.getData().toString());
        	    autoreDataLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        	    autoreDataLabel.setForeground(new Color(80, 80, 80));

        	    // Testo del commento
        	    JTextArea testoCommento = new JTextArea(commento.getTesto());
        	    testoCommento.setFont(new Font("Segoe UI", Font.ITALIC, 13));
        	    testoCommento.setLineWrap(true);
        	    testoCommento.setWrapStyleWord(true);
        	    testoCommento.setEditable(false);
        	    testoCommento.setBackground(Color.WHITE);
        	    testoCommento.setBorder(null);

        	    commentoBox.add(autoreDataLabel);
        	    commentoBox.add(Box.createVerticalStrut(4));
        	    commentoBox.add(testoCommento);

        	    commentiPanel.add(commentoBox);
        	    commentiPanel.add(Box.createVerticalStrut(8));
        	}

        }

        mainPanel.add(Box.createVerticalStrut(15));
        mainPanel.add(commentiPanel);

        // Bottone chiudi
        JButton closeBtn = new JButton("Chiudi");
        closeBtn.setBackground(accentColor);
        closeBtn.setForeground(Color.WHITE);
        closeBtn.setFocusPainted(false);
        closeBtn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        closeBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        closeBtn.addActionListener(e -> dispose());

        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(closeBtn);

        setContentPane(mainPanel);
        setVisible(true);
    }
}
