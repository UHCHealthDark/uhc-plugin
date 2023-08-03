package io.github.wickeddroid.plugin.team;

import io.github.wickeddroid.api.team.UhcTeam;
import org.bukkit.Bukkit;
import team.unnamed.inject.Singleton;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Singleton
public class UhcTeamRegistry {

  private final Map<String, UhcTeam> teamMap = new HashMap<>();

  public void createTeam(
          final String leader,
          final String name
  ) {
    this.teamMap.put(leader, new DefaultUhcTeam(
            leader, name
    ));
  }

  public void removeTeam(final String leader) {
    this.teamMap.remove(leader);
  }

  public UhcTeam getTeam(final String leader) {
    return this.teamMap.get(leader);
  }

  public Collection<UhcTeam> getTeams() {
    return this.teamMap.values();
  }
}
