package tenniscomp.view;

import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class RegisterTeamWindow extends BasePlayerTableWindow {
    
    private final JButton confirmButton;
    private final JButton cancelButton;

    public RegisterTeamWindow(final JFrame parent) {
        super(parent, "Registra Squadra");

        this.confirmButton = new JButton("Conferma");
        this.cancelButton = new JButton("Annulla");
        addButtonToPanel(confirmButton);
        addButtonToPanel(cancelButton);
    }

    @Override
    protected String getInfoLabelText() {
        return "Seleziona i giocatori per la squadra";
    }

    @Override
    protected void configureTableSelection() {
        // Disable default selection behaviour
        getPlayersTable().setRowSelectionAllowed(false);
        getPlayersTable().setFocusable(false);
    }

    public void setPlayersTableMouseListener(final MouseListener listener) {
        getPlayersTable().addMouseListener(listener);
    }

    public void setConfirmButtonListener(final ActionListener listener) {
        this.confirmButton.addActionListener(listener);
    }

    public void setCancelButtonListener(final ActionListener listener) {
        this.cancelButton.addActionListener(listener);
    }
}