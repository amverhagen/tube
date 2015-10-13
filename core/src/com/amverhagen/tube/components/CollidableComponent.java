package com.amverhagen.tube.components;

public class CollidableComponent extends com.artemis.Component {
	public CollisionType type;
	public CollisionAction action;

	public CollidableComponent(CollisionType type, CollisionAction action) {
		this.type = type;
		this.action = action;
	}

	public enum CollisionType {
		PLAYER, ORB, WALL, POINT, TUT
	}

	public interface CollisionAction {
		public void action();
	}
}
