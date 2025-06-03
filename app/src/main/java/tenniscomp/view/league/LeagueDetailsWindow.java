package tenniscomp.view.league;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JSplitPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import tenniscomp.utils.ImmutableTableModel;

public class LeagueDetailsWindow extends JFrame {

    private static final String FONT_STYLE = "Arial";
    private static final String TEAMS_TITLE = "Squadre";
    private static final String TIES_TITLE = "Incontri";

    private final JSplitPane tablesPanel;
    private final JLabel leagueNameLabel;
    /* private final JLabel seriesLabel;
    private final JLabel categoryLabel;
    private final JLabel genderLabel; 
    private final JLabel yearLabel; */
    
    private final JTable teamsTable;
    private final JTable tiesTable;
    
    private final JButton registerTeamButton;
    private final JButton addTieButton;
    private final JButton closeButton;

    public LeagueDetailsWindow(final JFrame parent) {
        setTitle("Dettagli Campionato");
        setSize(1000, 700);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        setLocationRelativeTo(parent);
        
        final var infoPanel = new JPanel(new GridLayout(4, 1, 5, 5));
        infoPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(10, 10, 10, 10),
            BorderFactory.createTitledBorder("Informazioni Campionato")
        ));
        
        this.leagueNameLabel = new JLabel();
        this.leagueNameLabel.setFont(new Font(FONT_STYLE, Font.BOLD, 18));
        this.leagueNameLabel.setHorizontalAlignment(SwingConstants.LEFT);
        
        // TODO: decommentare se servono
        /* this.seriesLabel = new JLabel("Serie: ");
        this.seriesLabel.setFont(new Font(FONT_STYLE, Font.PLAIN, 14));
        
        this.categoryLabel = new JLabel("Categoria: ");
        this.categoryLabel.setFont(new Font(FONT_STYLE, Font.PLAIN, 14));
        
        this.genderLabel = new JLabel("Genere: ");
        this.genderLabel.setFont(new Font(FONT_STYLE, Font.PLAIN, 14));
        
        this.yearLabel = new JLabel("Anno: ");
        this.yearLabel.setFont(new Font(FONT_STYLE, Font.PLAIN, 14)); */
        
        infoPanel.add(this.leagueNameLabel);
        /* infoPanel.add(this.seriesLabel);
        infoPanel.add(this.categoryLabel);
        infoPanel.add(this.genderLabel); */
        
        final var teamsPanel = new JPanel(new BorderLayout());
        final String[] teamsColumns = {"ID", "Circolo", "Città", "Num. Giocatori"};
        final var teamsModel = new ImmutableTableModel(teamsColumns, 0);
        this.teamsTable = new JTable(teamsModel);

        final var teamsButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        this.registerTeamButton = new JButton("Registra Squadra");
        teamsButtonPanel.add(this.registerTeamButton);
        
        final var teamsScrollPane = new JScrollPane(teamsTable);
        teamsScrollPane.setBorder(BorderFactory.createTitledBorder(TEAMS_TITLE));

        teamsPanel.add(teamsButtonPanel, BorderLayout.NORTH);
        teamsPanel.add(teamsScrollPane, BorderLayout.CENTER);
        
        final var tiesPanel = new JPanel(new BorderLayout());
        final String[] tiesColumns = {"ID", "Data", "Squadra casa", "Squadra trasferta", "Risultato"};
        final var tiesModel = new ImmutableTableModel(tiesColumns, 0);
        this.tiesTable = new JTable(tiesModel);

        final var tiesButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        this.addTieButton = new JButton("Aggiungi Incontro");
        tiesButtonPanel.add(this.addTieButton);

        final var tiesScrollPane = new JScrollPane(tiesTable);
        tiesScrollPane.setBorder(BorderFactory.createTitledBorder(TIES_TITLE));

        tiesPanel.add(tiesButtonPanel, BorderLayout.NORTH);
        tiesPanel.add(tiesScrollPane, BorderLayout.CENTER);
        
        this.tablesPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        tablesPanel.setLeftComponent(teamsPanel);
        tablesPanel.setRightComponent(tiesPanel);
        
        final var lowerButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        this.closeButton = new JButton("Chiudi");
        lowerButtonPanel.add(this.closeButton);

        final var mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(tablesPanel, BorderLayout.CENTER);
        
        add(infoPanel, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);
        add(lowerButtonPanel, BorderLayout.SOUTH);
    }
    
    public void setLeagueName(final String series, final String category, final String gender, final int year) {
        final String genderText = "M".equals(gender) ? "Maschile" : "Femminile";
        this.leagueNameLabel.setText("Campionato a squadre Serie "
                + series + " " + category + " " + genderText + " " + year);
    }
    
    /* public void setSeries(final String series) {
        this.seriesLabel.setText("Serie: " + series);
    }
    
    public void setCategory(final String category) {
        this.categoryLabel.setText("Categoria: " + category);
    }
    
    public void setGender(final String gender) {
        String genderText = "M".equals(gender) ? "Maschile" : "Femminile";
        this.genderLabel.setText("Genere: " + genderText);
    }
    
    public void setYear(final int year) {
        this.yearLabel.setText("Anno: " + year); 
    } */
    
    public JTable getTeamsTable() {
        return teamsTable;
    }
    
    public JTable getTiesTable() {
        return tiesTable;
    }

    public void setRegisterTeamListener(final ActionListener listener) {
        this.registerTeamButton.addActionListener(listener);
    }

    public void setAddTieListener(final ActionListener listener) {
        this.addTieButton.addActionListener(listener);
    }
    
    public void setCloseButtonListener(final ActionListener listener) {
        this.closeButton.addActionListener(listener);
    }
    
   public void display() {
        setVisible(true);
        SwingUtilities.invokeLater(() -> {
            this.tablesPanel.setDividerLocation(0.4);
            this.tablesPanel.setContinuousLayout(true);
        });
    }
}
