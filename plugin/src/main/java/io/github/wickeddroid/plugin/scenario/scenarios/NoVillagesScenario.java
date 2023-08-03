package io.github.wickeddroid.plugin.scenario.scenarios;

import io.github.wickeddroid.api.scenario.Scenario;
import io.github.wickeddroid.plugin.scenario.ListenerScenario;
import io.github.wickeddroid.plugin.scenario.RegisteredScenario;
import io.papermc.paper.event.player.PlayerTradeEvent;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;

@RegisteredScenario
@Scenario(
        name = "No Aldeas",
        description = "No podrás tradear con los aldeanos.",
        key = "no_villages",
        material = Material.EMERALD
)
public class NoVillagesScenario extends ListenerScenario {

  @EventHandler
  public void onPlayerTrade(final PlayerTradeEvent event) {
    event.setCancelled(true);
  }
}
