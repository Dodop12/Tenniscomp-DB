package tenniscomp.view.user;

import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import tenniscomp.utils.CommonUtils;

public class LoginWindow extends JFrame {

    private static final double WIDTH_RATIO = 0.4;
    private static final double HEIGHT_RATIO = 0.55;
    private static final double WINDOW_GAP_RATIO = 0.25;
    private static final double COMP_WIDTH_RATIO = 0.17;
    private static final double COMP_HEIGHT_RATIO = 0.05;
    private final JTextField usernameField;
    private final JPasswordField passwordField;
    private final JButton loginButton;
    private final JButton registerButton;
    private final boolean isAdmin;

    public LoginWindow(final boolean isAdmin) {
        setTitle("Login");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        final var screenSize = CommonUtils.getScreenSize();
        final int width = (int) (screenSize.width * WIDTH_RATIO);
        final int height = (int) (screenSize.height * HEIGHT_RATIO);
        setPreferredSize(new Dimension(width, height));

        final int windowGap = (int) (width * WINDOW_GAP_RATIO);
        final int compWidth = (int) (width * COMP_WIDTH_RATIO);
        final int compHeight = (int) (height * COMP_HEIGHT_RATIO);
        final int compGap = (int) (windowGap / 2);
        final JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(windowGap, windowGap, compWidth, compHeight);
        final JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(windowGap, windowGap + (int) (windowGap / 4), compWidth, compHeight);

        this.usernameField = new JTextField(20);
        usernameField.setBounds(windowGap + compGap, windowGap,
                windowGap + compGap, compHeight);
        passwordField = new JPasswordField(20);
        passwordField.setBounds(windowGap + compGap, windowGap + (int) (windowGap / 4),
                windowGap + compGap, compHeight);

        this.loginButton = new JButton("Login");
        this.loginButton.setBounds(windowGap + compGap, windowGap + compGap,
                compWidth, compHeight);
        this.registerButton = new JButton("Registrati");
        registerButton.setBounds(2 * (windowGap + compGap) - compWidth, windowGap + compGap,
                compWidth, compHeight);

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

        pack();
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
