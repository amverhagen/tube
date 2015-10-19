package com.amverhagen.tube.systems;

import com.amverhagen.tube.components.MovementDirection;
import com.amverhagen.tube.components.MovementSpeed;
import com.amverhagen.tube.components.Position;
import com.amverhagen.tube.game.ScreenState;
import com.amverhagen.tube.game.ScreenState.State;
import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.systems.EntityProcessingSystem;

public class MoveInDirectionSystem extends EntityProcessingSystem {
	@Wire
	ComponentMapper<Position> positionMapper;
	@Wire
	ComponentMapper<MovementDirection> directionMapper;
	@Wire
	ComponentMapper<MovementSpeed> speedMapper;
	ScreenState state;

	@SuppressWarnings("unchecked")
	public MoveInDirectionSystem(ScreenState state) {
		super(Aspect.all(Position.class, MovementDirection.class, MovementSpeed.class));
		this.state = state;
	}

	@Override
	protected void process(Entity e) {
		if (state.state == State.RUNNING) {
			Position positionComp = positionMapper.get(e);
			MovementDirection directionComp = directionMapper.get(e);
			MovementSpeed speedComp = speedMapper.get(e);
			float speed = speedComp.movementSpeed * world.delta;

			if (directionComp.direction == MovementDirection.Direction.NORTH) {
				positionComp.y += speed;
			} else if (directionComp.direction == MovementDirection.Direction.EAST) {
				positionComp.x += speed;
			} else if (directionComp.direction == MovementDirection.Direction.SOUTH) {
				positionComp.y -= speed;
			} else if (directionComp.direction == MovementDirection.Direction.WEST) {
				positionComp.x -= speed;
			}
		}
	}

}
