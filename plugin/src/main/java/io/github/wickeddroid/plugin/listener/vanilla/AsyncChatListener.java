package io.github.wickeddroid.plugin.listener.vanilla;

import io.github.wickeddroid.api.game.UhcGame;
import io.github.wickeddroid.plugin.message.MessageHandler;
import io.github.wickeddroid.plugin.message.Messages;
import io.github.wickeddroid.plugin.player.UhcPlayerRegistry;
import io.github.wickeddroid.plugin.team.UhcTeamManager;
import io.papermc.paper.event.player.AsyncChatEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import team.unnamed.inject.InjectAll;

@InjectAll
public class AsyncChatListener implements Listener {
  private Messages messages;
  private MessageHandler messageHandler;
  private UhcPlayerRegistry uhcPlayerRegistry;
  private UhcTeamManager uhcTeamManager;
  private UhcGame uhcGame;

  @EventHandler
  public void onAsyncChat(final AsyncChatEvent event) {
    final var player = event.getPlayer();
    final var uhcPlayer = this.uhcPlayerRegistry.getPlayer(player.getName());

    if (uhcPlayer == null) {
      return;
    }

    final var uhcTeam = uhcPlayer.getUhcTeam();

    if (!uhcPlayer.isTeamChat()) {
      return;
    }

    if (uhcTeam == null) {
      uhcPlayer.setTeamChat(false);
      this.messageHandler.send(player, this.messages.team().doesNotExist());
      return;
    }

    event.setCancelled(true);

    this.uhcTeamManager.sendMessageTeam(player, event.message());
  }
}
