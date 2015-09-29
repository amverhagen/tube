package com.amverhagen.tube.tubes;

import java.util.ArrayList;

import com.amverhagen.tube.components.SpriteComponent;
import com.amverhagen.tube.components.CollidableComponent;
import com.amverhagen.tube.components.CollidableComponent.CollisionType;
import com.amverhagen.tube.components.Deletable;
import com.amverhagen.tube.components.DrawToBackground;
import com.amverhagen.tube.components.HasParent;
import com.amverhagen.tube.components.PhysicsBody;
import com.amverhagen.tube.components.Position;
import com.artemis.Entity;
import com.artemis.World;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class Tube {
	private Vector2 position;
	private Vector2 bounds;
	private Type type;
	private ArrayList<Direction> boundingWalls;
	private Direction direction;

	public Tube(Vector2 position, Type type, Direction direction) {
		this.position = position;
		this.type = type;
		this.direction = direction;
		this.bounds = getBoundsByTypeAndDirection(this.type, this.direction);
		this.setBoundingWalls();
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
		this.setBoundingWalls();
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
				return new Vector2(1.25f, 6);
			} else if (direction == Direction.EAST || direction == Direction.WEST) {
				return new Vector2(6, 1.25f);
			}
			break;
		default:
			return new Vector2(1.25f, 1.25f);
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

	private void setBoundingWalls() {
		boundingWalls = new ArrayList<Tube.Direction>();
		if (this.type == Type.SHORT) {
			if (this.direction == Direction.NORTH || this.direction == Direction.SOUTH) {
				boundingWalls.add(Direction.EAST);
				boundingWalls.add(Direction.WEST);
			} else {
				boundingWalls.add(Direction.NORTH);
				boundingWalls.add(Direction.SOUTH);
			}
		} else {
			if ((this.type == Type.COUNTER && this.direction == Direction.EAST)
					|| (this.type == Type.CLOCK && this.direction == Direction.NORTH)) {
				boundingWalls.add(Direction.WEST);
				boundingWalls.add(Direction.SOUTH);
			}
			if ((this.type == Type.COUNTER && this.direction == Direction.NORTH)
					|| (this.type == Type.CLOCK && this.direction == Direction.WEST)) {
				boundingWalls.add(Direction.EAST);
				boundingWalls.add(Direction.SOUTH);
			}
			if ((this.type == Type.COUNTER && this.direction == Direction.SOUTH)
					|| (this.type == Type.CLOCK && this.direction == Direction.EAST)) {
				boundingWalls.add(Direction.NORTH);
				boundingWalls.add(Direction.WEST);
			}
			if ((this.type == Type.COUNTER && this.direction == Direction.WEST)
					|| (this.type == Type.CLOCK && this.direction == Direction.SOUTH)) {
				boundingWalls.add(Direction.NORTH);
				boundingWalls.add(Direction.EAST);
			}
		}
	}

	public Entity returnAsEntity(World world, Texture texture) {
		Entity tube = world.createEntity();
		SpriteComponent sc = new SpriteComponent(new Sprite(texture));
		Position pc = new Position(this.position);
		sc.sprite.setBounds(pc.x, pc.y, this.bounds.x, this.bounds.y);
		Deletable dc = new Deletable(false);
		DrawToBackground dtb = new DrawToBackground();
		tube.edit().add(sc).add(dc).add(pc).add(dtb);
		this.createWalls(world, tube);
		return tube;
	}

	private void createWalls(World world, Entity parent) {
		float thickness = 1f;
		for (Direction d : boundingWalls) {
			Entity wall = world.createEntity();
			CollidableComponent cc = new CollidableComponent(CollisionType.WALL);
			Position pos = new Position(0, 0);
			PhysicsBody pb = new PhysicsBody(0, 0);
			if (d == Direction.NORTH) {
				pos = new Position(this.position.x, this.position.y + this.bounds.y);
				pb = new PhysicsBody(this.bounds.x, thickness);
			} else if (d == Direction.EAST) {
				pos = new Position(this.position.x + this.bounds.x, this.position.y);
				pb = new PhysicsBody(thickness, this.bounds.y);
			} else if (d == Direction.SOUTH) {
				pos = new Position(this.position.x, this.position.y - thickness);
				pb = new PhysicsBody(this.bounds.x, thickness);
			} else if (d == Direction.WEST) {
				pos = new Position(this.position.x - thickness, this.position.y);
				pb = new PhysicsBody(thickness, this.bounds.y);
			}
			Deletable dc = new Deletable(false);
			HasParent hp = new HasParent(parent);
			wall.edit().add(cc).add(pos).add(pb).add(dc).add(hp);
		}
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
