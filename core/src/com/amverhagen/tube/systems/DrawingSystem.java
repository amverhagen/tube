package com.amverhagen.tube.systems;

import com.amverhagen.tube.components.Body;
import com.amverhagen.tube.components.Drawable;
import com.amverhagen.tube.components.Position;
import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class DrawingSystem extends EntityProcessingSystem {
	private SpriteBatch batch;
	@Wire
	ComponentMapper<Position> positionMapper;
	@Wire
	ComponentMapper<Body> bodyMapper;
	@Wire
	ComponentMapper<Drawable> drawMapper;

	@SuppressWarnings("unchecked")
	public DrawingSystem(SpriteBatch batch) {
		super(Aspect.all(Position.class, Body.class, Drawable.class));
		this.batch = batch;
	}

	@Override
	protected void begin() {
		this.batch.begin();
	};

	@Override
	protected void process(Entity e) {
		Position posComp = positionMapper.get(e);
		Body bodyComp = bodyMapper.get(e);
		Drawable drawComp = drawMapper.get(e);

		drawComp.batch.draw(drawComp.texture, posComp.x, posComp.y, bodyComp.width, bodyComp.height);
	}

	@Override
	protected void end() {
		this.batch.end();
	}
}
