package tenniscomp.view;

import java.awt.FlowLayout;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import tenniscomp.data.Club;

public class ClubSelector extends JPanel {

    private final JComboBox<Club> clubComboBox;

    public ClubSelector(final List<Club> clubs) {
        setLayout(new FlowLayout(FlowLayout.LEFT, 15, 0));

        final JLabel label = new JLabel("Seleziona circolo:");
        this.clubComboBox = new JComboBox<>(clubs.toArray(new Club[0]));

        add(label);
        add(clubComboBox);
    }
    
    public Club getSelectedClub() {
        return (Club) clubComboBox.getSelectedItem();
    }

}