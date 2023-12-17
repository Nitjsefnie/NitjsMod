package eu.nitjsefni.mod.api;

import java.util.HashSet;
import java.util.Set;
import java.util.function.BiFunction;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * 
 * @author jotty
 *
 */
public class Neighborhood {

	/**
	 * find the same blocks that is next to the current position pos
	 * 
	 * @param world       the world to look for the blocks
	 * @param pos         the starting position
	 * @param seekLimit   the limit of seek operations
	 * @param checkLambda check functionality
	 * @return a set of found block positions
	 */
	public static Set<BlockPos> findEqualBlock(World world, BlockPos pos, int seekLimit,
											   BiFunction<World, BlockPos, Boolean> checkLambda) {
		Set<NeighborhoodBean> found = new HashSet<>();
		found.add(new NeighborhoodBean(pos, true));

		while (pos != null && found.size() < seekLimit) {
			findNewNeighbor(world, pos.east(), found, checkLambda);
			findNewNeighbor(world, pos.south(), found, checkLambda);
			findNewNeighbor(world, pos.west(), found, checkLambda);
			findNewNeighbor(world, pos.north(), found, checkLambda);
			pos = findNextUncheckedField(found);
		}

		Set<BlockPos> finals = new HashSet<>();
		for (NeighborhoodBean bean : found) {
			finals.add(bean.getPos());
		}
		return finals;
	}

	private static BlockPos findNextUncheckedField(Set<NeighborhoodBean> found) {
		for (NeighborhoodBean bean : found) {
			if (!bean.isChecked()) {
				bean.setChecked(true);
				return bean.getPos();
			}
		}
		return null;
	}

	/**
	 * find new neighbor at pos
	 * 
	 * @param world the world
	 * @param pos   the position
	 * @param found the set with all already found positions
	 */
	private static void findNewNeighbor(World world, BlockPos pos, Set<NeighborhoodBean> found,
										BiFunction<World, BlockPos, Boolean> checkLambda) {
		NeighborhoodBean bean = new NeighborhoodBean(pos);
		if (!found.contains(bean) && !found.contains(bean.over()) && !found.contains(bean.below())) {
			if (checkLambda.apply(world, pos)) {
				found.add(bean);
			} else if (checkLambda.apply(world, pos.up())) {
				found.add(new NeighborhoodBean(pos.up()));
			} else if (checkLambda.apply(world, pos.down())) {
				found.add(new NeighborhoodBean(pos.down()));
			}
		}
	}
}
