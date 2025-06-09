package tenniscomp.view.user;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import tenniscomp.utils.CommonUtils;

public class LoginSelector extends JFrame {

    private static final double WIDTH_RATIO = 0.19;
    private static final double HEIGHT_RATIO = 0.18;
    private static final double BORDER_RATIO = 0.10;
    private final JButton playerButton;
    private final JButton refereeButton;

    public LoginSelector() {
        setTitle("TennisComp - Seleziona utente");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        final var screenSize = CommonUtils.getScreenSize();
        final int width = (int) (screenSize.width * WIDTH_RATIO);
        final int height = (int) (screenSize.height * HEIGHT_RATIO);
        setPreferredSize(new Dimension(width, height));

        final JLabel label = new JLabel("Effettua l'accesso:");
        label.setHorizontalAlignment(SwingConstants.CENTER);

        this.playerButton = new JButton("Giocatore");
        this.refereeButton = new JButton("Giudice Arbitro");

        final JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBorder(new EmptyBorder((int) (width * BORDER_RATIO), 0,
                (int) (height * BORDER_RATIO), 0));
        titlePanel.add(label, BorderLayout.CENTER);

        final JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.add(playerButton);
        buttonPanel.add(refereeButton);

        setLayout(new BorderLayout());
        add(titlePanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
    }

    public void setPlayerListener(final ActionListener listener) {
        playerButton.addActionListener(listener);
    }
    
    public void setRefereeListener(final ActionListener listener) {
        refereeButton.addActionListener(listener);
    }

    public void display() {
        pack();
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
