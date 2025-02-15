package io.github.racoondog.bsmpcompanion.mixin.reforge;

import draylar.tiered.reforge.ReforgeScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = ReforgeScreenHandler.class, remap = false)
public interface ReforgeScreenHandlerAccessor {
    @Accessor("context")
    ScreenHandlerContext bsmpCompanion$getContext();
}
