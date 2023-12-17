package eu.nitjsefni.mod.loot;

import net.minecraft.item.Item;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;

/**
 *
 * @author jotty
 *
 */
public class LootHelper {

	/**
	 * create a loot pool builder of the given object
	 *
	 * @return the loot pool builder
	 */
	public static final LootPool.Builder build(Integer number, Item item, float chance) {
		return LootPool.builder().rolls(ConstantLootNumberProvider.create(number))
				.conditionally(RandomChanceLootCondition.builder(chance))
				.with(ItemEntry.builder(item));
	}
}
