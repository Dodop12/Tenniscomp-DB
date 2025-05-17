package tenniscomp.controller;

import javax.swing.table.DefaultTableModel;

import tenniscomp.data.League;
import tenniscomp.model.Model;
import tenniscomp.utils.TableUtils;
import tenniscomp.view.LeagueDetailsWindow;

public class LeagueDetailsController {

    private final LeagueDetailsWindow view;
    private final Model model;
    private final League league;

    public LeagueDetailsController(final LeagueDetailsWindow view, final Model model, final League league) {
        this.view = view;
        this.model = model;
        this.league = league;

        loadLeagueData();
        loadTeams();
        loadTies();
    }

    private void loadLeagueData() {
        view.setLeagueName(league.getSeries(), league.getCategory(), league.getGender(), league.getYear());
        // TODO: decommentare se servono
        /* view.setSeries(league.getSeries());
        view.setCategory(league.getCategory());
        view.setGender(league.getGender());
        view.setYear(league.getYear()); */
    }

    private void loadTeams() {
        final var table = view.getTeamsTable();
        final var tableModel = (DefaultTableModel) table.getModel();
        TableUtils.clearTable(tableModel);
        
        final var teams = model.getLeagueTeams(league.getCompetitionId());
        for (final var team : teams) {
            final var club = model.getClubById(team.getClubId());
            if (club != null) {
                final Object[] rowData = {
                    team.getTeamId(),
                    club.getName(),
                    club.getCity()
                };
                tableModel.addRow(rowData);
            }
        }
        
        TableUtils.adjustColumnWidths(table);
    }

    private void loadTies() {
        final var table = view.getTiesTable();
        final var tableModel = (DefaultTableModel) table.getModel();
        TableUtils.clearTable(tableModel);
        
        final var ties = model.getLeagueTies(league.getCompetitionId());
        for (final var tie : ties) {
            final Object[] rowData = {
                tie.getTieId(),
                tie.getDate(),
                model.getClubByTeamId(tie.getHomeTeamId()).getName(),
                model.getClubByTeamId(tie.getAwayTeamId()).getName(),
                tie.getResult()
            };
            tableModel.addRow(rowData);
        }
        
        TableUtils.adjustColumnWidths(table);
    }
}
