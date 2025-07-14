package boundary;

import javax.swing.*;
import java.awt.*;
import controller.ControllerPoetUp;

public class CommentaFrame extends JFrame {

    private static final long serialVersionUID = 1L;

	public CommentaFrame(JFrame parentFrame, int idPoesia, String autore) {
    	Image icon = new ImageIcon(getClass().getResource("/res/logo.png")).getImage();
		setIconImage(icon);
        setTitle("Aggiungi commento");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setAlwaysOnTop(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(250, 250, 255));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel label = new JLabel("Scrivi il tuo commento:");
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(label);

        panel.add(Box.createVerticalStrut(10));

        JTextArea commentArea = new JTextArea(5, 30);
        commentArea.setLineWrap(true);
        commentArea.setWrapStyleWord(true);
        commentArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(commentArea);
        panel.add(scrollPane);

        panel.add(Box.createVerticalStrut(10));

        JButton sendButton = new JButton("Invia");
        sendButton.setBackground(new Color(100, 160, 240));
        sendButton.setForeground(Color.WHITE);
        sendButton.setFocusPainted(false);
        sendButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        sendButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        sendButton.addActionListener(e -> {
            String testo = commentArea.getText().trim();
            if (testo.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Inserisci un commento prima di inviare.", "Errore", JOptionPane.WARNING_MESSAGE);
                return;
            }

            boolean successo = ControllerPoetUp.commenta(idPoesia, testo); 
            if (successo) {
                JOptionPane.showMessageDialog(this, "Commento inviato con successo!", "Successo", JOptionPane.INFORMATION_MESSAGE);
                ((PoesiaFrame) parentFrame).aggiornaCommenti();
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Errore nell'invio del commento.", "Errore", JOptionPane.ERROR_MESSAGE);
            }
        });

        panel.add(sendButton);
        setContentPane(panel);
        setVisible(true);
    }
}
