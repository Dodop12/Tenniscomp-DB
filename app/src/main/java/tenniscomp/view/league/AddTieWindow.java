package tenniscomp.view.league;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;

import tenniscomp.data.Team;
import tenniscomp.utils.CommonUtils;

public class AddTieWindow extends JDialog {

    private static final double WIDTH_RATIO = 0.09;
    
    private final DatePicker datePicker;
    private final JComboBox<String> homeComboBox;
    private final JComboBox<String> awayComboBox;
    private final JButton saveButton;
    private final JButton cancelButton;
    
    private List<Team> teams;

    public AddTieWindow(final JFrame parent) {
        super(parent, "Aggiungi Partita", true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        final var mainPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        mainPanel.setBorder(new EmptyBorder(20, 20, 10, 20));

        mainPanel.add(new JLabel("Data:"));
        final var dateSettings = getDatePickerSettings();
        this.datePicker = new DatePicker(dateSettings);
        this.datePicker.setDate(LocalDate.now());
        mainPanel.add(this.datePicker);

        mainPanel.add(new JLabel("Squadra ospitante:"));
        this.homeComboBox = new JComboBox<>();

        final var screenSize = CommonUtils.getScreenSize();
        final int width = (int) (screenSize.width * WIDTH_RATIO);
        this.homeComboBox.setPreferredSize(new Dimension(width, this.homeComboBox.getPreferredSize().height));

        mainPanel.add(this.homeComboBox);
 
        mainPanel.add(new JLabel("Squadra ospite:"));
        this.awayComboBox = new JComboBox<>();
        mainPanel.add(this.awayComboBox);

        final var buttonsPanel = new JPanel(new FlowLayout());
        buttonsPanel.setBorder(new EmptyBorder(10, 20, 20, 20));
        this.saveButton = new JButton("Salva");
        this.cancelButton = new JButton("Annulla");
        buttonsPanel.add(this.saveButton);
        buttonsPanel.add(this.cancelButton);

        add(mainPanel, BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(parent);
    }

    public String getTieDate() {
        final var selectedDate = this.datePicker.getDate();
        return selectedDate != null ? selectedDate.format(CommonUtils.getYmdDateFormatter()) : "";
    }

    public Team getHomeTeam() {
        final int selectedIndex = homeComboBox.getSelectedIndex();
        return selectedIndex >= 0 && selectedIndex < this.teams.size() ? this.teams.get(selectedIndex) : null;
    }

    public Team getAwayTeam() {
        final int selectedIndex = awayComboBox.getSelectedIndex();
        return selectedIndex >= 0 && selectedIndex < this.teams.size() ? this.teams.get(selectedIndex) : null;
    }

    public void setTeams(final List<Team> teams, final Function<Team, String> labelProvider) {
        this.teams = new ArrayList<>(teams);

        final DefaultComboBoxModel<String> homeModel = new DefaultComboBoxModel<>();
        final DefaultComboBoxModel<String> awayModel = new DefaultComboBoxModel<>();

        for (final Team team : teams) {
            final String label = labelProvider.apply(team);
            homeModel.addElement(label);
            awayModel.addElement(label);
        }

        homeComboBox.setModel(homeModel);
        awayComboBox.setModel(awayModel);
    }

    public void setSaveButtonListener(final ActionListener listener) {
        this.saveButton.addActionListener(listener);
    }

    public void setCancelButtonListener(final ActionListener listener) {
        this.cancelButton.addActionListener(listener);
    }

    private DatePickerSettings getDatePickerSettings() {
        final var settings = new DatePickerSettings();
        settings.setFormatForDatesCommonEra("dd/MM/yyyy");
        return settings;
    }
}
