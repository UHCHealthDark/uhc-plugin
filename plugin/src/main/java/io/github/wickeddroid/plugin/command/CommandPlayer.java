package io.github.wickeddroid.plugin.command;

import io.github.wickeddroid.api.game.UhcGame;
import io.github.wickeddroid.plugin.game.UhcGameHandler;
import io.github.wickeddroid.plugin.menu.scenario.ScenariosEnabledInventory;
import io.github.wickeddroid.plugin.message.MessageHandler;
import io.github.wickeddroid.plugin.message.Messages;
import io.github.wickeddroid.plugin.player.UhcPlayerRegistry;
import io.github.wickeddroid.plugin.scenario.ScenarioManager;
import io.github.wickeddroid.plugin.team.UhcTeamManager;
import io.github.wickeddroid.plugin.util.MessageUtil;
import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import team.unnamed.inject.InjectAll;

@InjectAll
public class CommandPlayer implements CommandClass {

  private ScenariosEnabledInventory scenariosInventory;
  private UhcPlayerRegistry uhcPlayerRegistry;
  private ScenarioManager scenarioManager;
  private UhcGameHandler uhcGameHandler;
  private UhcTeamManager uhcTeamManager;
  private MessageHandler messageHandler;
  private Messages messages;
  private UhcGame uhcGame;

  @Command(names = {"teamchat", "tc", "chat", "c"})
  public void teamChat(final @Sender Player sender) {
    final var uhcPlayer = this.uhcPlayerRegistry.getPlayer(sender.getName());

    if (uhcPlayer == null) {
      return;
    }

    if (this.uhcTeamManager.getTeamByPlayer(uhcPlayer.getName()) == null) {
      this.messageHandler.send(sender, this.messages.team().doesNotExist());
      return;
    }

    uhcPlayer.setTeamChat(!uhcPlayer.isTeamChat());

    this.messageHandler.send(sender, uhcPlayer.isTeamChat()
            ? this.messages.other().teamChatOn()
            : this.messages.other().teamChatOff());
  }

  @Command(names = {"team-location", "tl"})
  public void teamLocation(final @Sender Player sender) {
    final var uhcPlayer = this.uhcPlayerRegistry.getPlayer(sender.getName());
    final var location = sender.getLocation();

    if (uhcPlayer == null) {
      return;
    }

    if (this.uhcTeamManager.getTeamByPlayer(uhcPlayer.getName()) == null) {
      this.messageHandler.send(sender, this.messages.team().doesNotExist());
      return;
    }

    this.uhcTeamManager.sendMessageTeam(sender, MessageUtil.parseStringToComponent(
            "Ubicación de <player>: X: <x> <gray>|</gray> Y: <y> <gray>|</gray> Z: <z> <gray>|</gray> Mundo: <world>",
            Placeholder.unparsed("player", sender.getName()),
            Placeholder.unparsed("x", String.valueOf(location.getBlockX())),
            Placeholder.unparsed("y", String.valueOf(location.getBlockY())),
            Placeholder.unparsed("z", String.valueOf(location.getBlockZ())),
            Placeholder.unparsed("world", location.getWorld().getEnvironment().name())
    ));
  }

  @Command(names = "cleanitem")
  public void cleanItem(@Sender Player sender) {
    final var inventory = sender.getInventory();
    final var item = inventory.getItemInMainHand();

     if (!this.uhcGame.isCleanItem()) {
       this.messageHandler.send(sender, this.messages.other().cleanItemDisabled());
       return;
     }

    if (item.getType() != Material.AIR) {
      if (item.getEnchantments().size() > 0 || item.getType() == Material.ENCHANTED_BOOK) {
        inventory.removeItem(inventory.getItemInMainHand());

        item.getEnchantments().forEach((enchantment, integer) -> item.removeEnchantment(enchantment));

        inventory.addItem((item.getType() == Material.ENCHANTED_BOOK) ?
                        new ItemStack(Material.BOOK) :
                        item);

        sender.playSound(sender.getLocation(), Sound.BLOCK_GRINDSTONE_USE, 1.0F, 1.0F);
      }
    }
  }

  @Command(names = "cobbleonly")
  public void cobbleOnly() {
    
  }

  @Command(names = "scenarios")
  public void scenarios(final @Sender Player sender) {
    sender.openInventory(scenariosInventory.createInventory());
  }
}
