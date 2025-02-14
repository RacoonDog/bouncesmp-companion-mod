package io.github.racoondog.bsmpcompanion;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.minecraft.world.GameRules;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BounceSMPCompanionMod implements ModInitializer {
	public static final String MOD_ID = "bouncesmp-companion-mod";
	public static final GameRules.Key<GameRules.BooleanRule> CREEPER_NO_BLOWY = GameRuleRegistry.register("creeperNoBlowyBlocks", GameRules.Category.MOBS, GameRuleFactory.createBooleanRule(false));
	public static final Logger LOGGER = LoggerFactory.getLogger("BounceSMP Companion Mod");

	@Override
	public void onInitialize() {
	}
}