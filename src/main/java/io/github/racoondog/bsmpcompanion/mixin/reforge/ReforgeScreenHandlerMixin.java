package io.github.racoondog.bsmpcompanion.mixin.reforge;

import draylar.tiered.reforge.ReforgeScreenHandler;
import net.minecraft.block.AnvilBlock;
import net.minecraft.block.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(value = ReforgeScreenHandler.class, remap = false)
public class ReforgeScreenHandlerMixin {
    /**
     * @author Crosby
     * @reason Relax condition to work on any anvil.
     */
    @Overwrite
    private boolean canUse(BlockState state) {
        return state.getBlock() instanceof AnvilBlock;
    }
}