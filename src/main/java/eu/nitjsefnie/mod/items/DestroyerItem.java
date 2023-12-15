package eu.nitjsefnie.mod.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;

public class DestroyerItem extends Item {
    public DestroyerItem() {
        super(new Item.Settings().maxCount(1));
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        BlockPos pos = context.getBlockPos();
        ItemStack stack = context.getStack();
        if (!stack.hasNbt()) {
            setTarget(stack, pos);
            return ActionResult.SUCCESS;
        }

        int[] targetPos = stack.getNbt().getIntArray("target");
        BlockPos target = new BlockPos(targetPos[0], targetPos[1], targetPos[2]);
        if (target.equals(pos)) {
            context.getWorld().breakBlock(pos, false);
            stack.setNbt(null);
        } else {
            setTarget(stack, pos);
        }

        return ActionResult.SUCCESS;
    }

    public static void setTarget(ItemStack itemStack, BlockPos target) {
        if (!itemStack.hasNbt()) itemStack.setNbt(new NbtCompound());
        itemStack.getNbt().putIntArray("target", new int[]{target.getX(), target.getY(), target.getZ()});
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return stack.hasNbt();
    }
}
