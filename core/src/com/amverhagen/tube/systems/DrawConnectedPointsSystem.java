package com.amverhagen.tube.systems;

import com.amverhagen.tube.components.Center;
import com.amverhagen.tube.components.RecordConnectedPoints;
import com.amverhagen.tube.components.RecordConnectedPoints.ConnectedPointList.ConnectedPoint;
import com.amverhagen.tube.systems.ScreenState.State;
import com.amverhagen.tube.components.DrawShape;
import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.managers.TagManager;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class DrawConnectedPointsSystem extends com.artemis.systems.EntityProcessingSystem {
	private Entity player;
	private Center playerCenter;
	private ScreenState state;
	ShapeRenderer renderer;
	@Wire
	ComponentMapper<RecordConnectedPoints> connectedPointsMapper;
	@Wire
	ComponentMapper<DrawShape> shapeMapper;
	@Wire
	ComponentMapper<Center> centerMapper;

	@SuppressWarnings("unchecked")
	public DrawConnectedPointsSystem(ShapeRenderer renderer, ScreenState state) {
		super(Aspect.all(RecordConnectedPoints.class, DrawShape.class));
		this.renderer = renderer;
		this.state = state;
	}

	@Override
	protected void begin() {
		this.renderer.begin();
		this.renderer.set(ShapeType.Filled);
		if (state.state == State.RUNNING) {
			player = world.getManager(TagManager.class).getEntity("PLAYER");
			playerCenter = centerMapper.get(player);
			this.renderer.circle(playerCenter.center.x, playerCenter.center.y, .25f, 1000);
		}
	}

	@Override
	protected void process(Entity e) {
		RecordConnectedPoints pointsComp = connectedPointsMapper.get(e);
		DrawShape renderComp = shapeMapper.get(e);
		for (ConnectedPoint cp : pointsComp.points.connectedPoints) {
			if (cp != null) {
				this.renderer.rectLine(cp.pointBehind.pos.x, cp.pointBehind.pos.y, cp.pos.x, cp.pos.y,
						renderComp.width);
			}
		}
	}

	@Override
	protected void end() {
		this.renderer.end();
	}
}