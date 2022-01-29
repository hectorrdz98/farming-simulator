package dev.sasukector.farmingsimulator.events;

import dev.sasukector.farmingsimulator.controllers.GameController;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class GameEvents implements Listener {

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (!GameController.getInstance().isPvpEnabled()) {
            event.setCancelled(true);
        }
    }

}
