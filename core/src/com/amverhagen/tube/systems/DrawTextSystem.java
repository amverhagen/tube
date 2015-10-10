package com.amverhagen.tube.systems;

import com.amverhagen.tube.components.DrawDuringState;
import com.amverhagen.tube.components.Text;
import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class DrawTextSystem extends com.artemis.systems.EntityProcessingSystem {
	private SpriteBatch batch;
	private ScreenState state;
	@Wire
	private ComponentMapper<Text> textMapper;
	@Wire
	private ComponentMapper<DrawDuringState> drawStateMapper;

	@SuppressWarnings("unchecked")
	public DrawTextSystem(SpriteBatch batch, ScreenState state) {
		super(Aspect.all(Text.class));
		this.batch = batch;
		this.state = state;
	}

	@Override
	protected void begin() {
		this.batch.begin();
	}

	@Override
	protected void process(Entity e) {
		Text t = textMapper.get(e);
		DrawDuringState drawState = drawStateMapper.get(e);
		if (drawState == null)
			t.font.draw(batch, t.text, t.xPos, t.yPos);
		else if (drawState.drawState.state == state.state)
			t.font.draw(batch, t.text, t.xPos, t.yPos);
	}

	@Override
	protected void end() {
		this.batch.end();
	}

}
