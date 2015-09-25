package com.amverhagen.tube.systems;

import com.artemis.annotations.Wire;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.amverhagen.tube.components.Renderable;
import com.amverhagen.tube.components.RenderBody;
import com.amverhagen.tube.components.ForeGround;
import com.amverhagen.tube.components.Position;
import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;

public class ForeGroundSystem extends com.artemis.systems.EntityProcessingSystem {
	private SpriteBatch batch;
	@Wire
	ComponentMapper<Position> positionMapper;
	@Wire
	ComponentMapper<RenderBody> bodyMapper;
	@Wire
	ComponentMapper<Renderable> drawMapper;

	@SuppressWarnings("unchecked")
	public ForeGroundSystem(SpriteBatch batch) {
		super(Aspect.all(Position.class, RenderBody.class, Renderable.class, ForeGround.class));
		this.batch = batch;
	}

	@Override
	protected void begin() {
		this.batch.begin();
	};

	@Override
	protected void process(Entity e) {
		Position posComp = positionMapper.get(e);
		RenderBody bodyComp = bodyMapper.get(e);
		Renderable drawComp = drawMapper.get(e);

		batch.draw(drawComp.texture, posComp.x, posComp.y, bodyComp.width, bodyComp.height);
	}

	@Override
	protected void end() {
		this.batch.end();
	}

}
