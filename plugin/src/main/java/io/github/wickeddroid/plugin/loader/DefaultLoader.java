package io.github.wickeddroid.plugin.loader;

import io.github.wickeddroid.api.loader.Loader;
import team.unnamed.inject.Inject;
import team.unnamed.inject.InjectAll;
import team.unnamed.inject.Named;

@InjectAll
public class DefaultLoader implements Loader {

  @Named("command-loader")
  private Loader commandLoader;

  @Named("listener-loader")
  private Loader listenerLoader;

  @Named("world-loader")
  private Loader worldLoader;

  @Named("scenario-loader")
  private Loader scenarioLoader;

  @Named("team-loader")
  private Loader teamLoader;

  @Named("redis-loader")
  private Loader redisLoader;

  @Override
  public void load() {
    this.worldLoader.load();
    this.commandLoader.load();
    this.listenerLoader.load();
    this.scenarioLoader.load();
    this.teamLoader.load();
    this.redisLoader.load();
  }

  @Override
  public void unload() {
    this.worldLoader.unload();
  }
}
