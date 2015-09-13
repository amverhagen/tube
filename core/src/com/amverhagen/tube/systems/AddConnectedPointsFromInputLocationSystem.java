package com.amverhagen.tube.systems;

import com.amverhagen.tube.components.AddConnectedPointsFromInputLocation;
import com.amverhagen.tube.components.RecordConnectedPoints;
import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;

public class AddConnectedPointsFromInputLocationSystem extends com.artemis.systems.EntityProcessingSystem {
	@Wire
	ComponentMapper<RecordConnectedPoints> recordedPointsMapper;

	@Wire
	ComponentMapper<AddConnectedPointsFromInputLocation> inputMapper;

	@SuppressWarnings("unchecked")
	public AddConnectedPointsFromInputLocationSystem() {
		super(Aspect.all(RecordConnectedPoints.class, AddConnectedPointsFromInputLocation.class));
	}

	@Override
	protected void process(Entity e) {
		if (Gdx.input.justTouched()) {
			RecordConnectedPoints recordedPointsComp = recordedPointsMapper.get(e);
			AddConnectedPointsFromInputLocation inputComp = inputMapper.get(e);
			Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
			inputComp.camera.unproject(touchPos);
			recordedPointsComp.points.addPoint(touchPos.x, touchPos.y);
		}
	}

}
