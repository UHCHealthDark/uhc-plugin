package io.github.wickeddroid.plugin.scoreboard;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;

import java.util.ArrayList;
import java.util.List;

@ConfigSerializable
public class Scoreboard {

  private String title = "<rainbow>UHC";
  private Lobby lobby = new Lobby();
  private Lobby game = new Lobby();
  private Lobby end = new Lobby();

  public @NonNull String title() {
    return title;
  }

  public @NonNull Lobby lobby() {
    return this.lobby;
  }

  public @NonNull Lobby game() {
    return this.game;
  }

  public @NonNull Lobby end() {
    return this.end;
  }

  @ConfigSerializable
  public static class Lobby {
    private List<String> lines = new ArrayList<>();

    public @NonNull List<String> lines() {
      return lines;
    }
  }

  @ConfigSerializable
  public static class Game {
    private List<String> lines = new ArrayList<>();

    public @NonNull List<String> lines() {
      return lines;
    }
  }

  @ConfigSerializable
  public static class End {
    private List<String> lines = new ArrayList<>();

    public @NonNull List<String> lines() {
      return lines;
    }
  }
}
