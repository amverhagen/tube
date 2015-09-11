package com.amverhagen.tube.components;

import com.artemis.Component;

public class MovementDirection extends Component{
	public Direction direction;

	public MovementDirection(Direction direction) {
		this.direction = direction;
	}

	public enum Direction {
		NORTH, EAST, SOUTH, WEST;
	}
}
