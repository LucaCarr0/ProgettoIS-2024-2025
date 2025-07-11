package boundary;

import controller.ControllerPoetUp;
import dto.CommentoDTO;
import dto.PoesiaCompletaDTO;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PoesiaFrame extends JFrame {

    public PoesiaFrame(int id_poesia,String autore) {
        // === Recupero dati ===
        PoesiaCompletaDTO poesia = ControllerPoetUp.visualizzaPoesia(id_poesia,autore);

        if (poesia == null) {
            JOptionPane.showMessageDialog(this, "Errore nel caricamento della poesia.", "Errore", JOptionPane.ERROR_MESSAGE);
            dispose();
            return;
        }

        setTitle(poesia.getTitolo());
        setSize(700, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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

        JLabel cuore = new JLabel("❤️");
        cuore.setFont(new Font("Segoe UI", Font.PLAIN, 24));
        JLabel likeCount = new JLabel(String.valueOf(poesia.getContatoreLike()));
        likeCount.setFont(new Font("Segoe UI", Font.BOLD, 16));
        likeCount.setForeground(likeColor);

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
