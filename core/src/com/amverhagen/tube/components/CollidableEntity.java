package com.amverhagen.tube.components;

public class CollidableEntity extends com.artemis.Component {
	public float width;
	public float height;
	public CollisionType type;

	public CollidableEntity(float width, float height, CollisionType type) {
		this.width = width;
		this.height = height;
		this.type = type;
	}

	public enum CollisionType {
		PLAYER, ORB, WALL
	}
}
