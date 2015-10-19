package com.amverhagen.tube.systems;

import com.amverhagen.tube.components.AddConnectedPointsFromEntityCenter;
import com.amverhagen.tube.components.Center;
import com.amverhagen.tube.components.RecordConnectedPoints;
import com.amverhagen.tube.game.ScreenState;
import com.amverhagen.tube.game.ScreenState.State;
import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;

public class AddConnectedPointsFromEntityCenterSystem extends com.artemis.systems.EntityProcessingSystem {
	@Wire
	ComponentMapper<RecordConnectedPoints> recordedPointsMapper;
	@Wire
	ComponentMapper<Center> centerMapper;
	private ScreenState gameState;

	@SuppressWarnings("unchecked")
	public AddConnectedPointsFromEntityCenterSystem(ScreenState gameState) {
		super(Aspect.all(RecordConnectedPoints.class, AddConnectedPointsFromEntityCenter.class, Center.class));
		this.gameState = gameState;
	}

	@Override
	protected void process(Entity e) {
		if (gameState.state == State.RUNNING) {
			RecordConnectedPoints recordedPointsComp = recordedPointsMapper.get(e);
			Center centerComp = centerMapper.get(e);
			recordedPointsComp.points.addPoint(centerComp.x, centerComp.y);
		}
	}
}
