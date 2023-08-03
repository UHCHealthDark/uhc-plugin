package io.github.wickeddroid.plugin.util;

import io.github.wickeddroid.api.game.UhcGame;
import org.bukkit.Bukkit;
import org.bukkit.Location;

public class LocationUtil {

  private LocationUtil() {}

  public static Location generateRandomLocation(
          final UhcGame uhcGame,
          final String worldName
  ) {
    final var world = Bukkit.getWorld(worldName);
    final var x = -(uhcGame.getWorldBorder() / 2) + PluginUtil.RANDOM.nextInt(uhcGame.getWorldBorder());
    final var z = -(uhcGame.getWorldBorder() / 2) + PluginUtil.RANDOM.nextInt(uhcGame.getWorldBorder());

    if (world == null) {
      return null;
    }

    final var y = world.getHighestBlockYAt(x, z) + 1.5;

    return new Location(world, x, y, z);
  }
}
