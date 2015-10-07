package com.amverhagen.tube.systems;

import com.amverhagen.tube.components.Text;
import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class DrawTextSystem extends com.artemis.systems.EntityProcessingSystem {
	private SpriteBatch batch;
	@Wire
	private ComponentMapper<Text> textMapper;

	@SuppressWarnings("unchecked")
	public DrawTextSystem(SpriteBatch batch) {
		super(Aspect.all(Text.class));
		this.batch = batch;
	}

	@Override
	protected void begin() {
		this.batch.begin();
	}

	@Override
	protected void process(Entity e) {
		Text t = textMapper.get(e);
		t.font.draw(batch, t.text, t.xPos, t.yPos);
	}

	@Override
	protected void end() {
		this.batch.end();
	}

}
