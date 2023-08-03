package io.github.wickeddroid.plugin.command.flow;

import io.github.wickeddroid.api.game.UhcTimeFormat;
import io.github.wickeddroid.plugin.command.flow.factory.UhcTimeFormatFactory;
import me.fixeddev.commandflow.annotated.part.AbstractModule;

public class UhcCommandModule extends AbstractModule {

  @Override
  public void configure() {
    this.bindFactory(UhcTimeFormat.class, new UhcTimeFormatFactory());
  }
}
