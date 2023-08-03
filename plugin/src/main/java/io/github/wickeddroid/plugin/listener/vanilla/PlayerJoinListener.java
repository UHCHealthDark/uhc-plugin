package io.github.wickeddroid.plugin.listener.vanilla;

import io.github.wickeddroid.api.game.UhcGame;
import io.github.wickeddroid.api.game.UhcGameState;
import io.github.wickeddroid.plugin.player.UhcPlayerRegistry;
import io.github.wickeddroid.plugin.scoreboard.ScoreboardEndGame;
import io.github.wickeddroid.plugin.scoreboard.ScoreboardGame;
import io.github.wickeddroid.plugin.scoreboard.ScoreboardLobby;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import team.unnamed.inject.Inject;
import team.unnamed.inject.InjectAll;

@InjectAll
public class PlayerJoinListener implements Listener {

  private UhcPlayerRegistry uhcPlayerRegistry;
  private ScoreboardEndGame scoreboardEndGame;
  private ScoreboardLobby scoreboardLobby;
  private ScoreboardGame scoreboardGame;
  private UhcGame uhcGame;

  @EventHandler
  public void onPlayerJoin(final PlayerJoinEvent event) {
    final var player = event.getPlayer();
    final var playerName = player.getName();

    final var uhcPlayer = this.uhcPlayerRegistry.getPlayer(playerName);

    if (uhcPlayer == null) {
      if (this.uhcGame.getUhcGameState() != UhcGameState.WAITING) {
        player.setGameMode(GameMode.SPECTATOR);
      }

      this.uhcPlayerRegistry.createPlayer(player.getUniqueId(), playerName);
    }

    if (this.uhcGame.getUhcGameState() == UhcGameState.WAITING) {
      this.scoreboardLobby.getSidebar().addViewer(player);
    } else if (this.uhcGame.getUhcGameState() == UhcGameState.FINISH) {
      this.scoreboardEndGame.getSidebar().addViewer(player);
    } else {
      this.scoreboardGame.getSidebar().addViewer(player);
    }

  }
}
