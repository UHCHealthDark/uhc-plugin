package io.github.wickeddroid.plugin.game;

import io.github.wickeddroid.api.game.UhcGame;
import io.github.wickeddroid.api.game.UhcGameState;
import io.github.wickeddroid.api.game.UhcTimeFormat;
import io.github.wickeddroid.plugin.message.MessageHandler;
import io.github.wickeddroid.plugin.message.Messages;
import io.github.wickeddroid.plugin.message.title.Titles;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import team.unnamed.inject.InjectAll;

@InjectAll
public class UhcGameHandler {

  private Titles titles;
  private UhcGame uhcGame;
  private Messages messages;
  private MessageHandler messageHandler;

  public void changePvp(final boolean pvp) {
    for (final var world : Bukkit.getWorlds()) {
      if (world == null) {
        continue;
      }

      world.setPVP(pvp);
    }

    if (pvp) {
      for (final var player : Bukkit.getOnlinePlayers()) {
        player.showTitle(this.titles.pvpTitle());
      }
    }

    this.uhcGame.setUhcGameState(UhcGameState.PVP);
    this.uhcGame.setPvp(pvp);
  }

  public void changeTimePvp(
          final @Sender Player sender,
          final int time,
          final UhcTimeFormat uhcTimeFormat
  ) {
    if (time < 0) {
      this.messageHandler.send(sender, this.messages.staff().invalidTime());
      return;
    }

    this.uhcGame.setTimeForPvp(time * uhcTimeFormat.getMultiplier());
    this.messageHandler.send(sender, this.messages.staff().changePvpTime(),
            String.valueOf(time),
            uhcTimeFormat.getFormat());
  }

  public void changeMeetupPvp(
          final @Sender Player sender,
          final int time,
          final UhcTimeFormat uhcTimeFormat
  ) {
    if (time < 0) {
      this.messageHandler.send(sender, this.messages.staff().invalidTime());
      return;
    }

    this.uhcGame.setTimeForMeetup(time * uhcTimeFormat.getMultiplier());
    this.messageHandler.send(sender, this.messages.staff().changeMeetupTime(),
            String.valueOf(time),
            uhcTimeFormat.getFormat());  }
}
