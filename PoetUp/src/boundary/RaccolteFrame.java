package boundary;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class RaccolteFrame extends JFrame {

    public RaccolteFrame() {
        setTitle("Le tue Raccolte");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Colori
        Color primaryColor = new Color(60, 164, 238);
        Color accentColor = new Color(255, 122, 89);
        Color bgColor = new Color(235, 243, 255);
        Color cardColor = new Color(255, 255, 255);
        Color textColor = new Color(44, 62, 80);

        Font titleFont = new Font("Segoe UI", Font.BOLD, 24);
        Font cardFont = new Font("Segoe UI", Font.PLAIN, 15);

        JPanel contentPane = new JPanel();
        contentPane.setBackground(bgColor);
        contentPane.setLayout(null);
        setContentPane(contentPane);

        JLabel titleLabel = new JLabel("Le tue Raccolte");
        titleLabel.setFont(titleFont);
        titleLabel.setForeground(textColor);
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

        // === QUI DEVI RECUPERARE LE RACCOLTE DAL DATABASE ===
        /*
        List<Raccolta> raccolte = RaccoltaDAO.getRaccolteByUtente(idUtente);
        */

        // === SIMULAZIONE (da rimuovere) ===
        List<Raccolta> raccolte = List.of(
            new Raccolta(1, "Momenti", "Una raccolta di momenti speciali"),
            new Raccolta(2, "Nostalgia", "Ricordi e malinconie"),
            new Raccolta(3, "Sorrisi", "Poesie leggere e divertenti"),
            new Raccolta(1, "Momenti", "Una raccolta di momenti speciali"),
            new Raccolta(2, "Nostalgia", "Ricordi e malinconie"),
            new Raccolta(3, "Sorrisi", "Poesie leggere e divertenti")
            
        );

        for (Raccolta raccolta : raccolte) {
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
                    JOptionPane.showMessageDialog(
                        card, 
                        "Funzione non ancora implementata per: " + raccolta.getTitolo(),
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


            card.setAlignmentX(Component.LEFT_ALIGNMENT);
            listPanel.add(card);
            listPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        

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

            // Azioni pulsanti
            modificaBtn.addActionListener(e -> {
                JOptionPane.showMessageDialog(this, "Modifica non ancora implementata per: " + raccolta.getTitolo());
                // new ModificaRaccoltaForm(raccolta);
            });

            eliminaBtn.addActionListener(e -> {
                int conferma = JOptionPane.showConfirmDialog(this, "Eliminare la raccolta '" + raccolta.getTitolo() + "'?", "Conferma", JOptionPane.YES_NO_OPTION);
                if (conferma == JOptionPane.YES_OPTION) {
                    // RaccoltaDAO.elimina(raccolta.getId());
                    JOptionPane.showMessageDialog(this, "Raccolta eliminata.");
                    this.dispose();
                    new RaccolteFrame(); // ricarica
                }
            });

            card.setAlignmentX(Component.LEFT_ALIGNMENT);
            listPanel.add(card);
            listPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        // Bottone "+"
        JButton addBtn = new JButton("+");
        addBtn.setFont(new Font("Segoe UI", Font.BOLD, 28));
        addBtn.setForeground(Color.WHITE);
        addBtn.setBackground(accentColor);
        addBtn.setBorderPainted(false);
        addBtn.setBounds(410, 500, 60, 50);
        addBtn.setFocusPainted(false);
        addBtn.addActionListener(e -> new RaccoltaForm(this));
        contentPane.add(addBtn);

        setVisible(true);
    }

    // Classe interna per rappresentare una raccolta (da sostituire con la tua classe reale)
    class Raccolta {
        private int id;
        private String titolo;
        private String descrizione;

        public Raccolta(int id, String titolo, String descrizione) {
            this.id = id;
            this.titolo = titolo;
            this.descrizione = descrizione;
        }

        public int getId() { return id; }
        public String getTitolo() { return titolo; }
        public String getDescrizione() { return descrizione; }
    }
}
