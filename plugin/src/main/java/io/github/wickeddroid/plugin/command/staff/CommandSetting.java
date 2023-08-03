package io.github.wickeddroid.plugin.command.staff;

import io.github.wickeddroid.plugin.menu.settings.SettingsInventory;
import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import org.bukkit.entity.Player;
import team.unnamed.inject.Inject;

@Command(names = "setting")
public class CommandSetting implements CommandClass {

  @Inject
  private SettingsInventory settingsInventory;

  @Command(names = "")
  public void enable(
          final @Sender Player sender
  ) {
    sender.openInventory(settingsInventory.createInventory());
  }
}
