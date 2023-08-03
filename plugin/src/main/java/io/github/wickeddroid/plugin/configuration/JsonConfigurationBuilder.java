package io.github.wickeddroid.plugin.configuration;

import io.github.wickeddroid.plugin.configuration.serializer.TitleSerializer;
import net.kyori.adventure.title.Title;
import org.spongepowered.configurate.BasicConfigurationNode;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.ConfigurationOptions;
import org.spongepowered.configurate.gson.GsonConfigurationLoader;
import org.spongepowered.configurate.loader.ConfigurationLoader;

import java.nio.file.Path;

public class JsonConfigurationBuilder<T> {

  private final ConfigurationLoader<BasicConfigurationNode> loader;
  private final ConfigurationNode node;
  private final Class<T> clazz;
  private final T value;

  private JsonConfigurationBuilder(
          final ConfigurationLoader<BasicConfigurationNode> loader,
          final ConfigurationNode node,
          final Class<T> clazz,
          final T value
  ) {
    this.loader = loader;
    this.node = node;
    this.clazz = clazz;
    this.value = value;
  }

  public static <T> JsonConfigurationBuilder<T> load(
          final Class<T> clazz,
          final Path path,
          final String file
  ) throws ConfigurateException {
    final var fileName = file.endsWith(".json") ? file : file + ".json";

    final var loader = GsonConfigurationLoader
            .builder()
            .defaultOptions(applyOptions())
            .path(path.resolve(fileName))
            .build();

    final var node = loader.load();
    final var configuration = node.get(clazz);

    node.set(clazz, configuration);

    loader.save(node);

    return new JsonConfigurationBuilder<>(
            loader,
            node,
            clazz,
            configuration
    );
  }

  private static ConfigurationOptions applyOptions() {
    return ConfigurationOptions.defaults()
            .serializers(builder -> builder.register(Title.class, TitleSerializer.INSTANCE))
            .shouldCopyDefaults(true);
  }

  public T get() {
    return value;
  }
}
