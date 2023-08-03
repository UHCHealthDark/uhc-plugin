package io.github.wickeddroid.plugin.scenario.scenarios;

import io.github.wickeddroid.api.scenario.Scenario;
import io.github.wickeddroid.api.util.item.ItemBuilder;
import io.github.wickeddroid.plugin.scenario.ListenerScenario;
import io.github.wickeddroid.plugin.scenario.RegisteredScenario;
import io.github.wickeddroid.plugin.util.MaterialUtil;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;

@RegisteredScenario
@Scenario(
        name = "HasteyBabies",
        key = "hastey_babies",
        description = "Todas las herramientas reciben Eficiencia 1 e Irrompibilidad 1",
        material = Material.IRON_PICKAXE
)
public class HasteyBabiesScenario extends ListenerScenario {

  @EventHandler
  public void onPrepareItemCraft(final PrepareItemCraftEvent event) {
    final var recipe = event.getRecipe();

    if (recipe == null) {
      return;
    }

    final var item = recipe.getResult();

    if (!MaterialUtil.isTool(item.getType())) {
      return;
    }

    event.getInventory().setResult(
            ItemBuilder.newBuilder(item.getType())
                    .enchantment(Enchantment.DIG_SPEED, 1)
                    .enchantment(Enchantment.DURABILITY, 1)
                    .build()
    );
  }

  @EventHandler
  public void onInventoryClick(final InventoryClickEvent event) {
    final var item = event.getCurrentItem();

    if (item == null
            || item.hasItemMeta()
            || !MaterialUtil.isTool(item.getType())
            || (item.containsEnchantment(Enchantment.DIG_SPEED) || item.containsEnchantment(Enchantment.DURABILITY))) {
      return;
    }


    event.setCurrentItem(
            ItemBuilder.newBuilder(item.getType())
                    .enchantment(Enchantment.DIG_SPEED, 1)
                    .enchantment(Enchantment.DURABILITY, 1)
                    .build()
    );
  }
}
