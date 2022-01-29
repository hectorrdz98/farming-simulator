package dev.sasukector.farmingsimulator.controllers;

import dev.sasukector.farmingsimulator.FarmingSimulator;
import dev.sasukector.farmingsimulator.helpers.ServerUtilities;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class GameController {

    private static GameController instance = null;
    private @Getter @Setter boolean pvpEnabled = false;
    private @Getter @Setter boolean feedEnabled = false;
    private final Random random = new Random();
    private final List<ItemStack> chestItems;

    public static GameController getInstance() {
        if (instance == null) {
            instance = new GameController();
        }
        return instance;
    }

    public GameController() {
        this.chestItems = new ArrayList<>();
        this.createRandomChestContents();
        this.createFeedTask();
    }

    public void createFeedTask() {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (feedEnabled) {
                    Bukkit.getOnlinePlayers().forEach(player -> {
                        int carrots = random.nextInt(3) + 1;
                        player.playSound(player.getLocation(), "minecraft:entity.rabbit.hurt", 1, 2);
                        ServerUtilities.sendServerMessage(player, " 2 zanahorias");
                        ServerUtilities.sendServerMessage(player, ServerUtilities.getMiniMessage().parse(
                                "<color:#FFFFFF>Te han alimentado con </color><bold><color:#B7094C>" + carrots +
                                        " zanahorias</color></bold>"
                        ));
                        player.getWorld().dropItem(player.getLocation(), new ItemStack(Material.CARROT, carrots));
                    });
                }
            }
        }.runTaskTimer(FarmingSimulator.getInstance(), 0L, 20L * 60 * 5);
    }

    public void createRandomChestContents() {
        // Food
        this.chestItems.add(new ItemStack(Material.CARROT));
        this.chestItems.add(new ItemStack(Material.BONE));
        this.chestItems.add(new ItemStack(Material.BONE_MEAL));
        this.chestItems.add(new ItemStack(Material.OBSIDIAN));
        this.chestItems.add(new ItemStack(Material.ENDER_PEARL));
        this.chestItems.add(new ItemStack(Material.BLAZE_ROD));
    }

    public void generateDrop() {
        World overworld = ServerUtilities.getOverworld();
        if (overworld != null) {
            int radius = (int) Math.floor(overworld.getWorldBorder().getSize() / 2.0);
            int x = random.nextInt(radius);
            int z = random.nextInt(radius);
            Location safeLocation = ServerUtilities.getSafeLocation(new Location(overworld, x, 0, z));
            Block block = safeLocation.getBlock();
            if (!(block.getType() == Material.CHEST || block.getType() == Material.TRAPPED_CHEST)) {
                block.setType(Material.CHEST);
            }
            Chest chest = (Chest) block.getState();
            List<ItemStack> itemsToAdd = new ArrayList<>(this.chestItems);
            Collections.shuffle(itemsToAdd);
            itemsToAdd = itemsToAdd.stream()
                    .limit(random.nextInt(9) + 1).collect(Collectors.toList());
            int totalItems = itemsToAdd.size();
            while (totalItems < 27) {
                itemsToAdd.add(new ItemStack(Material.AIR));
                totalItems++;
            }
            Collections.shuffle(itemsToAdd);
            for (int i = 0; i < 27; ++i) {
                ItemStack itemStack = itemsToAdd.get(i).clone();
                itemStack.setAmount(random.nextInt(9) + 1);
                chest.getBlockInventory().setItem(i, itemStack);
            }
            ServerUtilities.playBroadcastSound("minecraft:entity.wither.break_block", 1, 2);
            ServerUtilities.sendBroadcastMessage(ServerUtilities.getMiniMessage().parse(
                    "<color:#FFFFFF>Se ha generado un cofre con loot en [</color><bold><color:#B7094C>" +
                            block.getX() + ", " + block.getY() + ", " + block.getZ() + "</color></bold><color:#FFFFFF>]</color>"
            ));
        }
    }

}
