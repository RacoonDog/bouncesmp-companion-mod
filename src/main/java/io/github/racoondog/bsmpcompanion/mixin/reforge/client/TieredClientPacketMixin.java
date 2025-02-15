package io.github.racoondog.bsmpcompanion.mixin.reforge.client;

import draylar.tiered.network.TieredClientPacket;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Environment(EnvType.CLIENT)
@Mixin(value = TieredClientPacket.class, remap = false)
public class TieredClientPacketMixin {
    /**
     * @author Crosby
     * @reason Prevent sending unused packet to save on bandwidth.
     */
    @Overwrite
    public static void writeC2SSyncPosPacket(Boolean reforgeHandler) {
        // do nothing
    }
}
