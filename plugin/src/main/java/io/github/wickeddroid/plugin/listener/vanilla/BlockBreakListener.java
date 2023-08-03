package io.github.wickeddroid.plugin.listener.vanilla;

import io.github.wickeddroid.api.game.UhcGame;
import io.github.wickeddroid.api.game.UhcGameState;
import io.github.wickeddroid.plugin.util.PluginUtil;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import team.unnamed.inject.Inject;

public class BlockBreakListener implements Listener {

  @Inject
  private UhcGame uhcGame;

  @EventHandler
  public void onBlockBreak(final BlockBreakEvent event) {
    if (this.uhcGame.getUhcGameState() == UhcGameState.WAITING) {
      event.setCancelled(true);
      return;
    }

    final var block = event.getBlock();

    if (block.getType().toString().endsWith("_LEAVES")) {
      if (PluginUtil.RANDOM.nextInt(100) >= this.uhcGame.getAppleRate()) {
        block.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(Material.APPLE));
      }
    }
  }
}
