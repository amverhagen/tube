package com.amverhagen.tube.systems;

import com.amverhagen.tube.components.DrawToUI;
import com.amverhagen.tube.components.SpriteComponent;
import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class DrawToUISystem extends com.artemis.systems.EntityProcessingSystem {
	private SpriteBatch batch;
	private Camera uiCamera;
	@Wire
	ComponentMapper<SpriteComponent> spriteMapper;

	@SuppressWarnings("unchecked")
	public DrawToUISystem(SpriteBatch batch, Camera uiCamera) {
		super(Aspect.all(DrawToUI.class, SpriteComponent.class));
		this.batch = batch;
		this.uiCamera = uiCamera;
	}

	@Override
	protected void begin() {
		this.batch.setProjectionMatrix(uiCamera.combined);
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
