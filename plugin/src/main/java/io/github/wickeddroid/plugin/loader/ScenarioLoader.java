package io.github.wickeddroid.plugin.loader;

import io.github.wickeddroid.api.loader.Loader;
import io.github.wickeddroid.plugin.scenario.ScenarioRegistration;
import io.github.wickeddroid.plugin.scenario.SettingRegistration;
import team.unnamed.inject.Inject;

public class ScenarioLoader implements Loader {

  @Inject private ScenarioRegistration scenarioRegistration;
  @Inject private SettingRegistration settingRegistration;

  @Override
  public void load() {
    this.scenarioRegistration.registerScenarios();
    this.settingRegistration.registerSettings();
  }
}
