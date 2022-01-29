package dev.sasukector.farmingsimulator;

import dev.sasukector.farmingsimulator.commands.*;
import dev.sasukector.farmingsimulator.controllers.BoardController;
import dev.sasukector.farmingsimulator.controllers.PointsController;
import dev.sasukector.farmingsimulator.controllers.TeamsController;
import dev.sasukector.farmingsimulator.events.GameEvents;
import dev.sasukector.farmingsimulator.events.SpawnEvents;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class FarmingSimulator extends JavaPlugin {

    private static @Getter FarmingSimulator instance;

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info(ChatColor.DARK_PURPLE + "FarmingSimulator startup!");
        instance = this;

        // Configuration
        this.saveDefaultConfig();

        // Register events
        this.getServer().getPluginManager().registerEvents(new SpawnEvents(), this);
        this.getServer().getPluginManager().registerEvents(new GameEvents(), this);
        Bukkit.getOnlinePlayers().forEach(player -> BoardController.getInstance().newPlayerBoard(player));

        // Register commands
        Objects.requireNonNull(FarmingSimulator.getInstance().getCommand("conter-tps")).setExecutor(new TPSCommand());
        Objects.requireNonNull(FarmingSimulator.getInstance().getCommand("points")).setExecutor(new PointsCommand());
        Objects.requireNonNull(FarmingSimulator.getInstance().getCommand("played")).setExecutor(new PlayedCommand());
        Objects.requireNonNull(FarmingSimulator.getInstance().getCommand("inventory")).setExecutor(new InventoryCommand());
        Objects.requireNonNull(FarmingSimulator.getInstance().getCommand("sell")).setExecutor(new SellCommand());
        Objects.requireNonNull(FarmingSimulator.getInstance().getCommand("game")).setExecutor(new GameCommand());

        // Load points
        PointsController.getInstance().loadPointsFromFile();
        TeamsController.getInstance();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info(ChatColor.DARK_PURPLE + "FarmingSimulator shutdown!");
        PointsController.getInstance().savePointsToFile();
    }
}
