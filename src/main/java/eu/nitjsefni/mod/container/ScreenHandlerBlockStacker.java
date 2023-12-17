package eu.nitjsefni.mod.container;

import eu.nitjsefni.mod.init.RegistryManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

/**
 *
 * @author jotty
 *
 */
public class ScreenHandlerBlockStacker extends ScreenHandler {

	public static final Integer SLOTSIZE = 18;

	private final Inventory inventory;

	public ScreenHandlerBlockStacker(int syncId, PlayerInventory playerInventory) {
		this(syncId, playerInventory, new SimpleInventory(SLOTSIZE));
	}

	public ScreenHandlerBlockStacker(int syncId, PlayerInventory playerInventory, Inventory inventory) {
		super(RegistryManager.STACKER_SCREEN_HANDLER, syncId);
		checkSize(inventory, SLOTSIZE);
		this.inventory = inventory;
		inventory.onOpen(playerInventory.player);
    int m;
    int l;
    // whitelist
    for (m = 0; m < 3; ++m) {
        for (l = 0; l < 3; ++l) {
            this.addSlot(new Slot(inventory, l + m * 3, 8 + l * 18, 17 + m * 18));
        }
    }

    // blacklist
    for (m = 0; m < 3; ++m) {
      for (l = 0; l < 3; ++l) {
          this.addSlot(new Slot(inventory, l + m * 3 + 9, 116 + l * 18, 17 + m * 18));
      }
    }

    for (m = 0; m < 3; ++m) {
        for (l = 0; l < 9; ++l) {
            this.addSlot(new Slot(playerInventory, l + m * 9 + 9, 8 + l * 18, 84 + m * 18));
        }
    }
    for (m = 0; m < 9; ++m) {
        this.addSlot(new Slot(playerInventory, m, 8 + m * 18, 142));
    }
	}

	@Override
	public boolean canUse(PlayerEntity player) {
		return this.inventory.canPlayerUse(player);
	}

  @Override
	public ItemStack quickMove(PlayerEntity player, int invSlot) {
		ItemStack newStack = ItemStack.EMPTY;
		Slot slot = this.slots.get(invSlot);
		if (slot.hasStack()) {
			ItemStack originalStack = slot.getStack();
			newStack = originalStack.copy();
			if (invSlot < this.inventory.size()) {
				if (!this.insertItem(originalStack, this.inventory.size(), this.slots.size(), true)) {
					return ItemStack.EMPTY;
				}
			} else if (!this.insertItem(originalStack, 0, this.inventory.size(), false)) {
				return ItemStack.EMPTY;
			}
			if (originalStack.isEmpty()) {
				slot.setStack(ItemStack.EMPTY);
			} else {
				slot.markDirty();
			}
		}
		return newStack;
	}
}
