package io.github.wickeddroid.plugin.command.staff;

import io.github.wickeddroid.api.game.UhcGame;
import io.github.wickeddroid.plugin.message.MessageHandler;
import io.github.wickeddroid.plugin.message.Messages;
import io.github.wickeddroid.plugin.team.UhcTeamManager;
import io.github.wickeddroid.plugin.team.UhcTeamRegistry;
import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import team.unnamed.inject.Inject;
import team.unnamed.inject.InjectAll;

import java.util.Objects;

@InjectAll
@Command(names = "team")
public class CommandTeam implements CommandClass {

  private UhcTeamManager uhcTeamManager;
  private UhcTeamRegistry uhcTeamRegistry;
  private MessageHandler messageHandler;
  private Messages messages;
  private UhcGame uhcGame;

  @Command(names = "enable")
  public void enable(
          final @Sender Player sender,
          final boolean enable
  ) {
    this.uhcGame.setTeamEnabled(enable);

    this.messageHandler.send(sender, enable
            ? this.messages.team().enableTeam()
            : this.messages.team().disableTeam());
  }

  @Command(names = "create")
  public void create(
          final Player target
  ) {
    this.uhcTeamManager.createTeam(target, null);
  }

  @Command(names = "size")
  public void size(
          final @Sender Player sender,
          final int teamSize
  ) {
    this.uhcGame.setTeamSize(teamSize);

    this.messageHandler.send(sender, this.messages.team().changeTeamSize(), String.valueOf(teamSize));
  }

  @Command(names = "randomize")
  public void randomize(final @Sender Player sender) {
    this.uhcTeamManager.randomizeTeams(sender, this.uhcGame.getTeamSize());
  }

  @Command(names = "remove")
  public void remove(final Player target) {
    this.uhcTeamManager.removeTeam(target);
  }

  @Command(names = "remove-all")
  public void removeAll() {
    this.uhcTeamRegistry.getTeams().forEach(uhcTeam -> this.uhcTeamManager.removeTeam(
            Objects.requireNonNull(Bukkit.getPlayer(uhcTeam.getLeader()))
    ));
  }
}
