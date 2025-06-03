package tenniscomp.view.league;

import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import tenniscomp.view.player.BasePlayerTableWindow;

public class TeamDetailsWindow extends BasePlayerTableWindow {
    
    private final JButton closeButton;
    private String teamName;

    public TeamDetailsWindow(final JFrame parent) {
        super(parent, "Dettagli Squadra");
        
        this.closeButton = new JButton("Chiudi");
        addButtonToPanel(this.closeButton);
    }
    
    @Override
    protected String getInfoLabelText() {
        return "Giocatori della squadra " + (teamName != null ? teamName : "");
    }
    
    public void setTeamName(final String teamName) {
        this.teamName = teamName;
        setTitle("Dettagli Squadra - " + teamName);
        
        // Refresh title and info label
        refreshInfoLabel();
        repaint();
    }

    public void setPlayersTableMouseListener(final MouseListener listener) {
        getPlayersTable().addMouseListener(listener);
    }

    public void setCloseButtonListener(final ActionListener listener) {
        this.closeButton.addActionListener(listener);
    }
}