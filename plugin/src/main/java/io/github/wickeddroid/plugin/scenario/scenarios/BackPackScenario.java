package io.github.wickeddroid.plugin.scenario.scenarios;

import io.github.wickeddroid.api.game.UhcGame;
import io.github.wickeddroid.api.game.UhcGameState;
import io.github.wickeddroid.api.scenario.Scenario;
import io.github.wickeddroid.plugin.event.game.GameStartEvent;
import io.github.wickeddroid.plugin.event.game.PlayerScatteredEvent;
import io.github.wickeddroid.plugin.scenario.ListenerScenario;
import io.github.wickeddroid.plugin.scenario.RegisteredScenario;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;
import team.unnamed.inject.Inject;

@RegisteredScenario
@Scenario(
        name = "Backpack",
        description = "Recibirás una shulker box al iniciar la partida.",
        key = "backpack",
        material = Material.SHULKER_BOX
)
public class BackPackScenario extends ListenerScenario {

  @Inject private UhcGame uhcGame;

  @EventHandler
  public void onGameStart(final GameStartEvent event) {
    for (final var player : Bukkit.getOnlinePlayers()) {
      player.getInventory().addItem(new ItemStack(Material.SHULKER_BOX));
    }
  }

  @EventHandler
  public void onPlayerScatter(final PlayerScatteredEvent event) {
    final var player = event.getPlayer();

    if (!(this.uhcGame.getUhcGameState() == UhcGameState.PLAYING
            || this.uhcGame.getUhcGameState() == UhcGameState.PVP)) {
      return;
    }

    player.getInventory().addItem(new ItemStack(Material.SHULKER_BOX));
  }
}
