package io.github.wickeddroid.plugin.util;

import com.destroystokyo.paper.MaterialTags;
import org.bukkit.Material;

public class MaterialUtil {

  private MaterialUtil() {}

  public static boolean isTool(Material material){
    return MaterialTags.SHOVELS.isTagged(material)
            || MaterialTags.AXES.isTagged(material)
            || MaterialTags.PICKAXES.isTagged(material)
            || MaterialTags.HOES.isTagged(material)
            || material == Material.SHEARS;
  }
}
