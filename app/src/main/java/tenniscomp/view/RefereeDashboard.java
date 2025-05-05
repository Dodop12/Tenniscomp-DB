package tenniscomp.view;

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

import tenniscomp.utils.ImmutableTableModel;

public class RefereeDashboard extends JFrame {

    private static final String FONT_STYLE = "Arial";
    private static final String TOURNAMENTS_TITLE = "Tornei";
    private static final String TEAM_COMPETITIONS_TITLE = "Campionati a Squadre";

    private final JLabel nameLabel;
    private final JLabel titleLabel;

    private final JButton addTournamentButton;
    private final JButton addTeamCompetitionButton;
    private final JButton managePlayersButton;
    private final JButton manageClubsButton;

    private final JTable tournamentsTable;
    private final JTable teamCompetitionsTable;

    public RefereeDashboard() {
        setTitle("TennisComp - Giudice Arbitro");
        setSize(1000, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Top panel containing main referee info
        final var refereeInfoPanel = new JPanel(new GridLayout(2, 1));
        refereeInfoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        this.nameLabel = new JLabel("Nome");
        this.nameLabel.setFont(new Font(FONT_STYLE, Font.BOLD, 22));
        
        this.titleLabel = new JLabel("Qualifica");
        this.titleLabel.setFont(new Font(FONT_STYLE, Font.PLAIN, 16));
        
        refereeInfoPanel.add(this.nameLabel);
        refereeInfoPanel.add(this.titleLabel);
        
        // Panel with buttons to manage players and clubs
        final var managementPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        managementPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        
        this.managePlayersButton = new JButton("Gestione Giocatori");
        this.manageClubsButton = new JButton("Gestione Circoli");
        
        managementPanel.add(this.managePlayersButton);
        managementPanel.add(this.manageClubsButton);
        
        // Tournaments panel
        final var tournamentsPanel = new JPanel(new BorderLayout());
        final String[] tournamentColumns = {"ID", "Nome", "Data inizio", "Data fine", "Scadenza iscrizioni", "Tipo", "Limite Classifica"};
        final var tournamentsModel = new ImmutableTableModel(tournamentColumns, 0);
        this.tournamentsTable = new JTable(tournamentsModel);
        
        final var tournamentButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        this.addTournamentButton = new JButton("Aggiungi Torneo");
        tournamentButtonPanel.add(this.addTournamentButton);
        
        final var tournamentsScrollPane = new JScrollPane(tournamentsTable);
        tournamentsScrollPane.setBorder(BorderFactory.createTitledBorder(TOURNAMENTS_TITLE));
        
        tournamentsPanel.add(tournamentButtonPanel, BorderLayout.NORTH);
        tournamentsPanel.add(tournamentsScrollPane, BorderLayout.CENTER);
        
        // Team competitions panel
        final var teamCompPanel = new JPanel(new BorderLayout());
        final String[] teamCompColumns = {"ID", "Serie", "Categoria", "Sesso", "Anno"};
        final var teamCompModel = new ImmutableTableModel(teamCompColumns, 0);
        this.teamCompetitionsTable = new JTable(teamCompModel);
        
        final var teamCompButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        this.addTeamCompetitionButton = new JButton("Aggiungi Campionato");
        teamCompButtonPanel.add(this.addTeamCompetitionButton);
        
        final var teamCompScrollPane = new JScrollPane(teamCompetitionsTable);
        teamCompScrollPane.setBorder(BorderFactory.createTitledBorder(TEAM_COMPETITIONS_TITLE));
        
        teamCompPanel.add(teamCompButtonPanel, BorderLayout.NORTH);
        teamCompPanel.add(teamCompScrollPane, BorderLayout.CENTER);
        
        final var centerPanel = new JPanel(new GridLayout(2, 1, 0, 10));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        centerPanel.add(tournamentsPanel);
        centerPanel.add(teamCompPanel);
        
        add(refereeInfoPanel, BorderLayout.NORTH);
        add(managementPanel, BorderLayout.CENTER);
        add(centerPanel, BorderLayout.SOUTH);
        
        centerPanel.setPreferredSize(new Dimension(getWidth(), 500));
        managementPanel.setPreferredSize(new Dimension(getWidth(), 50));
        
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    public void setRefereeName(final String name) {
        nameLabel.setText(name);
    }
    
    public void setRefereeTitle(final String title) {
        titleLabel.setText(title);
    }
    
    public void setAddTournamentListener(final ActionListener listener) {
        this.addTournamentButton.addActionListener(listener);
    }
    
    public void setAddTeamCompetitionListener(final ActionListener listener) {
        this.addTeamCompetitionButton.addActionListener(listener);
    }
    
    public void setManagePlayersListener(final ActionListener listener) {
        this.managePlayersButton.addActionListener(listener);
    }
    
    public void setManageClubsListener(final ActionListener listener) {
        this.manageClubsButton.addActionListener(listener);
    }
}
