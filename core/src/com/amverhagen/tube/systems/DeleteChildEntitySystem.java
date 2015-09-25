package com.amverhagen.tube.systems;

import com.amverhagen.tube.components.Deletable;
import com.amverhagen.tube.components.HasParent;
import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;

public class DeleteChildEntitySystem extends com.artemis.systems.EntityProcessingSystem {
	@Wire
	ComponentMapper<HasParent> parentMapper;
	@Wire
	ComponentMapper<Deletable> deleteMapper;

	@SuppressWarnings("unchecked")
	public DeleteChildEntitySystem() {
		super(Aspect.all(HasParent.class, Deletable.class));
	}

	@Override
	protected void process(Entity e) {
		HasParent hasParent = parentMapper.get(e);
		Deletable childDeletable = deleteMapper.get(e);
		Deletable parentDeletable = deleteMapper.get(hasParent.parent);

		if (parentDeletable == null || parentDeletable.needsDeleted) {
			childDeletable.needsDeleted = true;
		}
	}
}
