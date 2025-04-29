package tenniscomp.utils;

import javax.swing.table.DefaultTableModel;

public class ImmutableTableModel extends DefaultTableModel {

    public ImmutableTableModel(final Object[] columnNames, final int rowCount) {
        super(columnNames, rowCount);
    }

    @Override
    public boolean isCellEditable(final int row, final int column) {
        return false;
    }
    
}
