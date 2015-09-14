package com.amverhagen.tube.systems;

import com.amverhagen.tube.components.AddConnectedPointsFromEntityCenter;
import com.amverhagen.tube.components.DrawingDimension;
import com.amverhagen.tube.components.Position;
import com.amverhagen.tube.components.RecordConnectedPoints;
import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;

public class AddConnectedPointsFromEntityCenterSystem extends com.artemis.systems.EntityProcessingSystem {
	@Wire
	ComponentMapper<RecordConnectedPoints> recordedPointsMapper;
	@Wire
	ComponentMapper<DrawingDimension> bodyMapper;
	@Wire
	ComponentMapper<Position> positionMapper;

	@SuppressWarnings("unchecked")
	public AddConnectedPointsFromEntityCenterSystem() {
		super(Aspect.all(RecordConnectedPoints.class, AddConnectedPointsFromEntityCenter.class, DrawingDimension.class,
				Position.class));
	}

	@Override
	protected void process(Entity e) {
		RecordConnectedPoints recordedPointsComp = recordedPointsMapper.get(e);
		DrawingDimension bodyComp = bodyMapper.get(e);
		Position positionComp = positionMapper.get(e);
		float x = positionComp.x + (bodyComp.width / 2);
		float y = positionComp.y + (bodyComp.height / 2);
		recordedPointsComp.points.addPoint(x, y);
	}

}
