package io.github.wickeddroid.plugin.world;

import io.github.wickeddroid.api.game.UhcGame;
import org.apache.commons.io.FileUtils;
import org.bukkit.*;
import team.unnamed.inject.Inject;
import team.unnamed.inject.Singleton;

import java.io.IOException;

@Singleton
public class WorldGenerator {

  @Inject private Worlds worlds;
  @Inject private UhcGame uhcGame;
  private World world;

  public void createWorld(final String name) {
    final var worldCreator = new WorldCreator(name);
    final var seed = this.worlds.seed();

    if (!seed.isEmpty()) {
      worldCreator.seed(Long.parseLong(seed));
    }

    worldCreator.environment(World.Environment.NORMAL);
    worldCreator.type(WorldType.NORMAL);

    this.world = worldCreator.createWorld();

    if (this.world == null) {
      return;
    }

    this.setupWorldBorder(this.world, this.worlds.worldBorder());
    this.applySettings(this.world);
  }

  public void removeWorld(final World world) throws IOException {
    Bukkit.unloadWorld(world, false);

    FileUtils.deleteDirectory(world.getWorldFolder());
  }

  public void applySettings(final World world) {
    final var naturalRegeneration = this.worlds.naturalRegeneration();

    if (!naturalRegeneration) {
      world.setGameRule(GameRule.NATURAL_REGENERATION, false);
    }

    world.setGameRule(GameRule.DO_WEATHER_CYCLE, false);
    world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
    world.setGameRule(GameRule.DO_MOB_SPAWNING, false);
    world.setGameRule(GameRule.DO_INSOMNIA, false);

    world.setDifficulty(Difficulty.HARD);

    world.setPVP(false);
  }

  public void setupWorldBorder(
          final World world,
          final int size
  ) {
    final var worldBorder = world.getWorldBorder();
    worldBorder.setDamageAmount(1);
    worldBorder.setSize(size * 2);
    worldBorder.setCenter(0, 0);

    this.uhcGame.setWorldBorder(size);
  }

  public World getWorld() {
    return this.world;
  }
}
