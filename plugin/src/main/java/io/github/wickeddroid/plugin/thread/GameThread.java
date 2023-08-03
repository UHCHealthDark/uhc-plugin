package io.github.wickeddroid.plugin.thread;

import io.github.wickeddroid.api.game.UhcGame;
import io.github.wickeddroid.plugin.game.UhcGameHandler;
import io.github.wickeddroid.plugin.game.UhcGameManager;
import io.github.wickeddroid.plugin.world.Worlds;
import org.bukkit.Bukkit;
import team.unnamed.inject.Inject;
import team.unnamed.inject.InjectAll;
import team.unnamed.inject.Singleton;

@InjectAll
@Singleton
public class GameThread implements Runnable {

  private Worlds worlds;
  private UhcGame uhcGame;
  private UhcGameHandler uhcGameHandler;
  private UhcGameManager uhcGameManager;

  @Override
  public void run() {
    final var currentTime = (int) (Math.floor((System.currentTimeMillis() - this.uhcGame.getStartTime()) / 1000.0));

    this.uhcGame.setCurrentTime(currentTime);

    if (currentTime == this.uhcGame.getTimeForPvp()) {
      this.uhcGameHandler.changePvp(true);
    }

    if (currentTime == this.uhcGame.getTimeForMeetup()) {
      this.uhcGameManager.startMeetup();
    }

    if (currentTime >= this.uhcGame.getTimeForMeetup()) {
      final var world = Bukkit.getWorld(this.worlds.worldName());

      if (world == null) {
        return;
      }

      this.uhcGame.setWorldBorder((int) world.getWorldBorder().getSize() / 2);
    }
  }
}
