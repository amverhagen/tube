package com.amverhagen.tube.systems;

import com.amverhagen.tube.components.DrawToForeground;
import com.amverhagen.tube.components.SpriteComponent;
import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class DrawToForegroundSystem extends com.artemis.systems.EntityProcessingSystem {
	private SpriteBatch batch;
	@Wire
	ComponentMapper<SpriteComponent> spriteMapper;

	@SuppressWarnings("unchecked")
	public DrawToForegroundSystem(SpriteBatch batch) {
		super(Aspect.all(DrawToForeground.class, SpriteComponent.class));
		this.batch = batch;
	}

	@Override
	protected void begin() {
		this.batch.begin();
	}

	@Override
	protected void process(Entity e) {
		SpriteComponent sprite = spriteMapper.get(e);
		sprite.sprite.draw(batch);
	}

	@Override
	protected void end() {
		this.batch.end();
	}
}
