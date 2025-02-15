package io.github.racoondog.bsmpcompanion.mixin.reforge;

import draylar.tiered.network.TieredServerPacket;
import draylar.tiered.reforge.ReforgeScreenHandler;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.AnvilScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.TranslatableText;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = TieredServerPacket.class, remap = false)
public class TieredServerPacketMixin {
    @Shadow
    private static void writeS2CMousePositionPacket(ServerPlayerEntity serverPlayerEntity, int mouseX, int mouseY) {
        throw new AssertionError();
    }

    /**
     * @author Crosby
     * @reason Rewrite code to fix bugs
     */
    @Overwrite
    private static void lambda$init$2(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buffer, PacketSender sender) {
        if (player == null) return;

        int mouseX = buffer.readInt();
        int mouseY = buffer.readInt();
        buffer.skipBytes(8); // skip block pos
        boolean reforgingScreen = buffer.readBoolean();

        server.execute(() -> {
            ScreenHandlerContext context;
            if (player.currentScreenHandler instanceof ForgingScreenHandlerAccessor accessor) {
                context = accessor.bsmpCompanion$getContext();
            } else if (player.currentScreenHandler instanceof ReforgeScreenHandlerAccessor accessor) {
                context = accessor.bsmpCompanion$getContext();
            } else {
                return;
            }

            if (reforgingScreen) {
                player.openHandledScreen(new SimpleNamedScreenHandlerFactory((syncId, playerInventory, playerx) -> {
                    return new ReforgeScreenHandler(syncId, playerInventory, context);
                }, new TranslatableText("container.reforge")));
            } else {
                player.openHandledScreen(new SimpleNamedScreenHandlerFactory((syncId, playerInventory, playerx) -> {
                    return new AnvilScreenHandler(syncId, playerInventory, context);
                }, new TranslatableText("container.repair")));
            }

            writeS2CMousePositionPacket(player, mouseX, mouseY);
        });
    }
}
