package eu.nitjsefni.mod.init;

import eu.nitjsefni.mod.blockentity.BlockEntityStacker;
import eu.nitjsefni.mod.blockentity.QuickieFabricBlockEntity;
import eu.nitjsefni.mod.container.ScreenHandlerBlockStacker;
import eu.nitjsefni.mod.blocks.QuickieBlocks;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
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

/**
 *
 * @author jotty
 *
 */
public class RegistryManager {
	private static final Logger LOGGER = LogManager.getLogger(RegistryManager.class);

	public static final String QUICKIEFABRIC = "nitjsefni";
	public static final Identifier STACKER_IDENTIFIER = new Identifier(QUICKIEFABRIC, "stacker");
	public static final ScreenHandlerType<ScreenHandlerBlockStacker> STACKER_SCREEN_HANDLER = ScreenHandlerRegistry
			.registerSimple(RegistryManager.STACKER_IDENTIFIER, ScreenHandlerBlockStacker::new);

	public static final RegistryKey<ItemGroup> QUICKIEFABRIC_GROUP = RegistryKey.of(RegistryKeys.ITEM_GROUP,
			new Identifier(QUICKIEFABRIC, "itemgroups"));

	public static void registerItemGroup() {
		Registry.register(Registries.ITEM_GROUP, QUICKIEFABRIC_GROUP,
				FabricItemGroup.builder().icon(() -> new ItemStack(QuickieBlocks.STACKER_UP))
						.displayName(Text.literal(QUICKIEFABRIC)).entries((enabledFeatures, stacks) -> {
							stacks.add(new ItemStack(QuickieBlocks.STACKER_UP));
							stacks.add(new ItemStack(QuickieBlocks.STACKER_DOWN));
							stacks.add(new ItemStack(QuickieBlocks.STACKER_EAST));
							stacks.add(new ItemStack(QuickieBlocks.STACKER_WEST));
							stacks.add(new ItemStack(QuickieBlocks.STACKER_NORTH));
							stacks.add(new ItemStack(QuickieBlocks.STACKER_SOUTH));
						}).build());
	}

	private static void registerBlock(Block block, String name) {
		Registry.register(Registries.BLOCK, new Identifier(QUICKIEFABRIC, name), block);
		Registry.register(Registries.ITEM, new Identifier(QUICKIEFABRIC, name),
				new BlockItem(block, new FabricItemSettings()));
	}

	public static <T extends BlockEntity> BlockEntityType<? extends T>
	registerBlockEntity(String name,
						FabricBlockEntityTypeBuilder.Factory<? extends T> supplier,
						Block... blocks) {
		FabricBlockEntityTypeBuilder<? extends T> builder = FabricBlockEntityTypeBuilder.create(supplier, blocks);
		BlockEntityType<? extends T> blockEntityType = builder.build(null);
		return Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(QUICKIEFABRIC, name), blockEntityType);
	}

	@SuppressWarnings("unchecked")
	public static void registerBlockEntities() {
		LOGGER.debug("registering quickiefabric block entities");
		QuickieFabricBlockEntity.STACKER_BLOCK_ENTITY = (BlockEntityType<BlockEntityStacker>) registerBlockEntity(
				"stacker_block_entity", BlockEntityStacker::new, QuickieBlocks.STACKER_UP, QuickieBlocks.STACKER_DOWN,
				QuickieBlocks.STACKER_EAST, QuickieBlocks.STACKER_WEST, QuickieBlocks.STACKER_NORTH,
				QuickieBlocks.STACKER_SOUTH);
	}

	public static void registerBlocks() {
		LOGGER.debug("registering quickiefabric blocks");
		registerBlock(QuickieBlocks.STACKER_UP, "stacker_up");
		registerBlock(QuickieBlocks.STACKER_DOWN, "stacker_down");
		registerBlock(QuickieBlocks.STACKER_EAST, "stacker_east");
		registerBlock(QuickieBlocks.STACKER_WEST, "stacker_west");
		registerBlock(QuickieBlocks.STACKER_NORTH, "stacker_north");
		registerBlock(QuickieBlocks.STACKER_SOUTH, "stacker_south");
	}

	public static void registerItems() {
		FuelRegistry.INSTANCE.add(Blocks.MAGMA_BLOCK, 5000);
	}

	public static void registerTags() {
		TagKey<Item> BLOCK_STACKER = TagKey.of(RegistryKeys.ITEM, STACKER_IDENTIFIER);
	}
}
