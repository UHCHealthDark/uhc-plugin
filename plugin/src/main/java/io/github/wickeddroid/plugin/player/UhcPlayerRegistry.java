package io.github.wickeddroid.plugin.player;

import io.github.wickeddroid.api.player.UhcPlayer;
import team.unnamed.inject.Singleton;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Singleton
public class UhcPlayerRegistry {

  private final Map<String, UhcPlayer> playerMap = new HashMap<>();

  public void createPlayer(
          final UUID uuid,
          final String name
  ) {
    this.playerMap.put(name, new DefaultUhcPlayer(
            uuid,
            name
    ));
  }

  public void removePlayer(String name) {
    this.playerMap.remove(name);
  }

  public UhcPlayer getPlayer(String name) {
    return this.playerMap.get(name);
  }

  public Collection<UhcPlayer> getPlayers() {
    return this.playerMap.values();
  }
}
