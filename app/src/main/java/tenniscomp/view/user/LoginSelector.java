package tenniscomp.view.user;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class LoginSelector extends JFrame {

    private final JButton playerButton;
    private final JButton refereeButton;

    public LoginSelector() {
        setTitle("TennisComp - Seleziona utente");
        setSize(350, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        final JLabel label = new JLabel("Effettua l'accesso");
        label.setHorizontalAlignment(SwingConstants.CENTER);

        this.playerButton = new JButton("Giocatore");
        this.refereeButton = new JButton("Giudice Arbitro");

        final JPanel buttonPanel = new JPanel();
        buttonPanel.add(playerButton);
        buttonPanel.add(refereeButton);

        setLayout(new BorderLayout());
        add(label, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);

        add(buttonPanel);
    }

    public void setPlayerListener(final ActionListener listener) {
        playerButton.addActionListener(listener);
    }
    
    public void setRefereeListener(final ActionListener listener) {
        refereeButton.addActionListener(listener);
    }

    public void display() {
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
