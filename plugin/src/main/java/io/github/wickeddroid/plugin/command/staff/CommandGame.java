package io.github.wickeddroid.plugin.command.staff;

import io.github.wickeddroid.api.game.UhcGame;
import io.github.wickeddroid.plugin.game.UhcGameManager;
import io.github.wickeddroid.plugin.team.UhcTeamRegistry;
import io.github.wickeddroid.plugin.world.Worlds;
import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.annotated.annotation.SubCommandClasses;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import team.unnamed.inject.InjectAll;

@InjectAll
@Command(names = "game")
@SubCommandClasses({ CommandTime.class })
public class CommandGame implements CommandClass {

  private Worlds worlds;
  private Plugin plugin;
  private UhcGame uhcGame;
  private UhcTeamRegistry uhcTeamRegistry;
  private UhcGameManager uhcGameManager;

  @Command(names = "host")
  public void host(final @Sender Player sender) {
    this.uhcGame.setHost(sender.getName());
  }

  @Command(names = "start")
  public void start(final @Sender Player sender) {
    this.uhcGameManager.startGame(sender);
  }

  @Command(names = "apple-rate")
  public void appleRate(final @Sender Player sender, final int appleRate) {
    this.uhcGame.setAppleRate(appleRate);
  }

}


