package com.amverhagen.tube.systems;

import com.amverhagen.tube.components.MovementDirection;
import com.amverhagen.tube.components.SetMoveDirectionBasedOnRightOrLeftPress;
import com.amverhagen.tube.systems.ScreenState.State;
import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.badlogic.gdx.Gdx;

public class ShiftDirectionLeftOrRightByPressSystem extends com.artemis.systems.EntityProcessingSystem {
	@Wire
	ComponentMapper<MovementDirection> directionMapper;
	private ScreenState state;
	private float time;
	private boolean left;
	private boolean right;

	@SuppressWarnings("unchecked")
	public ShiftDirectionLeftOrRightByPressSystem(ScreenState state) {
		super(Aspect.all(MovementDirection.class, SetMoveDirectionBasedOnRightOrLeftPress.class));
		this.state = state;
		this.time = 0;
		this.left = false;
		this.right = false;
	}

	@Override
	protected void begin() {
		left = false;
		right = false;
		if (state.state == State.RUNNING) {
			time += world.delta;
			if (time > .16f) {
				if (Gdx.input.justTouched()) {
					if (Gdx.input.getX() < Gdx.graphics.getWidth() / 2) {
						left = true;
					} else {
						right = true;
					}
					time = 0;
				}
			}
		}
	}

	@Override
	protected void process(Entity e) {
		if (left || right) {
			MovementDirection directionComp = directionMapper.get(e);
			if (left) {
				directionComp.shiftDirectionLeft();
			} else if (right) {
				directionComp.shiftDirectionRight();
			}
		}
	}
}
