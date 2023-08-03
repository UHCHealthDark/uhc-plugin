package io.github.wickeddroid.plugin.scenario.scenarios;

import io.github.wickeddroid.api.scenario.Scenario;
import io.github.wickeddroid.plugin.event.game.GameStartEvent;
import io.github.wickeddroid.plugin.scenario.ListenerScenario;
import io.github.wickeddroid.plugin.scenario.RegisteredScenario;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Wolf;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

@Scenario(
        name = "Uhc Special",
        description = "Escenario para los uhc's especiales",
        material = Material.WOLF_SPAWN_EGG,
        key = "uhc_special"
)
@RegisteredScenario
public class UhcSpecialScenario extends ListenerScenario {

  @EventHandler
  public void onGameStart(final GameStartEvent event) {
    final var mikecrackTeam = Bukkit.getScoreboardManager().getMainScoreboard().getTeam("rufk_");
    final var richTeam = Bukkit.getScoreboardManager().getMainScoreboard().getTeam("TanaSofia");

    if (mikecrackTeam == null || richTeam == null) {
      return;
    }

    for (final var player : Bukkit.getOnlinePlayers()) {
      if (mikecrackTeam.hasEntry(player.getName())) {
        player.getInventory().addItem(new ItemStack(Material.DIAMOND));
      } else if (richTeam.hasEntry(player.getName())) {
        player.getInventory().addItem(new ItemStack(Material.GOLD_BLOCK));
      }
      final var wolf = (Wolf) player.getWorld().spawnEntity(player.getLocation(), EntityType.WOLF);

      wolf.addPotionEffect(PotionEffectType.FIRE_RESISTANCE.createEffect(Integer.MAX_VALUE, 0));
      wolf.addPotionEffect(PotionEffectType.DAMAGE_RESISTANCE.createEffect(Integer.MAX_VALUE, 0));
      wolf.setOwner(player);
    }
  }
}
