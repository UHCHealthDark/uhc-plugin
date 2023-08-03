package io.github.wickeddroid.plugin.scenario.settings;

import io.github.wickeddroid.api.scenario.Setting;
import io.github.wickeddroid.plugin.event.game.GameStartEvent;
import io.github.wickeddroid.plugin.scenario.RegisteredSetting;
import io.github.wickeddroid.plugin.scenario.SettingScenario;
import io.github.wickeddroid.plugin.world.Worlds;
import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import team.unnamed.inject.Inject;

@Setting(
        name = "Mobs Off",
        description = "Los mobs se encuentran desactivados",
        key = "mobs",
        material = Material.ROTTEN_FLESH
)
@RegisteredSetting
public class MobsSetting extends SettingScenario {

  @Inject private Worlds worlds;

  @EventHandler
  public void onGameStart(final GameStartEvent event) {
    for (final var world : Bukkit.getWorlds()) {
      if (this.worlds.blacklist().contains(world.getName())) {
        continue;
      }

      world.setGameRule(GameRule.DO_MOB_SPAWNING, false);
      world.setGameRule(GameRule.DO_INSOMNIA, false);
    }
  }
}
