package io.github.racoondog.bsmpcompanion.client.compat.rei;

import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.ToolItem;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

@Environment(EnvType.CLIENT)
public class BSMPCompanionReiPlugin implements REIClientPlugin {
    public static final CategoryIdentifier<TieredReforgeDisplay> REFORGE_CATEGORY = CategoryIdentifier.of(new Identifier("tiered", "reforge"));

    @Override
    public void registerCategories(CategoryRegistry registry) {
        // Register reforging
        registry.add(new TieredReforgeCategory());
        registry.addWorkstations(REFORGE_CATEGORY, EntryStacks.of(Items.ANVIL));
    }

    @Override
    public void registerDisplays(DisplayRegistry registry) {
        // Register reforging
        for (Item item : Registry.ITEM) {
            if (item instanceof ArmorItem armorItem) {
                Ingredient repair = armorItem.getMaterial().getRepairIngredient();
                registry.add(new TieredReforgeDisplay(armorItem, repair));
            } else if (item instanceof ToolItem toolItem) {
                Ingredient repair = toolItem.getMaterial().getRepairIngredient();
                registry.add(new TieredReforgeDisplay(toolItem, repair));
            }
        }
    }
}
