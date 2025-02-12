package io.github.racoondog.bsmpcompanion.mixin.creeper;

import io.github.racoondog.bsmpcompanion.BounceSMPCompanionMod;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.world.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * Implement gamerule to prevent specifically mob explosions.
 * @author Crosby
 */
@Mixin(FireballEntity.class)
public class FireballEntityMixin {
    @Redirect(method = "onCollision", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/GameRules;getBoolean(Lnet/minecraft/world/GameRules$Key;)Z"))
    private boolean checkGamerule(GameRules instance, GameRules.Key<GameRules.BooleanRule> rule) {
        return instance.getBoolean(rule) && !instance.getBoolean(BounceSMPCompanionMod.CREEPER_NO_BLOWY);
    }
}
