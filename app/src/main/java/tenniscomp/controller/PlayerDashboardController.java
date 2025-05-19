package tenniscomp.controller;

import java.awt.event.MouseEvent;
import java.util.Optional;

import javax.swing.event.MouseInputAdapter;

import tenniscomp.data.Player;
import tenniscomp.model.Model;
import tenniscomp.utils.ImmutableTableModel;
import tenniscomp.utils.PlayerUtils;
import tenniscomp.utils.TableUtils;
import tenniscomp.view.PlayerDashboard;

public class PlayerDashboardController {
    
    private final PlayerDashboard view;
    private final Model model;
    private final Player player;
    
    public PlayerDashboardController(final PlayerDashboard view, final Model model, final Player player) {
        this.view = view;
        this.model = model;
        this.player = player;
        
        loadPlayerData();
        loadTournaments();
        setupListeners();
    }

    private void loadPlayerData() {
        this.view.setPlayerName(player.getSurname() + " " + player.getName());
        this.view.setPlayerRanking(player.getRanking());

        final String category = PlayerUtils.calculateCategory(player.getBirthDate());
        this.view.setPlayerCategory(category);

        Optional.ofNullable(player.getCardId())
            .map(model::getCardById)
            .ifPresent(card -> view.setCardInfo(card.getCardNumber(), card.getExpiryDate()));

        TableUtils.adjustColumnWidths(view.getTournamentsTable());
        TableUtils.adjustColumnWidths(view.getMatchesTable());
    }

    private void loadTournaments() {
        final var table = view.getTournamentsTable();
        final var tableModel = (ImmutableTableModel) table.getModel();
        TableUtils.clearTable(tableModel);
        
        final var tournaments = model.getEligibleTournaments(this.player.getRanking(), this.player.getGender());
         for (final var tournament : tournaments) {
            final Object[] rowData = {
                tournament.getTournamentId(),
                tournament.getName(),
                tournament.getStartDate(),
                tournament.getEndDate(),
                tournament.getRegistrationDeadline(),
                tournament.getType(),
                tournament.getGender().equals("M") ? "Maschile" : "Femminile",
                tournament.getRankingLimit()
            };
            tableModel.addRow(rowData);
        }

        TableUtils.adjustColumnWidths(table);
    }

    private void setupListeners() {
        // Double-click listener for tournament registration
        view.getTournamentsTable().addMouseListener(new MouseInputAdapter() {
            @Override
            public void mouseClicked(final MouseEvent e) {
                if (e.getClickCount() == 2) {
                    //handleTournamentRegistration();
                }
            }
        });
    }
    
}
