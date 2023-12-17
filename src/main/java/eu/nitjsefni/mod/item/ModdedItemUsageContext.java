package eu.nitjsefni.mod.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.world.World;

/**
 *
 * @author jotty
 *
 */
public class ModdedItemUsageContext extends ItemUsageContext {

	public ModdedItemUsageContext(World world, PlayerEntity player, Hand hand, ItemStack stack, BlockHitResult hit) {
		super(world, player, hand, stack, hit);
	}

}
