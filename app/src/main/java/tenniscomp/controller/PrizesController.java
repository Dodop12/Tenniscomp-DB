package tenniscomp.controller;

import java.util.List;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import tenniscomp.utils.PrizePosition;
import tenniscomp.view.PrizesWindow;

public class PrizesController {
    
    private final PrizesWindow view;
    private final double totalPrizePool;

    private final PrizePosition[] positions = PrizePosition.values();
    
    public PrizesController(final PrizesWindow view, final double totalPrizePool) {
        this.view = view;
        this.totalPrizePool = totalPrizePool;
        setupListeners();
        updateDisplay();
    }
    
    private void setupListeners() {
        view.setPercentModeListener(e -> {
            view.setPercentMode(true);
            updateDisplay();
        });
        
        view.setCurrencyModeListener(e -> {
            view.setPercentMode(false);
            updateDisplay();
        });
        
        final var docListener = new DocumentListener() {
            public void insertUpdate(final DocumentEvent e) {
                updateDisplay();
            }
            public void removeUpdate(final DocumentEvent e) {
                updateDisplay();
            }
            public void changedUpdate(final DocumentEvent e) {
                updateDisplay();
            }
        };
        
        view.setPrizeFieldsDocumentListener(docListener);
    }
    
    public void updateDisplay() {
        final List<Double> values = view.parseFieldValues();
        final boolean isValid = isValidDistribution(values);
        
        if (values != null) {
            updateLabels(values);
            updateSummary(values);
        } else {
            view.resetLabels();
        }
        
        view.setSaveButtonEnabled(isValid);
    }
    
    private boolean isValidDistribution(final List<Double> values) {
        if (values == null || values.size() != positions.length) {
            return false;
        }
        
        // Winner is mandatory and must be positive
        if (values.get(0) <= 0) {
            return false;
        }
        
        // All values must be non-negative
        for (final double value : values) {
            if (value < 0) {
                return false;
            }
        }
        
        // Descending order (only for non-zero values)
        for (int i = 0; i < positions.length - 1; i++) {
            for (int j = i + 1; j < positions.length; j++) {
                if (values.get(j) > 0 && values.get(i) > 0 && values.get(j) >= values.get(i)) {
                    return false;
                }
            }
        }
        
        // Check total is equal to 100% of the prize pool
        final double total = calculateTotal(values);
        return view.isPercentMode() ? total == 100 : total == totalPrizePool;
    }
    
    private double calculateTotal(final List<Double> values) {
        double total = 0;
        for (int i = 0; i < positions.length; i++) {
            total += values.get(i) * positions[i].getMultiplier();
        }
        return total;
    }
    
    private void updateLabels(final List<Double> values) {
        for (int i = 0; i < positions.length; i++) {
            double amount = values.get(i);
            if (view.isPercentMode() && amount > 0) {
                amount = (amount / 100.0) * totalPrizePool;
            }
            view.updatePrizeLabel(i, amount);
        }
    }
    
    private void updateSummary(final List<Double> values) {
        final double totalUsed = calculateTotal(values);
        
        if (view.isPercentMode()) {
            final double totalAmount = (totalUsed / 100.0) * totalPrizePool;
            final double remaining = totalPrizePool - totalAmount;
            final double remainingPercent = 100 - totalUsed;
            
            view.updateSummary(totalAmount, totalUsed, remaining, remainingPercent);
        } else {
            final double remaining = totalPrizePool - totalUsed;
            final double usedPercent = (totalUsed / totalPrizePool) * 100;
            final double remainingPercent = (remaining / totalPrizePool) * 100;
            
            view.updateSummary(totalUsed, usedPercent, remaining, remainingPercent);
        }
    }
    
    public List<Double> getPrizeDistribution() {
        return view.calculatePrizeDistribution(totalPrizePool);
    }
}