package io.github.wickeddroid.plugin.util;


import com.destroystokyo.paper.MaterialSetTag;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;

import java.util.Random;

public class PluginUtil {

  public static final Random RANDOM = new Random();
  public static final String VERIFY_CHANNEL = "VERIFY";

  private PluginUtil() {

  }

  public static String formatTime(final long totalSecs){
    final var hours = (int) totalSecs / 3600;
    final var minutes = (int) (totalSecs % 3600) / 60;
    final var seconds = (int) totalSecs % 60;

    return String.format("%02d:%02d:%02d", hours, minutes, seconds);
  }
}
