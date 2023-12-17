package eu.nitjsefni.mod.mixin;

import eu.nitjsefni.mod.enchantments.NitjsEnchantments;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;

@Mixin(FishingBobberEntity.class)
public class MixinFishingBobberEntity {

    @Inject(method = "use", at = @At(
            ordinal = 1,
            target = "Lnet/minecraft/advancement/criterion/FishingRodHookedCriterion;trigger(Lnet/minecraft/server/network/ServerPlayerEntity;Lnet/minecraft/item/ItemStack;Lnet/minecraft/entity/projectile/FishingBobberEntity;Ljava/util/Collection;)V",
            value = "INVOKE"),
            locals = LocalCapture.CAPTURE_FAILHARD
    )
    private void use(ItemStack usedItem, CallbackInfoReturnable<Integer> cir, PlayerEntity player, int damage,
                     LootContextParameterSet lootContextParameterSet, LootTable lootTable, List<ItemStack> list) {
        // if used item has enchantment
        int level = EnchantmentHelper.getLevel(NitjsEnchantments.MULTI_ROD, usedItem);
        World world = player.getWorld();
        // extend itemstack list with new items
        for (int i = 0; i < level; i++) {
            int random = world.random.nextInt(3);
            if (random == 0) {
                list.addAll(lootTable.generateLoot(lootContextParameterSet));
            }
        }
    }
}
