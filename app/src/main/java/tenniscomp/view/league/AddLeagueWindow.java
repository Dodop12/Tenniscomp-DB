package tenniscomp.view.league;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.time.LocalDate;

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

import tenniscomp.utils.LeagueCategory;
import tenniscomp.utils.LeagueSeries;

public class AddLeagueWindow extends JDialog {

    private final JComboBox<String> seriesComboBox;
    private final JComboBox<String> categoryComboBox;
    private final JRadioButton maleRadio;
    private final JRadioButton femaleRadio;
    private final JSpinner yearSpinner;

    private final JButton saveButton;
    private final JButton cancelButton;

    public AddLeagueWindow(final JFrame parent) {
        super(parent, "Aggiungi Campionato", true);
        setLayout(new BorderLayout(10, 10));

        final var formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        formPanel.add(new JLabel("Serie:"));
        final var series = LeagueSeries.getAllLabels();
        this.seriesComboBox = new JComboBox<>(series.toArray(new String[0]));
        formPanel.add(this.seriesComboBox);

        formPanel.add(new JLabel("Categoria:"));
        final var categories = LeagueCategory.getAllLabels();
        this.categoryComboBox = new JComboBox<>(categories.toArray(new String[0]));
        formPanel.add(this.categoryComboBox);

        formPanel.add(new JLabel("Sesso:"));
        final var genderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        this.maleRadio = new JRadioButton("M");
        this.femaleRadio = new JRadioButton("F");
        
        final var genderGroup = new ButtonGroup();
        genderGroup.add(this.maleRadio);
        genderGroup.add(this.femaleRadio);
        this.maleRadio.setSelected(true);
        
        genderPanel.add(this.maleRadio);
        genderPanel.add(this.femaleRadio);
        formPanel.add(genderPanel);

        formPanel.add(new JLabel("Anno:"));
        final int currentYear = LocalDate.now().getYear();
        this.yearSpinner = new JSpinner(new SpinnerNumberModel(currentYear, currentYear - 5, currentYear + 5, 1));
        this.yearSpinner.setEditor(new JSpinner.NumberEditor(this.yearSpinner, "#")); // Set the proper format for years
        formPanel.add(this.yearSpinner);

        add(formPanel, BorderLayout.CENTER);

        final var buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        this.saveButton = new JButton("Salva");
        this.cancelButton = new JButton("Annulla");
        
        buttonsPanel.add(this.saveButton);
        buttonsPanel.add(this.cancelButton);
        
        add(buttonsPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(parent);
    }

    public String getSeries() {
        return (String) this.seriesComboBox.getSelectedItem();
    }

    public String getCategory() {
        return (String) this.categoryComboBox.getSelectedItem();
    }

    public String getGender() {
        return this.maleRadio.isSelected() ? "M" : "F";
    }

    public int getYear() {
        return (int) this.yearSpinner.getValue();
    }

    public void setSaveButtonListener(final ActionListener listener) {
        this.saveButton.addActionListener(listener);
    }

    public void setCancelButtonListener(final ActionListener listener) {
        this.cancelButton.addActionListener(listener);
    }
}
