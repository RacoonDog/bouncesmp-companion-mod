package io.github.racoondog.bsmpcompanion.client.compat.emi;

import dev.alexnijjar.extractinator.Extractinator;
import dev.alexnijjar.extractinator.recipes.ExtractinatorRecipe;
import dev.alexnijjar.extractinator.registry.ModItems;
import dev.alexnijjar.extractinator.registry.ModRecipeTypes;
import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiStack;
import net.minecraft.item.Item;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class BSMPCompanionEmiPlugin implements EmiPlugin {
    private static final EmiStack EXTRACTINATOR = EmiStack.of(ModItems.EXTRACTINATOR.get());
    public static final EmiRecipeCategory EXTRACTINATOR_CATEGORY = new EmiRecipeCategory(new Identifier(Extractinator.MOD_ID, "extractinator"), EXTRACTINATOR);

    @Override
    public void register(EmiRegistry registry) {
        // Register extractinator recipes
        registry.addCategory(EXTRACTINATOR_CATEGORY);

        registry.addWorkstation(EXTRACTINATOR_CATEGORY, EXTRACTINATOR);

        for (ExtractinatorRecipe recipe : registry.getRecipeManager().listAllOfType(ModRecipeTypes.EXTRACTINATOR_RECIPE.get())) {
            registry.addRecipe(new ExtractinatorEmiRecipe(recipe));
        }

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
        hideModFromItemList(registry, "skylorlib");
        hideModFromItemList(registry, "hookshot");
        hideModFromItemList(registry, "itemfilters");
        hideModFromItemList(registry, "immersive_aircraft");
        hideModFromItemList(registry, "staffofbuilding");
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
