package com.amverhagen.tube.systems;

import com.amverhagen.tube.components.Center;
import com.amverhagen.tube.components.MovementDirection;
import com.amverhagen.tube.game.ScreenState;
import com.amverhagen.tube.game.ScreenState.State;
import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;

public class UpdateCenterSystem extends com.artemis.systems.EntityProcessingSystem {
	private ScreenState state;

	@Wire
	ComponentMapper<Center> centerMapper;

	@SuppressWarnings("unchecked")
	public UpdateCenterSystem(ScreenState state) {
		super(Aspect.all(Center.class).one(MovementDirection.class));
		this.state = state;
	}

	@Override
	protected void process(Entity e) {
		if (state.state == State.RUNNING) {
			centerMapper.get(e).updateCenter();
		}
	}
}
