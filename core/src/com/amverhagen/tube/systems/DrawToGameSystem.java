package com.amverhagen.tube.systems;

import com.amverhagen.tube.components.DrawToGame;
import com.amverhagen.tube.components.SpriteComponent;
import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class DrawToGameSystem extends com.artemis.systems.EntityProcessingSystem {
	private SpriteBatch batch;
	private Camera gameCamera;
	@Wire
	ComponentMapper<SpriteComponent> spriteMapper;

	@SuppressWarnings("unchecked")
	public DrawToGameSystem(SpriteBatch batch, Camera gameCamera) {
		super(Aspect.all(DrawToGame.class, SpriteComponent.class));
		this.batch = batch;
		this.gameCamera = gameCamera;
	}

	@Override
	protected void begin() {
		this.batch.setProjectionMatrix(gameCamera.combined);
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
