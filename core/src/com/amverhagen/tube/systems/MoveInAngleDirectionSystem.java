package com.amverhagen.tube.systems;

import com.amverhagen.tube.components.AngleDirection;
import com.amverhagen.tube.components.MovementSpeed;
import com.amverhagen.tube.components.Position;
import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.badlogic.gdx.Gdx;

public class MoveInAngleDirectionSystem extends com.artemis.systems.EntityProcessingSystem {
	@Wire
	ComponentMapper<AngleDirection> angleMapper;
	@Wire
	ComponentMapper<MovementSpeed> speedMapper;
	@Wire
	ComponentMapper<Position> positionMapper;

	@SuppressWarnings("unchecked")
	public MoveInAngleDirectionSystem() {
		super(Aspect.all(AngleDirection.class, MovementSpeed.class, Position.class));
	}

	@Override
	protected void process(Entity e) {
		Position posComp = positionMapper.get(e);
		MovementSpeed speedComp = speedMapper.get(e);
		AngleDirection angleComp = angleMapper.get(e);

		posComp.x += (float) (Math.cos(angleComp.angle) * (speedComp.movementSpeed) * Gdx.graphics.getDeltaTime());
		posComp.y += (float) (Math.sin(angleComp.angle) * (speedComp.movementSpeed) * Gdx.graphics.getDeltaTime());
	}
}
