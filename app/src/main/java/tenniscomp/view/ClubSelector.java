package tenniscomp.view;

import java.util.List;

import javax.swing.JComboBox;

import tenniscomp.data.Club;

public class ClubSelector extends JComboBox<Club> {

    public ClubSelector(final List<Club> clubs) {
        super(clubs.toArray(new Club[0]));

        if (getItemCount() > 0) {
            setSelectedIndex(0);
        }
    }
    
    public Club getSelectedClub() {
        return (Club) getSelectedItem();
    }

}