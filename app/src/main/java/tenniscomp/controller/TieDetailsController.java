package tenniscomp.controller;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import tenniscomp.data.Court;
import tenniscomp.data.LeagueTie;
import tenniscomp.data.Player;
import tenniscomp.model.Model;
import tenniscomp.utils.CommonUtils;
import tenniscomp.utils.MatchType;
import tenniscomp.utils.TableUtils;
import tenniscomp.view.AddLeagueMatchWindow;
import tenniscomp.view.TieDetailsWindow;

public class TieDetailsController {

    private final static String HOME_TEAM = "Casa";

    private final TieDetailsWindow view;
    private final Model model;
    private LeagueTie tie;
    private Runnable onTieUpdatedCallback;

    public TieDetailsController(final TieDetailsWindow view, final Model model, final LeagueTie tie) {
        this.view = view;
        this.model = model;
        this.tie = tie;

        loadTieData();
        loadMatches();
        setupListeners();
    }

    private void loadTieData() {
        final var homeClub = model.getClubByTeamId(tie.getHomeTeamId());
        final var awayClub = model.getClubByTeamId(tie.getAwayTeamId());
        final var homeTeamName = homeClub != null ? homeClub.getName() : "Squadra Casa";
        final var awayTeamName = awayClub != null ? awayClub.getName() : "Squadra Ospite";
        
        final var result = this.tie.getResult() != null ? this.tie.getResult() : "0-0";
        view.setTieInfo(homeTeamName, awayTeamName, CommonUtils.convertDateFormat(tie.getDate()), result);
    }

    private void loadMatches() {
        final var table = view.getMatchesTable();
        final var tableModel = (DefaultTableModel) table.getModel();
        TableUtils.clearTable(tableModel);
        
        final var matches = model.getLeagueTieMatches(tie.getTieId());
        for (final var match : matches) {
            final var players = model.getPlayersByLeagueMatch(match.getMatchId());
            final var winners = new ArrayList<Player>();
            final var opponents = new ArrayList<Player>();
            
            for (final var player : players) {
                if (player != null) {
                    if (model.isPlayerLeagueMatchWinner(player.getPlayerId(), match.getMatchId())) {
                        winners.add(player);
                    } else {
                        opponents.add(player);
                    }
                }
            }
        
            final var clubWinner = model.getClubById(winners.get(0).getClubId());
            final var court = model.getCourtById(match.getCourtId());
            final var courtNumber = court != null ? court.getNumber() : 0;
            
            final Object[] rowData = {
                match.getMatchId(),
                match.getType(),
                CommonUtils.getMatchPlayersString(winners),
                clubWinner != null ? clubWinner.getName() : "",
                CommonUtils.getMatchPlayersString(opponents),
                match.getResult(),
                courtNumber
            };
            tableModel.addRow(rowData);
        }
        
        TableUtils.adjustColumnWidths(table);
    }

    private void setupListeners() {
        view.setAddMatchListener(e -> openAddMatchWindow());
        view.setCloseButtonListener(e -> view.dispose());
    }

    private void openAddMatchWindow() {
        final var homePlayers = model.getPlayersByTeam(tie.getHomeTeamId());
        final var awayPlayers = model.getPlayersByTeam(tie.getAwayTeamId());
        final var homeClub = model.getClubByTeamId(tie.getHomeTeamId());
        final var courts = new ArrayList<>(model.getCourtsByClub(homeClub.getClubId()));

        if (!checkPlayersAndCourts(homePlayers, awayPlayers, courts)) {
            return;
        }
        
        final var addMatchWindow = new AddLeagueMatchWindow(view, homePlayers, awayPlayers, courts);
        
        addMatchWindow.setSaveButtonListener(e -> handleSaveMatch(addMatchWindow));
        addMatchWindow.setCancelButtonListener(e -> addMatchWindow.dispose());
        addMatchWindow.setVisible(true);
    }

    private void handleSaveMatch(final AddLeagueMatchWindow addMatchWindow) {
        final var matchType = addMatchWindow.getMatchType();
        final var homePlayer1 = addMatchWindow.getHomePlayer1();
        final var homePlayer2 = addMatchWindow.getHomePlayer2();
        final var awayPlayer1 = addMatchWindow.getAwayPlayer1();
        final var awayPlayer2 = addMatchWindow.getAwayPlayer2();
        final var court = addMatchWindow.getCourt();
        final var winner = addMatchWindow.getWinner();
        final var result = addMatchWindow.getResult();

        if (!validateMatchInput(matchType, homePlayer1, homePlayer2, awayPlayer1, awayPlayer2, court, result)) {
            return;
        }

        final var winnerPlayers = new ArrayList<Integer>();
        final var loserPlayers = new ArrayList<Integer>();
        preparePlayersByResult(winner, homePlayer1, homePlayer2, awayPlayer1, awayPlayer2,
                winnerPlayers, loserPlayers);

        final boolean success = model.addLeagueMatch(MatchType.fromLabel(matchType), result,
                tie.getTieId(), court.getCourtId(), null, winnerPlayers, loserPlayers);
        if (success) {
            loadMatches();
            updateTieResult();
            addMatchWindow.dispose();
            // Notify parent controller that the tie was updated
            if (onTieUpdatedCallback != null) {
                onTieUpdatedCallback.run();
            }
        } else {
            showError("Errore durante il salvataggio della partita.");
        }
    }

    private boolean validateMatchInput(final String matchType, final Player h1, final Player h2,
            final Player a1, final Player a2, final Court court, final String result) {
        if (matchType == null || h1 == null || a1 == null || court == null || result.isEmpty()) {
            showError("Tutti i campi sono obbligatori.");
            return false;
        }

        if (MatchType.DOPPIO.getLabel().equals(matchType) && (h2 == null || a2 == null)) {
            showError("Per il doppio sono necessari 2 giocatori per squadra.");
            return false;
        }

        if (MatchType.SINGOLARE.getLabel().equals(matchType) && (h2 != null || a2 != null)) {
            showError("Per il singolare è necessario un solo giocatore per squadra.");
            return false;
        }

        if (!arePlayersDistinct(h1, h2, a1, a2)) {
            showError("I giocatori selezionati devono essere tutti diversi.");
            return false;
        }

        return true;
    }

    private boolean arePlayersDistinct(final Player h1, final Player h2, final Player a1, final Player a2) {
        if (h1.getPlayerId() == a1.getPlayerId()) return false;
        if (h2 != null && a2 != null && h2.getPlayerId() == a2.getPlayerId()) return false;
        if (h2 != null && h1.getPlayerId() == h2.getPlayerId()) return false;
        if (a2 != null && a1.getPlayerId() == a2.getPlayerId()) return false;
        return true;
    }

    private boolean checkPlayersAndCourts(final List<Player> homePlayers, final List<Player> awayPlayers,
            final List<Court> courts) {
        if (homePlayers.isEmpty() || awayPlayers.isEmpty()) {
            showError("Non ci sono abbastanza giocatori nelle squadre per inserire una partita.");
            return false;
        }
        if (courts.isEmpty()) {
            showError("Nessun campo trovato per il circolo della squadra ospitante.");
            return false;
        }
        return true;
    }

    private void preparePlayersByResult(final String winnerTeam, final Player homePlayer1,
            final Player homePlayer2, final Player awayPlayer1, final Player awayPlayer2,
            final ArrayList<Integer> winnerPlayers, final ArrayList<Integer> loserPlayers) {
        final boolean homeWins = HOME_TEAM.equals(winnerTeam);

        if (homeWins) {
            winnerPlayers.add(homePlayer1.getPlayerId());
            if (homePlayer2 != null) {
                winnerPlayers.add(homePlayer2.getPlayerId());
            }
            loserPlayers.add(awayPlayer1.getPlayerId());
            if (awayPlayer2 != null) {
                loserPlayers.add(awayPlayer2.getPlayerId());
            }
        } else {
            winnerPlayers.add(awayPlayer1.getPlayerId());
            if (awayPlayer2 != null) {
                winnerPlayers.add(awayPlayer2.getPlayerId());
            }
            loserPlayers.add(homePlayer1.getPlayerId());
            if (homePlayer2 != null) {
                loserPlayers.add(homePlayer2.getPlayerId());
            }
        }
    }

    private void updateTieResult() {
        final var homeWins = model.getTieMatchWinsByTeam(tie.getHomeTeamId(), tie.getTieId());
        final var awayWins = model.getTieMatchWinsByTeam(tie.getAwayTeamId(), tie.getTieId());
        final var newResult = homeWins + "-" + awayWins;
        
        if (model.updateLeagueTieResult(tie.getTieId(), newResult)) {
            // Reassign the tie instance to refresh its data
            this.tie = model.getLeagueTieById(this.tie.getTieId());
            loadTieData(); // Refresh the tie info display
        }
    }

    public void setOnTieUpdatedCallback(final Runnable callback) {
        this.onTieUpdatedCallback = callback;
    }

    private void showError(final String message) {
        JOptionPane.showMessageDialog(
            view,
            message,
            "Errore",
            JOptionPane.ERROR_MESSAGE
        );
    }
}