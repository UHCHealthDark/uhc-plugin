package io.github.wickeddroid.plugin.scenario.scenarios;

import io.github.wickeddroid.api.scenario.Scenario;
import io.github.wickeddroid.plugin.scenario.ListenerScenario;
import io.github.wickeddroid.plugin.scenario.RegisteredScenario;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;

@RegisteredScenario
@Scenario(
        name = "Blood Diamonds",
        key = "blood_diamonds",
        description = "Recibirás medio corazón de daño por cada diamante minado.",
        material = Material.DIAMOND
)
public class BloodDiamondScenario extends ListenerScenario {

  @EventHandler
  public void onBlockBreak(final BlockBreakEvent event) {
    if (event.getBlock().getType() == Material.DIAMOND_ORE
            || event.getBlock().getType() == Material.DEEPSLATE_DIAMOND_ORE) {
      event.getPlayer().damage(1.0D);
    }
  }
}
