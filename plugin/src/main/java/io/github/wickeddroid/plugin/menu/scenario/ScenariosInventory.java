package io.github.wickeddroid.plugin.menu.scenario;

import io.github.wickeddroid.api.scenario.GameScenario;
import io.github.wickeddroid.api.util.item.ItemBuilder;
import io.github.wickeddroid.plugin.menu.UhcInventory;
import io.github.wickeddroid.plugin.scenario.ScenarioManager;
import io.github.wickeddroid.plugin.util.MessageUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import team.unnamed.gui.menu.item.ItemClickable;
import team.unnamed.gui.menu.type.MenuInventory;
import team.unnamed.inject.Inject;

import java.util.Arrays;
import java.util.stream.Collectors;

public class ScenariosInventory extends UhcInventory {

  @Inject private ScenarioManager scenarioManager;

  public ScenariosInventory() {
    super(MessageUtil.parseStringToComponent("Scenarios"), 6);
  }

  @Override
  public Inventory createInventory() {
    final var menuInventory = MenuInventory.newPaginatedBuilder(
            GameScenario.class, title
    );

    final var entities = scenarioManager.getScenarios();

    menuInventory.entities(entities)
            .entityParser(gameScenario -> ItemClickable.builder()
                    .item(gameScenario.isEnabled() ? enableItem(gameScenario) : rawItem(gameScenario).build())
                    .action(event -> {
                      final var currentItem = event.getCurrentItem();

                      if (currentItem == null) {
                        return true;
                      }

                      if (!(event.getWhoClicked() instanceof Player player)) {
                        return true;
                      }

                      if (!gameScenario.isEnabled()) {
                        scenarioManager.enableScenario(player, gameScenario.getKey());
                        currentItem.setItemMeta(enableItem(gameScenario).getItemMeta());
                        return true;
                      }

                      scenarioManager.disableScenario(player, gameScenario.getKey());
                      currentItem.setItemMeta(rawItem(gameScenario).build().getItemMeta());
                      return true;
                    })
                    .build()
            )
            .bounds(9, 45)
            .itemIfNoNextPage(ItemClickable.onlyItem(ItemBuilder.newBuilder(Material.LIGHT_GRAY_STAINED_GLASS_PANE)
                    .name(Component.text(" "))
                    .build()))
            .itemIfNoPreviousPage(ItemClickable.onlyItem(ItemBuilder.newBuilder(Material.LIGHT_GRAY_STAINED_GLASS_PANE)
                    .name(Component.text(" "))
                    .build()))
            .nextPageItem(page -> ItemClickable.onlyItem(ItemBuilder.newBuilder(Material.ARROW)
                    .name(Component.text("Siguiente pagina - " + page))
                    .build()))
            .previousPageItem(page -> ItemClickable.onlyItem(ItemBuilder.newBuilder(Material.ARROW)
                    .name(Component.text("Anterior pagina - " + page))
                    .build()))
            .layoutLines(
                    "xxxxxxxxx",
                    "eeeeeeeee",
                    "eeeeeeeee",
                    "eeeeeeeee",
                    "eeeeeeeee",
                    "pxxxxxxxn"
            )
            .layoutItem('x', ItemClickable.onlyItem(
                    ItemBuilder.newBuilder(Material.WHITE_STAINED_GLASS_PANE)
                            .name(Component.text(" "))
                            .build())
            );

    return menuInventory.build();
  }

  private ItemStack enableItem(final GameScenario gameScenario) {
    return rawItem(gameScenario)
            .glowing(true)
            .build();
  }

  private ItemBuilder rawItem(final GameScenario gameScenario) {
    return ItemBuilder.newBuilder(gameScenario.getMaterial())
            .name(MessageUtil.parseStringToComponent("<color:#93FF9E>" + gameScenario.getName())
                    .decoration(TextDecoration.ITALIC, TextDecoration.State.FALSE))
            .lore(Arrays.stream(gameScenario.getDescription())
                    .map(lore -> MessageUtil.parseStringToComponent(lore)
                            .color(TextColor.color(255, 255, 255))
                            .decoration(TextDecoration.ITALIC, TextDecoration.State.FALSE))
                    .collect(Collectors.toList()));
  }
}
