package io.github.wickeddroid.plugin.loader;

import io.github.wickeddroid.api.loader.Loader;
import org.bukkit.Bukkit;

public class TeamLoader implements Loader {

  @Override
  public void load() {
    for (final var team : Bukkit.getScoreboardManager().getMainScoreboard().getTeams()) {
      team.unregister();
    }
  }
}
