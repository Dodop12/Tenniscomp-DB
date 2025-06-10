package tenniscomp.view.league;

import java.awt.BorderLayout;
import java.awt.Dimension;
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

import tenniscomp.utils.CommonUtils;
import tenniscomp.utils.ImmutableTableModel;

public class LeagueDetailsWindow extends JFrame {

    private static final double WIDTH_RATIO = 0.52;
    private static final double HEIGHT_RATIO = 0.45;

    private static final String FONT_STYLE = "Arial";
    private static final String TEAMS_TITLE = "Squadre";
    private static final String TIES_TITLE = "Incontri";

    private final JSplitPane tablesPanel;
    private final JLabel leagueNameLabel;
    
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
        
        final var infoPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        infoPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(10, 10, 10, 10),
            BorderFactory.createTitledBorder("Informazioni Campionato")
        ));
        
        this.leagueNameLabel = new JLabel();
        this.leagueNameLabel.setFont(new Font(FONT_STYLE, Font.BOLD, 18));
        this.leagueNameLabel.setHorizontalAlignment(SwingConstants.LEFT);
        
        infoPanel.add(this.leagueNameLabel);
        
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

        final var screenSize = CommonUtils.getScreenSize();
        final int width = (int) (screenSize.width * WIDTH_RATIO);
        final int height = (int) (screenSize.height * HEIGHT_RATIO);
        this.tablesPanel.setPreferredSize(new Dimension(width, height));
        
        final var lowerButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        this.closeButton = new JButton("Chiudi");
        lowerButtonPanel.add(this.closeButton);

        final var mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(tablesPanel, BorderLayout.CENTER);
        
        add(infoPanel, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);
        add(lowerButtonPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(parent);
    }
    
    public void setLeagueName(final String series, final String category, final String gender, final int year) {
        this.leagueNameLabel.setText("Campionato a squadre Serie "
                + series + " " + category + " " + gender + " " + year);
    }
    
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
