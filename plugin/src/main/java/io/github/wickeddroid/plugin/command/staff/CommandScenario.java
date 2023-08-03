package io.github.wickeddroid.plugin.command.staff;

import io.github.wickeddroid.api.scenario.GameScenario;
import io.github.wickeddroid.plugin.menu.scenario.ScenariosInventory;
import io.github.wickeddroid.plugin.scenario.ScenarioManager;
import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import org.bukkit.entity.Player;
import team.unnamed.inject.Inject;

@Command(names = "scenario")
public class CommandScenario implements CommandClass {

  @Inject
  private ScenariosInventory scenariosInventory;
  @Inject
  private ScenarioManager scenarioManager;

  @Command(names = "")
  public void enable(
          final @Sender Player sender
  ) {
    sender.openInventory(scenariosInventory.createInventory());
  }
}
