package com.amverhagen.tube.components;

public class CollidableComponent extends com.artemis.Component {
	public CollisionType type;

	public CollidableComponent(CollisionType type) {
		this.type = type;
	}

	public enum CollisionType {
		PLAYER, ORB, WALL
	}
}
