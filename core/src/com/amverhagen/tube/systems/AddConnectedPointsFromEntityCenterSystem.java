package com.amverhagen.tube.systems;

import com.amverhagen.tube.components.AddConnectedPointsFromEntityCenter;
import com.amverhagen.tube.components.Center;
import com.amverhagen.tube.components.RecordConnectedPoints;
import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;

public class AddConnectedPointsFromEntityCenterSystem extends com.artemis.systems.EntityProcessingSystem {
	@Wire
	ComponentMapper<RecordConnectedPoints> recordedPointsMapper;
	@Wire
	ComponentMapper<Center> centerMapper;

	@SuppressWarnings("unchecked")
	public AddConnectedPointsFromEntityCenterSystem() {
		super(Aspect.all(RecordConnectedPoints.class, AddConnectedPointsFromEntityCenter.class, Center.class));
	}

	@Override
	protected void process(Entity e) {
		RecordConnectedPoints recordedPointsComp = recordedPointsMapper.get(e);
		Center centerComp = centerMapper.get(e);
		recordedPointsComp.points.addPoint(centerComp.center.x, centerComp.center.y);
	}

}
