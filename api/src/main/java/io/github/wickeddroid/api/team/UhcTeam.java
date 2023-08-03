package io.github.wickeddroid.api.team;

import org.bukkit.scoreboard.Team;

import java.util.List;

public interface UhcTeam {

  String getLeader();

  void setLeader(String leader);

  void incrementKills();

  void decrementKills();

  int getKills();

  void setKills(int kills);

  void incrementPlayersAlive();

  void decrementPlayersAlive();

  int getPlayersAlive();

  void setPlayersAlive(int playersAlive);

  String getName();

  void setName(String name);

  boolean isAlive();

  void setAlive(boolean alive);

  void addMember(String name);

  void removeMember(String name);

  List<String> getMembers();

  Team getTeam();
}
