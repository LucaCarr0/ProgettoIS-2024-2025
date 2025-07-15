package boundary;

import java.awt.BorderLayout;
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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import boundary.theme.Theme;
import boundary.theme.ThemeManager;
import controller.ControllerUtenti;
import dto.PoesiaDTO;

public class PoesieRaccoltaFrame extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JFrame parent;
	private int raccoltaid;
	private String titoloraccolta;
	
    public PoesieRaccoltaFrame(JFrame parentFrame, int raccoltaId, String titoloRaccolta) {
    	Image icon = new ImageIcon(getClass().getResource("/res/logo.png")).getImage();
		setIconImage(icon);
    	parent=parentFrame;
    	raccoltaid=raccoltaId;
    	titoloraccolta=titoloRaccolta;
        setTitle(titoloRaccolta);
        setSize(900, 600);
        setLocationRelativeTo(parentFrame);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        ThemeManager tema_app=ThemeManager.getInstance();
		Theme theme = tema_app.getTheme();

        Color primaryColor = theme.getPalette().get(1);
        Color accentColor = theme.getPalette().get(4);
        Color bgColor = theme.getPalette().get(2);
        Color cardColor = theme.getPalette().get(3);
        //Color textColor = theme.getPalette().get(0);
        Font titleFont = new Font("Segoe UI", Font.BOLD, 24);
        Font cardFont = new Font("Segoe UI", Font.PLAIN, 14);

        JPanel contentPane = new JPanel();
        contentPane.setBackground(bgColor);
        contentPane.setLayout(null);
        setContentPane(contentPane);

        JLabel titleLabel = new JLabel(titoloRaccolta);
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

        
        System.out.println("id raccolta: "+raccoltaId);

        ArrayList<PoesiaDTO> poesie = ControllerUtenti.getPoesieByRaccolta(raccoltaId);
        System.out.println(poesie);
        for (PoesiaDTO element : poesie) {
        	System.out.println(element.getTitolo());
        }

        for (PoesiaDTO poesia : poesie) {
        	System.out.println("id poesia da interfaccia: "+poesia.getId());
            JPanel card = new JPanel();
            card.setLayout(new BorderLayout());
            card.setMaximumSize(new Dimension(480, 80)); 
            card.setBackground(cardColor);
            card.setBorder(BorderFactory.createLineBorder(primaryColor, 1));

           
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

            
            card.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                	new PoesiaFrame(PoesieRaccoltaFrame.this,poesia.getId(),poesia.getAutore());
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
    public void aggiornaLista() {
        this.dispose();
        new PoesieRaccoltaFrame(this.parent,this.raccoltaid,this.titoloraccolta); 
    }




}
