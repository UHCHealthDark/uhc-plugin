package io.github.wickeddroid.api.util.item;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;

public interface ItemBuilder {
  ItemBuilder name(Component component);

  ItemBuilder lore(Component... components);

  ItemBuilder lore(List<Component> components);

  ItemBuilder glowing(boolean glowing);

  ItemBuilder enchantments(Map<Enchantment, Integer> enchantmentMap);

  ItemBuilder enchantment(Enchantment enchantment, int level);

  ItemBuilder itemFlags(ItemFlag... itemFlags);

  ItemStack build();

  static ItemBuilder newBuilder(final Material material) {
    return newBuilder(material, 1);
  }

  static ItemBuilder newBuilder(
          final Material material,
          final int amount
  ) {
    return new SimpleItemBuilder(material, amount);
  }
}
