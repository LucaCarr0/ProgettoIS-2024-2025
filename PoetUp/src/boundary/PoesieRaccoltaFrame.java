package boundary;

import controller.ControllerPoetUp;
import dto.PoesiaDTO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class PoesieRaccoltaFrame extends JFrame {

    public PoesieRaccoltaFrame(JFrame parentFrame, int raccoltaId, String titoloRaccolta) {
        setTitle("Poesie in \"" + titoloRaccolta + "\"");
        setSize(900, 600);
        setLocationRelativeTo(parentFrame);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        Color primaryColor = new Color(60, 164, 238);
        Color accentColor = new Color(255, 122, 89);
        Color bgColor = new Color(235, 243, 255);
        Color cardColor = new Color(255, 255, 255);
        Color textColor = new Color(44, 62, 80);

        Font titleFont = new Font("Segoe UI", Font.BOLD, 24);
        Font cardFont = new Font("Segoe UI", Font.PLAIN, 14);

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
        
        // Caricamento poesie della raccolta
        System.out.println("id raccolta: "+raccoltaId);
        
        ArrayList<PoesiaDTO> poesie = ControllerPoetUp.getPoesieByRaccolta(raccoltaId);
        System.out.println(poesie);
        for (int i=0;i<poesie.size();i++) {
        	System.out.println(poesie.get(i).getTitolo());
        }
        
        for (int i=0;i<poesie.size();i++) {
        	PoesiaDTO poesia = poesie.get(i);
        	System.out.println("id poesia da interfaccia: "+poesia.getId());
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

            JLabel likeLabel = new JLabel("\u2665 " + poesia.getLike());
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
                	new PoesiaFrame(poesia.getId(),poesia.getAutore());
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
	

        setVisible(true);
    }
	

   
    
}
