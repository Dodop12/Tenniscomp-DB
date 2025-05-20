package tenniscomp.utils;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 * Utility class for JTable operations.
 */
public final class TableUtils {
    
    private static final int MAX_ROWS_TO_CHECK = 50; // Limit the number of rows to check for performance
    private static final int COLUMN_PADDING = 20;
    private static final int MIN_COLUMN_WIDTH = 40;
    private static final int MAX_COLUMN_WIDTH = 300; // Prevent extremely wide columns

    private TableUtils() {
        
    }

    public static void clearTable(final DefaultTableModel model) {
        model.setRowCount(0);
    }
    
    /**
     * Adjust the column widths of a JTable based on the content of its cells.
     * 
     * @param table The JTable whose columns need to be resized
     */
    public static void adjustColumnWidths(final JTable table) {
        final var columnModel = table.getColumnModel();
        
        for (int column = 0; column < table.getColumnCount(); column++) {
            final int width = getPreferredColumnWidth(table, column);
            
            // Set the column width
            final var tableColumn = columnModel.getColumn(column);
            tableColumn.setPreferredWidth(width);
        }
    }

    public static void highlightRow(final JTable table, final int targetRow) {
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {

            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                var component = super.getTableCellRendererComponent(
                        table, value, isSelected, hasFocus, row, column);
                
                if (row == targetRow && !isSelected) {
                    component.setBackground(new Color(230, 255, 230)); // Light green
                } else {
                    component.setBackground(table.getBackground());
                }
                return component;
            }
        });
    }
    
    /**
     * Calculate the preferred width for a column based on its content.
     * 
     * @param table The JTable containing the column
     * @param column The index of the column
     * @return The preferred width for the column
     */
    private static int getPreferredColumnWidth(final JTable table, final int column) {
        int maxWidth = MIN_COLUMN_WIDTH;
        
        // Check header width
        final var columnModel = table.getColumnModel();
        final var tableColumn = columnModel.getColumn(column);
        final var headerRenderer = table.getTableHeader().getDefaultRenderer();
        final var headerValue = tableColumn.getHeaderValue();

        final var headerComp = headerRenderer.getTableCellRendererComponent(
                table, headerValue, false, false, 0, column);
        maxWidth = Math.max(maxWidth, headerComp.getPreferredSize().width);
        
        // Check data rows
        final int rowsToCheck = Math.min(MAX_ROWS_TO_CHECK, table.getRowCount());
        for (int row = 0; row < rowsToCheck; row++) {
            final var renderer = table.getCellRenderer(row, column);
            final var comp = table.prepareRenderer(renderer, row, column);
            
            final int width = comp.getPreferredSize().width;
            maxWidth = Math.max(maxWidth, width);
        }
        
        // Add padding and ensure it's within the min/max constraints
        return Math.min(MAX_COLUMN_WIDTH, maxWidth + COLUMN_PADDING);
    }
}
