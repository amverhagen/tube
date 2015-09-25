package com.amverhagen.tube.systems;

import com.artemis.ComponentMapper;
import com.amverhagen.tube.components.Deletable;
import com.artemis.Aspect;
import com.artemis.Entity;
import com.artemis.annotations.Wire;

public class DeleteEntitySystem extends com.artemis.systems.EntityProcessingSystem {
	@Wire
	ComponentMapper<Deletable> deleteMapper;

	@SuppressWarnings("unchecked")
	public DeleteEntitySystem() {
		super(Aspect.all(Deletable.class));
	}

	@Override
	protected void process(Entity e) {
		Deletable d = deleteMapper.get(e);
		if (d.needsDeleted)
			e.deleteFromWorld();
	}
}
