package io.github.wickeddroid.plugin.module;

import io.github.wickeddroid.plugin.configuration.JsonConfigurationBuilder;
import io.github.wickeddroid.plugin.message.Messages;
import io.github.wickeddroid.plugin.message.title.Titles;
import io.github.wickeddroid.plugin.scoreboard.Scoreboard;
import io.github.wickeddroid.plugin.world.Worlds;
import net.kyori.adventure.title.Title;
import org.bukkit.plugin.Plugin;
import org.spongepowered.configurate.ConfigurateException;
import team.unnamed.inject.AbstractModule;
import team.unnamed.inject.Provides;
import team.unnamed.inject.Singleton;

import java.nio.file.Path;

public class ConfigurationModule extends AbstractModule {

  private final Path path;

  public ConfigurationModule(final Plugin plugin) {
    this.path = Path.of(plugin.getDataFolder().getAbsolutePath());
  }

  @Override
  protected void configure() {
    try {
      bind(Messages.class)
              .toInstance(JsonConfigurationBuilder
                      .load(Messages.class, path, "messages")
                      .get()
              );

      bind(Scoreboard.class)
              .toInstance(JsonConfigurationBuilder
                      .load(Scoreboard.class, path, "scoreboards")
                      .get()
              );

      bind(Worlds.class)
              .toInstance(JsonConfigurationBuilder
                      .load(Worlds.class, path, "worlds")
                      .get()
              );

      bind(Titles.class)
              .toInstance(JsonConfigurationBuilder
                      .load(Titles.class, path, "titles")
                      .get()
              );
    } catch (ConfigurateException e) {
      throw new RuntimeException(e);
    }
  }

  @Provides @Singleton
  public Messages providesMessage() {
    try {
      return JsonConfigurationBuilder.load(Messages.class, path, "messages").get();
    } catch (ConfigurateException e) {
      throw new RuntimeException("Has error occurred with the file messages.");
    }
  }
}
