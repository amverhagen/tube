package com.amverhagen.tube.systems;

import com.amverhagen.tube.components.MovementDirection;
import com.amverhagen.tube.components.SetMoveDirectionBasedOnRightOrLeftPress;
import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;

public class ShiftDirectionLeftOrRightByPressSystem extends com.artemis.systems.EntityProcessingSystem {
	@Wire
	ComponentMapper<MovementDirection> directionMapper;
	@Wire
	ComponentMapper<SetMoveDirectionBasedOnRightOrLeftPress> setMoveMapper;

	@SuppressWarnings("unchecked")
	public ShiftDirectionLeftOrRightByPressSystem() {
		super(Aspect.all(MovementDirection.class, SetMoveDirectionBasedOnRightOrLeftPress.class));
	}

	@Override
	protected void process(Entity e) {
		if (Gdx.input.justTouched()) {
			MovementDirection directionComp = directionMapper.get(e);
			SetMoveDirectionBasedOnRightOrLeftPress setMoveComp = setMoveMapper.get(e);
			Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
			setMoveComp.camera.unproject(touchPos);
			if (touchPos.x < setMoveComp.camera.position.x) {
				directionComp.shiftDirectionLeft();
			} else if (touchPos.x > setMoveComp.camera.position.x) {
				directionComp.shiftDirectionRight();
			}
		}
	}

}