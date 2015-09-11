package com.amverhagen.tube.systems;

import com.amverhagen.tube.components.Body;
import com.amverhagen.tube.components.Drawable;
import com.amverhagen.tube.components.Position;
import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.systems.EntityProcessingSystem;

public class DrawingSystem extends EntityProcessingSystem {
	@Wire
	ComponentMapper<Position> positionMapper;
	@Wire
	ComponentMapper<Body> bodyMapper;
	@Wire
	ComponentMapper<Drawable> drawMapper;

	@SuppressWarnings("unchecked")
	public DrawingSystem() {
		super(Aspect.all(Position.class, Body.class, Drawable.class));
	}

	@Override
	protected void process(Entity e) {
		Position posComp = positionMapper.get(e);
		Body bodyComp = bodyMapper.get(e);
		Drawable drawComp = drawMapper.get(e);

		drawComp.batch.draw(drawComp.texture, posComp.x, posComp.y, bodyComp.width, bodyComp.height);
	}

}
