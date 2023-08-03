package io.github.wickeddroid.plugin.thread;

import io.github.wickeddroid.api.team.UhcTeam;
import io.github.wickeddroid.plugin.event.game.PlayerScatteredEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class ScatterThread implements Runnable {

  private final Location location;
  private Player player;
  private UhcTeam uhcTeam;

  public ScatterThread(
          final Player player,
          final Location location
  ) {
    this.player = player;
    this.location = location;
  }

  public ScatterThread(
          final UhcTeam uhcTeam,
          final Location location
  ) {
    this.uhcTeam = uhcTeam;
    this.location = location;
  }

  @Override
  public void run() {
    if (uhcTeam == null) {
      Bukkit.getPluginManager().callEvent(new PlayerScatteredEvent(player, location, false));
      return;
    }

    for (final var member : this.uhcTeam.getMembers()) {
      final var player = Bukkit.getPlayer(member);

      if (player == null || !player.isOnline()) {
        this.uhcTeam.removeMember(member);
        return;
      }

      Bukkit.getPluginManager().callEvent(new PlayerScatteredEvent(player, location, false));
    }
  }
}

