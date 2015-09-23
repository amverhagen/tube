package com.amverhagen.tube.systems;

import com.amverhagen.tube.components.CollidableEntity;
import com.amverhagen.tube.components.CollidableEntity.CollisionType;
import com.amverhagen.tube.components.Position;
import com.amverhagen.tube.systems.ScreenState.State;
import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.managers.TagManager;

public class CheckPlayerCollisionSystem extends com.artemis.systems.EntityProcessingSystem {
	@Wire
	ComponentMapper<CollidableEntity> collisionMapper;
	@Wire
	ComponentMapper<Position> positionMapper;

	private Entity player;
	private Position playerPos;
	private CollidableEntity playerBounds;
	private ScreenState state;

	@SuppressWarnings("unchecked")
	public CheckPlayerCollisionSystem(ScreenState state) {
		super(Aspect.all(CollidableEntity.class, Position.class));
		this.state = state;
	}

	@Override
	protected void begin() {
		player = world.getManager(TagManager.class).getEntity("PLAYER");
		playerPos = player.getComponent(Position.class);
		playerBounds = player.getComponent(CollidableEntity.class);
	}

	@Override
	protected void process(Entity e) {
		if (state.state == State.RUNNING) {
			CollidableEntity collisionComp = collisionMapper.get(e);
			if (collisionComp.type != CollisionType.PLAYER) {
				Position posComp = positionMapper.get(e);
				if ((playerPos.x + playerBounds.width) > (posComp.x)
						&& (playerPos.x) < (posComp.x + collisionComp.width)
						&& (playerPos.y + playerBounds.height) > (posComp.y)
						&& (playerPos.y) < (posComp.y + collisionComp.height)) {
					if (collisionComp.type == CollisionType.ORB) {
						e.deleteFromWorld();
					}
					// new EntityBuilder(world).with(new Position(centerX,
					// centerY),
					// new RenderConnectedPoints(Color.BLACK, .05f), new
					// RecordConnectedPoints(20),
					// new MovementSpeed(5f), new AngleDirection(1f), new
					// AddConnectedPointsFromEntityPos()).build();
				}
			}
		}
	}
}
