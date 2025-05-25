package tenniscomp.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import tenniscomp.data.Club;
import tenniscomp.utils.Surface;

public class CourtRegistrationWindow extends JDialog {
    
    private final JSpinner courtNumberSpinner;
    private final JComboBox<String> surfaceComboBox;
    private final JRadioButton indoorYesRadio;
    private final JRadioButton indoorNoRadio;
    private final JButton saveButton;
    private final JButton cancelButton;
    
    public CourtRegistrationWindow(final JFrame parent, final Club club) {
        super(parent, "Registrazione Campo", true);
        
        setSize(400, 300);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout(10, 10));
        
        final var titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        titlePanel.add(new JLabel("Registra un nuovo campo per " + club.getName() + " - " + club.getCity()));
        
        final var formPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        formPanel.add(new JLabel("Numero campo:"));
        this.courtNumberSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 999, 1));
        formPanel.add(this.courtNumberSpinner);
        
        formPanel.add(new JLabel("Superficie:"));
        final var surfaces = Surface.getAllLabels();
        this.surfaceComboBox = new JComboBox<>(surfaces.toArray(new String[0]));
        formPanel.add(this.surfaceComboBox);
        
        formPanel.add(new JLabel("Indoor:"));
        final var radioPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        this.indoorYesRadio = new JRadioButton("Sì");
        this.indoorNoRadio = new JRadioButton("No", true);

        final var radioGroup = new ButtonGroup();
        radioGroup.add(this.indoorYesRadio);
        radioGroup.add(this.indoorNoRadio);
        radioPanel.add(this.indoorYesRadio);
        radioPanel.add(this.indoorNoRadio);
        formPanel.add(radioPanel);
        
        final var buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        this.saveButton = new JButton("Salva");
        this.cancelButton = new JButton("Annulla");
        
        buttonsPanel.add(this.saveButton);
        buttonsPanel.add(this.cancelButton);
        
        add(titlePanel, BorderLayout.NORTH);
        add(formPanel, BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.SOUTH);
    }
    
    public int getCourtNumber() {
        return (int) this.courtNumberSpinner.getValue();
    }
    
    public String getSurface() {
        return this.surfaceComboBox.getSelectedItem().toString();
    }
    
    public boolean isIndoor() {
        return this.indoorYesRadio.isSelected();
    }
    
    public void setSaveButtonListener(final ActionListener listener) {
        this.saveButton.addActionListener(listener);
    }
    
    public void setCancelButtonListener(final ActionListener listener) {
        this.cancelButton.addActionListener(listener);
    }
}