package eu.nitjsefni.mod.api;

import java.util.Objects;

import net.minecraft.util.math.BlockPos;

/**
 * 
 * @author jotty
 *
 */
public class NeighborhoodBean {
	private final BlockPos pos;
	private boolean checked;

	public NeighborhoodBean(BlockPos pos, boolean checked) {
		super();
		this.pos = pos;
		this.checked = checked;
	}

	public NeighborhoodBean(BlockPos pos) {
		super();
		this.pos = pos;
		this.checked = false;
	}

	public NeighborhoodBean over() {
		return new NeighborhoodBean(pos.up(), checked);
	}

	public NeighborhoodBean below() {
		return new NeighborhoodBean(pos.down(), checked);
	}

	@Override
	public int hashCode() {
		return Objects.hash(pos);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		NeighborhoodBean other = (NeighborhoodBean) obj;
		return Objects.equals(pos, other.pos);
	}

	/**
	 * @return the checked
	 */
	public boolean isChecked() {
		return checked;
	}

	/**
	 * @param checked the checked to set
	 */
	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	/**
	 * @return the pos
	 */
	public BlockPos getPos() {
		return pos;
	}
}
