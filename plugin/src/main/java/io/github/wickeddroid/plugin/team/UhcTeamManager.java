package io.github.wickeddroid.plugin.team;

import io.github.wickeddroid.api.game.UhcGame;
import io.github.wickeddroid.api.team.UhcTeam;
import io.github.wickeddroid.plugin.message.MessageHandler;
import io.github.wickeddroid.plugin.message.Messages;
import io.github.wickeddroid.plugin.player.UhcPlayerRegistry;
import io.github.wickeddroid.plugin.util.MessageUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import team.unnamed.inject.InjectAll;

import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

@InjectAll
public class UhcTeamManager {

  private UhcGame uhcGame;
  private Messages messages;
  private MessageHandler messageHandler;
  private UhcTeamHandler uhcTeamHandler;
  private UhcTeamRegistry uhcTeamRegistry;
  private UhcPlayerRegistry uhcPlayerRegistry;

  public void createTeam(
          final Player leader,
          String name
  ) {
    final var uhcPlayer = this.uhcPlayerRegistry.getPlayer(leader.getName());

    if (!this.uhcGame.isTeamEnabled()) {
      this.messageHandler.send(leader, this.messages.team().teamHasNotEnabled());
      return;
    }

    if (this.getTeamByLeader(leader.getName()) != null) {
      this.messageHandler.send(leader, this.messages.team().alreadyExists());
      return;
    }

    if (name == null || name.isEmpty()) {
      name = String.format("%s team", leader.getName());
    }

    this.uhcTeamRegistry.createTeam(
            leader.getName(),
            name
    );

    this.messageHandler.send(leader, this.messages.team().create(), name);

    uhcPlayer.setUhcTeam(this.getTeamByLeader(leader.getName()));
  }

  public void removeTeam(final Player leader) {
    final var uhcTeam = this.getTeamByPlayer(leader.getName());

    if (uhcTeam == null) {
      this.messageHandler.send(leader, this.messages.team().doesNotExist());
      return;
    }

    if (!uhcTeam.getLeader().equalsIgnoreCase(leader.getName())) {
      this.messageHandler.send(leader, this.messages.team().leaderAsMember());
      return;
    }

    for (final var member : uhcTeam.getMembers()) {
      final var player = Bukkit.getPlayer(member);

      if (player == null || !player.isOnline()) {
        return;
      }

      this.messageHandler.send(player, this.messages.team().leave(), uhcTeam.getName());
      this.uhcPlayerRegistry.getPlayer(member).setUhcTeam(null);
    }

    uhcTeam.getTeam().unregister();

    this.messageHandler.send(leader, this.messages.team().remove());
    this.uhcTeamRegistry.removeTeam(leader.getName());
  }

  public void randomizeTeams(
          final Player sender,
          final double teamSize
  ) {
    if (teamSize > 1) {
      final var playersWithoutTeam = Bukkit.getOnlinePlayers()
              .stream()
              .filter(player -> this.getTeamByPlayer(player.getName()) == null)
              .collect(Collectors.toCollection(ArrayList::new));

      Collections.shuffle(playersWithoutTeam);

      final var playersWithoutTeamSize = playersWithoutTeam.size();
      final var teamsNeeded = (int) Math.ceil(playersWithoutTeamSize / teamSize) + 1;

      for (int i = 0;i<teamsNeeded;i++) {
        final var leader = playersWithoutTeam.remove(0);

        this.createTeam(leader, null);

        final var uhcTeam = this.getTeamByPlayer(leader.getName());

        if (uhcTeam != null) {
          for (var j = 0;j<teamSize - 1;j++) {
            final var member = playersWithoutTeam.remove(0);

            this.uhcTeamHandler.addPlayerToTeam(leader, member, true);
          }
        }
      }
    }
  }

  public void changeTeamSetting(
          final Player player) {

  }

  public void sendMessageTeam(
          final Player player,
          final Component message
  ) {
    final var uhcTeam = this.getTeamByPlayer(player.getName());

    if (uhcTeam == null) {
      return;
    }

    for (final var memberName : uhcTeam.getMembers()) {
      final var member = Bukkit.getPlayer(memberName);

      if (member == null || !member.isOnline()) {
        return;
      }

      member.playSound(member.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 1.0f, 1.0f);

      member.sendMessage(MessageUtil.parseStringToComponent(
              "<red>[Team] <white><player></white> <red>➣</red> ",
              Placeholder.parsed("player", player.getName())
      ).append(message.color(TextColor.color(255, 255, 255))));
    }
  }

  public UhcTeam getTeamByLeader(final String leader) {
    return this.uhcTeamRegistry.getTeam(leader);
  }

  public UhcTeam getTeamByPlayer(final String player) {
    final var uhcPlayer = this.uhcPlayerRegistry.getPlayer(player);

    return uhcPlayer == null ? null : uhcPlayer.getUhcTeam();
  }
}
