package io.github.wickeddroid.plugin.scenario.scenarios;

import io.github.wickeddroid.api.scenario.Scenario;
import io.github.wickeddroid.plugin.scenario.RegisteredScenario;
import io.github.wickeddroid.plugin.scenario.ListenerScenario;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;

@RegisteredScenario
@Scenario(
        name = "Timber", 
        key = "timber",
        description = "Los árboles se talarán de forma automática.",
        material = Material.OAK_LOG
)
public class TimberScenario extends ListenerScenario {

  @EventHandler
  public void onBlockBreak(final BlockBreakEvent event) {
    final var block = event.getBlock();
  }
}

