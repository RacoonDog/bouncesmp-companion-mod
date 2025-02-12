package io.github.racoondog.bsmpcompanion.mixin.altar;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import party.lemons.biomemakeover.block.blockentity.AltarBlockEntity;

import java.util.List;
import java.util.stream.Stream;

/**
 * Prevent curses from the Mantori mod from being applied in the altar.
 * These curses have effects that are beneficial.
 * @author Crosby
 */
@Mixin(value = AltarBlockEntity.class, remap = false)
public class AltarBlockEntityMixin {
    @Redirect(method = "getRandomCurse", at = @At(value = "INVOKE", target = "Ljava/util/stream/Stream;toList()Ljava/util/List;"))
    private static <T extends Enchantment> List<T> filterMantoriCurses(Stream<T> instance) {
        return instance.filter(enchantment -> {
            Identifier id = Registry.ENCHANTMENT.getId(enchantment);
            assert id != null;

            return !id.getNamespace().equals("mantori");
        }).toList();
    }
}
