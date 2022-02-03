package dev.sasukector.contersubshelper;

import dev.sasukector.contersubshelper.commands.*;
import dev.sasukector.contersubshelper.controllers.BoardController;
import dev.sasukector.contersubshelper.events.GameEvents;
import dev.sasukector.contersubshelper.events.SpawnEvents;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class ConterSubsHelper extends JavaPlugin {

    private static @Getter
    ConterSubsHelper instance;

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info(ChatColor.DARK_PURPLE + "ConterSubsHelper startup!");
        instance = this;

        // Configuration
        this.saveDefaultConfig();

        // Register events
        this.getServer().getPluginManager().registerEvents(new SpawnEvents(), this);
        this.getServer().getPluginManager().registerEvents(new GameEvents(), this);
        Bukkit.getOnlinePlayers().forEach(player -> BoardController.getInstance().newPlayerBoard(player));

        // Register commands
        Objects.requireNonNull(ConterSubsHelper.getInstance().getCommand("played")).setExecutor(new PlayedCommand());
        Objects.requireNonNull(ConterSubsHelper.getInstance().getCommand("sit")).setExecutor(new SitCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info(ChatColor.DARK_PURPLE + "ConterSubsHelper shutdown!");
    }
}
