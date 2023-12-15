package eu.nitjsefnie.mod.mixin;

import net.minecraft.network.packet.s2c.play.EntityStatusEffectS2CPacket;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Optional;

@Mixin(PlayerManager.class)
public class MixinPlayerManager {

    // Based on code analysis by PR0CESS: https://bugs.mojang.com/browse/MC-6431?focusedCommentId=1081896#comment-1081896
    @Inject(method = "respawnPlayer(Lnet/minecraft/server/network/ServerPlayerEntity;Z)Lnet/minecraft/server/network/ServerPlayerEntity;",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/server/PlayerManager;sendWorldInfo(Lnet/minecraft/server/network/ServerPlayerEntity;Lnet/minecraft/server/world/ServerWorld;)V",
                    shift = At.Shift.BEFORE),
            locals = LocalCapture.CAPTURE_FAILHARD)
    private void respawnPlayerAddEffects(ServerPlayerEntity player, boolean alive,
                                         CallbackInfoReturnable<ServerPlayerEntity> cir,
                                         BlockPos blockPos, float f, boolean bl, ServerWorld serverWorld,
                                         Optional optional, ServerWorld serverWorld2,
                                         ServerPlayerEntity serverPlayerEntity
                                         ) {
        serverPlayerEntity.getActiveStatusEffects().forEach(
                (name, eff) -> serverPlayerEntity.networkHandler.sendPacket(
                        new EntityStatusEffectS2CPacket(serverPlayerEntity.getId(), eff)
                )
        );
    }

}