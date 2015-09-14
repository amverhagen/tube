package com.amverhagen.tube.systems;

import com.amverhagen.tube.components.AddConnectedPointsFromEntityPos;
import com.amverhagen.tube.components.Position;
import com.amverhagen.tube.components.RecordConnectedPoints;
import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;

public class AddConnectedPointsFromEntityPosSystem extends com.artemis.systems.EntityProcessingSystem {
	@Wire
	ComponentMapper<RecordConnectedPoints> recordedPointsMapper;
	@Wire
	ComponentMapper<Position> positionMapper;

	@SuppressWarnings("unchecked")
	public AddConnectedPointsFromEntityPosSystem() {
		super(Aspect.all(AddConnectedPointsFromEntityPos.class, RecordConnectedPoints.class, Position.class));
	}

	@Override
	protected void process(Entity e) {
		RecordConnectedPoints recordedPointsComp = recordedPointsMapper.get(e);
		Position positionComp = positionMapper.get(e);

		recordedPointsComp.points.addPoint(positionComp.x, positionComp.y);
	}

}
