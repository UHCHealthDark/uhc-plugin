package io.github.wickeddroid.api.game;

public enum UhcTimeFormat {
  SECONDS("segundo(s)", 1),
  MINUTES("minuto(s)", 60),
  HOURS("hora(s)", 3600),
  DAYS("día(s)", 86400);

  private final String format;
  private final int multiplier;

  UhcTimeFormat(String format, int multiplier) {
    this.format = format;
    this.multiplier = multiplier;
  }

  public String getFormat() {
    return format;
  }

  public int getMultiplier() {
    return multiplier;
  }
}
