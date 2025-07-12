package boundary;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import boundary.theme.Theme;
import boundary.theme.ThemeManager;
import controller.ControllerPoetUp;

public class RegistrationForm extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JPasswordField passwordField;
	private JPasswordField passwordField_1;
	private JLabel resultLabel;

	public RegistrationForm() {
		Theme theme = ThemeManager.getTheme();

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 550);
		setLocationRelativeTo(null);

		contentPane = new JPanel();
		contentPane.setBackground(theme.getBackgroundPrimary());
		contentPane.setLayout(null);
		setContentPane(contentPane);

		JPanel panel = new JPanel();
		panel.setBackground(theme.getBackgroundTertiary());
		panel.setBounds(0, 0, 327, 338);
		panel.setLayout(null);
		contentPane.add(panel);

		JLabel lblNewLabel_4 = new JLabel("");
		lblNewLabel_4.setBounds(75, 63, 192, 221);
		lblNewLabel_4.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_4.setIcon(new ImageIcon(RegistrationForm.class.getResource("/res/iconr.png")));
		panel.add(lblNewLabel_4);

		JLabel lblNewLabel = new JLabel("NickName");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel.setForeground(theme.getTextPrimary());
		lblNewLabel.setBounds(337, 161, 135, 14);
		contentPane.add(lblNewLabel);

		textField = new JTextField();
		textField.setBounds(337, 186, 312, 30);
		contentPane.add(textField);

		JLabel lblNewLabel_3 = new JLabel("Email Id");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_3.setForeground(theme.getTextPrimary());
		lblNewLabel_3.setBounds(337, 227, 83, 14);
		contentPane.add(lblNewLabel_3);

		textField_1 = new JTextField();
		textField_1.setBounds(337, 252, 312, 30);
		contentPane.add(textField_1);

		JLabel lblNewLabel_1 = new JLabel("Password");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_1.setForeground(theme.getTextPrimary());
		lblNewLabel_1.setBounds(337, 293, 108, 14);
		contentPane.add(lblNewLabel_1);

		passwordField_1 = new JPasswordField();
		passwordField_1.setBounds(337, 318, 312, 30);
		contentPane.add(passwordField_1);

		JLabel lblNewLabel_2 = new JLabel("Confirm Password");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_2.setForeground(theme.getTextPrimary());
		lblNewLabel_2.setBounds(337, 359, 146, 13);
		contentPane.add(lblNewLabel_2);

		passwordField = new JPasswordField();
		passwordField.setBounds(337, 384, 312, 30);
		contentPane.add(passwordField);

		resultLabel = new JLabel("");
		resultLabel.setBounds(337, 425, 312, 67);
		resultLabel.setForeground(theme.getHighlightColor());
		resultLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		contentPane.add(resultLabel);

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(theme.getButtonColor());
		panel_1.setBounds(0, 338, 327, 154);
		panel_1.setLayout(null);
		contentPane.add(panel_1);

		JButton btnRegistrazione = new JButton("Registrati");
		btnRegistrazione.setForeground(theme.getTextPrimary());
		btnRegistrazione.setBackground(theme.getButtonColor());
		btnRegistrazione.setFont(new Font("Segoe UI", Font.BOLD, 20));
		btnRegistrazione.setBounds(0, 0, 327, 154);
		btnRegistrazione.setBorderPainted(false);
		panel_1.add(btnRegistrazione);

		btnRegistrazione.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String nickname = textField.getText();
				String email = textField_1.getText();
				String pwd = new String(passwordField_1.getPassword());
				String confirmPwd = new String(passwordField.getPassword());

				if (nickname.isEmpty() || email.isEmpty() || pwd.isEmpty() || confirmPwd.isEmpty()) {
					resultLabel.setText("Tutti i campi sono obbligatori.");
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
				} else if (!pwd.equals(confirmPwd)) {
					resultLabel.setText("Le password non corrispondono.");
					resultLabel.setForeground(theme.getHighlightColor());
					return;
				}

				String esito = ControllerPoetUp.registrazione(nickname, email, pwd);
				if (esito.equals("Registrazione completata!")) {
					JOptionPane.showMessageDialog(null, "Registrazione avvenuta con successo!", "Successo", JOptionPane.INFORMATION_MESSAGE);
					dispose();
					LoginForm login = new LoginForm();
					login.setVisible(true);
				} else {
					resultLabel.setText(esito);
					resultLabel.setForeground(theme.getHighlightColor());
				}
			}
		});

		JLabel lblNewLabel_7 = new JLabel("POET UP!");
		lblNewLabel_7.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_7.setFont(new Font("Segoe UI", Font.BOLD, 26));
		lblNewLabel_7.setForeground(theme.getTextPrimary());
		lblNewLabel_7.setBounds(412, 26, 171, 126);
		contentPane.add(lblNewLabel_7);
	}
}