package io.github.wickeddroid.plugin.scenario;

import io.github.wickeddroid.api.scenario.GameScenario;
import io.github.wickeddroid.plugin.message.MessageHandler;
import io.github.wickeddroid.plugin.message.Messages;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;
import team.unnamed.inject.InjectAll;
import io.github.wickeddroid.plugin.scenario.SettingRegistration;

import java.util.Collection;

@InjectAll
public class SettingManager {

  private Plugin plugin;
  private Messages messages;
  private MessageHandler messageHandler;
  private SettingRegistration settingRegistration;

  public void enableSetting(
          final Player player,
          final String key
  ) {
    final var scenario = (SettingScenario) this.settingRegistration.getSettings().get(key);

    if (scenario == null) {
      this.messageHandler.send(player, this.messages.other().settingNotExists(), key);
      return;
    }

    scenario.setEnabled(true);
    Bukkit.getPluginManager().registerEvents(scenario, this.plugin);
    this.messageHandler.send(player, this.messages.other().settingEnabled(), scenario.getName());
  }

  public void disableSetting(
          final Player player,
          final String key
  ) {
    final var scenario = (SettingScenario) this.settingRegistration.getSettings().get(key);

    if (scenario == null) {
      this.messageHandler.send(player, this.messages.other().settingNotExists(), key);
      return;
    }

    scenario.setEnabled(false);
    this.messageHandler.send(player, this.messages.other().settingDisabled(), scenario.getName());
    HandlerList.unregisterAll(scenario);
  }

  public Collection<GameScenario> getSettings() {
    return this.settingRegistration.getSettings().values();
  }
}
