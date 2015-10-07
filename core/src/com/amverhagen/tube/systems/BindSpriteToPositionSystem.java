package com.amverhagen.tube.systems;

import com.amverhagen.tube.components.Position;
import com.amverhagen.tube.components.SpriteComponent;
import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;

public class BindSpriteToPositionSystem extends com.artemis.systems.EntityProcessingSystem {
	@Wire
	ComponentMapper<SpriteComponent> spriteMapper;
	@Wire
	ComponentMapper<Position> positionMapper;

	@SuppressWarnings("unchecked")
	public BindSpriteToPositionSystem() {
		super(Aspect.all(Position.class, SpriteComponent.class));
	}

	@Override
	protected void process(Entity e) {
		SpriteComponent sc = spriteMapper.get(e);
		Position pos = positionMapper.get(e);
		sc.sprite.setPosition(pos.x, pos.y);
	}
}
