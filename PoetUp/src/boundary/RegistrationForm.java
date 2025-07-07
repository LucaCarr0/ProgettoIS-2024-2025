package boundary;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import controller.ControllerPoetUp;
import javax.swing.*;

public class RegistrationForm extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JPasswordField passwordField;
	private JPasswordField passwordField_1;

	private JLabel resultLabel; // Label per mostrare il risultato


	public RegistrationForm() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 550);

		contentPane = new JPanel();
		contentPane.setBackground(new Color(245, 248, 250));
		contentPane.setLayout(null);
		setContentPane(contentPane);

		// Pannello laterale con immagine
		JPanel panel = new JPanel();
		panel.setBackground(new Color(60, 164, 238));
		panel.setBounds(0, 0, 327, 338);
		panel.setLayout(null);
		contentPane.add(panel);

		JLabel lblNewLabel_4 = new JLabel("");
		lblNewLabel_4.setBounds(75, 63, 192, 221);
		lblNewLabel_4.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_4.setIcon(new ImageIcon(RegistrationForm.class.getResource("/res/iconr.png")));
		panel.add(lblNewLabel_4);

		Color textColor = new Color(44, 62, 80);

		JLabel lblNewLabel = new JLabel("NickName");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel.setForeground(textColor);
		lblNewLabel.setBounds(337, 161, 135, 14);
		contentPane.add(lblNewLabel);

		textField = new JTextField();
		textField.setBounds(337, 186, 312, 30);
		contentPane.add(textField);

		JLabel lblNewLabel_3 = new JLabel("Email Id");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_3.setForeground(textColor);
		lblNewLabel_3.setBounds(337, 227, 83, 14);
		contentPane.add(lblNewLabel_3);

		textField_1 = new JTextField();
		textField_1.setBounds(337, 252, 312, 30);
		contentPane.add(textField_1);

		JLabel lblNewLabel_1 = new JLabel("Password");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_1.setForeground(textColor);
		lblNewLabel_1.setBounds(337, 293, 108, 14);
		contentPane.add(lblNewLabel_1);

		passwordField_1 = new JPasswordField();
		passwordField_1.setBounds(337, 318, 312, 30);
		contentPane.add(passwordField_1);

		JLabel lblNewLabel_2 = new JLabel("Confirm Password");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_2.setForeground(textColor);
		lblNewLabel_2.setBounds(337, 359, 146, 13);
		contentPane.add(lblNewLabel_2);

		passwordField = new JPasswordField();
		passwordField.setBounds(337, 384, 312, 30);
		contentPane.add(passwordField);

		// Etichetta risultato (visibile sotto ai campi)
		resultLabel = new JLabel("");
		resultLabel.setBounds(337, 425, 312, 67);
		resultLabel.setForeground(new Color(200, 50, 50));
		resultLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		contentPane.add(resultLabel);

		// Bottone "Registrati"
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(255, 122, 89));
		panel_1.setBounds(0, 338, 327, 154);
		panel_1.setLayout(null);
		contentPane.add(panel_1);

		JButton btnRegistrazione = new JButton("Registrati");
		btnRegistrazione.setForeground(Color.WHITE);
		btnRegistrazione.setBackground(new Color(255, 122, 89));
		btnRegistrazione.setFont(new Font("Segoe UI", Font.BOLD, 20));
		btnRegistrazione.setBounds(0, 0, 327, 154);
		btnRegistrazione.setBorderPainted(false);
		panel_1.add(btnRegistrazione);

		btnRegistrazione.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nickname = textField.getText();
				String email = textField_1.getText();
				String pwd = new String(passwordField_1.getPassword());
				String confirmPwd = new String(passwordField.getPassword());
				
				//VALIDAZIONE INPUT
				
				if (nickname.isEmpty() || email.isEmpty() || pwd.isEmpty() || confirmPwd.isEmpty()) {
					resultLabel.setText("Tutti i campi sono obbligatori.");
					resultLabel.setForeground(Color.RED);
					return;
				}
				
				else if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")) {
					resultLabel.setText("Formato email non valido.");
					resultLabel.setForeground(Color.RED);
					return;
				}
				
				//la password deve avere almeno una cifra [0-9], almeno un carattere speciale tra quelli indicati, e una lunghezza tra gli 8 e i 32 caratteri 
				
				else if (!pwd.matches("^(?=.*[0-9])(?=.*[!@#$%^&*()_+\\-={}|\\[\\]:;\"'<>,.?/]).{8,32}$")) {
					resultLabel.setText("<html>La password deve contenere almeno un <br> numero, un carattere speciale e avere tra 8 e 32 caratteri.<html>");
					resultLabel.setForeground(Color.RED);
					return;
				}

				else if (!pwd.equals(confirmPwd)) {
					resultLabel.setText("Le password non corrispondono.");
					resultLabel.setForeground(Color.RED);
					return;
				}
				
				//UTILIZZO IL CONTROLLER
				
				else {
					String esito = ControllerPoetUp.registrazione(nickname,email,pwd);
					if (esito.equals("Registrazione completata!")) {
						JOptionPane.showMessageDialog(null, "Registrazione avvenuta con successo!", "Successo", JOptionPane.INFORMATION_MESSAGE);
				 
						// Chiudi il form corrente
						dispose();
				 
						// Torna alla schermata di login
						LoginForm login = new LoginForm();
						login.setVisible(true);
					} else {
						resultLabel.setText(esito);
						resultLabel.setForeground(Color.RED);
					}
				}
				
				

				
			}
		});

		// Titolo
		JLabel lblNewLabel_7 = new JLabel("POET UP!");
		lblNewLabel_7.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_7.setFont(new Font("Segoe UI", Font.BOLD, 26));
		lblNewLabel_7.setForeground(textColor);
		lblNewLabel_7.setBounds(412, 26, 171, 126);
		contentPane.add(lblNewLabel_7);
	}
}
