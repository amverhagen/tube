package com.amverhagen.tube.tubes;

import com.badlogic.gdx.math.Vector2;

public class Tube {
	private Vector2 position;
	private Vector2 bounds;
	private Type type;
	private Direction direction;

	public Tube(Vector2 position, Type type, Direction direction) {
		this.position = position;
		this.type = type;
		this.direction = direction;
		this.setBoundsByTypeAndDirection();
	}

	public Tube(Tube oldTube, Type type) {
		this.type = type;
		if (type == Type.COUNTER) {
			this.direction = Direction.counterDirection(oldTube.direction);
		} else if (type == Type.CLOCK) {
			this.direction = Direction.clockDirection(oldTube.direction);
		} else {
			this.direction = oldTube.direction;
		}
		this.setBoundsByTypeAndDirection();
		this.setPositionFromConnectingTube(oldTube);
	}

	private void setBoundsByTypeAndDirection() {
		switch (type) {
		case SHORT:
			if (direction == Direction.NORTH || direction == Direction.SOUTH) {
				this.bounds = new Vector2(1, 3);
			} else if (direction == Direction.EAST || direction == Direction.WEST) {
				this.bounds = new Vector2(3, 1);
			}
			break;
		case MEDIUM:
			if (direction == Direction.NORTH || direction == Direction.SOUTH) {
				this.bounds = new Vector2(1, 5);
			} else if (direction == Direction.EAST || direction == Direction.WEST) {
				this.bounds = new Vector2(5, 1);
			}
			break;
		case LONG:
			if (direction == Direction.NORTH || direction == Direction.SOUTH) {
				this.bounds = new Vector2(1, 7);
			} else if (direction == Direction.EAST || direction == Direction.WEST) {
				this.bounds = new Vector2(7, 1);
			}
			break;
		default:
			this.bounds = new Vector2(1, 1);
			break;
		}
	}

	private void setPositionFromConnectingTube(Tube connectingTube) {
		if (connectingTube.direction == Direction.NORTH) {
			this.position = new Vector2(connectingTube.position.x, connectingTube.position.y + connectingTube.bounds.y);
		} else if (connectingTube.direction == Direction.EAST) {
			this.position = new Vector2(connectingTube.position.x + connectingTube.bounds.x, connectingTube.position.y);
		} else if (connectingTube.direction == Direction.SOUTH) {
			this.position = new Vector2(connectingTube.position.x, connectingTube.position.y - this.bounds.y);
		} else if (connectingTube.direction == Direction.WEST) {
			this.position = new Vector2(connectingTube.position.x - this.bounds.x, connectingTube.position.y);
		}
	}

	public Vector2 getPosition() {
		return this.position;
	}

	public Vector2 getBounds() {
		return this.bounds;
	}

	public Direction getDirection() {
		return this.direction;
	}

	public Type getType() {
		return this.type;
	}

	public enum Direction {
		NORTH, EAST, SOUTH, WEST;

		public static Direction counterDirection(Direction d) {
			if (d == NORTH) {
				return WEST;
			} else if (d == EAST) {
				return NORTH;
			} else if (d == SOUTH) {
				return EAST;
			} else if (d == WEST) {
				return SOUTH;
			}
			return null;
		}

		public static Direction clockDirection(Direction d) {
			if (d == NORTH) {
				return EAST;
			} else if (d == EAST) {
				return SOUTH;
			} else if (d == SOUTH) {
				return WEST;
			} else if (d == WEST) {
				return NORTH;
			}
			return null;
		}
	}

	public enum Type {
		LONG, MEDIUM, SHORT, COUNTER, CLOCK;
	}
}
