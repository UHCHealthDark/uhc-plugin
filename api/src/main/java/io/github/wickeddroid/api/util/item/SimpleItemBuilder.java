package io.github.wickeddroid.api.util.item;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class SimpleItemBuilder implements ItemBuilder {

  private final Material material;
  private final int amount;
  private Component name;
  private boolean glowing;
  private Map<Enchantment, Integer> enchantments;
  private List<ItemFlag> itemFlags;
  private List<Component> lore;

  public SimpleItemBuilder(
          final Material material,
          final int amount
  ) {
    this.material = material;
    this.amount = amount;
    this.enchantments = new HashMap<>();
    this.itemFlags = new ArrayList<>();
    this.lore = new ArrayList<>();
  }

  @Override
  public ItemBuilder name(Component name) {
    this.name = name;
    return this;
  }

  @Override
  public ItemBuilder lore(Component... lore) {
    this.lore = Arrays.asList(lore);
    return this;
  }

  @Override
  public ItemBuilder lore(List<Component> lore) {
    this.lore = lore;
    return this;
  }

  @Override
  public ItemBuilder glowing(boolean glowing) {
    this.glowing = glowing;
    return this;
  }

  @Override
  public ItemBuilder enchantments(Map<Enchantment, Integer> enchantments) {
    this.enchantments = enchantments;
    return this;
  }

  @Override
  public ItemBuilder enchantment(Enchantment enchantment, int level) {
    this.enchantments.put(enchantment, level);
    return this;
  }

  @Override
  public ItemBuilder itemFlags(ItemFlag... itemFlags) {
    this.itemFlags = Arrays.asList(itemFlags);
    return this;
  }

  @Override
  public ItemStack build() {
    final var itemStack = new ItemStack(material, amount);

    itemStack.editMeta(itemMeta -> {
      if (glowing) {
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemMeta.addEnchant(Enchantment.ARROW_DAMAGE, 1, true);
      }

      itemMeta.displayName(name);
      itemMeta.lore(lore);

      this.enchantments.forEach((enchantment, level) -> itemMeta.addEnchant(enchantment, level, true));
      this.itemFlags.forEach(itemMeta::addItemFlags);
    });

    return itemStack;
  }
}
