package io.github.wickeddroid.plugin.listener.vanilla;

import io.github.wickeddroid.plugin.world.Worlds;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import team.unnamed.inject.Inject;

public class PlayerPortalListener implements Listener {

  @Inject private Worlds worlds;

  @EventHandler
  public void onPortalCreate(final PlayerPortalEvent event) {
    if (event.getCause() != PlayerTeleportEvent.TeleportCause.NETHER_PORTAL
          || event.getFrom().getWorld().getEnvironment() != World.Environment.NETHER) {
      return;
    }

    final var location = event.getTo();

    final var world = Bukkit.getWorld(worlds.worldName());

    if (world == null) {
      return;
    }

    location.setWorld(world);

    event.setTo(location);

    Bukkit.getLogger().info(event.getCanCreatePortal() + "");
  }
}
