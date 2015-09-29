package com.amverhagen.tube.systems;

import com.amverhagen.tube.components.RecordConnectedPoints;
import com.amverhagen.tube.components.RecordConnectedPoints.ConnectedPointList.ConnectedPoint;
import com.amverhagen.tube.components.RenderConnectedPoints;
import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class DrawConnectedPointsSystem extends com.artemis.systems.EntityProcessingSystem {
	ShapeRenderer renderer;
	@Wire
	ComponentMapper<RecordConnectedPoints> connectedPointsMapper;
	@Wire
	ComponentMapper<RenderConnectedPoints> renderPointsMapper;

	@SuppressWarnings("unchecked")
	public DrawConnectedPointsSystem(ShapeRenderer renderer) {
		super(Aspect.all(RecordConnectedPoints.class, RenderConnectedPoints.class));
		this.renderer = renderer;
	}

	@Override
	protected void begin() {
		this.renderer.begin();
		this.renderer.set(ShapeType.Filled);
	}

	@Override
	protected void process(Entity e) {
		RecordConnectedPoints pointsComp = connectedPointsMapper.get(e);
		RenderConnectedPoints renderComp = renderPointsMapper.get(e);
		this.renderer.setColor(renderComp.color);
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
