package io.github.wickeddroid.plugin.scenario.settings;

import com.destroystokyo.paper.MaterialTags;
import io.github.wickeddroid.api.game.UhcGame;
import io.github.wickeddroid.api.scenario.Setting;
import io.github.wickeddroid.plugin.scenario.RegisteredScenario;
import io.github.wickeddroid.plugin.scenario.RegisteredSetting;
import io.github.wickeddroid.plugin.scenario.SettingScenario;
import io.github.wickeddroid.plugin.util.PluginUtil;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.inventory.ItemStack;
import team.unnamed.inject.Inject;

@Setting(
        name = "Apple Rate",
        description = "El apple rate de los arboles cambia dependiendo del host.",
        key = "apple_rate",
        material = Material.APPLE
)
@RegisteredSetting
public class AppleRateSetting extends SettingScenario {

  @Inject private UhcGame uhcGame;

  @EventHandler
  public void onLeavesDecay(final LeavesDecayEvent event) {
    final var world = event.getBlock();

    if (PluginUtil.RANDOM.nextInt(100) >= this.uhcGame.getAppleRate()) {
      return;
    }

    world.getWorld().dropItemNaturally(world.getLocation(), new ItemStack(Material.APPLE));
  }

  @EventHandler
  public void onBlockBreak(final BlockBreakEvent event) {
    final var world = event.getBlock();

    if (!event.getBlock().getType().toString().endsWith("_LEAVES")) {
      return;
    }

    if (PluginUtil.RANDOM.nextInt(100) >= this.uhcGame.getAppleRate()) {
      return;
    }

    world.getWorld().dropItemNaturally(world.getLocation(), new ItemStack(Material.APPLE));
  }
}
