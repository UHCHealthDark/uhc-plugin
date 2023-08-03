package io.github.wickeddroid.plugin.event.game;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class PlayerScatteredEvent extends Event {

  private static final HandlerList HANDLER_LIST = new HandlerList();
  private final Player player;
  private final Location location;
  private final boolean laterScatter;

  public PlayerScatteredEvent(
          final Player player,
          final Location location,
          final boolean laterScatter
  ) {
    this.player = player;
    this.location = location;
    this.laterScatter = laterScatter;
  }

  public Player getPlayer() {
    return player;
  }

  public Location getLocation() {
    return location;
  }

  public boolean isLaterScatter() {
    return laterScatter;
  }

  public static HandlerList getHandlerList() {
    return HANDLER_LIST;
  }

  @Override
  public @NotNull HandlerList getHandlers() {
    return HANDLER_LIST;
  }
}
