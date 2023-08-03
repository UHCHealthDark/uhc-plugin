package io.github.wickeddroid.plugin.loader;

import io.github.wickeddroid.api.loader.Loader;
import io.github.wickeddroid.plugin.world.WorldGenerator;
import io.github.wickeddroid.plugin.world.Worlds;
import org.bukkit.Bukkit;
import team.unnamed.inject.Inject;

import java.io.File;
import java.io.IOException;

public class WorldLoader implements Loader {

  @Inject private Worlds worlds;
  @Inject private WorldGenerator worldGenerator;

  @Override
  public void load() {
    for (final var world : Bukkit.getWorlds()) {
      if (this.worlds.blacklist().contains(world.getName())) {
        continue;
      }

      this.worldGenerator.applySettings(world);
    }

    this.worldGenerator.createWorld(this.worlds.worldName());
  }

  @Override
  public void unload() {
    for (final var world : Bukkit.getWorlds()) {
      if (world == null) {
        continue;
      }

      if (this.worlds.blacklist().contains(world.getName())) {
        continue;
      }

      deleteDirectory("advancements");
      deleteDirectory("playerdata");
      deleteDirectory("stats");

      try {
        this.worldGenerator.removeWorld(world);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  private void deleteDirectory(String name) {
    var world = Bukkit.getWorld("world");

    if (world != null) {
      var files = new File(world.getWorldFolder().getAbsolutePath() + "/" + name + "/").listFiles();

      if (files != null) {
        for (var file : files) {
          file.delete();
        }
      }
    }
  }
}
