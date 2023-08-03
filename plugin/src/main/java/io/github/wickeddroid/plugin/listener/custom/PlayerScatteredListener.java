package io.github.wickeddroid.plugin.listener.custom;

import io.github.wickeddroid.plugin.event.game.PlayerScatteredEvent;
import io.github.wickeddroid.plugin.message.MessageHandler;
import io.github.wickeddroid.plugin.message.Messages;
import io.github.wickeddroid.plugin.player.UhcPlayerRegistry;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import team.unnamed.inject.Inject;
import team.unnamed.inject.InjectAll;

@InjectAll
public class PlayerScatteredListener implements Listener {

  private Messages messages;
  private MessageHandler messageHandler;
  private UhcPlayerRegistry uhcPlayerRegistry;

  @EventHandler
  public void onPlayerScattered(final PlayerScatteredEvent event) {
    final var player = event.getPlayer();
    final var uhcPlayer = this.uhcPlayerRegistry.getPlayer(player.getName());

    if (!event.isLaterScatter()) {
      player.addPotionEffect(PotionEffectType.SLOW.createEffect(Integer.MAX_VALUE, 255));
      player.addPotionEffect(PotionEffectType.BLINDNESS.createEffect(Integer.MAX_VALUE, 255));
      player.addPotionEffect(PotionEffectType.JUMP.createEffect(Integer.MAX_VALUE, 250));
    }

    player.setGameMode(GameMode.SURVIVAL);
    player.getInventory().clear();
    player.setFoodLevel(20);
    player.setHealth(20);
    player.setLevel(0);
    player.setExp(0);

    player.teleport(event.getLocation());

    for (final var onlinePlayer : Bukkit.getOnlinePlayers()) {
      this.messageHandler.send(onlinePlayer, this.messages.other().scattered(), player.getName());
    }

    if (uhcPlayer == null) {
      this.uhcPlayerRegistry.createPlayer(player.getUniqueId(), player.getName());
      this.uhcPlayerRegistry.getPlayer(player.getName()).setScattered(true);
      return;
    }

    uhcPlayer.setScattered(true);
  }
}
