package io.github.wickeddroid.plugin.scenario.settings;

import io.github.wickeddroid.api.scenario.Setting;
import io.github.wickeddroid.plugin.scenario.RegisteredSetting;
import io.github.wickeddroid.plugin.scenario.SettingScenario;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;

@Setting(
        name = "AntiBurn",
        description = "Los items no se queman.",
        key = "anti_burn",
        material = Material.FLINT_AND_STEEL
)
@RegisteredSetting
public class AntiBurnSetting extends SettingScenario {

  @EventHandler
  public void onEntityDamage(final EntityDamageEvent event) {
    if (!(event.getEntity() instanceof Item)) {
      return;
    }

    if (!(event.getCause() == EntityDamageEvent.DamageCause.FIRE
            || event.getCause() == EntityDamageEvent.DamageCause.FIRE_TICK
            || event.getCause() == EntityDamageEvent.DamageCause.LAVA)) {
      return;
    }

    event.setCancelled(true);
  }
}
