package io.github.racoondog.bsmpcompanion.mixin.reforge.client;

import draylar.tiered.network.TieredClientPacket;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

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

    /**
     * Prevent crash by writing a dummy value instead of a null value.
     * @author Crosby
     */
    @ModifyArg(method = "writeC2SScreenPacket", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/PacketByteBuf;writeBlockPos(Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/network/PacketByteBuf;", remap = true))
    private static BlockPos preventCrash(BlockPos pos) {
        return BlockPos.ORIGIN; // use dummy value
    }
}
