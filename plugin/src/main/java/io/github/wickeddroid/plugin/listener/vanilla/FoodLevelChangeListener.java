package io.github.wickeddroid.plugin.listener.vanilla;

import io.github.wickeddroid.api.game.UhcGame;
import io.github.wickeddroid.api.game.UhcGameState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import team.unnamed.inject.Inject;

public class FoodLevelChangeListener implements Listener {

  @Inject
  private UhcGame uhcGame;

  @EventHandler
  public void onFoodLevelChange(final FoodLevelChangeEvent event) {
    if (this.uhcGame.getUhcGameState() == UhcGameState.WAITING) {
      event.setCancelled(true);
    }
  }
}
