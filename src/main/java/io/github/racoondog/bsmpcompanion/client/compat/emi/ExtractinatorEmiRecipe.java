package io.github.racoondog.bsmpcompanion.client.compat.emi;

import dev.alexnijjar.extractinator.recipes.ExtractinatorRecipe;
import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.SlotWidget;
import dev.emi.emi.api.widget.WidgetHolder;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Style;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public record ExtractinatorEmiRecipe(ExtractinatorRecipe recipe) implements EmiRecipe {
    @Override
    public EmiRecipeCategory getCategory() {
        return BSMPCompanionEmiPlugin.EXTRACTINATOR_CATEGORY;
    }

    @Override
    public @NotNull Identifier getId() {
        return recipe().getId();
    }

    @Override
    public List<EmiIngredient> getInputs() {
        return List.of(EmiIngredient.of(recipe().input()));
    }

    @Override
    public List<EmiStack> getOutputs() {
        return recipe().getOutputs().stream().map(i -> EmiStack.of(i.getMatchingStacks()[0].getItem())).toList();
    }

    @Override
    public int getDisplayWidth() {
        return 144;
    }

    @Override
    public int getDisplayHeight() {
        List<ExtractinatorRecipe.Drop> outputs = recipe().outputs();
        int rows = Math.min(MathHelper.ceil(outputs.size() / 8d), 7);
        return 26 + rows * 18;
    }

    @Override
    public boolean supportsRecipeTree() {
        return false;
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        // Adds an input slot on the left
        widgets.addSlot(EmiIngredient.of(recipe().input()), 63, 5);

        // Adds an output slot on the right
        // Note that output slots need to call `recipeContext` to inform EMI about their recipe context
        // This includes being able to resolve recipe trees, favorite stacks with recipe context, and more
        List<ExtractinatorRecipe.Drop> outputs = recipe().outputs();
        int rows = Math.min(MathHelper.ceil(outputs.size() / 8d), 7);
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < rows; j++) {
                int index = 8 * j + i;
                if (outputs.size() > index) {
                    ExtractinatorRecipe.Drop drop = outputs.get(index);

                    EmiIngredient display = EmiIngredient.of(drop.drops().stream().map(holder -> EmiStack.of(holder.value()).setAmount(drop.maxDropCount())).toList());

                    SlotWidget widget = widgets.addSlot(display, i * 18, j * 18 + 26).recipeContext(this);

                    if (drop.dropChance() < 1d) {
                        widget.appendTooltip(new LiteralText(String.format("Production Chance: %.1f", drop.dropChance() * 100) + "%")
                                .setStyle(Style.EMPTY.withColor(Formatting.GOLD)));
                    }
                } else {
                    widgets.addSlot(i * 18, j * 18 + 26).recipeContext(this);
                }
            }
        }
    }
}
