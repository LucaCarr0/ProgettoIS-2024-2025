package boundary;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class SelettoreImmagineProfilo extends JDialog {

    private String immagineSelezionata;

    public SelettoreImmagineProfilo(JFrame parent) {
        super(parent, "Seleziona immagine profilo", true);
        setSize(500, 400);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(0, 4, 10, 10));
        panel.setBackground(Color.DARK_GRAY);
        JScrollPane scrollPane = new JScrollPane(panel);
        add(scrollPane, BorderLayout.CENTER);

        List<String> immagini = caricaNomiImmagini(); // es: "/res/pfp/avatar1.png"

        for (String path : immagini) {
            ImageIcon icon = resizeIcon(path, 80, 80);
            JLabel label = new JLabel(icon);
            label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            label.setToolTipText(path);
            label.setHorizontalAlignment(JLabel.CENTER);
            label.setVerticalAlignment(JLabel.CENTER);
            label.setOpaque(true);
            label.setBackground(Color.LIGHT_GRAY);
            label.setBorder(BorderFactory.createLineBorder(Color.BLACK));

            label.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    immagineSelezionata = path;
                    dispose();
                }
            });

            panel.add(label);
        }
    }

    public String getImmagineSelezionata() {
        return immagineSelezionata;
    }

    private List<String> caricaNomiImmagini() {
        // Qui puoi caricare i nomi delle immagini manualmente
        // o in alternativa elencare da file system in fase di deploy
        List<String> paths = new ArrayList<>();
        String[] nomi = {
            "pfp1.jpg", "pfp2.jpg", "pfp3.jpg", "pfp4.jpg", "pfp5.jpg", 
            "pfp6.jpg", "pfp7.jpg", "pfp8.jpg", "pfp9.jpg", "pfp10.jpg"
        };

        for (String nome : nomi) {
            paths.add("/res/pfp/" + nome);
        }
        return paths;
    }

    private ImageIcon resizeIcon(String resourcePath, int width, int height) {
        URL url = getClass().getResource(resourcePath);
        if (url == null) return new ImageIcon();
        Image img = new ImageIcon(url).getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }
}