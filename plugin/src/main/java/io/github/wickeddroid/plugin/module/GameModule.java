package io.github.wickeddroid.plugin.module;

import io.github.wickeddroid.api.game.UhcGame;
import io.github.wickeddroid.plugin.game.DefaultUhcGame;
import team.unnamed.inject.AbstractModule;

public class GameModule extends AbstractModule {

  @Override
  protected void configure() {
    bind(UhcGame.class)
            .to(DefaultUhcGame.class)
            .singleton();
  }
}
