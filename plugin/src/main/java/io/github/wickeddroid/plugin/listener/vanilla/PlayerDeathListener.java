package io.github.wickeddroid.plugin.listener.vanilla;

import io.github.wickeddroid.api.game.UhcGame;
import io.github.wickeddroid.api.game.UhcGameState;
import io.github.wickeddroid.plugin.player.UhcPlayerRegistry;
import io.github.wickeddroid.plugin.team.UhcTeamManager;
import io.github.wickeddroid.plugin.team.UhcTeamRegistry;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import team.unnamed.inject.InjectAll;

@InjectAll
public class PlayerDeathListener implements Listener {

  private UhcPlayerRegistry uhcPlayerRegistry;
  private UhcTeamManager uhcTeamManager;
  private UhcTeamRegistry uhcTeamRegistry;
  private UhcGame uhcGame;

  @EventHandler
  public void onPlayerDeath(PlayerDeathEvent event) {
    final var player = event.getPlayer();

    final var uhcPlayer = this.uhcPlayerRegistry.getPlayer(player.getName());

    if (this.uhcGame == null) {
      return;
    }

    if (this.uhcGame.getUhcGameState() == UhcGameState.WAITING
            || this.uhcGame.getUhcGameState() == UhcGameState.FINISH) {
      return;
    }

    final var playerKiller = player.getKiller();

    if (playerKiller != null) {
      final var uhcKiller = this.uhcPlayerRegistry.getPlayer(playerKiller.getName());

      if (uhcKiller == null) {
        return;
      }

      uhcKiller.incrementKills();
    }

    if (uhcPlayer == null) {
      return;
    }

    Bukkit.getOnlinePlayers().forEach(onlinePlayer -> onlinePlayer.playSound(Sound.sound(Key.key("block.beacon.deactivate"), Sound.Source.PLAYER, 1.0F, 1.0F)));

    uhcPlayer.setAlive(false);

    player.setGameMode(GameMode.SPECTATOR);

    final var uhcTeam = uhcPlayer.getUhcTeam();

    if (uhcTeam != null) {
      uhcTeam.decrementPlayersAlive();

      if (uhcTeam.getPlayersAlive() > 0) {
        return;
      }

      uhcTeam.getTeam().unregister();
      this.uhcTeamRegistry.removeTeam(uhcTeam.getLeader());
    }
  }
}
