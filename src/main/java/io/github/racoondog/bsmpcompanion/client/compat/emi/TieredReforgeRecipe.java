package io.github.racoondog.bsmpcompanion.client.compat.emi;

import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Environment(EnvType.CLIENT)
public record TieredReforgeRecipe(Item item, Ingredient repairIngredient) implements EmiRecipe {
    private static final EmiTexture BUTTON_TEXTURE = new EmiTexture(new Identifier("tiered", "textures/gui/reforging_screen.png"), 176, 0, 18, 18);

    @Override
    public EmiRecipeCategory getCategory() {
        return BSMPCompanionEmiPlugin.REFORGE_CATEGORY;
    }

    @Override
    public @NotNull Identifier getId() {
        return new Identifier("tiered", Registry.ITEM.getId(item()).getNamespace());
    }

    @Override
    public List<EmiIngredient> getInputs() {
        return List.of(
            EmiIngredient.of(repairIngredient()),
            EmiStack.of(item()),
            EmiStack.of(Items.AMETHYST_SHARD)
        );
    }

    @Override
    public List<EmiStack> getOutputs() {
        return List.of(EmiStack.of(item()));
    }

    @Override
    public int getDisplayWidth() {
        return 88;
    }

    @Override
    public int getDisplayHeight() {
        return 40;
    }

    @Override
    public boolean supportsRecipeTree() {
        return false;
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        widgets.addTexture(BUTTON_TEXTURE, 34, 22);

        widgets.addSlot(EmiIngredient.of(repairIngredient()), 0, 13);
        widgets.addSlot(EmiStack.of(item()), 35, 0);
        widgets.addSlot(EmiStack.of(Items.AMETHYST_SHARD), 70, 13);
    }
}
