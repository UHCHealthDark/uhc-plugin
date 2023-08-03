package io.github.wickeddroid.plugin.scoreboard;

import io.github.wickeddroid.plugin.util.MessageUtil;
import me.catcoder.sidebar.ProtocolSidebar;
import me.catcoder.sidebar.Sidebar;
import me.catcoder.sidebar.util.lang.ThrowingFunction;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public abstract class ScoreboardCreator {

  private final Sidebar<Component> sidebar;

  public ScoreboardCreator(
          final String name,
          final Plugin plugin
  ) {
    this.sidebar = ProtocolSidebar.newAdventureSidebar(
            MessageUtil.parseStringToComponent(name), plugin
    );

    this.updateLinesPeriodically(0, 20);
  }

  protected void updateLine(final ThrowingFunction<Player, Component, Throwable> line) {
    this.sidebar.addUpdatableLine(line);
  }

  public void updateLinesPeriodically(
          final int delay,
          final int period
  ) {
    this.sidebar.updateLinesPeriodically(delay, period);
  }

  public Sidebar<Component> getSidebar() {
    return this.sidebar;
  }

  protected Component replaceVariables(
          final String text,
          final Player player
  ) {
    return MessageUtil.parseStringToComponent(
            text,
            Placeholder.parsed("player", player.getName())
    );
  }
}
