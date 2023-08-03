package io.github.wickeddroid.plugin.command.staff;

import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import org.bukkit.World;
import org.bukkit.entity.Player;

@Command(names = "world")
public class CommandWorld implements CommandClass {

  @Command(names = "tp")
  public void tp(
          final @Sender Player sender,
          final World world
  ) {
    sender.teleport(world.getSpawnLocation());
  }
}
