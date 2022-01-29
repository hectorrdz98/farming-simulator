package dev.sasukector.farmingsimulator.commands;

import dev.sasukector.farmingsimulator.controllers.PointsController;
import dev.sasukector.farmingsimulator.helpers.ServerUtilities;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class SellCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player) {
            int carrots = this.getPlayerCarrots(player);
            player.playSound(player.getLocation(), "minecraft:block.note_block.bell", 1, 1);
            PointsController.getInstance().addPointsToPlayer(player, carrots);
            player.sendActionBar(Component.text("+" + carrots, TextColor.color(0xB7094C)));
            ServerUtilities.sendServerMessage(player, ServerUtilities.getMiniMessage().parse(
                    "<color:#FFFFFF>Se han vendido </color><bold><color:#B7094C>" + carrots + " zanahorias</color></bold>"
            ));
        }
        return true;
    }

    public int getPlayerCarrots(Player player) {
        int carrots = 0;
        for (ItemStack itemStack : player.getInventory().getContents()) {
            if (itemStack != null && itemStack.getType() == Material.CARROT) {
                carrots += itemStack.getAmount();
                itemStack.setAmount(0);
            }
        }
        return carrots;
    }

}
