package io.github.wickeddroid.plugin.listener.vanilla;

import com.destroystokyo.paper.MaterialTags;
import io.github.wickeddroid.plugin.team.UhcTeamManager;
import io.github.wickeddroid.plugin.util.MaterialUtil;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import team.unnamed.inject.Inject;

public class EntityDamageByEntityListener implements Listener {

  @Inject
  private UhcTeamManager uhcTeamManager;

  @EventHandler
  public void onEntityDamageByEntity(final EntityDamageByEntityEvent event) {
    if (!(event.getDamager() instanceof Player player
            && event.getEntity() instanceof Player victim)) {
      return;
    }

    if (!(victim.isBlocking() && MaterialTags.AXES.isTagged(player.getInventory().getItemInMainHand()))) {
      return;
    }

    player.playSound(player.getLocation(), Sound.ITEM_SHIELD_BLOCK, 1.0f, 1.0f);
  }
}
