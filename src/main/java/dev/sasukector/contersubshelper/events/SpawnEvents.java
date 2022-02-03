package dev.sasukector.contersubshelper.events;

import dev.sasukector.contersubshelper.controllers.BoardController;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class SpawnEvents implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        BoardController.getInstance().newPlayerBoard(event.getPlayer());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        BoardController.getInstance().removePlayerBoard(event.getPlayer());
    }

}
