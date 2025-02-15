package io.github.racoondog.bsmpcompanion.client.compat.emi;

import com.glisco.numismaticoverhaul.client.gui.shop.ShopScreen;
import dev.alexnijjar.extractinator.Extractinator;
import dev.alexnijjar.extractinator.recipes.ExtractinatorRecipe;
import dev.alexnijjar.extractinator.registry.ModItems;
import dev.alexnijjar.extractinator.registry.ModRecipeTypes;
import dev.emi.emi.EmiUtil;
import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.EmiCraftingRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.recipe.EmiWorldInteractionRecipe;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.Bounds;
import dev.emi.emi.mixin.accessor.HandledScreenAccessor;
import draylar.tiered.reforge.ReforgeScreen;
import earth.terrarium.chipped.recipe.ChippedRecipe;
import earth.terrarium.chipped.registry.ModRecipeSerializers;
import io.github.apace100.apoli.component.PowerHolderComponent;
import io.github.apace100.apoli.power.RecipePower;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.AnvilScreen;
import net.minecraft.client.gui.screen.ingame.CraftingScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.ToolItem;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.ShapelessRecipe;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import website.skylorbeck.minecraft.tokenablefurnaces.Declarer;
import website.skylorbeck.minecraft.tokenablefurnaces.Screenhandlers.*;

import java.util.List;
import java.util.function.Supplier;

@Environment(EnvType.CLIENT)
public class BSMPCompanionEmiPlugin implements EmiPlugin {
    private static final EmiStack EXTRACTINATOR = EmiStack.of(ModItems.EXTRACTINATOR.get());
    public static final EmiRecipeCategory EXTRACTINATOR_CATEGORY = new EmiRecipeCategory(new Identifier(Extractinator.MOD_ID, "extractinator"), EXTRACTINATOR);

    private static final EmiStack ANVIL = EmiStack.of(Items.ANVIL);
    public static final EmiRecipeCategory REFORGE_CATEGORY = new EmiRecipeCategory(new Identifier("tiered", "reforge"), ANVIL);

    @Override
    public void register(EmiRegistry registry) {
        /* --- Register recipes --- */

        // Register Extractinator recipes
        registry.addCategory(EXTRACTINATOR_CATEGORY);
        registry.addWorkstation(EXTRACTINATOR_CATEGORY, EXTRACTINATOR);

        for (ExtractinatorRecipe recipe : registry.getRecipeManager().listAllOfType(ModRecipeTypes.EXTRACTINATOR_RECIPE.get())) {
            registry.addRecipe(new ExtractinatorEmiRecipe(recipe));
        }

        // Register Tiered reforging
        registry.addCategory(REFORGE_CATEGORY);
        registry.addWorkstation(REFORGE_CATEGORY, ANVIL);

        for (Item item : Registry.ITEM) {
            if (item instanceof ArmorItem armorItem) {
                Ingredient repair = armorItem.getMaterial().getRepairIngredient();
                registry.addRecipe(new TieredReforgeRecipe(armorItem, repair));
            } else if (item instanceof ToolItem toolItem) {
                Ingredient repair = toolItem.getMaterial().getRepairIngredient();
                registry.addRecipe(new TieredReforgeRecipe(toolItem, repair));
            }
        }

        // Show recipes based on origin
        PowerHolderComponent.getPowers(MinecraftClient.getInstance().player, RecipePower.class)
            .forEach(recipePower -> {
                Recipe<CraftingInventory> recipe = recipePower.getRecipe();

                List<EmiIngredient> inputs = recipe.getIngredients().stream()
                    .map(EmiIngredient::of).toList();

                EmiStack output = EmiStack.of(recipe.getOutput());

                registry.addRecipe(new EmiCraftingRecipe(inputs, output, recipe.getId(), recipe instanceof ShapelessRecipe));
            });

        // Show Chipped recipes
        registerChippedWorkbench(registry, "botanist_workbench", ModRecipeSerializers.BOTANIST_WORKBENCH_SERIALIZER);
        registerChippedWorkbench(registry, "glassblower", ModRecipeSerializers.GLASSBLOWER_SERIALIZER);
        registerChippedWorkbench(registry, "carpenters_table", ModRecipeSerializers.CARPENTERS_TABLE_SERIALIZER);
        registerChippedWorkbench(registry, "loom_table", ModRecipeSerializers.LOOM_TABLE_SERIALIZER);
        registerChippedWorkbench(registry, "mason_table", ModRecipeSerializers.MASON_TABLE_SERIALIZER);
        registerChippedWorkbench(registry, "alchemy_bench", ModRecipeSerializers.ALCHEMY_BENCH_SERIALIZER);
        registerChippedWorkbench(registry, "mechanist_workbench", ModRecipeSerializers.MECHANIST_WORKBENCH_SERIALIZER);

        // Show Tokenable Furnaces upgrading
        registerTokenableFurnacesUpgradeTree(registry, Items.BARREL, Declarer.ironBarrel, Declarer.goldBarrel, Declarer.diamondBarrel, Declarer.netheriteBarrel, Declarer.amethystBarrel);
        registerTokenableFurnacesUpgradeTree(registry, Items.BLAST_FURNACE, Declarer.ironBlast, Declarer.goldBlast, Declarer.diamondBlast, Declarer.netheriteBlast, Declarer.amethystBlast);
        registerTokenableFurnacesUpgradeTree(registry, Items.CHEST, Declarer.ironChest, Declarer.goldChest, Declarer.diamondChest, Declarer.netheriteChest, Declarer.amethystChest);
        registerTokenableFurnacesUpgradeTree(registry, Items.FURNACE, Declarer.ironFurnace, Declarer.goldFurnace, Declarer.diamondFurnace, Declarer.netheriteFurnace, Declarer.amethystFurnace);
        registerTokenableFurnacesUpgradeTree(registry, Items.HOPPER, Declarer.ironHopper, Declarer.goldHopper, Declarer.diamondHopper, Declarer.netheriteHopper, Declarer.amethystHopper);
        registerTokenableFurnacesUpgradeTree(registry, Items.SHULKER_BOX, Declarer.ironShulker, Declarer.goldShulker, Declarer.diamondShulker, Declarer.netheriteShulker, Declarer.amethystShulker);
        registerTokenableFurnacesUpgradeTree(registry, Items.SMOKER, Declarer.ironSmoker, Declarer.goldSmoker, Declarer.diamondSmoker, Declarer.netheriteSmoker, Declarer.amethystSmoker);
        registerTokenableFurnacesUpgradeTree(registry, Items.TRAPPED_CHEST, Declarer.ironTrappedChest, Declarer.goldTrappedChest, Declarer.diamondTrappedChest, Declarer.netheriteTrappedChest, Declarer.amethystTrappedChest);

        /* --- Unregister recipes ---*/

        // Hide unused
        hideTagFromItemList(registry, new Identifier("c", "disabled_upgrades"));
        hideTagFromItemList(registry, new Identifier("c", "disabled_armor"));
        hideTagFromItemList(registry, new Identifier("c", "hide_items"));
        hideTagFromItemList(registry, new Identifier("friendsandfoes", "copper_buttons"));
        hideItemFromItemList(registry, new Identifier("valley", "climbing_axe"));
        hideItemFromItemList(registry, new Identifier("spirit", "soul_pedestal"));
        hideItemFromItemList(registry, new Identifier("spirit", "crystal_pedestal"));
        hideItemFromItemList(registry, new Identifier("spirit", "pedestal"));
        hideItemFromItemList(registry, new Identifier("spirit", "soul_glass_1"));
        hideItemFromItemList(registry, new Identifier("spirit", "soul_glass_2"));
        hideItemFromItemList(registry, new Identifier("spirit", "soul_glass_3"));
        hideItemFromItemList(registry, new Identifier("spirit", "soul_glass_4"));
        hideItemFromItemList(registry, new Identifier("spirit", "soul_glass_5"));
        hideItemFromItemList(registry, new Identifier("spirit", "soul_glass_6"));
        hideItemFromItemList(registry, new Identifier("spirit", "soul_glass_7"));
        hideItemFromItemList(registry, new Identifier("spirit", "soul_glass_8"));
        hideItemFromItemList(registry, new Identifier("spirit", "soul_glass_9"));
        hideItemFromItemList(registry, new Identifier("spirit", "soul_glass_10"));
        hideItemFromItemList(registry, new Identifier("spirit", "soul_glass_11"));
        hideItemFromItemList(registry, new Identifier("spirit", "soul_glass_12"));
        hideItemFromItemList(registry, new Identifier("spirit", "soul_glass_13"));
        hideItemFromItemList(registry, new Identifier("spirit", "crude_soul_crystal"));
        hideItemFromItemList(registry, new Identifier("spirit", "soul_steel_axe"));
        hideItemFromItemList(registry, new Identifier("spirit", "soul_steel_bow"));
        hideItemFromItemList(registry, new Identifier("spirit", "soul_steel_hoe"));
        hideItemFromItemList(registry, new Identifier("spirit", "soul_steel_pickaxe"));
        hideItemFromItemList(registry, new Identifier("spirit", "soul_steel_shovel"));
        hideItemFromItemList(registry, new Identifier("spirit", "soul_steel_sword"));
        hideItemFromItemList(registry, new Identifier("spirit", "soul_steel_wand"));
        hideItemFromItemList(registry, new Identifier("goodall", "animal_crate"));
        hideItemFromItemList(registry, new Identifier("mcda", "entertainer_garb_leggings"));
        hideItemFromItemList(registry, new Identifier("mcda", "ghostly_armor_boots"));
        hideItemFromItemList(registry, new Identifier("mcda", "spelunker_armor_leggings"));
        hideItemFromItemList(registry, new Identifier("mcda", "frost_bite_armor_helmet"));
        hideItemFromItemList(registry, new Identifier("mcda", "stalwart_armor_leggings"));
        hideItemFromItemList(registry, new Identifier("mcda", "rugged_climbing_gear_helmet"));
        hideItemFromItemList(registry, new Identifier("mcda", "fox_armor_boots"));
        hideItemFromItemList(registry, new Identifier("mcda", "reinforced_mail_leggings"));
        hideItemFromItemList(registry, new Identifier("mcda", "squid_armor_helmet"));
        hideItemFromItemList(registry, new Identifier("mcda", "beenest_armor_chestplate"));
        hideItemFromItemList(registry, new Identifier("mcda", "cave_crawler_leggings"));
        hideItemFromItemList(registry, new Identifier("mcda", "living_vines_armor_leggings"));
        hideItemFromItemList(registry, new Identifier("mcda", "shadow_walker_armor_helmet"));
        hideItemFromItemList(registry, new Identifier("mcda", "turtle_armor_boots"));
        hideItemFromItemList(registry, new Identifier("mcda", "entertainer_garb_helmet"));
        hideItemFromItemList(registry, new Identifier("mcda", "nimble_turtle_armor_leggings"));
        hideItemFromItemList(registry, new Identifier("mcda", "squid_armor_chestplate"));
        hideItemFromItemList(registry, new Identifier("mcda", "shulker_armor_leggings"));
        hideItemFromItemList(registry, new Identifier("mcda", "nimble_turtle_armor_helmet"));
        hideItemFromItemList(registry, new Identifier("mcda", "teleportation_robe_chestplate"));
        hideItemFromItemList(registry, new Identifier("mcda", "stalwart_armor_chestplate"));
        hideItemFromItemList(registry, new Identifier("mcda", "wolf_armor_boots"));
        hideItemFromItemList(registry, new Identifier("mcda", "wolf_armor_leggings"));
        hideItemFromItemList(registry, new Identifier("mcda", "highland_armor_leggings"));
        hideItemFromItemList(registry, new Identifier("mcda", "climbing_gear_leggings"));
        hideItemFromItemList(registry, new Identifier("mcda", "reinforced_mail_chestplate"));
        hideItemFromItemList(registry, new Identifier("mcda", "glow_squid_armor_boots"));
        hideItemFromItemList(registry, new Identifier("mcda", "nimble_turtle_armor_chestplate"));
        hideItemFromItemList(registry, new Identifier("mcda", "sturdy_shulker_armor_chestplate"));
        hideItemFromItemList(registry, new Identifier("mcda", "spelunker_armor_boots"));
        hideItemFromItemList(registry, new Identifier("mcda", "goat_gear_boots"));
        hideItemFromItemList(registry, new Identifier("mcda", "goat_gear_leggings"));
        hideItemFromItemList(registry, new Identifier("mcda", "ghostly_armor_leggings"));
        hideItemFromItemList(registry, new Identifier("mcda", "beehive_armor_chestplate"));
        hideItemFromItemList(registry, new Identifier("mcda", "grim_armor_leggings"));
        hideItemFromItemList(registry, new Identifier("mcda", "goat_gear_chestplate"));
        hideItemFromItemList(registry, new Identifier("mcda", "thief_armor_helmet"));
        hideItemFromItemList(registry, new Identifier("mcda", "snow_armor_boots"));
        hideItemFromItemList(registry, new Identifier("mcda", "frost_bite_armor_boots"));
        hideItemFromItemList(registry, new Identifier("mcda", "snow_armor_helmet"));
        hideItemFromItemList(registry, new Identifier("mcda", "climbing_gear_helmet"));
        hideItemFromItemList(registry, new Identifier("mcda", "golden_piglin_armor_chestplate"));
        hideItemFromItemList(registry, new Identifier("mcda", "arctic_fox_armor_helmet"));
        hideItemFromItemList(registry, new Identifier("mcda", "turtle_armor_leggings"));
        hideItemFromItemList(registry, new Identifier("mcda", "highland_armor_chestplate"));
        hideItemFromItemList(registry, new Identifier("mcda", "ocelot_armor_helmet"));
        hideItemFromItemList(registry, new Identifier("mcda", "black_wolf_armor_boots"));
        hideItemFromItemList(registry, new Identifier("mcda", "ghostly_armor_helmet"));
        hideItemFromItemList(registry, new Identifier("mcda", "golden_piglin_armor_boots"));
        hideItemFromItemList(registry, new Identifier("mcda", "shulker_armor_chestplate"));
        hideItemFromItemList(registry, new Identifier("mcda", "piglin_armor_leggings"));
        hideItemFromItemList(registry, new Identifier("mcda", "shulker_armor_boots"));
        hideItemFromItemList(registry, new Identifier("mcda", "goat_gear_helmet"));
        hideItemFromItemList(registry, new Identifier("mcda", "rugged_climbing_gear_leggings"));
        hideItemFromItemList(registry, new Identifier("mcda", "cave_crawler_boots"));
        hideItemFromItemList(registry, new Identifier("mcda", "vanguard_armor_chestplate"));
        hideItemFromItemList(registry, new Identifier("mcda", "splendid_robe_leggings"));
        hideItemFromItemList(registry, new Identifier("mcda", "guards_armor_chestplate"));
        hideItemFromItemList(registry, new Identifier("mcda", "archers_boots"));
        hideItemFromItemList(registry, new Identifier("mcda", "sprout_armor_chestplate"));
        hideItemFromItemList(registry, new Identifier("mcda", "spelunker_armor_chestplate"));
        hideItemFromItemList(registry, new Identifier("mcda", "sweet_tooth_boots"));
        hideItemFromItemList(registry, new Identifier("mcda", "stalwart_armor_boots"));
        hideItemFromItemList(registry, new Identifier("mcda", "beenest_armor_leggings"));
        hideItemFromItemList(registry, new Identifier("mcda", "teleportation_robe_leggings"));
        hideItemFromItemList(registry, new Identifier("mcda", "sturdy_shulker_armor_boots"));
        hideItemFromItemList(registry, new Identifier("mcda", "sprout_armor_leggings"));
        hideItemFromItemList(registry, new Identifier("mcda", "reinforced_mail_boots"));
        hideItemFromItemList(registry, new Identifier("mcda", "sweet_tooth_leggings"));
        hideItemFromItemList(registry, new Identifier("mcda", "climbing_gear_chestplate"));
        hideItemFromItemList(registry, new Identifier("mcda", "sweet_tooth_chestplate"));
        hideItemFromItemList(registry, new Identifier("mcda", "scale_mail_boots"));
        hideItemFromItemList(registry, new Identifier("mcda", "entertainer_garb_boots"));
        hideItemFromItemList(registry, new Identifier("mcda", "glow_squid_armor_leggings"));
        hideItemFromItemList(registry, new Identifier("mcda", "fox_armor_chestplate"));
        hideItemFromItemList(registry, new Identifier("mcda", "fox_armor_helmet"));
        hideItemFromItemList(registry, new Identifier("mcda", "soul_robe_leggings"));
        hideItemFromItemList(registry, new Identifier("mcda", "archers_leggings"));
        hideItemFromItemList(registry, new Identifier("mcda", "golden_piglin_armor_leggings"));
        hideItemFromItemList(registry, new Identifier("mcda", "highland_armor_helmet"));
        hideItemFromItemList(registry, new Identifier("mcda", "black_wolf_armor_chestplate"));
        hideItemFromItemList(registry, new Identifier("mcda", "ocelot_armor_leggings"));
        hideItemFromItemList(registry, new Identifier("mcda", "sprout_armor_helmet"));
        hideItemFromItemList(registry, new Identifier("mcda", "phantom_armor_chestplate"));
        hideItemFromItemList(registry, new Identifier("mcda", "ocelot_armor_chestplate"));
        hideItemFromItemList(registry, new Identifier("mcda", "sweet_tooth_helmet"));
        hideItemFromItemList(registry, new Identifier("mcda", "turtle_armor_chestplate"));
        hideItemFromItemList(registry, new Identifier("mcda", "wolf_armor_helmet"));
        hideItemFromItemList(registry, new Identifier("mcda", "beehive_armor_helmet"));
        hideItemFromItemList(registry, new Identifier("mcda", "shulker_armor_helmet"));
        hideItemFromItemList(registry, new Identifier("mcda", "soul_robe_chestplate"));
        hideItemFromItemList(registry, new Identifier("mcda", "scale_mail_chestplate"));
        hideItemFromItemList(registry, new Identifier("mcda", "arctic_fox_armor_leggings"));
        hideItemFromItemList(registry, new Identifier("mcda", "frost_bite_armor_leggings"));
        hideItemFromItemList(registry, new Identifier("mcda", "phantom_armor_helmet"));
        hideItemFromItemList(registry, new Identifier("mcda", "piglin_armor_boots"));
        hideItemFromItemList(registry, new Identifier("mcda", "rugged_climbing_gear_chestplate"));
        hideItemFromItemList(registry, new Identifier("mcda", "grim_armor_boots"));
        hideItemFromItemList(registry, new Identifier("mcda", "entertainer_garb_chestplate"));
        hideItemFromItemList(registry, new Identifier("mcda", "troubadour_armor_chestplate"));
        hideItemFromItemList(registry, new Identifier("mcda", "black_wolf_armor_helmet"));
        hideItemFromItemList(registry, new Identifier("mcda", "guards_armor_boots"));
        hideItemFromItemList(registry, new Identifier("mcda", "golden_piglin_armor_helmet"));
        hideItemFromItemList(registry, new Identifier("mcda", "thief_armor_leggings"));
        hideItemFromItemList(registry, new Identifier("mcda", "shadow_walker_armor_leggings"));
        hideItemFromItemList(registry, new Identifier("mcda", "piglin_armor_helmet"));
        hideItemFromItemList(registry, new Identifier("mcda", "climbing_gear_boots"));
        hideItemFromItemList(registry, new Identifier("mcda", "squid_armor_boots"));
        hideItemFromItemList(registry, new Identifier("mcda", "shadow_walker_armor_chestplate"));
        hideItemFromItemList(registry, new Identifier("mcda", "arctic_fox_armor_chestplate"));
        hideItemFromItemList(registry, new Identifier("mcda", "vanguard_armor_helmet"));
        hideItemFromItemList(registry, new Identifier("mcda", "ocelot_armor_boots"));
        hideItemFromItemList(registry, new Identifier("mcda", "living_vines_armor_helmet"));
        hideItemFromItemList(registry, new Identifier("mcda", "soul_robe_boots"));
        hideItemFromItemList(registry, new Identifier("mcda", "sturdy_shulker_armor_leggings"));
        hideItemFromItemList(registry, new Identifier("mcda", "piglin_armor_chestplate"));
        hideItemFromItemList(registry, new Identifier("mcda", "sturdy_shulker_armor_helmet"));
        hideItemFromItemList(registry, new Identifier("mcda", "turtle_armor_helmet"));
        hideItemFromItemList(registry, new Identifier("mcda", "phantom_armor_boots"));
        hideItemFromItemList(registry, new Identifier("mcda", "glow_squid_armor_chestplate"));
        hideItemFromItemList(registry, new Identifier("mcda", "sprout_armor_boots"));
        hideItemFromItemList(registry, new Identifier("mcda", "spelunker_armor_helmet"));
        hideItemFromItemList(registry, new Identifier("mcda", "guards_armor_leggings"));
        hideItemFromItemList(registry, new Identifier("mcda", "highland_armor_boots"));
        hideItemFromItemList(registry, new Identifier("mcda", "reinforced_mail_helmet"));
        hideItemFromItemList(registry, new Identifier("mcda", "hunters_chestplate"));
        hideItemFromItemList(registry, new Identifier("mcda", "battle_robe_chestplate"));
        hideItemFromItemList(registry, new Identifier("mcda", "vanguard_armor_leggings"));
        hideItemFromItemList(registry, new Identifier("mcda", "archers_helmet"));
        hideItemFromItemList(registry, new Identifier("mcda", "scale_mail_leggings"));
        hideItemFromItemList(registry, new Identifier("mcda", "snow_armor_leggings"));
        hideItemFromItemList(registry, new Identifier("mcda", "archers_chestplate"));
        hideItemFromItemList(registry, new Identifier("mcda", "nimble_turtle_armor_boots"));
        hideItemFromItemList(registry, new Identifier("mcda", "living_vines_armor_boots"));
        hideItemFromItemList(registry, new Identifier("mcda", "soul_robe_helmet"));
        hideItemFromItemList(registry, new Identifier("mcda", "stalwart_armor_helmet"));
        hideItemFromItemList(registry, new Identifier("mcda", "beenest_armor_boots"));
        hideItemFromItemList(registry, new Identifier("mcda", "wolf_armor_chestplate"));
        hideItemFromItemList(registry, new Identifier("mcda", "teleportation_robe_boots"));
        hideItemFromItemList(registry, new Identifier("mcda", "beehive_armor_boots"));
        hideItemFromItemList(registry, new Identifier("mcda", "troubadour_armor_leggings"));
        hideItemFromItemList(registry, new Identifier("mcda", "troubadour_armor_boots"));
        hideItemFromItemList(registry, new Identifier("mcda", "splendid_robe_chestplate"));
        hideItemFromItemList(registry, new Identifier("mcda", "grim_armor_chestplate"));
        hideItemFromItemList(registry, new Identifier("mcda", "shadow_walker_armor_boots"));
        hideItemFromItemList(registry, new Identifier("mcda", "beehive_armor_leggings"));
        hideItemFromItemList(registry, new Identifier("mcda", "glow_squid_armor_helmet"));
        hideItemFromItemList(registry, new Identifier("mcda", "thief_armor_chestplate"));
        hideItemFromItemList(registry, new Identifier("mcda", "ghostly_armor_chestplate"));
        hideItemFromItemList(registry, new Identifier("mcda", "squid_armor_leggings"));
        hideItemFromItemList(registry, new Identifier("mcda", "cave_crawler_chestplate"));
        hideItemFromItemList(registry, new Identifier("mcda", "rugged_climbing_gear_boots"));
        hideItemFromItemList(registry, new Identifier("mcda", "cave_crawler_helmet"));
        hideItemFromItemList(registry, new Identifier("mcda", "grim_armor_helmet"));
        hideItemFromItemList(registry, new Identifier("mcda", "arctic_fox_armor_boots"));
        hideItemFromItemList(registry, new Identifier("mcda", "frost_bite_armor_chestplate"));
        hideItemFromItemList(registry, new Identifier("mcda", "black_wolf_armor_leggings"));
        hideItemFromItemList(registry, new Identifier("mcda", "living_vines_armor_chestplate"));
        hideItemFromItemList(registry, new Identifier("mcda", "teleportation_robe_helmet"));
        hideItemFromItemList(registry, new Identifier("mcda", "phantom_armor_leggings"));
        hideItemFromItemList(registry, new Identifier("mcda", "battle_robe_leggings"));
        hideItemFromItemList(registry, new Identifier("mcda", "snow_armor_chestplate"));
        hideItemFromItemList(registry, new Identifier("mcda", "fox_armor_leggings"));
        hideItemFromItemList(registry, new Identifier("mcda", "beenest_armor_helmet"));
        hideItemFromItemList(registry, new Identifier("mcda", "troubadour_armor_helmet"));
        hideItemFromItemList(registry, new Identifier("mcda", "thief_armor_boots"));
        hideItemFromItemList(registry, new Identifier("mcda", "guards_armor_helmet"));
        hideItemFromItemList(registry, new Identifier("immersive_armors", "prismarine_leggings"));
        hideItemFromItemList(registry, new Identifier("immersive_armors", "divine_boots"));
        hideItemFromItemList(registry, new Identifier("immersive_armors", "warrior_leggings"));
        hideItemFromItemList(registry, new Identifier("immersive_armors", "bone_helmet"));
        hideItemFromItemList(registry, new Identifier("immersive_armors", "wither_helmet"));
        hideItemFromItemList(registry, new Identifier("immersive_armors", "prismarine_helmet"));
        hideItemFromItemList(registry, new Identifier("immersive_armors", "warrior_helmet"));
        hideItemFromItemList(registry, new Identifier("immersive_armors", "wooden_leggings"));
        hideItemFromItemList(registry, new Identifier("immersive_armors", "slime_boots"));
        hideItemFromItemList(registry, new Identifier("immersive_armors", "heavy_leggings"));
        hideItemFromItemList(registry, new Identifier("immersive_armors", "robe_boots"));
        hideItemFromItemList(registry, new Identifier("immersive_armors", "slime_helmet"));
        hideItemFromItemList(registry, new Identifier("immersive_armors", "bone_chestplate"));
        hideItemFromItemList(registry, new Identifier("immersive_armors", "bone_boots"));
        hideItemFromItemList(registry, new Identifier("immersive_armors", "slime_chestplate"));
        hideItemFromItemList(registry, new Identifier("immersive_armors", "wither_chestplate"));
        hideItemFromItemList(registry, new Identifier("immersive_armors", "robe_leggings"));
        hideItemFromItemList(registry, new Identifier("immersive_armors", "wooden_chestplate"));
        hideItemFromItemList(registry, new Identifier("immersive_armors", "divine_helmet"));
        hideItemFromItemList(registry, new Identifier("immersive_armors", "prismarine_boots"));
        hideItemFromItemList(registry, new Identifier("immersive_armors", "robe_chestplate"));
        hideItemFromItemList(registry, new Identifier("immersive_armors", "wither_boots"));
        hideItemFromItemList(registry, new Identifier("immersive_armors", "prismarine_chestplate"));
        hideItemFromItemList(registry, new Identifier("immersive_armors", "wooden_boots"));
        hideItemFromItemList(registry, new Identifier("immersive_armors", "warrior_boots"));
        hideItemFromItemList(registry, new Identifier("immersive_armors", "heavy_boots"));
        hideItemFromItemList(registry, new Identifier("immersive_armors", "heavy_chestplate"));
        hideItemFromItemList(registry, new Identifier("immersive_armors", "slime_leggings"));
        hideItemFromItemList(registry, new Identifier("immersive_armors", "bone_leggings"));
        hideItemFromItemList(registry, new Identifier("immersive_armors", "robe_helmet"));
        hideItemFromItemList(registry, new Identifier("immersive_armors", "divine_chestplate"));
        hideItemFromItemList(registry, new Identifier("immersive_armors", "wither_leggings"));
        hideItemFromItemList(registry, new Identifier("immersive_armors", "wooden_helmet"));
        hideItemFromItemList(registry, new Identifier("immersive_armors", "warrior_chestplate"));
        hideItemFromItemList(registry, new Identifier("immersive_armors", "divine_leggings"));
        hideItemFromItemList(registry, new Identifier("immersive_armors", "heavy_helmet"));
        hideItemFromItemList(registry, new Identifier("mcdw", "bow_mechanical_shortbow"));
        hideItemFromItemList(registry, new Identifier("mcdw", "crossbow_doom_crossbow"));
        hideItemFromItemList(registry, new Identifier("mcdw", "bow_twisting_vine_bow"));
        hideItemFromItemList(registry, new Identifier("mcdw", "crossbow_dual_crossbow"));
        hideItemFromItemList(registry, new Identifier("mcdw", "bow_weeping_vine_bow"));
        hideItemFromItemList(registry, new Identifier("mcdw", "bow_haunted_bow"));
        hideItemFromItemList(registry, new Identifier("mcdw", "crossbow_shadow_crossbow"));
        hideItemFromItemList(registry, new Identifier("mcdw", "crossbow_rapid_crossbow"));
        hideItemFromItemList(registry, new Identifier("mcdw", "shield_royal_guard"));
        hideItemFromItemList(registry, new Identifier("mcdw", "bow_web_bow"));
        hideItemFromItemList(registry, new Identifier("mcdw", "crossbow_azure_seeker"));
        hideItemFromItemList(registry, new Identifier("mcdw", "bow_phantom_bow"));
        hideItemFromItemList(registry, new Identifier("mcdw", "crossbow_pride_of_the_piglins"));
        hideItemFromItemList(registry, new Identifier("mcdw", "crossbow_corrupted_crossbow"));
        hideItemFromItemList(registry, new Identifier("mcdw", "crossbow_veiled_crossbow"));
        hideItemFromItemList(registry, new Identifier("mcdw", "bow_void_bow"));
        hideItemFromItemList(registry, new Identifier("mcdw", "gauntlet_soul_fists"));
        hideItemFromItemList(registry, new Identifier("mcdw", "crossbow_slayer_crossbow"));
        hideItemFromItemList(registry, new Identifier("mcdw", "bow_purple_storm"));
        hideItemFromItemList(registry, new Identifier("mcdw", "bow_green_menace"));
        hideItemFromItemList(registry, new Identifier("mcdw", "bow_bonebow"));
        hideItemFromItemList(registry, new Identifier("mcdw", "staff_growing_staff"));
        hideItemFromItemList(registry, new Identifier("mcdw", "bow_elite_power_bow"));
        hideItemFromItemList(registry, new Identifier("mcdw", "bow_trickbow"));
        hideItemFromItemList(registry, new Identifier("mcdw", "crossbow_the_slicer"));
        hideItemFromItemList(registry, new Identifier("mcdw", "bow_shortbow"));
        hideItemFromItemList(registry, new Identifier("mcdw", "bow_twin_bow"));
        hideItemFromItemList(registry, new Identifier("mcdw", "bow_call_of_the_void"));
        hideItemFromItemList(registry, new Identifier("mcdw", "bow_hunting_bow"));
        hideItemFromItemList(registry, new Identifier("mcdw", "crossbow_soul_hunter_crossbow"));
        hideItemFromItemList(registry, new Identifier("mcdw", "bow_pink_scoundrel"));
        hideItemFromItemList(registry, new Identifier("mcdw", "bow_masters_bow"));
        hideItemFromItemList(registry, new Identifier("mcdw", "whip_whip"));
        hideItemFromItemList(registry, new Identifier("mcdw", "bow_shivering_bow"));
        hideItemFromItemList(registry, new Identifier("mcdw", "crossbow_nautical_crossbow"));
        hideItemFromItemList(registry, new Identifier("mcdw", "crossbow_imploding_crossbow"));
        hideItemFromItemList(registry, new Identifier("mcdw", "crossbow_exploding_crossbow"));
        hideItemFromItemList(registry, new Identifier("mcdw", "crossbow_harp_crossbow"));
        hideItemFromItemList(registry, new Identifier("mcdw", "crossbow_harpoon_crossbow"));
        hideItemFromItemList(registry, new Identifier("mcdw", "crossbow_cog_crossbow"));
        hideItemFromItemList(registry, new Identifier("mcdw", "bow_love_spell_bow"));
        hideItemFromItemList(registry, new Identifier("mcdw", "bow_ancient_bow"));
        hideItemFromItemList(registry, new Identifier("mcdw", "bow_winters_touch"));
        hideItemFromItemList(registry, new Identifier("mcdw", "crossbow_baby_crossbow"));
        hideItemFromItemList(registry, new Identifier("mcdw", "crossbow_lightning_harp_crossbow"));
        hideItemFromItemList(registry, new Identifier("mcdw", "crossbow_scatter_crossbow"));
        hideItemFromItemList(registry, new Identifier("mcdw", "bow_burst_gale_bow"));
        hideItemFromItemList(registry, new Identifier("mcdw", "spear_cackling_broom"));
        hideItemFromItemList(registry, new Identifier("mcdw", "bow_hunters_promise"));
        hideItemFromItemList(registry, new Identifier("mcdw", "bow_wind_bow"));
        hideItemFromItemList(registry, new Identifier("mcdw", "bow_red_snake"));
        hideItemFromItemList(registry, new Identifier("mcdw", "crossbow_spellbound_crossbow"));
        hideItemFromItemList(registry, new Identifier("mcdw", "gauntlet_gauntlet"));
        hideItemFromItemList(registry, new Identifier("mcdw", "crossbow_feral_soul_crossbow"));
        hideItemFromItemList(registry, new Identifier("mcdw", "gauntlet_maulers"));
        hideItemFromItemList(registry, new Identifier("mcdw", "crossbow_voidcaller_crossbow"));
        hideItemFromItemList(registry, new Identifier("mcdw", "crossbow_burst_crossbow"));
        hideItemFromItemList(registry, new Identifier("mcdw", "whip_vine_whip"));
        hideItemFromItemList(registry, new Identifier("mcdw", "shield_vanguard"));
        hideItemFromItemList(registry, new Identifier("mcdw", "crossbow_butterfly_crossbow"));
        hideItemFromItemList(registry, new Identifier("mcdw", "bow_nocturnal_bow"));
        hideItemFromItemList(registry, new Identifier("mcdw", "bow_lost_souls"));
        hideItemFromItemList(registry, new Identifier("mcdw", "bow_power_bow"));
        hideItemFromItemList(registry, new Identifier("mcdw", "staff_battlestaff_of_terror"));
        hideItemFromItemList(registry, new Identifier("mcdw", "bow_longbow"));
        hideItemFromItemList(registry, new Identifier("mcdw", "crossbow_heavy_crossbow"));
        hideItemFromItemList(registry, new Identifier("mcdw", "bow_echo_of_the_valley"));
        hideItemFromItemList(registry, new Identifier("mcdw", "bow_soul_bow"));
        hideItemFromItemList(registry, new Identifier("mcdw", "crossbow_auto_crossbow"));
        hideItemFromItemList(registry, new Identifier("mcdw", "bow_bubble_burster"));
        hideItemFromItemList(registry, new Identifier("mcdw", "bow_snow_bow"));
        hideItemFromItemList(registry, new Identifier("mcdw", "crossbow_soul_crossbow"));
        hideItemFromItemList(registry, new Identifier("mcdw", "bow_sabrewing"));
        hideItemFromItemList(registry, new Identifier("mcdw", "crossbow_firebolt_thrower"));
        hideItemFromItemList(registry, new Identifier("mcdw", "bow_guardian_bow"));
        hideItemFromItemList(registry, new Identifier("mcdw", "bow_bubble_bow"));
        hideItemFromItemList(registry, new Identifier("mcdw", "staff_battlestaff"));
        hideItemFromItemList(registry, new Identifier("halfdoors", "door_launcher"));
        hideItemFromItemList(registry, new Identifier("halfdoors", "door_flipper"));
        hideItemFromItemList(registry, new Identifier("halfdoors", "gold_door_nugget"));
        hideItemFromItemList(registry, new Identifier("etched", "complex_music_label"));
        hideItemFromItemList(registry, new Identifier("etched", "etched_music_disc"));
        hideItemFromItemList(registry, new Identifier("etched", "portal_radio"));
        hideModFromItemList(registry, "playdate");
        hideModFromItemList(registry, "mobscarecrow");
        hideModFromItemList(registry, "hookshot");
        hideModFromItemList(registry, "itemfilters");
        hideModFromItemList(registry, "immersive_aircraft");
        hideModFromItemList(registry, "staffofbuilding");

        /* --- Register exclusion areas --- */

        // Survival inventory trash slot
        registry.addExclusionArea(InventoryScreen.class, (screen, consumer) -> {
            int left = ((HandledScreenAccessor) screen).getX();
            int top = ((HandledScreenAccessor) screen).getY();
            int right = left + ((HandledScreenAccessor) screen).getBackgroundWidth();
            int bottom = top + ((HandledScreenAccessor) screen).getBackgroundHeight();
            consumer.accept(new Bounds(right - 32, bottom, 32, 21));
        });
        registry.addExclusionArea(CraftingScreen.class, (screen, consumer) -> {
            int left = ((HandledScreenAccessor) screen).getX();
            int top = ((HandledScreenAccessor) screen).getY();
            int right = left + ((HandledScreenAccessor) screen).getBackgroundWidth();
            int bottom = top + ((HandledScreenAccessor) screen).getBackgroundHeight();
            consumer.accept(new Bounds(right - 32, bottom, 32, 21));
        });

        // Numismatic overhaul shop screen
        registry.addExclusionArea(ShopScreen.class, (screen, consumer) -> {
            int left = ((HandledScreenAccessor) screen).getX();
            int top = ((HandledScreenAccessor) screen).getY();
            int right = left + ((HandledScreenAccessor) screen).getBackgroundWidth();

            consumer.accept(new Bounds(left - 29, top + 5, 29, 60));

            int tab = screen.getSelectedTab();
            if (tab == 0) {
                consumer.accept(new Bounds(right, top, 36, 86));
            } else {
                consumer.accept(new Bounds(right, top, 100, 54));
                consumer.accept(new Bounds(right, top + 54, 36, 60));
            }
        });

        // Tiered anvil reforge button
        registry.addExclusionArea(AnvilScreen.class, (screen, consumer) -> {
            int left = ((HandledScreenAccessor) screen).getX();
            int top = ((HandledScreenAccessor) screen).getY();
            consumer.accept(new Bounds(left, top - 23, 49, 23));
        });
        registry.addExclusionArea(ReforgeScreen.class, (screen, consumer) -> {
            int left = ((HandledScreenAccessor) screen).getX();
            int top = ((HandledScreenAccessor) screen).getY();
            consumer.accept(new Bounds(left, top - 23, 49, 23));
        });

        // Tokenable furnaces chest tabs
        registry.addExclusionArea(AmethystDoubleHandledScreen.class, (screen, consumer) -> {
            int left = ((HandledScreenAccessor) screen).getX();
            int top = ((HandledScreenAccessor) screen).getY();
            int right = left + ((HandledScreenAccessor) screen).getBackgroundWidth();
            consumer.accept(new Bounds(left - 29, top + 4, 29, 8 * 16));
            consumer.accept(new Bounds(right, top + 4, 29, 8 * 16));
        });
        registerResourceChestExclusion(registry, AmethystHandledScreen.class, 8);
        registerResourceChestExclusion(registry, DiamondHandledScreen.class, 4);
        registerResourceChestExclusion(registry, GoldHandledScreen.class, 2);
        registerResourceChestExclusion(registry, IronHandledScreen.class, 1);
    }

    private static final EmiStack OMNI_TOKEN = EmiStack.of(Declarer.omniToken),
        IRON_TOKEN = EmiStack.of(Declarer.ironToken),
        GOLD_TOKEN = EmiStack.of(Declarer.goldToken),
        DIAMOND_TOKEN = EmiStack.of(Declarer.diamondToken),
        NETHERITE_TOKEN = EmiStack.of(Declarer.netheriteToken),
        AMETHYST_TOKEN = EmiStack.of(Declarer.amethystToken);

    private static void registerTokenableFurnacesUpgradeTree(EmiRegistry registry, Item baseItem, Item ironItem, Item goldItem, Item diamondItem, Item netheriteItem, Item amethystItem) {
        EmiStack baseStack = EmiStack.of(baseItem),
            ironStack = EmiStack.of(ironItem),
            goldStack = EmiStack.of(goldItem),
            diamondStack = EmiStack.of(diamondItem),
            netheriteStack = EmiStack.of(netheriteItem),
            amethystStack = EmiStack.of(amethystItem);

        registerTokenableFurnacesUpgradeRecipes(registry, baseStack, IRON_TOKEN, ironStack);
        registerTokenableFurnacesUpgradeRecipes(registry, ironStack, GOLD_TOKEN, goldStack);
        registerTokenableFurnacesUpgradeRecipes(registry, goldStack, DIAMOND_TOKEN, diamondStack);
        registerTokenableFurnacesUpgradeRecipes(registry, diamondStack, NETHERITE_TOKEN, netheriteStack);
        registerTokenableFurnacesUpgradeRecipe(registry, diamondStack, AMETHYST_TOKEN, amethystStack);
    }

    // register both regular and omni recipe
    private static void registerTokenableFurnacesUpgradeRecipes(EmiRegistry registry, EmiStack inputStack, EmiStack tokenStack, EmiStack outputStack) {
        registerTokenableFurnacesUpgradeRecipe(registry, inputStack, tokenStack, outputStack);
        Identifier outputId = Registry.ITEM.getId(outputStack.getKeyOfType(Item.class));
        // omni token upgrade recipe
        registry.addRecipe(EmiWorldInteractionRecipe.builder()
            .id(new Identifier("tokenablefurnaces", "omni_upgrade/" + outputId.getPath()))
            .leftInput(inputStack)
            .rightInput(OMNI_TOKEN, true)
            .output(outputStack)
            .supportsRecipeTree(false)
            .build()
        );
    }

    // register only regular recipe
    private static void registerTokenableFurnacesUpgradeRecipe(EmiRegistry registry, EmiStack inputStack, EmiStack tokenStack, EmiStack outputStack) {
        Identifier outputId = Registry.ITEM.getId(outputStack.getKeyOfType(Item.class));
        // regular token upgrade recipe
        registry.addRecipe(EmiWorldInteractionRecipe.builder()
            .id(new Identifier("tokenablefurnaces", "upgrade/" + outputId.getPath()))
            .leftInput(inputStack)
            .rightInput(tokenStack, false)
            .output(outputStack)
            .build()
        );
    }

    private static <T extends Screen> void registerResourceChestExclusion(EmiRegistry registry, Class<T> clazz, int tabs) {
        registry.addExclusionArea(clazz, (screen, consumer) -> {
            int left = ((HandledScreenAccessor) screen).getX();
            int top = ((HandledScreenAccessor) screen).getY();
            consumer.accept(new Bounds(left - 29, top + 4, 29, tabs * 16));
        });
    }

    private static void registerChippedWorkbench(EmiRegistry registry, String workbench, Supplier<ChippedRecipe.Serializer> serializer) {
        Identifier id = new Identifier("chipped", workbench);
        EmiStack workbenchStack = EmiStack.of(Registry.ITEM.get(id));
        EmiRecipeCategory category = new EmiRecipeCategory(id, workbenchStack);

        registry.addCategory(category);
        registry.addWorkstation(category, workbenchStack);

        for (ChippedRecipe recipe : registry.getRecipeManager().listAllOfType(serializer.get().type.get())) {
            // get all tags in recipe as TagKey
            // distinct because 'mason_table.json' contains duplicate entries
            recipe.tags().stream().map(entryList -> entryList.getStorage().orThrow()).distinct().forEach(tag -> {
                // get all items in stack
                List<EmiStack> outputs = EmiUtil.values(tag).map(RegistryEntry::value).map(EmiStack::of).toList();
                outputs.forEach(stack -> {
                    Identifier inputId = Registry.ITEM.getId(stack.getKeyOfType(Item.class));
                    // chipped:workbench/input_id/output_tag_id
                    Identifier recipeId = new Identifier("chipped", workbench + "/" + inputId.getPath() + "/" + tag.id().getPath());
                    registry.addRecipe(new ChippedEmiRecipe(category, recipeId, stack, outputs));
                });
            });
        }
    }

    private static void hideTagFromItemList(EmiRegistry registry, Identifier tagIdentifier) {
        TagKey<Item> tag = TagKey.of(Registry.ITEM_KEY, tagIdentifier);
        registry.removeEmiStacks(emiStack -> emiStack.getItemStack().isIn(tag));
    }

    private static void hideItemFromItemList(EmiRegistry registry, Identifier itemIdentifier) {
        registry.removeEmiStacks(emiStack -> emiStack.getId().equals(itemIdentifier));
    }

    private static void hideModFromItemList(EmiRegistry registry, String modId) {
        registry.removeEmiStacks(emiStack -> emiStack.getId().getNamespace().equals(modId));
    }
}
