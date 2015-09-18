package com.amverhagen.tube.systems;

import com.amverhagen.tube.components.DrawingDimension;
import com.amverhagen.tube.components.Position;
import com.amverhagen.tube.components.UIRenderable;
import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class RenderUISystem extends com.artemis.systems.EntityProcessingSystem {
	@Wire
	ComponentMapper<Position> positionMapper;
	@Wire
	ComponentMapper<DrawingDimension> dimensionMappper;
	@Wire
	ComponentMapper<UIRenderable> renderMapper;

	private Camera uiCamera;
	private SpriteBatch uiBatch;

	@SuppressWarnings("unchecked")
	public RenderUISystem(SpriteBatch uiBatch, Camera uiCamera) {
		super(Aspect.all(Position.class, DrawingDimension.class, UIRenderable.class));
		this.uiBatch = uiBatch;
		this.uiCamera = uiCamera;
	}

	@Override
	protected void begin() {
		uiBatch.setProjectionMatrix(uiCamera.combined);
		uiBatch.begin();
	}

	@Override
	protected void process(Entity e) {
		Position pos = positionMapper.get(e);
		DrawingDimension dimensions = dimensionMappper.get(e);
		UIRenderable renderable = renderMapper.get(e);

		uiBatch.draw(renderable.texture, pos.x, pos.y, dimensions.width, dimensions.height);
	}

	@Override
	protected void end() {
		uiBatch.end();
	}

}
