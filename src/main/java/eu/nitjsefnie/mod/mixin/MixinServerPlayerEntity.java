package eu.nitjsefnie.mod.mixin;

import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Debug(export = true)
@Mixin(ServerPlayerEntity.class)
public class MixinServerPlayerEntity {
    @Inject(method = "copyFrom(Lnet/minecraft/server/network/ServerPlayerEntity;Z)V",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/server/network/ServerPlayerEntity;setScore(I)V",
                    shift = At.Shift.AFTER))
    private void copyFromAddEffects(ServerPlayerEntity oldPlayer, boolean alive, CallbackInfo ci) {
        ((ServerPlayerEntity) (Object) this).getActiveStatusEffects().putAll(oldPlayer.getActiveStatusEffects());
    }
}
