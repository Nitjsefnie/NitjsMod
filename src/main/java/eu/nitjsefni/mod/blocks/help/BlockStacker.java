package eu.nitjsefni.mod.blocks.help;

import net.minecraft.block.BlockEntityProvider;
import net.minecraft.util.math.Direction;

public interface BlockStacker extends BlockEntityProvider {

	Direction getSourceOffset();

	Direction getDestOffset();

}
