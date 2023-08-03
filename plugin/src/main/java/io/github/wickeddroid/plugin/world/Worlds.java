package io.github.wickeddroid.plugin.world;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ConfigSerializable
public class Worlds {

  private String seed = "";
  private String worldName = "uhc_world";
  private int worldBorder = 2000;
  private List<String> blacklist = new ArrayList<>(Collections.singleton("world"));
  private boolean hardcore = true;
  private boolean naturalRegeneration = false;

  public @NonNull List<String> blacklist() {
    return this.blacklist;
  }

  public int worldBorder() {
    return this.worldBorder;
  }

  public @NonNull String worldName() {
    return this.worldName;
  }

  public @NonNull String seed() {
    return this.seed;
  }

  public boolean naturalRegeneration() {
    return this.naturalRegeneration;
  }

  public boolean hardcore() {
    return this.hardcore;
  }
}
