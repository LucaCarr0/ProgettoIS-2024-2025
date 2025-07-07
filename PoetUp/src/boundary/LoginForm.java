package boundary;
 
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import controller.ControllerPoetUp;
import session.SessioneUtente;
 
public class LoginForm extends JFrame {
 
	private JPanel contentPane;
	private JTextField emailField;
	private JPasswordField passwordField;
	private JLabel resultLabel;
 
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				LoginForm frame = new LoginForm();
				frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}
 
	public LoginForm() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 500);
 
		contentPane = new JPanel();
		contentPane.setBackground(new Color(245, 248, 250));
		contentPane.setLayout(null);
		setContentPane(contentPane);
 
		// Titolo
		JLabel titleLabel = new JLabel("Accedi a POET UP!");
		titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
		titleLabel.setForeground(new Color(44, 62, 80));
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setBounds(200, 50, 300, 50);
		contentPane.add(titleLabel);
 
		// Email
		JLabel emailLabel = new JLabel("Email:");
		emailLabel.setBounds(200, 130, 100, 25);
		emailLabel.setForeground(new Color(44, 62, 80));
		contentPane.add(emailLabel);
 
		emailField = new JTextField();
		emailField.setBounds(200, 160, 300, 30);
		contentPane.add(emailField);
 
		// Password
		JLabel passwordLabel = new JLabel("Password:");
		passwordLabel.setBounds(200, 210, 100, 25);
		passwordLabel.setForeground(new Color(44, 62, 80));
		contentPane.add(passwordLabel);
 
		passwordField = new JPasswordField();
		passwordField.setBounds(200, 240, 300, 30);
		contentPane.add(passwordField);
 
		// Etichetta risultato
		resultLabel = new JLabel("");
		resultLabel.setBounds(200, 280, 300, 51);
		resultLabel.setForeground(Color.RED);
		contentPane.add(resultLabel);
 
		// Bottone Login
		JButton loginButton = new JButton("Login");
		loginButton.setBackground(new Color(60, 164, 238));
		loginButton.setForeground(Color.WHITE);
		loginButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
		loginButton.setBounds(200, 341, 300, 40);
		loginButton.setBorderPainted(false);
		contentPane.add(loginButton);
 
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String email = emailField.getText();
				String pwd = new String(passwordField.getPassword());
 
				//Validazione Input
				if (email.isEmpty() || pwd.isEmpty()) {
					resultLabel.setText("Inserisci email e password.");
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
				// Qui va chiamato il Controller per il login
				String autenticato = ControllerPoetUp.autenticazione(email, pwd); 
				if (autenticato.equals("Utente autenticato")) {
					resultLabel.setForeground(new Color(34, 139, 34));
					resultLabel.setText(autenticato);
					
					 
				    // 2. Apri la Home
				    HomePage home = new HomePage();
				    home.setVisible(true);
				 
				    // 3. Chiudi la schermata di login
				    dispose();
					// apri schermata principale...
				} else {
					resultLabel.setForeground(Color.RED);
					resultLabel.setText(autenticato);
				}
			}
		});
 
		// Bottone Registrazione
		JButton registerButton = new JButton("Non sei registrato?");
		registerButton.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		registerButton.setBounds(200, 391, 300, 30);
		registerButton.setBackground(new Color(255, 122, 89));
		registerButton.setForeground(Color.WHITE);
		registerButton.setBorderPainted(false);
		contentPane.add(registerButton);
 
		registerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose(); // chiude la finestra attuale
				EventQueue.invokeLater(() -> {
					try {
						RegistrationForm registrationForm = new RegistrationForm();
						registrationForm.setVisible(true);
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				});
			}
		});
		
	}
}