package com.amverhagen.tube.systems;

import com.amverhagen.tube.components.DrawLineAroundBody;
import com.amverhagen.tube.components.DrawingDimension;
import com.amverhagen.tube.components.Position;
import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class RenderBoxSystem extends com.artemis.systems.EntityProcessingSystem {
	private ShapeRenderer renderer;

	@Wire
	ComponentMapper<Position> positionMapper;
	@Wire
	ComponentMapper<DrawingDimension> dimensionMapper;

	@SuppressWarnings("unchecked")
	public RenderBoxSystem(ShapeRenderer renderer) {
		super(Aspect.all(DrawLineAroundBody.class, Position.class, DrawingDimension.class));
		this.renderer = renderer;
	}

	@Override
	protected void begin() {
		renderer.begin();
		renderer.setColor(Color.GREEN);
	}

	@Override
	protected void process(Entity e) {
		Position pos = positionMapper.get(e);
		DrawingDimension dim = dimensionMapper.get(e);
		renderer.rect(pos.x, pos.y, dim.width, dim.height);
	}

	@Override
	protected void end() {
		renderer.end();
	}

}
