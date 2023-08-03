package io.github.wickeddroid.plugin.player;

import io.github.wickeddroid.api.player.UhcPlayer;
import io.github.wickeddroid.api.team.UhcTeam;

import java.util.UUID;

public class DefaultUhcPlayer implements UhcPlayer {

  private final UUID uuid;
  private final String name;
  private UhcTeam uhcTeam;
  private int kills;
  private boolean alive;
  private boolean scattered;
  private boolean teamChat;

  public DefaultUhcPlayer(
          final UUID uuid,
          final String name
  ) {
    this.uuid = uuid;
    this.name = name;
    this.uhcTeam = null;
    this.kills = 0;
    this.alive = true;
    this.teamChat = false;
    this.scattered = false;
  }

  @Override
  public UUID getUuid() {
    return uuid;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public UhcTeam getUhcTeam() {
    return uhcTeam;
  }

  @Override
  public void setUhcTeam(UhcTeam uhcTeam) {
    this.uhcTeam = uhcTeam;
  }

  @Override
  public int getKills() {
    return kills;
  }

  @Override
  public void setKills(int kills) {
    this.kills = kills;
  }

  @Override
  public void incrementKills() {
    ++this.kills;
  }

  @Override
  public int decrementKills() {
    return --this.kills;
  }

  @Override
  public boolean isAlive() {
    return alive;
  }

  @Override
  public void setAlive(boolean alive) {
    this.alive = alive;
  }

  @Override
  public boolean isScattered() {
    return scattered;
  }

  @Override
  public void setScattered(boolean scattered) {
    this.scattered = scattered;
  }

  @Override
  public boolean isTeamChat() {
    return teamChat;
  }

  @Override
  public void setTeamChat(boolean teamChat) {
    this.teamChat = teamChat;
  }
}
