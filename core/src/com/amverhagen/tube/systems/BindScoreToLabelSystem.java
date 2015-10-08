package com.amverhagen.tube.systems;

import com.amverhagen.tube.components.DisplayScore;
import com.amverhagen.tube.components.Text;
import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;

public class BindScoreToLabelSystem extends com.artemis.systems.EntityProcessingSystem {
	@Wire
	ComponentMapper<Text> textMapper;
	@Wire
	ComponentMapper<DisplayScore> scoreMapper;

	@SuppressWarnings("unchecked")
	public BindScoreToLabelSystem() {
		super(Aspect.all(DisplayScore.class, Text.class));
	}

	@Override
	protected void process(Entity e) {
		Text t = textMapper.get(e);
		DisplayScore ds = scoreMapper.get(e);
		t.text = ds.score.getScore() + "";
	}

}
