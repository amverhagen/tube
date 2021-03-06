package com.amverhagen.tube.systems;

import com.amverhagen.tube.components.DrawToBackground;
import com.amverhagen.tube.components.SpriteComponent;
import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class DrawToBackgroundSystem extends com.artemis.systems.EntityProcessingSystem {
	private SpriteBatch batch;
	private Camera backgroundCam;
	@Wire
	ComponentMapper<SpriteComponent> spriteMapper;

	@SuppressWarnings("unchecked")
	public DrawToBackgroundSystem(SpriteBatch batch, Camera backgroundCam) {
		super(Aspect.all(DrawToBackground.class, SpriteComponent.class));
		this.batch = batch;
		this.backgroundCam = backgroundCam;
	}

	@Override
	protected void begin() {
		this.batch.setProjectionMatrix(backgroundCam.combined);
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
