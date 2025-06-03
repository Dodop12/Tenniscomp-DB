package tenniscomp.view.tournament;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentListener;

import tenniscomp.utils.PrizePosition;

public class PrizesWindow extends JDialog {
    
    private final double totalPrizePool;
    private final DecimalFormat currencyFormat = new DecimalFormat("#,##0.00");
    private final DecimalFormat percentFormat = new DecimalFormat("0.##");
    
    private final JRadioButton percentRadio;
    private final JRadioButton currencyRadio;
    
    private final PrizePosition[] positions = PrizePosition.values();
    private final JTextField[] prizeFields = new JTextField[positions.length];
    private final JLabel[] prizeLabels = new JLabel[positions.length];
    
    private final JLabel totalUsedLabel;
    private final JLabel remainingLabel;
    private final JButton saveButton;
    private final JButton cancelButton;
    
    private boolean isPercentMode = true;
    
    public PrizesWindow(final JDialog parent, final double totalPrizePool) {
        super(parent, "Configura Premi", true);
        this.totalPrizePool = totalPrizePool;
        
        setSize(500, 400);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout(10, 10));

        final var headerPanel = new JPanel(new GridLayout(3, 1, 5, 5));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        
        final var totalLabel = new JLabel("Montepremi totale: € " + this.currencyFormat.format(totalPrizePool));
        totalLabel.setHorizontalAlignment(SwingConstants.CENTER);
        headerPanel.add(totalLabel);
        
        // Amount / Percentage mode selection
        final var modePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        modePanel.add(new JLabel("Modalità inserimento: "));
        
        percentRadio = new JRadioButton("Percentuale (%)", true);
        currencyRadio = new JRadioButton("Importo (€)");
        
        final var buttonGroup = new ButtonGroup();
        buttonGroup.add(percentRadio);
        buttonGroup.add(currencyRadio);
        
        modePanel.add(percentRadio);
        modePanel.add(currencyRadio);
        headerPanel.add(modePanel);

        final var formPanel = new JPanel(new GridLayout(positions.length, 3, 10, 10));
        formPanel.setBorder(BorderFactory.createTitledBorder("Distribuzione Premi"));
        
        for (int i = 0; i < positions.length; i++) {
            formPanel.add(new JLabel(positions[i].getLabel() + ":"));
            prizeFields[i] = new JTextField();
            formPanel.add(prizeFields[i]);
            prizeLabels[i] = new JLabel("€ 0.00");
            formPanel.add(prizeLabels[i]);
        }
        
        final var summaryPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        summaryPanel.setBorder(BorderFactory.createTitledBorder("Riepilogo"));
        
        totalUsedLabel = new JLabel("Totale utilizzato: € 0.00 (0%)");
        remainingLabel = new JLabel("Rimanente: € " + this.currencyFormat.format(totalPrizePool) + " (100%)");
        
        summaryPanel.add(totalUsedLabel);
        summaryPanel.add(remainingLabel);
        
        final var buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        this.saveButton = new JButton("Salva");
        this.cancelButton = new JButton("Annulla");
        
        this.saveButton.setEnabled(false);
        
        buttonPanel.add(this.saveButton);
        buttonPanel.add(this.cancelButton);
        
        add(headerPanel, BorderLayout.NORTH);
        add(formPanel, BorderLayout.CENTER);
        
        final var southPanel = new JPanel(new BorderLayout());
        southPanel.add(summaryPanel, BorderLayout.CENTER);
        southPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(southPanel, BorderLayout.SOUTH);
    }

    public DecimalFormat getCurrencyFormat() {
        return currencyFormat;
    }
    
    public boolean isPercentMode() {
        return isPercentMode;
    }
    
    public void setPercentMode(final boolean percentMode) {
        this.isPercentMode = percentMode;
        percentRadio.setSelected(percentMode);
        currencyRadio.setSelected(!percentMode);
    }
    
    public List<Double> getFieldValues() {
        final List<Double> values = new ArrayList<>();
        for (final JTextField field : prizeFields) {
            try {
                final String text = field.getText().trim();
                values.add(text.isEmpty() ? 0.0 : Double.parseDouble(text));
            } catch (final NumberFormatException e) {
                return null;
            }
        }
        return values;
    }
    
    public void updatePrizeLabel(final int index, final double amount) {
        if (index >= 0 && index < prizeLabels.length) {
            prizeLabels[index].setText("€ " + this.currencyFormat.format(amount));
        }
    }
    
    public void updateSummary(final double totalUsed, final double usedPercent, 
            final double remaining, final double remainingPercent) {
        totalUsedLabel.setText("Totale utilizzato: € " + this.currencyFormat.format(totalUsed) + 
                " (" + percentFormat.format(usedPercent) + "%)");
        remainingLabel.setText("Rimanente: € " + this.currencyFormat.format(remaining) + 
                " (" + percentFormat.format(remainingPercent) + "%)");
    }
    
    public void resetLabels() {
        for (final JLabel label : prizeLabels) {
            label.setText("€ 0.00");
        }
        totalUsedLabel.setText("Totale utilizzato: € 0.00 (0%)");
        remainingLabel.setText("Rimanente: € " + this.currencyFormat.format(this.totalPrizePool) + " (100%)");
    }

    public void setSaveButtonEnabled(final boolean enabled) {
        saveButton.setEnabled(enabled);
    }
    
    public void setPercentModeListener(final ActionListener listener) {
        percentRadio.addActionListener(listener);
    }
    
    public void setCurrencyModeListener(final ActionListener listener) {
        currencyRadio.addActionListener(listener);
    }
    
    public void setPrizeFieldsDocumentListener(final DocumentListener listener) {
        for (final JTextField field : prizeFields) {
            field.getDocument().addDocumentListener(listener);
        }
    }   
    
    public void setSaveButtonListener(final ActionListener listener) {
        saveButton.addActionListener(listener);
    }

    public void setCancelButtonListener(final ActionListener listener) {
        cancelButton.addActionListener(listener);
    }
}