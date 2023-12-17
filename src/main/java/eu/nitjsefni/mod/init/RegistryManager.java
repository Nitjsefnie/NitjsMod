package eu.nitjsefni.mod.init;

import eu.nitjsefni.mod.blockentity.BlockEntityStacker;
import eu.nitjsefni.mod.blockentity.NitjsBlockEntities;
import eu.nitjsefni.mod.blocks.NitjsBlocks;
import eu.nitjsefni.mod.container.ScreenHandlerBlockStacker;
import eu.nitjsefni.mod.enchantments.NitjsEnchantments;
import eu.nitjsefni.mod.items.NitjsItems;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RegistryManager {
	private static final Logger LOGGER = LogManager.getLogger(RegistryManager.class);

	public static final String MOD_ID = "nitjsefni";
	public static final Identifier STACKER_IDENTIFIER = new Identifier(MOD_ID, "stacker");
	public static final ScreenHandlerType<ScreenHandlerBlockStacker> STACKER_SCREEN_HANDLER = ScreenHandlerRegistry
			.registerSimple(RegistryManager.STACKER_IDENTIFIER, ScreenHandlerBlockStacker::new);

	public static final RegistryKey<ItemGroup> MOD_GROUP = RegistryKey.of(RegistryKeys.ITEM_GROUP,
			new Identifier(MOD_ID, "itemgroups"));

	public static void registerItemGroup() {
		Registry.register(Registries.ITEM_GROUP, MOD_GROUP,
				FabricItemGroup.builder().icon(() -> new ItemStack(NitjsBlocks.STACKER_UP))
						.displayName(Text.literal(MOD_ID)).entries((enabledFeatures, stacks) -> {
							stacks.add(new ItemStack(NitjsBlocks.STACKER_UP));
							stacks.add(new ItemStack(NitjsBlocks.STACKER_DOWN));
							stacks.add(new ItemStack(NitjsBlocks.STACKER_EAST));
							stacks.add(new ItemStack(NitjsBlocks.STACKER_WEST));
							stacks.add(new ItemStack(NitjsBlocks.STACKER_NORTH));
							stacks.add(new ItemStack(NitjsBlocks.STACKER_SOUTH));
						}).build());
	}

	private static void registerBlock(Block block, String name) {
		Registry.register(Registries.BLOCK, new Identifier(MOD_ID, name), block);
		Registry.register(Registries.ITEM, new Identifier(MOD_ID, name),
				new BlockItem(block, new FabricItemSettings()));
	}

	public static <T extends BlockEntity> BlockEntityType<? extends T>
	registerBlockEntity(String name,
						FabricBlockEntityTypeBuilder.Factory<? extends T> supplier,
						Block... blocks) {
		FabricBlockEntityTypeBuilder<? extends T> builder = FabricBlockEntityTypeBuilder.create(supplier, blocks);
		BlockEntityType<? extends T> blockEntityType = builder.build(null);
		return Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(MOD_ID, name), blockEntityType);
	}

	@SuppressWarnings("unchecked")
	public static void registerBlockEntities() {
		NitjsBlockEntities.STACKER_BLOCK_ENTITY = (BlockEntityType<BlockEntityStacker>) registerBlockEntity(
				"stacker_block_entity", BlockEntityStacker::new, NitjsBlocks.STACKER_UP, NitjsBlocks.STACKER_DOWN,
				NitjsBlocks.STACKER_EAST, NitjsBlocks.STACKER_WEST, NitjsBlocks.STACKER_NORTH,
				NitjsBlocks.STACKER_SOUTH);
	}

	public static void registerBlocks() {
		registerBlock(NitjsBlocks.STACKER_UP, "stacker_up");
		registerBlock(NitjsBlocks.STACKER_DOWN, "stacker_down");
		registerBlock(NitjsBlocks.STACKER_EAST, "stacker_east");
		registerBlock(NitjsBlocks.STACKER_WEST, "stacker_west");
		registerBlock(NitjsBlocks.STACKER_NORTH, "stacker_north");
		registerBlock(NitjsBlocks.STACKER_SOUTH, "stacker_south");
	}

	public static void registerItems() {
		FuelRegistry.INSTANCE.add(Blocks.MAGMA_BLOCK, 5000);
		Registry.register(Registries.ITEM, new Identifier(MOD_ID, "destroyer"), NitjsItems.DESTROYER);
	}

	public static void registerTags() {
		TagKey<Item> BLOCK_STACKER = TagKey.of(RegistryKeys.ITEM, STACKER_IDENTIFIER);
	}

	public static <T extends Enchantment> void registerEnchantment(T enchantment, String name) {
		Registry.register(Registries.ENCHANTMENT, new Identifier(MOD_ID, name), enchantment);
	}

	public static void registerEnchantments() {
		registerEnchantment(NitjsEnchantments.MULTI_ROD, "multi_rod");
		registerEnchantment(NitjsEnchantments.RANDOM_FISHING, "random_fishing");
	}
}
