package io.github.wickeddroid.plugin.scenario.scenarios;

import com.destroystokyo.paper.MaterialTags;
import io.github.wickeddroid.api.scenario.Scenario;
import io.github.wickeddroid.plugin.scenario.ListenerScenario;
import io.github.wickeddroid.plugin.scenario.RegisteredScenario;
import io.github.wickeddroid.plugin.util.MaterialUtil;
import io.github.wickeddroid.plugin.util.PluginUtil;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

@RegisteredScenario
@Scenario(
        name = "CutClean",
        key = "cut_clean",
        description = "Todos los minerales y animales dropean su drop cocinado.",
        material = Material.IRON_INGOT
)
public class CutCleanScenario extends ListenerScenario {

  @EventHandler
  public void onBlockBreak(final BlockBreakEvent event) {
    final var block = event.getBlock();
    final var player = event.getPlayer();
    final var item = player.getInventory().getItemInMainHand();
    final var location = block.getLocation();
    final var world = block.getWorld();

    if (block.getType() == Material.GRAVEL) {
      event.setDropItems(false);
      world.dropItemNaturally(location, new ItemStack(Material.FLINT));
      return;
    }

    if (!MaterialTags.ORES.isTagged(block)) {
      return;
    }

    if (!MaterialUtil.isTool(item.getType()) || item.containsEnchantment(Enchantment.SILK_TOUCH)) {
      return;
    }

    event.setDropItems(false);

    switch (block.getType()) {
      case GOLD_ORE, DEEPSLATE_GOLD_ORE -> {
        world.dropItemNaturally(location, new ItemStack(Material.GOLD_INGOT));
        ((ExperienceOrb) world.spawnEntity(location, EntityType.EXPERIENCE_ORB)).setExperience(2);
      }
      case IRON_ORE, DEEPSLATE_IRON_ORE -> {
        world.dropItemNaturally(location, new ItemStack(Material.IRON_INGOT));
        ((ExperienceOrb) world.spawnEntity(location, EntityType.EXPERIENCE_ORB)).setExperience(2);
      }
      case COPPER_ORE, DEEPSLATE_COPPER_ORE -> {
        world.dropItemNaturally(location, new ItemStack(Material.COPPER_INGOT));
        ((ExperienceOrb) world.spawnEntity(location, EntityType.EXPERIENCE_ORB)).setExperience(2);
      }
      case ANCIENT_DEBRIS -> {
        world.dropItemNaturally(location, new ItemStack(Material.NETHERITE_SCRAP));
        ((ExperienceOrb) world.spawnEntity(location, EntityType.EXPERIENCE_ORB)).setExperience(4);
      }
      default -> event.setDropItems(true);
    }
  }

  @EventHandler
  public void onEntityDeath(final EntityDeathEvent event) {
    final var entity = event.getEntity();
    final var drops = event.getDrops();
    final var amount = PluginUtil.RANDOM.nextInt(3) + 1;

    if (entity instanceof Pig
            || entity instanceof Cow
            || entity instanceof Chicken
            || entity instanceof Sheep) {
      drops.clear();

      if (entity instanceof Pig) {
        drops.add(new ItemStack(Material.COOKED_PORKCHOP, amount));
      } else if (entity instanceof Cow) {
        drops.add(new ItemStack(Material.COOKED_BEEF, amount));
        drops.add(new ItemStack(Material.LEATHER, PluginUtil.RANDOM.nextInt(2)+1));
      } else if (entity instanceof Sheep) {
        drops.add(new ItemStack(Material.COOKED_MUTTON, amount));
      } else {
        drops.add(new ItemStack(Material.COOKED_CHICKEN, amount));
        drops.add(new ItemStack(Material.FEATHER, PluginUtil.RANDOM.nextInt(1)+1));
      }
    }
  }
}
