package io.github.racoondog.bsmpcompanion.mixin.creeper;

import io.github.racoondog.bsmpcompanion.BounceSMPCompanionMod;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

/**
 * Mixin into Creeper Overhaul
 */
@Pseudo
@Mixin(targets = "tech.thatgravyboat.creeperoverhaul.common.entity.base.BaseCreeper", remap = false)
public class BaseCreeperMixin {
    @ModifyArgs(method = "explode", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;createExplosion(Lnet/minecraft/entity/Entity;DDDFLnet/minecraft/world/explosion/Explosion$DestructionType;)Lnet/minecraft/world/explosion/Explosion;", remap = true), remap = false)
    private void checkGamerule(Args args) {
        Entity entity = args.get(0);
        World world = entity.getWorld();

        if (world.getGameRules().getBoolean(BounceSMPCompanionMod.CREEPER_NO_BLOWY)) {
            args.set(5, Explosion.DestructionType.NONE);
        }
    }
}
