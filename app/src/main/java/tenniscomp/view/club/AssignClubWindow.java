package tenniscomp.view.club;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import tenniscomp.data.Club;
import tenniscomp.data.Player;
import tenniscomp.model.Model;

public class AssignClubWindow extends JDialog {
    
    private final ClubSelector clubSelector;
    private final JButton saveButton;
    private final JButton cancelButton;
    
    public AssignClubWindow(final JFrame parent, final Player player, final Model model, final List<Club> clubs) {
        super(parent, "Assegna Circolo", true);
        setLayout(new BorderLayout(10, 10));
        setSize(400, 200);
        
        final JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 0, 10));
        infoPanel.add(new JLabel("Assegna un circolo al giocatore "
            + player.getSurname() + " " + player.getName()));
        if (player.getClubId() != null) {
            infoPanel.add(Box.createVerticalStrut(5));
            infoPanel.add(new JLabel("Circolo attuale: " + model.getClubById(player.getClubId()).getName()));
        }
        
        final JPanel clubPanel = new JPanel();
        clubPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 0));
        this.clubSelector = new ClubSelector(clubs);
        clubPanel.add(new JLabel("Seleziona circolo:"));
        clubPanel.add(clubSelector);
        
        final JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        this.saveButton = new JButton("Salva");
        this.cancelButton = new JButton("Annulla");
        
        buttonsPanel.add(saveButton);
        buttonsPanel.add(cancelButton);
        
        final JPanel contentPanel = new JPanel(new BorderLayout(10, 10));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
        contentPanel.add(clubPanel, BorderLayout.CENTER);
        
        add(infoPanel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.SOUTH);
        
        setLocationRelativeTo(parent);
    }
    
    public Club getSelectedClub() {
        return clubSelector.getSelectedClub();
    }
    
    public void setSaveButtonListener(final ActionListener listener) {
        this.saveButton.addActionListener(listener);
    }
    
    public void setCancelButtonListener(final ActionListener listener) {
        this.cancelButton.addActionListener(listener);
    }
}