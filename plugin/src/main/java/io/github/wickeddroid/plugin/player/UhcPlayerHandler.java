package io.github.wickeddroid.plugin.player;

import io.github.wickeddroid.api.game.UhcGame;
import io.github.wickeddroid.api.team.UhcTeam;
import io.github.wickeddroid.plugin.event.game.PlayerScatteredEvent;
import io.github.wickeddroid.plugin.team.UhcTeamHandler;
import io.github.wickeddroid.plugin.team.UhcTeamManager;
import io.github.wickeddroid.plugin.util.LocationUtil;
import io.github.wickeddroid.plugin.world.Worlds;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import team.unnamed.inject.Inject;
import team.unnamed.inject.InjectAll;

@InjectAll
public class UhcPlayerHandler {

  private Worlds worlds;
  private UhcGame uhcGame;
  private UhcTeamManager uhcTeamManager;
  private UhcTeamHandler uhcTeamHandler;

  public void scatterPlayer(
          final Player player,
          final Player leader,
          final boolean laterScatter
  ) {
    final var location = LocationUtil.generateRandomLocation(uhcGame, worlds.worldName());

    if (location == null) {
      return;
    }

    Bukkit.getPluginManager().callEvent(new PlayerScatteredEvent(player, location, laterScatter));

    if (!this.uhcGame.isTeamEnabled()) {
      return;
    }

    if (leader != null) {
      this.uhcTeamHandler.addPlayerToTeam(leader, player, true);
      return;
    }

    this.uhcTeamManager.createTeam(player, null);
  }
}
