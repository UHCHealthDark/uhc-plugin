package io.github.wickeddroid.plugin.loader;

import io.github.wickeddroid.api.loader.Loader;
import io.github.wickeddroid.plugin.command.CommandPlayer;
import io.github.wickeddroid.plugin.command.CommandTeam;
import io.github.wickeddroid.plugin.command.CommandUhcStaff;
import io.github.wickeddroid.plugin.command.flow.UhcCommandModule;
import io.github.wickeddroid.plugin.scenario.ScenarioRegistration;
import me.fixeddev.commandflow.annotated.AnnotatedCommandTreeBuilder;
import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.builder.AnnotatedCommandBuilderImpl;
import me.fixeddev.commandflow.annotated.part.PartInjector;
import me.fixeddev.commandflow.annotated.part.defaults.DefaultsModule;
import me.fixeddev.commandflow.bukkit.BukkitCommandManager;
import me.fixeddev.commandflow.bukkit.factory.BukkitModule;
import team.unnamed.inject.InjectAll;
import team.unnamed.inject.Injector;

@InjectAll
public class CommandLoader implements Loader {

  private Injector injector;

  private CommandPlayer commandPlayer;
  private CommandTeam commandTeam;
  private CommandUhcStaff commandUhcStaff;

  @Override
  public void load() {
    registerCommands(
            commandPlayer,
            commandTeam,
            commandUhcStaff
    );
  }

  private void registerCommands(CommandClass... commandClasses) {
    final var partInjector = PartInjector.create();
    partInjector.install(new DefaultsModule());
    partInjector.install(new BukkitModule());
    partInjector.install(new UhcCommandModule());

    final var treeBuilder = AnnotatedCommandTreeBuilder.create(
            new AnnotatedCommandBuilderImpl(partInjector),
            ((clazz, parent) -> this.injector.getInstance(clazz))
    );
    final var commandManager = new BukkitCommandManager("uhc");

    for (final var commandClass : commandClasses) {
      commandManager.registerCommands(treeBuilder.fromClass(commandClass));
    }
  }
}
