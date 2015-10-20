package com.amverhagen.tube.systems;

import com.amverhagen.tube.components.CollidableComponent;
import com.amverhagen.tube.components.CollidableComponent.CollisionType;
import com.amverhagen.tube.components.Deletable;
import com.amverhagen.tube.components.PhysicsBody;
import com.amverhagen.tube.components.Position;
import com.amverhagen.tube.game.ScreenState;
import com.amverhagen.tube.game.ScreenState.State;
import com.amverhagen.tube.managers.PlayerManager;
import com.amverhagen.tube.managers.ScoreManager;
import com.amverhagen.tube.managers.TubeManager;
import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;

public class CheckPlayerCollisionSystem extends com.artemis.systems.EntityProcessingSystem {
	@Wire
	ComponentMapper<CollidableComponent> collisionMapper;
	@Wire
	ComponentMapper<PhysicsBody> physicsBodyMapper;
	@Wire
	ComponentMapper<Position> positionMapper;
	@Wire
	ComponentMapper<Deletable> deleteMapper;

	private Entity player;
	private Position playerPos;
	private PhysicsBody playerBounds;
	private ScreenState state;

	@SuppressWarnings("unchecked")
	public CheckPlayerCollisionSystem(ScreenState state) {
		super(Aspect.all(CollidableComponent.class, PhysicsBody.class, Position.class));
		this.state = state;
	}

	@Override
	protected void initialize() {
		player = world.getManager(PlayerManager.class).player;
		playerPos = positionMapper.get(player);
		playerBounds = physicsBodyMapper.get(player);
	}

	@Override
	protected void process(Entity e) {
		if (state.state == State.RUNNING) {
			CollidableComponent cc = collisionMapper.get(e);
			if (cc.type != CollisionType.PLAYER) {
				Position pos = positionMapper.get(e);
				PhysicsBody body = physicsBodyMapper.get(e);
				if ((playerPos.x + playerBounds.width) > (pos.x) && (playerPos.x) < (pos.x + body.width)
						&& (playerPos.y + playerBounds.height) > (pos.y) && (playerPos.y) < (pos.y + body.height)) {
					handleCollision(cc, e);
				}
			}
		}
	}

	private void handleCollision(CollidableComponent cc, Entity e) {
		if (cc.type == CollisionType.POINT) {
			world.getManager(ScoreManager.class).increaseScore();
		}

		if (cc.type == CollisionType.TUT) {
			Deletable d = deleteMapper.get(e);
			d.needsDeleted = true;
			cc.action.action();
		}
		if (cc.type == CollisionType.ORB || cc.type == CollisionType.POINT) {
			Deletable d = deleteMapper.get(e);
			d.needsDeleted = true;
			world.getManager(TubeManager.class).addRandomTubeToWorld();
		} else if (cc.type == CollisionType.WALL) {
			state.state = State.OVER;
		}
	}
}
