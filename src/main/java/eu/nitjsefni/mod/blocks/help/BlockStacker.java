package eu.nitjsefni.mod.blocks.help;

import net.minecraft.block.BlockEntityProvider;
import net.minecraft.util.math.Direction;

/**
 *
 * @author jotty
 *
 */
public interface BlockStacker extends BlockEntityProvider {
	/**
	 * define the source offset
	 *
	 * @return the direction of the source offset (1 block beside)
	 */
	Direction getSourceOffset();

	/**
	 * define the dest offset
	 *
	 * @return the direction of the dest offset (1 block beside)
	 */
	Direction getDestOffset();
}
