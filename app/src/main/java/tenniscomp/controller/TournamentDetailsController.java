package tenniscomp.controller;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import tenniscomp.data.Tournament;
import tenniscomp.model.Model;
import tenniscomp.utils.TableUtils;
import tenniscomp.view.TournamentDetailsWindow;

public class TournamentDetailsController {

    private final TournamentDetailsWindow view;
    private final Model model;
    private final Tournament tournament;

    public TournamentDetailsController(final TournamentDetailsWindow view, final Model model, final Tournament tournament) {
        this.view = view;
        this.model = model;
        this.tournament = tournament;

        loadTournamentData();
        loadRegistrations();
        loadMatches();
    }

    private void loadTournamentData() {
        view.setTournamentName(tournament.getName());
        view.setDates(tournament.getStartDate(), tournament.getEndDate(), tournament.getRegistrationDeadline());
        view.setRankingLimit(tournament.getRankingLimit());
        
        final var club = model.getClubById(tournament.getClubId());
        if (club != null) {
            view.setClub(club.getName());
        }
        
        view.setPrizeMoney(tournament.getPrizeMoney());
    }

    private void loadRegistrations() {
        final JTable table = view.getRegistrationsTable();
        final DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
        TableUtils.clearTable(tableModel);
        
        final var registrations = model.getTournamentRegistrations(tournament.getTournamentId());
        for (final var registration : registrations) {
            final var player = model.getPlayerById(registration.getPlayerId());
            if (player != null) {
                final Object[] rowData = {
                    registration.getRegistrationId(),
                    player.getName() + " " + player.getSurname(),
                    player.getRanking(),
                    registration.getDate()
                };
                tableModel.addRow(rowData);
            }
        }
        
        TableUtils.adjustColumnWidths(table);
    }

    private void loadMatches() {
        final JTable table = view.getMatchesTable();
        final DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
        TableUtils.clearTable(tableModel);
        
        final var matches = model.getTournamentMatches(tournament.getTournamentId());
        for (final var match : matches) {
            final Object[] rowData = {
                match.getMatchId(),
                match.getType(),
                "Players info", // TODO: replace it with actual player names
                match.getWinner(),
                match.getResult(),
                match.getCourtId()
            };
            tableModel.addRow(rowData);
        }
        
        TableUtils.adjustColumnWidths(table);
    }
}