package io.github.wickeddroid.plugin.command;

import io.github.wickeddroid.plugin.team.UhcTeamHandler;
import io.github.wickeddroid.plugin.team.UhcTeamManager;
import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.annotated.annotation.OptArg;
import me.fixeddev.commandflow.annotated.annotation.SubCommandClasses;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import org.bukkit.entity.Player;
import team.unnamed.inject.Inject;

@Command(names = "team")
public class CommandTeam implements CommandClass {

  @Inject private UhcTeamHandler uhcTeamHandler;
  @Inject private UhcTeamManager uhcTeamManager;

  @Command(names = "create")
  public void create(
          final @Sender Player sender,
          final @OptArg String name
  ) {
    this.uhcTeamManager.createTeam(sender, name);
  }

  @Command(names = "disband")
  public void disband(final @Sender Player sender) {
    this.uhcTeamManager.removeTeam(sender);
  }

  @Command(names = "invite")
  public void invite(
          final @Sender Player sender,
          final Player target
  ) {
    this.uhcTeamHandler.invitePlayerToTeam(sender, target);
  }

  @Command(names = "accept")
  public void accept(
          final @Sender Player sender,
          final Player target
  ) {
    this.uhcTeamHandler.addPlayerToTeam(target, sender, false);
  }

  @Command(names = "leave")
  public void leave(final @Sender Player sender) {
    this.uhcTeamHandler.removePlayerOfTeam(sender);
  }

  @Command(names = "force-join")
  public void forceJoin(
          final Player target,
          final Player leader
  ) {
    this.uhcTeamHandler.addPlayerToTeam(target, leader, true);
  }
}
