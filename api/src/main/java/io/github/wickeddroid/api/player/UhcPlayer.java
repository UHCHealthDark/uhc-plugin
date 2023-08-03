package io.github.wickeddroid.api.player;

import io.github.wickeddroid.api.team.UhcTeam;

import java.util.UUID;

public interface UhcPlayer {

  UUID getUuid();
  String getName();
  UhcTeam getUhcTeam();
  void setUhcTeam(UhcTeam uhcTeam);
  int getKills();
  void setKills(int kills);
  void incrementKills();
  int decrementKills();
  boolean isAlive();
  void setAlive(boolean alive);
  boolean isScattered();
  void setScattered(boolean scattered);
  boolean isTeamChat();
  void setTeamChat(boolean teamChat);
}
