package io.github.racoondog.bsmpcompanion.client.compat.emi;

import dev.alexnijjar.extractinator.Extractinator;
import dev.alexnijjar.extractinator.recipes.ExtractinatorRecipe;
import dev.alexnijjar.extractinator.registry.ModItems;
import dev.alexnijjar.extractinator.registry.ModRecipeTypes;
import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiStack;
import net.minecraft.util.Identifier;

public class BSMPCompanionEmiPlugin implements EmiPlugin {
    private static final EmiStack EXTRACTINATOR = EmiStack.of(ModItems.EXTRACTINATOR.get());
    public static final EmiRecipeCategory CATEGORY = new EmiRecipeCategory(new Identifier(Extractinator.MOD_ID, "extractinator"), EXTRACTINATOR);

    @Override
    public void register(EmiRegistry registry) {
        registry.addCategory(CATEGORY);

        registry.addWorkstation(CATEGORY, EXTRACTINATOR);

        for (ExtractinatorRecipe recipe : registry.getRecipeManager().listAllOfType(ModRecipeTypes.EXTRACTINATOR_RECIPE.get())) {
            registry.addRecipe(new ExtractinatorEmiRecipe(recipe));
        }
    }
}
