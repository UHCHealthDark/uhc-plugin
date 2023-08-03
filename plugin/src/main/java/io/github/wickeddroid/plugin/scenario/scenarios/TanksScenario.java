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
import org.bukkit.attribute.Attribute;
import org.bukkit.event.EventHandler;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import team.unnamed.inject.Inject;

@RegisteredScenario
@Scenario(
        name = "Tanques",
        description = "Recibes una barra de corazones más el iniciar la partida.",
        key = "tanks",
        material = Material.DIAMOND_CHESTPLATE
)
public class TanksScenario extends ListenerScenario {

  @Inject
  private UhcGame uhcGame;

  @EventHandler
  public void onGameStart(final GameStartEvent event) {
    for (final var player : Bukkit.getOnlinePlayers()) {
      final var attribute = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);

      if (attribute == null) {
        continue;
      }

      attribute.setBaseValue(40.0D);
      player.addPotionEffect(PotionEffectType.REGENERATION.createEffect(1, 255));
    }
  }

  @EventHandler
  public void onPlayerScatter(final PlayerScatteredEvent event) {
    final var player = event.getPlayer();

    if (!(this.uhcGame.getUhcGameState() == UhcGameState.PLAYING
            || this.uhcGame.getUhcGameState() == UhcGameState.PVP)) {
      return;
    }

    final var attribute = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);

    if (attribute == null) {
      return;
    }

    attribute.setBaseValue(40.0D);
    player.addPotionEffect(PotionEffectType.REGENERATION.createEffect(1, 255));
  }
}
