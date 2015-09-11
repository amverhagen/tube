package com.amverhagen.tube.components;

import com.artemis.Component;

public class MovementDirection extends Component {
	public Direction direction;

	public MovementDirection(Direction direction) {
		this.direction = direction;
	}

	public void shiftDirectionLeft() {
		if (this.direction == Direction.NORTH) {
			this.direction = Direction.WEST;
		} else if (this.direction == Direction.EAST) {
			this.direction = Direction.NORTH;
		} else if (this.direction == Direction.SOUTH) {
			this.direction = Direction.EAST;
		} else if (this.direction == Direction.WEST) {
			this.direction = Direction.SOUTH;
		}
	}

	public void shiftDirectionRight() {
		if (this.direction == Direction.NORTH) {
			this.direction = Direction.EAST;
		} else if (this.direction == Direction.EAST) {
			this.direction = Direction.SOUTH;
		} else if (this.direction == Direction.SOUTH) {
			this.direction = Direction.WEST;
		} else if (this.direction == Direction.WEST) {
			this.direction = Direction.NORTH;
		}
	}

	public enum Direction {
		NORTH, EAST, SOUTH, WEST;
	}
}
