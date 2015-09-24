package com.amverhagen.tube.tubes;

import java.util.ArrayList;

//import com.amverhagen.tube.components.Center;
import com.amverhagen.tube.components.Drawable;
import com.amverhagen.tube.components.DrawingDimension;
import com.amverhagen.tube.components.Position;
import com.artemis.Entity;
import com.artemis.World;
import com.badlogic.gdx.graphics.Texture;
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
		this.bounds = getBoundsByTypeAndDirection(this.type, this.direction);
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
		this.bounds = getBoundsByTypeAndDirection(this.type, this.direction);
		this.setPositionFromConnectingTube(oldTube);
	}

	public Tube(Tube oldTube) {
		if (oldTube.type == Type.SHORT) {
			this.type = Type.createRandomTurn();
		} else {
			this.type = Type.SHORT;
		}
		this.direction = getDirectionFromTypeAndPreviousTube(oldTube, this.type);
		this.bounds = getBoundsByTypeAndDirection(this.type, this.direction);
		this.setPositionFromConnectingTube(oldTube);
	}

	private Direction getDirectionFromTypeAndPreviousTube(Tube oldTube, Type type) {
		if (type == Type.COUNTER) {
			return Direction.counterDirection(oldTube.direction);
		} else if (type == Type.CLOCK) {
			return Direction.clockDirection(oldTube.direction);
		} else {
			return oldTube.direction;
		}
	}

	private Vector2 getBoundsByTypeAndDirection(Type type, Direction direction) {
		switch (type) {
		case SHORT:
			if (direction == Direction.NORTH || direction == Direction.SOUTH) {
				return new Vector2(2, 6);
			} else if (direction == Direction.EAST || direction == Direction.WEST) {
				return new Vector2(6, 2);
			}
			break;
		default:
			return new Vector2(2, 2);
		}
		return new Vector2(1, 1);
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

	public Entity returnAsEntity(World world, Texture texture) {
		Entity tube = world.createEntity();
		Drawable dc = new Drawable(texture);
		Position pc = new Position(this.position);
		DrawingDimension ddc = new DrawingDimension(this.bounds);
		// Center center = new Center(pc, ddc);
		tube.edit().add(dc).add(pc).add(ddc);
		return tube;
	}

	public Vector2 getPosition() {
		return this.position;
	}

	public Vector2 getBounds() {
		return this.bounds;
	}

	public Vector2 getCenter() {
		return new Vector2(position.x + (bounds.x / 2), position.y + (bounds.y / 2));
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
		SHORT, COUNTER, CLOCK;
		public static ArrayList<Type> getTurnList() {
			ArrayList<Type> types = new ArrayList<Type>();
			types.add(COUNTER);
			types.add(CLOCK);
			return types;
		}

		public static Type createRandomTurn() {
			if (Math.random() < .5) {
				return Type.COUNTER;
			} else {
				return Type.CLOCK;
			}
		}

		public static ArrayList<Type> getStraightList() {
			ArrayList<Type> types = new ArrayList<Type>();
			types.add(SHORT);
			return types;
		}
	}
}
