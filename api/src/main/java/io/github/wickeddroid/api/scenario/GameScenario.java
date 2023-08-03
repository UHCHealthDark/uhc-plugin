package io.github.wickeddroid.api.scenario;

import org.bukkit.Material;

public class GameScenario {

  private final String name;
  private final String key;
  private final String[] description;
  private final Material material;
  private boolean experimental;
  private boolean enabled;

  public GameScenario() {
    if (this.getClass().isAnnotationPresent(Scenario.class)) {
      final var annotation = this.getClass().getAnnotation(Scenario.class);

      this.name = annotation.name();
      this.key = annotation.key();
      this.description = annotation.description();
      this.material = annotation.material();
      this.experimental = annotation.experimental();
      this.enabled = false;
    } else if (this.getClass().isAnnotationPresent(Setting.class)) {
      final var annotation = this.getClass().getAnnotation(Setting.class);

      this.name = annotation.name();
      this.key = annotation.key();
      this.description = annotation.description();
      this.material = annotation.material();
      this.enabled = false;
    } else {
      throw new IllegalArgumentException("Has error ocurred with Scenario " + this.getClass().getSimpleName());
    }
  }

  public String getName() {
    return this.name;
  }

  public String getKey() {
    return this.key;
  }

  public String[] getDescription() {
    return this.description;
  }

  public Material getMaterial() {
    return this.material;
  }

  public boolean isExperimental() {
    return this.experimental;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  public boolean isEnabled() {
    return enabled;
  }
}
