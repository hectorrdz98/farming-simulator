package dev.sasukector.farmingsimulator.controllers;

import dev.sasukector.farmingsimulator.FarmingSimulator;
import dev.sasukector.farmingsimulator.helpers.FastBoard;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;

import java.util.*;

public class BoardController {

    private static BoardController instance = null;
    private final Map<UUID, FastBoard> boards = new HashMap<>();
    private @Setter @Getter boolean hideDays;

    public static BoardController getInstance() {
        if (instance == null) {
            instance = new BoardController();
        }
        return instance;
    }

    public BoardController() {
        Bukkit.getScheduler().runTaskTimer(FarmingSimulator.getInstance(), this::updateBoards, 0L, 20L);
        this.hideDays = false;
    }

    public void newPlayerBoard(Player player) {
        FastBoard board = new FastBoard(player);
        this.boards.put(player.getUniqueId(), board);
    }

    public void removePlayerBoard(Player player) {
        FastBoard board = this.boards.remove(player.getUniqueId());
        if (board != null) {
            board.delete();
        }
    }

    public void updateBoards() {
        boards.forEach((uuid, board) -> {
            Player player = Bukkit.getPlayer(uuid);
            assert player != null;

            board.updateTitle("§9§lGranjeros");

            List<String> lines = new ArrayList<>();
            lines.add("");
            lines.add("Puntos: §d" + PointsController.getInstance().getPlayerPoint(player));
            lines.add("");

            lines.add("Top:");
            LinkedHashMap<UUID, Integer> top5 = PointsController.getInstance().getTop5();
            for (Map.Entry<UUID, Integer> entry : top5.entrySet()) {
                String playerName = Bukkit.getOfflinePlayer(entry.getKey()).getName();
                if (playerName != null) {
                    lines.add(playerName + ": §d" + entry.getValue());
                }
            }
            lines.add("");

            double hours = player.getStatistic(Statistic.PLAY_ONE_MINUTE) / 20.0/ 60.0 / 60.0;
            lines.add("Jugado: §d" + String.format("%.2f", hours) + " h");

            lines.add("");
            lines.add("Online: §6" + Bukkit.getOnlinePlayers().size());
            lines.add("");

            board.updateLines(lines);
        });
    }

}
