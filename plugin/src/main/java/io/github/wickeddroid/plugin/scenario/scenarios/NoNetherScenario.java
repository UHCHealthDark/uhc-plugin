package io.github.wickeddroid.plugin.scenario.scenarios;

import io.github.wickeddroid.api.scenario.Scenario;
import io.github.wickeddroid.plugin.scenario.ListenerScenario;
import io.github.wickeddroid.plugin.scenario.RegisteredScenario;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerPortalEvent;

@RegisteredScenario
@Scenario(
        name = "No Nether",
        description = "No podrás entrar a la dimensión del nether.",
        key = "no_nether",
        material = Material.NETHERRACK
)
public class NoNetherScenario extends ListenerScenario {

  @EventHandler
  public void onPlayerPortal(final PlayerPortalEvent event) {
    if (event.getTo().getWorld().getEnvironment() == World.Environment.NETHER) {
      event.setCancelled(true);
    }
  }
}
