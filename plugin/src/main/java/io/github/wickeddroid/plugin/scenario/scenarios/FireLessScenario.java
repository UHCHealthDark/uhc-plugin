package io.github.wickeddroid.plugin.scenario.scenarios;

import io.github.wickeddroid.api.scenario.Scenario;
import io.github.wickeddroid.plugin.scenario.ListenerScenario;
import io.github.wickeddroid.plugin.scenario.RegisteredScenario;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;

@RegisteredScenario
@Scenario(
        name = "FireLess",
        description = "No recibirás daño por el fuego.",
        key = "fire_less",
        material = Material.LAVA_BUCKET
)
public class FireLessScenario extends ListenerScenario {

  @EventHandler
  public void onEntityDamage(final EntityDamageEvent event) {
    if (!(event.getEntity() instanceof Player)) {
      return;
    }

    if (!(event.getCause() == EntityDamageEvent.DamageCause.FIRE
            || event.getCause() == EntityDamageEvent.DamageCause.LAVA
            || event.getCause() == EntityDamageEvent.DamageCause.FIRE_TICK
            || event.getCause() == EntityDamageEvent.DamageCause.HOT_FLOOR)) {
      return;
    }

    event.setCancelled(true);
  }
}
