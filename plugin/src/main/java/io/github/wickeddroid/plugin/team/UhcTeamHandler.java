package io.github.wickeddroid.plugin.team;

import io.github.wickeddroid.api.cache.Cache;
import io.github.wickeddroid.api.game.UhcGame;
import io.github.wickeddroid.api.game.UhcGameState;
import io.github.wickeddroid.plugin.cache.DynamicCache;
import io.github.wickeddroid.plugin.message.MessageHandler;
import io.github.wickeddroid.plugin.message.Messages;
import io.github.wickeddroid.plugin.player.UhcPlayerRegistry;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import team.unnamed.inject.Inject;
import team.unnamed.inject.InjectAll;
import team.unnamed.inject.InjectIgnore;
import team.unnamed.inject.Singleton;

import java.util.concurrent.TimeUnit;

@Singleton
@InjectAll
public class UhcTeamHandler {

  private UhcGame uhcGame;
  private Messages messages;
  private MessageHandler messageHandler;
  private UhcTeamManager uhcTeamManager;
  private UhcPlayerRegistry uhcPlayerRegistry;

  @InjectIgnore
  private final Cache<String, String> inviteCache = new DynamicCache<>(5, TimeUnit.MINUTES);

  public void addPlayerToTeam(
          final Player leader,
          final Player player,
          final boolean forceJoin
  ) {
    final var playerName = player.getName();
    final var uhcTeam = this.uhcTeamManager.getTeamByPlayer(leader.getName());
    final var uhcPlayer = this.uhcPlayerRegistry.getPlayer(playerName);
    final var teamInvite = this.inviteCache.get(player.getName());

    if (uhcTeam == null) {
      this.messageHandler.send(player, this.messages.team().playerDoesNotTeamExist());
      return;
    }

    if (!forceJoin && (teamInvite == null || !teamInvite.equals(uhcTeam.getName()))) {
      this.messageHandler.send(player, this.messages.team().inviteDoesNotExist(), uhcTeam.getName());
      return;
    }

    if (this.uhcTeamManager.getTeamByPlayer(player.getName()) != null) {
      this.messageHandler.send(player, this.messages.team().alreadyExists());
      return;
    }

    this.messageHandler.send(leader, this.messages.team().joinPlayer(), playerName);
    this.messageHandler.send(player, this.messages.team().join(), uhcTeam.getName());
    this.inviteCache.invalidate(playerName);

    uhcTeam.incrementPlayersAlive();

    uhcPlayer.setUhcTeam(uhcTeam);
    uhcTeam.addMember(playerName);
  }

  public void removePlayerOfTeam(
          final Player player
  ) {
    final var playerName = player.getName();

    final var uhcTeam = this.uhcTeamManager.getTeamByPlayer(playerName);
    final var uhcPlayer = this.uhcPlayerRegistry.getPlayer(playerName);

    if (uhcTeam == null) {
      this.messageHandler.send(player, this.messages.team().doesNotExist());
      return;
    }

    final var leaderName = uhcTeam.getLeader();
    final var leader = Bukkit.getPlayer(leaderName);

    if (leaderName.equals(playerName)) {
      this.messageHandler.send(player, this.messages.team().leaveAsLeader());
      return;
    }

    if (leader != null && leader.isOnline()) {
      this.messageHandler.send(leader, this.messages.team().leavePlayer(), playerName);
    }

    this.messageHandler.send(player, this.messages.team().leave(), uhcTeam.getName());
    uhcPlayer.setUhcTeam(null);
    uhcTeam.removeMember(playerName);
  }

  public void invitePlayerToTeam(
          final Player leader,
          final Player player
  ) {
    final var playerName = player.getName();
    final var uhcTeam = this.uhcTeamManager.getTeamByLeader(leader.getName());
    final var invite = this.inviteCache.get(playerName);

    if (this.uhcGame.getUhcGameState() != UhcGameState.WAITING) {
      this.messageHandler.send(leader, this.messages.other().gameHasStarted());
      return;
    }

    if (leader.getName().equals(playerName)) {
      this.messageHandler.send(leader, this.messages.team().inviterEqualsPlayer());
      return;
    }

    if (invite != null) {
      this.messageHandler.send(leader, this.messages.team().leaderCancelInvite(), playerName);
      this.messageHandler.send(player, this.messages.team().cancelInvite(), playerName);
      this.inviteCache.invalidate(playerName);
      return;
    }

    if (uhcTeam == null) {
      this.messageHandler.send(leader, this.messages.team().doesNotExist());
      return;
    }

    if (this.uhcTeamManager.getTeamByPlayer(player.getName()) != null) {
      this.messageHandler.send(leader, this.messages.team().playerTeamExist());
      return;
    }

    if (!leader.getName().equals(uhcTeam.getLeader())) {
      this.messageHandler.send(leader, this.messages.team().leaderAsMember());
      return;
    }

    this.messageHandler.send(leader, this.messages.team().leaderInvitePlayer(), playerName);
    this.messageHandler.send(player, this.messages.team().invitePlayer(), leader.getName());
    this.inviteCache.save(playerName, uhcTeam.getName());
  }
}
@Singleton
@InjectAll
public class UhcTeamHandler {

  private UhcGame uhcGame;
  private Messages messages;
  private MessageHandler messageHandler;
  private UhcTeamManager uhcTeamManager;
  private UhcPlayerRegistry uhcPlayerRegistry;

  @InjectIgnore
  private final Cache<String, String> inviteCache = new DynamicCache<>(5, TimeUnit.MINUTES);

  public void addPlayerToTeam(
          final Player leader,
          final Player player,
          final boolean forceJoin
  ) {
    final var playerName = player.getName();
    final var uhcTeam = this.uhcTeamManager.getTeamByPlayer(leader.getName());
    final var uhcPlayer = this.uhcPlayerRegistry.getPlayer(playerName);
    final var teamInvite = this.inviteCache.get(player.getName());

    if (uhcTeam == null) {
      this.messageHandler.send(player, this.messages.team().playerDoesNotTeamExist());
      return;
    }

    if (!forceJoin && (teamInvite == null || !teamInvite.equals(uhcTeam.getName()))) {
      this.messageHandler.send(player, this.messages.team().inviteDoesNotExist(), uhcTeam.getName());
      return;
    }

    if (this.uhcTeamManager.getTeamByPlayer(player.getName()) != null) {
      this.messageHandler.send(player, this.messages.team().alreadyExists());
      return;
    }

    this.messageHandler.send(leader, this.messages.team().joinPlayer(), playerName);
    this.messageHandler.send(player, this.messages.team().join(), uhcTeam.getName());
    this.inviteCache.invalidate(playerName);

    uhcTeam.incrementPlayersAlive();

    uhcPlayer.setUhcTeam(uhcTeam);
    uhcTeam.addMember(playerName);
  }

  public void removePlayerOfTeam(
          final Player player
  ) {
    final var playerName = player.getName();

    final var uhcTeam = this.uhcTeamManager.getTeamByPlayer(playerName);
    final var uhcPlayer = this.uhcPlayerRegistry.getPlayer(playerName);

    if (uhcTeam == null) {
      this.messageHandler.send(player, this.messages.team().doesNotExist());
      return;
    }

    final var leaderName = uhcTeam.getLeader();
    final var leader = Bukkit.getPlayer(leaderName);

    if (leaderName.equals(playerName)) {
      this.messageHandler.send(player, this.messages.team().leaveAsLeader());
      return;
    }

    if (leader != null && leader.isOnline()) {
      this.messageHandler.send(leader, this.messages.team().leavePlayer(), playerName);
    }

    this.messageHandler.send(player, this.messages.team().leave(), uhcTeam.getName());
    uhcPlayer.setUhcTeam(null);
    uhcTeam.removeMember(playerName);
  }

  public void invitePlayerToTeam(
          final Player leader,
          final Player player
  ) {
    final var playerName = player.getName();
    final var uhcTeam = this.uhcTeamManager.getTeamByLeader(leader.getName());
    final var invite = this.inviteCache.get(playerName);

    if (this.uhcGame.getUhcGameState() != UhcGameState.WAITING) {
      this.messageHandler.send(leader, this.messages.other().gameHasStarted());
      return;
    }

    if (leader.getName().equals(playerName)) {
      this.messageHandler.send(leader, this.messages.team().inviterEqualsPlayer());
      return;
    }

    if (invite != null) {
      this.messageHandler.send(leader, this.messages.team().leaderCancelInvite(), playerName);
      this.messageHandler.send(player, this.messages.team().cancelInvite(), playerName);
      this.inviteCache.invalidate(playerName);
      return;
    }

    if (uhcTeam == null) {
      this.messageHandler.send(leader, this.messages.team().doesNotExist());
      return;
    }

    if (this.uhcTeamManager.getTeamByPlayer(player.getName()) != null) {
      this.messageHandler.send(leader, this.messages.team().playerTeamExist());
      return;
    }

    if (!leader.getName().equals(uhcTeam.getLeader())) {
      this.messageHandler.send(leader, this.messages.team().leaderAsMember());
      return;
    }

    this.messageHandler.send(leader, this.messages.team().leaderInvitePlayer(), playerName);
    this.messageHandler.send(player, this.messages.team().invitePlayer(), leader.getName());
    this.inviteCache.save(playerName, uhcTeam.getName());
  }
}
@Singleton
@InjectAll
public class UhcTeamHandler {

  private UhcGame uhcGame;
  private Messages messages;
  private MessageHandler messageHandler;
  private UhcTeamManager uhcTeamManager;
  private UhcPlayerRegistry uhcPlayerRegistry;

  @InjectIgnore
  private final Cache<String, String> inviteCache = new DynamicCache<>(5, TimeUnit.MINUTES);

  public void addPlayerToTeam(
          final Player leader,
          final Player player,
          final boolean forceJoin
  ) {
    final var playerName = player.getName();
    final var uhcTeam = this.uhcTeamManager.getTeamByPlayer(leader.getName());
    final var uhcPlayer = this.uhcPlayerRegistry.getPlayer(playerName);
    final var teamInvite = this.inviteCache.get(player.getName());

    if (uhcTeam == null) {
      this.messageHandler.send(player, this.messages.team().playerDoesNotTeamExist());
      return;
    }

    if (!forceJoin && (teamInvite == null || !teamInvite.equals(uhcTeam.getName()))) {
      this.messageHandler.send(player, this.messages.team().inviteDoesNotExist(), uhcTeam.getName());
      return;
    }

    if (this.uhcTeamManager.getTeamByPlayer(player.getName()) != null) {
      this.messageHandler.send(player, this.messages.team().alreadyExists());
      return;
    }

    this.messageHandler.send(leader, this.messages.team().joinPlayer(), playerName);
    this.messageHandler.send(player, this.messages.team().join(), uhcTeam.getName());
    this.inviteCache.invalidate(playerName);

    uhcTeam.incrementPlayersAlive();

    uhcPlayer.setUhcTeam(uhcTeam);
    uhcTeam.addMember(playerName);
  }

  public void removePlayerOfTeam(
          final Player player
  ) {
    final var playerName = player.getName();

    final var uhcTeam = this.uhcTeamManager.getTeamByPlayer(playerName);
    final var uhcPlayer = this.uhcPlayerRegistry.getPlayer(playerName);

    if (uhcTeam == null) {
      this.messageHandler.send(player, this.messages.team().doesNotExist());
      return;
    }

    final var leaderName = uhcTeam.getLeader();
    final var leader = Bukkit.getPlayer(leaderName);

    if (leaderName.equals(playerName)) {
      this.messageHandler.send(player, this.messages.team().leaveAsLeader());
      return;
    }

    if (leader != null && leader.isOnline()) {
      this.messageHandler.send(leader, this.messages.team().leavePlayer(), playerName);
    }

    this.messageHandler.send(player, this.messages.team().leave(), uhcTeam.getName());
    uhcPlayer.setUhcTeam(null);
    uhcTeam.removeMember(playerName);
  }

  public void invitePlayerToTeam(
          final Player leader,
          final Player player
  ) {
    final var playerName = player.getName();
    final var uhcTeam = this.uhcTeamManager.getTeamByLeader(leader.getName());
    final var invite = this.inviteCache.get(playerName);

    if (this.uhcGame.getUhcGameState() != UhcGameState.WAITING) {
      this.messageHandler.send(leader, this.messages.other().gameHasStarted());
      return;
    }

    if (leader.getName().equals(playerName)) {
      this.messageHandler.send(leader, this.messages.team().inviterEqualsPlayer());
      return;
    }

    if (invite != null) {
      this.messageHandler.send(leader, this.messages.team().leaderCancelInvite(), playerName);
      this.messageHandler.send(player, this.messages.team().cancelInvite(), playerName);
      this.inviteCache.invalidate(playerName);
      return;
    }

    if (uhcTeam == null) {
      this.messageHandler.send(leader, this.messages.team().doesNotExist());
      return;
    }

    if (this.uhcTeamManager.getTeamByPlayer(player.getName()) != null) {
      this.messageHandler.send(leader, this.messages.team().playerTeamExist());
      return;
    }

    if (!leader.getName().equals(uhcTeam.getLeader())) {
      this.messageHandler.send(leader, this.messages.team().leaderAsMember());
      return;
    }

    this.messageHandler.send(leader, this.messages.team().leaderInvitePlayer(), playerName);
    this.messageHandler.send(player, this.messages.team().invitePlayer(), leader.getName());
    this.inviteCache.save(playerName, uhcTeam.getName());
  }
}
