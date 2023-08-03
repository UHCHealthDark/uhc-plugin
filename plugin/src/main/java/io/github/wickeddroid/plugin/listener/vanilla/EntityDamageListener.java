package io.github.wickeddroid.plugin.listener.vanilla;

import io.github.wickeddroid.api.game.UhcGame;
import io.github.wickeddroid.api.game.UhcGameState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import team.unnamed.inject.Inject;

public class EntityDamageListener implements Listener {

  @Inject
  private UhcGame uhcGame;

  @EventHandler
  public void onEntityDamage(EntityDamageEvent event) {
    if (this.uhcGame.getUhcGameState() == UhcGameState.WAITING) {
      if (event.getCause() == EntityDamageEvent.DamageCause.VOID) {
        return;
      }

      event.setCancelled(true);
    }


  }
}
