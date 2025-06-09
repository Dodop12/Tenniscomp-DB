package tenniscomp.view.club;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AddClubWindow extends JDialog {
    
    private final JTextField nameField;
    private final JTextField addressField;
    private final JTextField cityField;
    private final JButton saveButton;
    private final JButton cancelButton;
    
    public AddClubWindow(final JFrame parent) {
        super(parent, "Aggiungi Nuovo Circolo", true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        
        final var fieldsPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        fieldsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        final var nameLabel = new JLabel("Nome:");
        this.nameField = new JTextField(20);
        
        final var addressLabel = new JLabel("Indirizzo:");
        this.addressField = new JTextField(20);
        
        final var cityLabel = new JLabel("Città:");
        this.cityField = new JTextField(20);
        
        fieldsPanel.add(nameLabel);
        fieldsPanel.add(this.nameField);
        fieldsPanel.add(addressLabel);
        fieldsPanel.add(this.addressField);
        fieldsPanel.add(cityLabel);
        fieldsPanel.add(this.cityField);
        
        final var buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        
        this.saveButton = new JButton("Salva");
        this.cancelButton = new JButton("Annulla");
        
        buttonsPanel.add(this.saveButton);
        buttonsPanel.add(this.cancelButton);
        
        add(fieldsPanel, BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.SOUTH);
        
        pack();
        setLocationRelativeTo(parent);
    }
    
    public void setSaveButtonListener(final ActionListener listener) {
        this.saveButton.addActionListener(listener);
    }
    
    public void setCancelButtonListener(final ActionListener listener) {
        this.cancelButton.addActionListener(listener);
    }
    
    public String getClubName() {
        return nameField.getText();
    }
    
    public String getClubAddress() {
        return addressField.getText();
    }
    
    public String getClubCity() {
        return cityField.getText();
    }

}
