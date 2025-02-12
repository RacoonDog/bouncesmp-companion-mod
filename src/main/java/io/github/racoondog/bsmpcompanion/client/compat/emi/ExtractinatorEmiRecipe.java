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
        return 144 + 10;
    }

    @Override
    public int getDisplayHeight() {
        return 144 + 15;
    }

    @Override
    public boolean supportsRecipeTree() {
        return false;
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        // Add an arrow texture to indicate processing
        //widgets.addTexture(EmiTexture.EMPTY_ARROW, 26, 1);

        // Adds an input slot on the left
        widgets.addSlot(EmiIngredient.of(recipe().input()), 69, 5);

        // Adds an output slot on the right
        // Note that output slots need to call `recipeContext` to inform EMI about their recipe context
        // This includes being able to resolve recipe trees, favorite stacks with recipe context, and more
        List<ExtractinatorRecipe.Drop> outputs = recipe().outputs();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 7; j++) {
                int index = 8 * j + i;
                if (outputs.size() > index) {
                    ExtractinatorRecipe.Drop drop = outputs.get(index);

                    EmiIngredient display = EmiIngredient.of(drop.drops().stream().map(holder -> EmiStack.of(holder.value()).setAmount(drop.maxDropCount())).toList());

                    SlotWidget widget = widgets.addSlot(display, 5 + (i * 18 + 1), j * 18 + 26).recipeContext(this);

                    if (drop.dropChance() < 1d) {
                        widget.appendTooltip(new LiteralText(String.format("Production Chance: %.1f", drop.dropChance() * 100) + "%")
                                .setStyle(Style.EMPTY.withColor(Formatting.GOLD)));
                    }
                } else {
                    widgets.addSlot(5 + (i * 18 + 1), j * 18 + 26).recipeContext(this);
                }
            }
        }
    }
}
