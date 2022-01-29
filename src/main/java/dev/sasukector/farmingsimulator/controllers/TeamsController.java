package dev.sasukector.farmingsimulator.controllers;

import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class TeamsController {

    private static TeamsController instance = null;
    private @Getter Team masterTeam;
    private @Getter Team oneLegTeam;
    private @Getter Team walkerTeam;
    private @Getter Team runnerTeam;
    private @Getter Team speedTeam;
    private @Getter Team addictTeam;
    private @Getter Team adventureTeam;
    private @Getter Team missionTeam;
    private @Getter Team godlikeTeam;

    public static TeamsController getInstance() {
        if (instance == null) {
            instance = new TeamsController();
        }
        return instance;
    }

    public TeamsController() {
        this.createOrLoadTeams();
    }

    public void createOrLoadTeams() {
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        masterTeam = scoreboard.getTeam("master");
        oneLegTeam = scoreboard.getTeam("oneLeg");
        walkerTeam = scoreboard.getTeam("walker");
        runnerTeam = scoreboard.getTeam("runner");
        speedTeam = scoreboard.getTeam("speed");
        addictTeam = scoreboard.getTeam("addict");
        adventureTeam = scoreboard.getTeam("adventure");
        missionTeam = scoreboard.getTeam("mission");
        godlikeTeam = scoreboard.getTeam("godlike");

        if (masterTeam == null) {
            masterTeam = scoreboard.registerNewTeam("master");
            masterTeam.color(NamedTextColor.AQUA);
            masterTeam.prefix(Component.text("♔ "));
            masterTeam.setAllowFriendlyFire(false);
        }

        if (oneLegTeam == null) {
            oneLegTeam = scoreboard.registerNewTeam("oneLeg");
            oneLegTeam.color(NamedTextColor.GRAY);
            oneLegTeam.prefix(Component.text("◎ "));
        }

        if (walkerTeam == null) {
            walkerTeam = scoreboard.registerNewTeam("walker");
            walkerTeam.color(NamedTextColor.DARK_RED);
            walkerTeam.prefix(Component.text("♫ "));
        }

        if (runnerTeam == null) {
            runnerTeam = scoreboard.registerNewTeam("runner");
            runnerTeam.color(NamedTextColor.DARK_GREEN);
            runnerTeam.prefix(Component.text("★ "));
        }

        if (speedTeam == null) {
            speedTeam = scoreboard.registerNewTeam("speed");
            speedTeam.color(NamedTextColor.GREEN);
            speedTeam.prefix(Component.text("☀ "));
        }

        if (addictTeam == null) {
            addictTeam = scoreboard.registerNewTeam("addict");
            addictTeam.color(NamedTextColor.DARK_BLUE);
            addictTeam.prefix(Component.text("✿ "));
        }

        if (adventureTeam == null) {
            adventureTeam = scoreboard.registerNewTeam("adventure");
            adventureTeam.color(NamedTextColor.BLUE);
            adventureTeam.prefix(Component.text("☯ "));
        }

        if (missionTeam == null) {
            missionTeam = scoreboard.registerNewTeam("mission");
            missionTeam.color(NamedTextColor.GOLD);
            missionTeam.prefix(Component.text("⚜ "));
        }

        if (godlikeTeam == null) {
            godlikeTeam = scoreboard.registerNewTeam("godlike");
            godlikeTeam.color(NamedTextColor.LIGHT_PURPLE);
            godlikeTeam.prefix(Component.text("∞ "));
        }
    }

}
