package com.amverhagen.tube.systems;

import com.amverhagen.tube.components.CollidableComponent;
import com.amverhagen.tube.components.CollidableComponent.CollisionType;
import com.amverhagen.tube.components.Deletable;
import com.amverhagen.tube.components.PhysicsBody;
import com.amverhagen.tube.components.Position;
import com.amverhagen.tube.game.Score;
import com.amverhagen.tube.managers.TubeManager;
import com.amverhagen.tube.systems.ScreenState.State;
import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.managers.TagManager;

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
	private Deletable playerDeletion;
	private ScreenState state;
	private Score score;

	@SuppressWarnings("unchecked")
	public CheckPlayerCollisionSystem(ScreenState state, Score score) {
		super(Aspect.all(CollidableComponent.class, PhysicsBody.class, Position.class));
		this.state = state;
		this.score = score;
	}

	@Override
	protected void begin() {
		if (state.state == State.RUNNING) {
			player = world.getManager(TagManager.class).getEntity("PLAYER");
			playerPos = positionMapper.get(player);
			playerBounds = physicsBodyMapper.get(player);
			playerDeletion = deleteMapper.get(player);
		}
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
					handleCollision(cc.type, e);
				}
			}
		}
	}

	private void handleCollision(CollidableComponent.CollisionType ct, Entity e) {
		if (ct == CollisionType.POINT)
			score.incrementScore();
		if (ct == CollisionType.ORB || ct == CollisionType.POINT) {
			Deletable d = deleteMapper.get(e);
			d.needsDeleted = true;
			world.getManager(TubeManager.class).addTube();
		} else if (ct == CollisionType.WALL) {
			state.state = State.OVER;
			playerDeletion.needsDeleted = true;
		}
	}
}
// new EntityBuilder(world).with(new Position(centerX,
// centerY),
// new RenderConnectedPoints(Color.BLACK, .05f), new
// RecordConnectedPoints(20),
// new MovementSpeed(5f), new AngleDirection(1f), new
// AddConnectedPointsFromEntityPos()).build();
