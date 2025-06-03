package tenniscomp.view.user;

import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginWindow extends JFrame {

    private final JTextField usernameField;
    private final JPasswordField passwordField;
    private final JButton loginButton;
    private final JButton registerButton;
    private final boolean isAdmin;

    public LoginWindow(final boolean isAdmin) {
        setTitle("Login");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        final JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(200, 200, 100, 30);
        final JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(200, 250, 100, 30);

        this.usernameField = new JTextField(20);
        usernameField.setBounds(300, 200, 300, 30);
        passwordField = new JPasswordField(20);
        passwordField.setBounds(300, 250, 300, 30);

        this.loginButton = new JButton("Login");
        this.loginButton.setBounds(300, 300, 120, 30);
        this.registerButton = new JButton("Registrati");
        registerButton.setBounds(480, 300, 120, 30);

        this.add(usernameLabel);
        this.add(usernameField);
        this.add(passwordLabel);
        this.add(passwordField);
        this.add(loginButton);
        this.add(registerButton);
        this.getRootPane().setDefaultButton(this.loginButton);

        this.isAdmin = isAdmin;
        if(isAdmin) {
            registerButton.setEnabled(false);
        }

        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public String getUsername() {
        return usernameField.getText().trim();
    }
    
    public String getPassword() {
        return new String(passwordField.getPassword());
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setLoginListener(final ActionListener listener) {
        this.loginButton.addActionListener(listener);
    }

    public void setRegisterListener(final ActionListener listener) {
        this.registerButton.addActionListener(listener);
    }

    public void showMessage(final String message, final int messageType) {
        JOptionPane.showMessageDialog(this, message, "Login", messageType);
    }

}
