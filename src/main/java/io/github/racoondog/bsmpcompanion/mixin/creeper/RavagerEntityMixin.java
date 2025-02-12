package io.github.racoondog.bsmpcompanion.mixin.creeper;

import io.github.racoondog.bsmpcompanion.BounceSMPCompanionMod;
import net.minecraft.entity.mob.RavagerEntity;
import net.minecraft.world.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * Implement gamerule to prevent specifically mob explosions.
 * @author Crosby
 */
@Mixin(RavagerEntity.class)
public class RavagerEntityMixin {
    @Redirect(method = "tickMovement", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/GameRules;getBoolean(Lnet/minecraft/world/GameRules$Key;)Z"))
    private boolean checkGamerule(GameRules instance, GameRules.Key<GameRules.BooleanRule> rule) {
        return instance.getBoolean(rule) && !instance.getBoolean(BounceSMPCompanionMod.CREEPER_NO_BLOWY);
    }
}
