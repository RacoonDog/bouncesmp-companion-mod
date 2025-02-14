package io.github.racoondog.bsmpcompanion.client.compat.emi;

import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public record ChippedEmiRecipe(EmiRecipeCategory category, Identifier recipeId, EmiStack input, List<EmiStack> output) implements EmiRecipe {
    @Override
    public EmiRecipeCategory getCategory() {
        return category();
    }

    @Override
    public @NotNull Identifier getId() {
        return recipeId();
    }

    @Override
    public List<EmiIngredient> getInputs() {
        return List.of(input());
    }

    @Override
    public List<EmiStack> getOutputs() {
        return output();
    }

    @Override
    public int getDisplayWidth() {
        return 88;
    }

    @Override
    public int getDisplayHeight() {
        return 26;
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        widgets.addTexture(EmiTexture.EMPTY_ARROW, 26, 5);

        widgets.addSlot(input(), 0, 4);
        widgets.addSlot(EmiIngredient.of(output()), 62, 0).output(true).recipeContext(this);
    }
}
