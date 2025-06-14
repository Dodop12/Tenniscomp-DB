package tenniscomp.view.referee;

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

import tenniscomp.utils.CommonUtils;
import tenniscomp.utils.ImmutableTableModel;

public class RefereeDashboard extends JFrame {

    private static final double WIDTH_RATIO = 0.55;
    private static final double HEIGHT_RATIO = 0.47;
    private static final String FONT_STYLE = "Arial";
    private static final String TOURNAMENTS_TITLE = "Tornei";
    private static final String TEAM_COMPETITIONS_TITLE = "Campionati a Squadre";

    private final JLabel nameLabel;
    private final JLabel titleLabel;

    private final JTable tournamentsTable;
    private final JTable leaguesTable;

    private final JButton addTournamentButton;
    private final JButton addLeagueButton;
    private final JButton managePlayersButton;
    private final JButton manageClubsButton;
    private final JButton manageUmpiresButton;
    private final JButton logoutButton;

    public RefereeDashboard() {
        setTitle("TennisComp - Giudice Arbitro");
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
        
        // Panel with buttons to manage players, clubs and umpires
        final var managementPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        managementPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        
        this.managePlayersButton = new JButton("Gestione Giocatori");
        this.manageClubsButton = new JButton("Gestione Circoli");
        this.manageUmpiresButton = new JButton("Gestione Arbitri");
        
        managementPanel.add(this.managePlayersButton);
        managementPanel.add(this.manageClubsButton);
        managementPanel.add(this.manageUmpiresButton);
        
        // Tournaments panel
        final var tournamentsPanel = new JPanel(new BorderLayout());
        final String[] tournamentColumns = {"ID", "Nome", "Data inizio", "Data fine",
                "Scadenza iscrizioni", "Tipo", "Sesso", "Limite Classifica"};
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
        final var leaguesPanel = new JPanel(new BorderLayout());
        final String[] leaguesColumns = {"ID", "Serie", "Categoria", "Sesso", "Anno"};
        final var leaguesModel = new ImmutableTableModel(leaguesColumns, 0);
        this.leaguesTable = new JTable(leaguesModel);
        
        final var leaguesButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        this.addLeagueButton = new JButton("Aggiungi Campionato");
        leaguesButtonPanel.add(this.addLeagueButton);
        
        final var leaguesScrollPane = new JScrollPane(leaguesTable);
        leaguesScrollPane.setBorder(BorderFactory.createTitledBorder(TEAM_COMPETITIONS_TITLE));
        
        leaguesPanel.add(leaguesButtonPanel, BorderLayout.NORTH);
        leaguesPanel.add(leaguesScrollPane, BorderLayout.CENTER);
        
        final var centerPanel = new JPanel(new GridLayout(2, 1, 0, 10));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        centerPanel.add(tournamentsPanel);
        centerPanel.add(leaguesPanel);

        final var screenSize = CommonUtils.getScreenSize();
        final var width = (int) (screenSize.width * WIDTH_RATIO);
        final var height = (int) (screenSize.height * HEIGHT_RATIO);
        centerPanel.setPreferredSize(new Dimension(width, height));

        final var logoutPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        logoutPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        this.logoutButton = new JButton("Logout");
        logoutPanel.add(this.logoutButton);

        final var bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(centerPanel, BorderLayout.CENTER);
        bottomPanel.add(logoutPanel, BorderLayout.SOUTH);
        
        add(refereeInfoPanel, BorderLayout.NORTH);
        add(managementPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    public JTable getTournamentsTable() {
        return tournamentsTable;
    }
    
    public JTable getLeaguesTable() {
        return leaguesTable;
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
        this.addLeagueButton.addActionListener(listener);
    }
    
    public void setManagePlayersListener(final ActionListener listener) {
        this.managePlayersButton.addActionListener(listener);
    }
    
    public void setManageClubsListener(final ActionListener listener) {
        this.manageClubsButton.addActionListener(listener);
    }

    public void setManageUmpiresListener(final ActionListener listener) {
        this.manageUmpiresButton.addActionListener(listener);
    }

    public void setLogoutListener(final ActionListener listener) {
        this.logoutButton.addActionListener(listener);
    }

    public void display() {
        pack();
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
