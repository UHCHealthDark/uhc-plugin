package io.github.wickeddroid.plugin.module;

import io.github.wickeddroid.api.loader.Loader;
import io.github.wickeddroid.plugin.loader.*;
import team.unnamed.inject.AbstractModule;

public class LoaderModule extends AbstractModule {

  @Override
  protected void configure() {
    bindLoader("default-loader", DefaultLoader.class);
    bindLoader("command-loader", CommandLoader.class);
    bindLoader("listener-loader", ListenerLoader.class);
    bindLoader("world-loader", WorldLoader.class);
    bindLoader("scenario-loader", ScenarioLoader.class);
    bindLoader("team-loader", TeamLoader.class);
    bindLoader("redis-loader", RedisLoader.class);
  }

  private void bindLoader(
          final String name,
          final Class<? extends Loader> clazz
  ) {
    bind(Loader.class)
            .named(name)
            .to(clazz);
  }
}
