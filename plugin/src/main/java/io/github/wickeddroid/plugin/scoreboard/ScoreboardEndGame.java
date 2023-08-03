package io.github.wickeddroid.plugin.scoreboard;

import org.bukkit.plugin.Plugin;

public class ScoreboardEndGame extends ScoreboardCreator {

  public ScoreboardEndGame(Scoreboard scoreboard, Plugin plugin) {
    super(scoreboard.title(), plugin);

    for (final var line : scoreboard.end().lines()) {
      this.updateLine(player -> this.replaceVariables(line, player));
    }
  }
}
