package eu.nitjsefni.mod.blockentity;

import java.util.ArrayList;
import java.util.List;

import eu.nitjsefni.mod.blocks.help.BlockStacker;
import eu.nitjsefni.mod.container.ScreenHandlerBlockStacker;
import eu.nitjsefni.mod.container.ImplementedInventory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 *
 * @author jotty
 *
 */
public class BlockEntityStacker extends BlockEntity implements NamedScreenHandlerFactory, ImplementedInventory {

	private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(ScreenHandlerBlockStacker.SLOTSIZE, ItemStack.EMPTY);

	public BlockEntityStacker(BlockPos blockPos, BlockState blockState) {
		super(QuickieFabricBlockEntity.STACKER_BLOCK_ENTITY, blockPos, blockState);
	}

	@Override
	public DefaultedList<ItemStack> getItems() {
		return inventory;
	}

	public List<ItemStack> getWhiteList() {
		int counter = 0;
		List<ItemStack> list = new ArrayList<>();
		for (ItemStack stack : inventory) {
			counter++;
			if (counter < 10) { // first 9 items are whitelist items
				list.add(stack);
			}
		}
		return list;
	}

	public List<ItemStack> getBlackList() {
		int counter = 0;
		List<ItemStack> list = new ArrayList<>();
		for (ItemStack stack : inventory) {
			counter++;
			if (counter > 9) { // second 9 items are blacklist items
				list.add(stack);
			}
		}
		return list;
	}

	@Override
  public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
      return new ScreenHandlerBlockStacker(syncId, playerInventory, this);
  }

  @Override
  public Text getDisplayName() {
      return Text.translatable(getCachedState().getBlock().getTranslationKey());
  }
	@Override
	public void readNbt(NbtCompound nbt) {
		super.readNbt(nbt);
		Inventories.readNbt(nbt, inventory);
	}

	@Override
	public void writeNbt(NbtCompound nbt) {
		Inventories.writeNbt(nbt, inventory);
		super.writeNbt(nbt);
	}

	public static void tick(World world, BlockPos pos, BlockState state, BlockEntityStacker entity) {
		if (!world.isClient) {
			BlockStacker block = (BlockStacker) state.getBlock();
			BlockEntity source = world.getBlockEntity(pos.offset(block.getSourceOffset()));
			BlockEntity dest = world.getBlockEntity(pos.offset(block.getDestOffset()));
			if (source instanceof LootableContainerBlockEntity lootableSource
					&& dest instanceof LootableContainerBlockEntity lootableDest
					&& !lootableSource.isEmpty()) {
				transferOneStack(lootableSource, lootableDest, entity.getWhiteList(), entity.getBlackList());
			}
		}
	}

	private static void transferOneStack(LootableContainerBlockEntity source, LootableContainerBlockEntity dest, List<ItemStack> whiteList, List<ItemStack> blackList) {
		if (hasItems(whiteList)) {
			transferOneStack(source, dest, whiteList, false);
		} else if (hasItems(blackList)) {
			transferOneStack(source, dest, blackList, true);
		} else {
			transferOneStack(source, dest, blackList, true);
		}
	}

	private static void transferOneStack(LootableContainerBlockEntity source, LootableContainerBlockEntity dest,
											List<ItemStack> specificItemStacks, boolean isBlackList) {
			List<Item> specificItems = specificItemStacks.stream().map(ItemStack::getItem).toList();
			for (int sourceSlot = 0; sourceSlot < source.size(); sourceSlot++) {
				ItemStack sourceStack = source.getStack(sourceSlot);
				if (!sourceStack.isEmpty() &&
						( (isBlackList && !specificItems.contains(sourceStack.getItem())) || (!isBlackList && specificItems.contains(sourceStack.getItem())) )
				) {
					Integer destSlot = findDestPos(dest, sourceStack);
					if (destSlot != null) {
						ItemStack destStack = dest.getStack(destSlot);
						if (destStack.isEmpty()) {
							dest.setStack(destSlot, sourceStack.copy());
							source.removeStack(sourceSlot);
							return;
						}
						int occupied = destStack.getCount();
						int free = destStack.getMaxCount() - occupied;
						int candidates = sourceStack.getCount();
						int travellers = Math.min(candidates, free);
						if (travellers > 0) {
							sourceStack.decrement(travellers);
							if (sourceStack.getCount() < 1) {
								source.removeStack(sourceSlot);
							}
							destStack.increment(travellers);
							return;
						}
					}
				}
			}
	}

	private static boolean hasItems(List<ItemStack> list) {
		boolean result = false;
		for (ItemStack stack : list) {
			result = result || (stack != null && !stack.isEmpty() && stack.getCount() > 0);
		}
		return result;
	}

	private static Integer findDestPos(LootableContainerBlockEntity lcbe, ItemStack sourceStack) {
		Integer firstEmpty = null;
		for (int counter = 0; counter < lcbe.size(); counter++) {
			ItemStack stack = lcbe.getStack(counter);
			if (sourceStack.isOf(stack.getItem())) {
				if (stack.getCount() < stack.getMaxCount()) {
					return counter;
				}
			}
			if (stack.isEmpty() && firstEmpty == null) {
				firstEmpty = counter;
			}
		}
		return firstEmpty;
	}

	@Override
	public int size() {
		return inventory.size();
	}
}
