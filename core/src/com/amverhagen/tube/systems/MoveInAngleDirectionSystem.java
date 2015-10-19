package com.amverhagen.tube.systems;

import com.amverhagen.tube.components.AngleDirection;
import com.amverhagen.tube.components.MovementSpeed;
import com.amverhagen.tube.components.Position;
import com.amverhagen.tube.game.ScreenState.State;
import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;

public class MoveInAngleDirectionSystem extends com.artemis.systems.EntityProcessingSystem {
	@Wire
	ComponentMapper<AngleDirection> angleMapper;
	@Wire
	ComponentMapper<MovementSpeed> speedMapper;
	@Wire
	ComponentMapper<Position> positionMapper;
	private State state;

	@SuppressWarnings("unchecked")
	public MoveInAngleDirectionSystem(State state) {
		super(Aspect.all(AngleDirection.class, MovementSpeed.class, Position.class));
		this.state = state;
	}

	@Override
	protected void process(Entity e) {
		if (state == State.RUNNING) {
			Position posComp = positionMapper.get(e);
			MovementSpeed speedComp = speedMapper.get(e);
			AngleDirection angleComp = angleMapper.get(e);

			posComp.x += (float) (Math.cos(angleComp.angle) * (speedComp.movementSpeed) * world.delta);
			posComp.y += (float) (Math.sin(angleComp.angle) * (speedComp.movementSpeed) * world.delta);
		}
	}
}
