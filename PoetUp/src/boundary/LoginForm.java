package boundary;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import controller.ControllerPoetUp;
import boundary.theme.Theme;
import boundary.theme.ThemeManager;

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
		Theme theme = ThemeManager.getTheme();

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 500);

		contentPane = new JPanel();
		contentPane.setBackground(theme.getBackgroundPrimary());
		contentPane.setLayout(null);
		setContentPane(contentPane);
		setLocationRelativeTo(null);

		// Titolo
		JLabel titleLabel = new JLabel("Accedi a POET UP!");
		titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
		titleLabel.setForeground(theme.getTextPrimary());
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setBounds(200, 50, 300, 50);
		contentPane.add(titleLabel);

		// Email
		JLabel emailLabel = new JLabel("Email:");
		emailLabel.setBounds(200, 130, 100, 25);
		emailLabel.setForeground(theme.getTextSecondary());
		contentPane.add(emailLabel);

		emailField = new JTextField();
		emailField.setBounds(200, 160, 300, 30);
		contentPane.add(emailField);

		// Password
		JLabel passwordLabel = new JLabel("Password:");
		passwordLabel.setBounds(200, 210, 100, 25);
		passwordLabel.setForeground(theme.getTextSecondary());
		contentPane.add(passwordLabel);

		passwordField = new JPasswordField();
		passwordField.setBounds(200, 240, 300, 30);
		contentPane.add(passwordField);

		// Etichetta risultato
		resultLabel = new JLabel("");
		resultLabel.setBounds(200, 280, 300, 51);
		resultLabel.setForeground(theme.getHighlightColor());
		contentPane.add(resultLabel);

		// Bottone Login
		JButton loginButton = new JButton("Login");
		loginButton.setBackground(theme.getButtonColor());
		loginButton.setForeground(theme.getTextPrimary());
		loginButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
		loginButton.setBounds(200, 341, 300, 40);
		loginButton.setBorderPainted(false);
		contentPane.add(loginButton);

		loginButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String email = emailField.getText();
				String pwd = new String(passwordField.getPassword());

				if (email.isEmpty() || pwd.isEmpty()) {
					resultLabel.setText("Inserisci email e password.");
					resultLabel.setForeground(theme.getHighlightColor());
					return;
				} else if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")) {
					resultLabel.setText("Formato email non valido.");
					resultLabel.setForeground(theme.getHighlightColor());
					return;
				} else if (!pwd.matches("^(?=.*[0-9])(?=.*[!@#$%^&*()_+\\-={}|\\[\\]:;\"'<>,.?/]).{8,32}$")) {
					resultLabel.setText("<html>La password deve contenere almeno un <br> numero, un carattere speciale e avere tra 8 e 32 caratteri.<html>");
					resultLabel.setForeground(theme.getHighlightColor());
					return;
				}

				String autenticato = ControllerPoetUp.autenticazione(email, pwd);
				if (autenticato.equals("Utente autenticato")) {
					resultLabel.setForeground(theme.getHighlightColor());
					resultLabel.setText(autenticato);

					HomePage home = new HomePage();
					home.setVisible(true);
					dispose();
				} else {
					resultLabel.setForeground(theme.getHighlightColor());
					resultLabel.setText(autenticato);
				}
			}
		});

		// Bottone Registrazione
		JButton registerButton = new JButton("Non sei registrato?");
		registerButton.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		registerButton.setBounds(200, 391, 300, 30);
		registerButton.setBackground(theme.getAccentColor());
		registerButton.setForeground(theme.getTextPrimary());
		registerButton.setBorderPainted(false);
		contentPane.add(registerButton);

		registerButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
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
