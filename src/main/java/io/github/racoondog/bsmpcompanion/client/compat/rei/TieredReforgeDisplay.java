package io.github.racoondog.bsmpcompanion.client.compat.rei;

import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;

import java.util.List;

@Environment(EnvType.CLIENT)
public record TieredReforgeDisplay(Item item, Ingredient repairIngredient) implements Display {
    @Override
    public List<EntryIngredient> getInputEntries() {
        return List.of(
            EntryIngredients.ofIngredient(repairIngredient()),
            EntryIngredients.of(item()),
            EntryIngredients.of(Items.AMETHYST_SHARD)
        );
    }

    @Override
    public List<EntryIngredient> getOutputEntries() {
        return List.of(EntryIngredients.of(item()));
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return BSMPCompanionReiPlugin.REFORGE_CATEGORY;
    }
}
