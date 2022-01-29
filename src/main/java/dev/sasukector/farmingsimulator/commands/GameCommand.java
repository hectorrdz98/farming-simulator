package dev.sasukector.farmingsimulator.commands;

import dev.sasukector.farmingsimulator.controllers.GameController;
import dev.sasukector.farmingsimulator.helpers.ServerUtilities;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class GameCommand implements CommandExecutor, TabExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player && player.isOp()) {
            if (args.length > 0) {
                String option = args[0];
                if (validOptions(player).contains(option)) {
                    switch (option.toLowerCase(Locale.ROOT)) {
                        case "pvp" -> {
                            GameController.getInstance().setPvpEnabled(!GameController.getInstance().isPvpEnabled());
                            player.playSound(player.getLocation(), "minecraft:block.note_block.bell", 1, 1);
                            ServerUtilities.sendServerMessage(player, "PVP: §6" +
                                    (GameController.getInstance().isPvpEnabled() ? "habilitado" : "deshabilitado")
                            );
                        }
                        case "feed" -> {
                            GameController.getInstance().setFeedEnabled(!GameController.getInstance().isFeedEnabled());
                            player.playSound(player.getLocation(), "minecraft:block.note_block.bell", 1, 1);
                            ServerUtilities.sendServerMessage(player, "Feed: §6" +
                                    (GameController.getInstance().isFeedEnabled() ? "habilitado" : "deshabilitado")
                            );
                        }
                        case "drop" -> GameController.getInstance().generateDrop();
                    }
                } else {
                    ServerUtilities.sendServerMessage(player, "§cOpción no válida");
                }
            } else {
                ServerUtilities.sendServerMessage(player, "§cSelecciona una opción");
            }
        }
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        List<String> completions = new ArrayList<>();

        if(sender instanceof Player player) {
            if (args.length == 1) {
                String partialItem = args[0];
                StringUtil.copyPartialMatches(partialItem, validOptions(player), completions);
            }
        }

        Collections.sort(completions);

        return completions;
    }

    public List<String> validOptions(Player player) {
        List<String> valid = new ArrayList<>();
        if (player.isOp()) {
            valid.add("pvp");
            valid.add("feed");
            valid.add("drop");
        }
        Collections.sort(valid);
        return valid;
    }

}
