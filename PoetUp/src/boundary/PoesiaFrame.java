package boundary;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

import boundary.theme.Theme;
import boundary.theme.ThemeManager;
import controller.ControllerPoesie;
import controller.ControllerUtenti;
import dto.CommentoDTO;
import dto.PoesiaCompletaDTO;

public class PoesiaFrame extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel commentiPanel;
    private JScrollPane commentiScroll;
    private int id_poesia;
    private String autore;

    public PoesiaFrame(JFrame parentFrame, int id_poesia, String autore) {
        Image icon = new ImageIcon(getClass().getResource("/res/logo.png")).getImage();
        setIconImage(icon);
        this.id_poesia = id_poesia;
        this.autore = autore;
        Theme theme = ThemeManager.getTheme();

        PoesiaCompletaDTO poesia = ControllerPoesie.visualizzaPoesia(id_poesia, autore);

        setTitle(poesia.getTitolo());
        setSize(700, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setAlwaysOnTop(true);

        Color bgColor = theme.getBackgroundPrimary();
        Color textColor = theme.getTextSecondary();
        Color accentColor = theme.getAccentColor();

        Font titleFont = new Font("Segoe UI", Font.BOLD, 26);
        Font subtitleFont = new Font("Segoe UI", Font.ITALIC, 14);
        Font bodyFont = new Font("Segoe UI", Font.PLAIN, 16);

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(bgColor);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));

        if (parentFrame instanceof PoesieRaccoltaFrame) {
            JPanel topPanel = new JPanel(new BorderLayout());
            topPanel.setBackground(bgColor);

            JButton xButton = new JButton("X");
            xButton.setFocusPainted(false);
            xButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
            xButton.setBackground(accentColor);
            xButton.setForeground(textColor);
            xButton.setPreferredSize(new Dimension(45, 25));

            topPanel.add(xButton, BorderLayout.EAST);
            mainPanel.add(topPanel);

            xButton.addActionListener(e -> {
                String[] options = {"Elimina poesia", "Sposta in un'altra raccolta"};
                String scelta = (String) JOptionPane.showInputDialog(
                        this,
                        "Cosa vuoi fare con questa poesia?",
                        "Gestione poesia",
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        options,
                        options[0]);

                if (scelta == null) return;

                if (scelta.equals("Elimina poesia")) {
                    int conferma = JOptionPane.showConfirmDialog(
                            this,
                            "Sei sicuro di voler eliminare questa poesia?",
                            "Conferma eliminazione",
                            JOptionPane.YES_NO_OPTION);

                    if (conferma == JOptionPane.YES_OPTION) {
                        String esito = ControllerPoesie.eliminaPoesia(id_poesia);
                        JOptionPane.showMessageDialog(this, esito);
                        PoesieRaccoltaFrame parent = (PoesieRaccoltaFrame) parentFrame;
                        parent.aggiornaLista();
                    }
                    dispose();
                } else if (scelta.equals("Sposta in un'altra raccolta")) {
                    String titolo = JOptionPane.showInputDialog(this, "Inserisci il titolo della raccolta di destinazione:");
                    while (titolo.trim().isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Il titolo non può essere vuoto!");
                        titolo = JOptionPane.showInputDialog(this, "Inserisci il titolo della raccolta di destinazione:");
                    }
                    String esito = ControllerUtenti.spostaPoesia(titolo.trim(), id_poesia);
                    JOptionPane.showMessageDialog(this, esito);
                    PoesieRaccoltaFrame parent = (PoesieRaccoltaFrame) parentFrame;
                    parent.aggiornaLista();
                }
            });
        }

        JLabel titleLabel = new JLabel(poesia.getTitolo(), SwingConstants.RIGHT);
        titleLabel.setFont(titleFont);
        titleLabel.setForeground(accentColor);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        mainPanel.add(titleLabel);

        JLabel autoreLabel = new JLabel("di " + poesia.getAutore() + " • Stato: " + poesia.getStato(), SwingConstants.CENTER);
        autoreLabel.setFont(subtitleFont);
        autoreLabel.setForeground(textColor);
        autoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        autoreLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        mainPanel.add(Box.createVerticalStrut(4));
        mainPanel.add(autoreLabel);

        JPanel tagPanel = new JPanel();
        tagPanel.setBackground(bgColor);
        tagPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 8, 5));

        String[] tags = poesia.getTag().split("#");
        for (String rawTag : tags) {
            if (!rawTag.isBlank()) {
                JLabel tagLabel = new JLabel("#" + rawTag.trim());
                tagLabel.setOpaque(true);
                tagLabel.setBackground(accentColor);
                tagLabel.setForeground(bgColor);
                tagLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
                tagLabel.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
                tagPanel.add(tagLabel);
            }
        }
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(tagPanel);

        JTextArea testoArea = new JTextArea(poesia.getTesto());
        testoArea.setFont(bodyFont);
        testoArea.setForeground(textColor);
        testoArea.setBackground(bgColor);
        testoArea.setLineWrap(true);
        testoArea.setWrapStyleWord(true);
        testoArea.setEditable(false);
        testoArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JScrollPane testoScroll = new JScrollPane(testoArea);
        testoScroll.setPreferredSize(new Dimension(650, 250));
        testoScroll.setBorder(BorderFactory.createLineBorder(accentColor, 1));
        mainPanel.add(Box.createVerticalStrut(15));
        mainPanel.add(testoScroll);

        JPanel likePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        likePanel.setBackground(bgColor);

        final boolean[] liked = {poesia.isAlreadyLiked()};
        JLabel cuore = new JLabel(liked[0] ? "\u2665" : "\u2661");
        cuore.setFont(new Font("Dialog", Font.PLAIN, 24));
        cuore.setForeground(liked[0] ? accentColor : textColor);
        cuore.setCursor(new Cursor(Cursor.HAND_CURSOR));

        int[] conteggio = {poesia.getLike()};
        JLabel likeCount = new JLabel(String.valueOf(conteggio[0]));
        likeCount.setFont(new Font("Segoe UI", Font.BOLD, 16));
        likeCount.setForeground(textColor);

        cuore.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                boolean successo = ControllerPoesie.like(liked[0], poesia.getId());
                if (successo) {
                    liked[0] = !liked[0];
                    poesia.setAlreadyLiked(liked[0]);
                    if (liked[0]) {
                        cuore.setText("\u2665");
                        cuore.setForeground(textColor);
                        conteggio[0]++;
                    } else {
                        cuore.setText("\u2661");
                        cuore.setForeground(Color.GRAY);
                        if (conteggio[0] > 0) conteggio[0]--;
                    }
                    likeCount.setText(String.valueOf(conteggio[0]));
                } else {
                    liked[0] = false;
                    poesia.setAlreadyLiked(false);
                    cuore.setText("\u2661");
                    cuore.setForeground(Color.GRAY);
                    if (conteggio[0] > 0) conteggio[0]--;
                    likeCount.setText(String.valueOf(conteggio[0]));
                }

                if (parentFrame instanceof HomePage) {
                    ((HomePage) parentFrame).popolaFeed();
                }
            }
        });

        JButton commentaBtn = new JButton("Commenta");
        commentaBtn.setBackground(accentColor);
        commentaBtn.setForeground(bgColor);
        commentaBtn.setFocusPainted(false);
        commentaBtn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        commentaBtn.setAlignmentX(Component.RIGHT_ALIGNMENT);
        commentaBtn.addActionListener(e -> new CommentaFrame(this, poesia.getId(), poesia.getAutore()));

        likePanel.add(cuore);
        likePanel.add(likeCount);
        likePanel.add(commentaBtn);

        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(likePanel);

        commentiPanel = new JPanel();
        commentiPanel.setLayout(new BoxLayout(commentiPanel, BoxLayout.Y_AXIS));
        commentiPanel.setBackground(bgColor);
        commentiPanel.setBorder(BorderFactory.createTitledBorder("Ultimi commenti"));

        commentiScroll = new JScrollPane(commentiPanel);
        commentiScroll.setPreferredSize(new Dimension(550, 110));
        commentiScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        commentiScroll.setBorder(BorderFactory.createLineBorder(accentColor));

        mainPanel.add(Box.createVerticalStrut(15));
        mainPanel.add(commentiScroll);

        popolaCommenti();

        JButton closeBtn = new JButton("Chiudi");
        closeBtn.setBackground(accentColor);
        closeBtn.setForeground(bgColor);
        closeBtn.setFocusPainted(false);
        closeBtn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        closeBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        closeBtn.addActionListener(e -> dispose());

        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(closeBtn);

        setContentPane(mainPanel);
        setVisible(true);
    }

    private void popolaCommenti() {
        commentiPanel.removeAll();
        PoesiaCompletaDTO poesia = ControllerPoesie.visualizzaPoesia(id_poesia, autore);
        ArrayList<CommentoDTO> commenti = poesia.getUltimiCommenti();

        if (commenti.isEmpty()) {
            CommentoDTO commento = new CommentoDTO();
            commento.setAutore("");
            commento.setTesto("Nessun commento disponibile");
            commento.setData("");
            commentiPanel.add(creaCommentoBox(commento));
            commentiPanel.add(Box.createVerticalStrut(8));
        } else {
            for (CommentoDTO commento : commenti) {
                commentiPanel.add(creaCommentoBox(commento));
                commentiPanel.add(Box.createVerticalStrut(8));
            }
        }

        commentiPanel.revalidate();
        commentiPanel.repaint();
    }

    private JPanel creaCommentoBox(CommentoDTO commento) {
        Theme theme = ThemeManager.getTheme();
        JPanel commentoBox = new JPanel();
        commentoBox.setLayout(new BoxLayout(commentoBox, BoxLayout.Y_AXIS));
        commentoBox.setBackground(theme.getBackgroundPrimary());
        commentoBox.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(theme.getTextSecondary()),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        commentoBox.setMaximumSize(new Dimension(600, 60));

        JLabel autoreDataLabel = new JLabel(commento.getAutore() + " • " + commento.getData());
        autoreDataLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        autoreDataLabel.setForeground(theme.getTextPrimary());

        JTextArea testoCommento = new JTextArea(commento.getTesto());
        testoCommento.setFont(new Font("Segoe UI", Font.ITALIC, 13));
        testoCommento.setLineWrap(true);
        testoCommento.setWrapStyleWord(true);
        testoCommento.setEditable(false);
        testoCommento.setBackground(theme.getBackgroundPrimary());
        testoCommento.setForeground(theme.getTextPrimary());
        testoCommento.setBorder(null);

        commentoBox.add(autoreDataLabel);
        commentoBox.add(Box.createVerticalStrut(4));
        commentoBox.add(testoCommento);

        return commentoBox;
    }

    public void aggiornaCommenti() {
        popolaCommenti();
    }
}