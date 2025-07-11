package boundary;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import controller.ControllerPoetUp;

public class RaccoltaForm extends JFrame {

    public RaccoltaForm(JFrame parentFrame,JFrame home) {
        setTitle("Nuova Raccolta");
        setSize(500, 400);
        setLocationRelativeTo(parentFrame);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        // Colori e font coerenti con RegistrationForm
        Color primaryColor = new Color(60, 164, 238);
        Color bgColor = new Color(245, 248, 250);
        Color textColor = new Color(44, 62, 80);
        Font labelFont = new Font("Segoe UI", Font.BOLD, 16);
        Font inputFont = new Font("Segoe UI", Font.PLAIN, 14);

        JPanel panel = new JPanel();
        panel.setBackground(bgColor);
        panel.setLayout(null);
        setContentPane(panel);

        JLabel titoloLabel = new JLabel("Titolo raccolta:");
        titoloLabel.setBounds(50, 50, 150, 25);
        titoloLabel.setFont(labelFont);
        titoloLabel.setForeground(textColor);
        panel.add(titoloLabel);

        JTextField titoloField = new JTextField();
        titoloField.setBounds(50, 80, 380, 30);
        titoloField.setFont(inputFont);
        panel.add(titoloField);

        JLabel descrizioneLabel = new JLabel("Descrizione:");
        descrizioneLabel.setBounds(50, 130, 150, 25);
        descrizioneLabel.setFont(labelFont);
        descrizioneLabel.setForeground(textColor);
        panel.add(descrizioneLabel);

        JTextArea descrizioneArea = new JTextArea();
        descrizioneArea.setFont(inputFont);
        descrizioneArea.setLineWrap(true);
        descrizioneArea.setWrapStyleWord(true);

        JScrollPane scroll = new JScrollPane(descrizioneArea);
        scroll.setBounds(50, 160, 380, 100);
        panel.add(scroll);

        JButton creaBtn = new JButton("Crea");
        creaBtn.setBounds(180, 290, 120, 40);
        creaBtn.setBackground(primaryColor);
        creaBtn.setForeground(Color.WHITE);
        creaBtn.setFocusPainted(false);
        creaBtn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        creaBtn.setBorderPainted(false);
        panel.add(creaBtn);

        creaBtn.addActionListener(e -> {
            String titolo = titoloField.getText().trim();
            String descrizione = descrizioneArea.getText().trim();

            if (titolo.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Il titolo non può essere vuoto.", "Errore", JOptionPane.ERROR_MESSAGE);
                return;
            }
            // Controllo 2: lunghezza massima
            if (titolo.length() > 25) {
                JOptionPane.showMessageDialog(this, "Il titolo non può superare i 25 caratteri.", "Errore", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Controllo 3: caratteri speciali (sono ammessi solo lettere, numeri e spazi)
            if (!titolo.matches("[a-zA-Z0-9àèéìòùÀÈÉÌÒÙ' ]+")) {
                JOptionPane.showMessageDialog(this, "Il titolo non può contenere caratteri speciali.", "Errore", JOptionPane.ERROR_MESSAGE);
                return;
            }


            String esito = ControllerPoetUp.addRaccolta(titolo,descrizione);
            if (esito.equals("Raccolta Creata")) {
                JOptionPane.showMessageDialog(this, esito);
                this.dispose(); // chiudi il form
                parentFrame.dispose(); // chiudi finestra raccolte vecchia
                new RaccolteFrame(home); // riapri aggiornata
            } else {
                JOptionPane.showMessageDialog(this,esito, "Errore", JOptionPane.ERROR_MESSAGE);
            }
        });

        setVisible(true);
    }
}