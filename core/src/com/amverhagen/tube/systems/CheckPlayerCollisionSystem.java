package com.amverhagen.tube.systems;

import com.amverhagen.tube.components.AddConnectedPointsFromEntityPos;
import com.amverhagen.tube.components.AngleDirection;
import com.amverhagen.tube.components.Bar;
import com.amverhagen.tube.components.CollidableRectangle;
import com.amverhagen.tube.components.MovementSpeed;
import com.amverhagen.tube.components.Position;
import com.amverhagen.tube.components.RecordConnectedPoints;
import com.amverhagen.tube.components.RenderConnectedPoints;
import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.artemis.managers.TagManager;
import com.artemis.utils.EntityBuilder;
import com.badlogic.gdx.graphics.Color;

public class CheckPlayerCollisionSystem extends com.artemis.systems.EntityProcessingSystem {
	@Wire
	ComponentMapper<CollidableRectangle> rectangleMapper;
	@Wire
	ComponentMapper<Position> positionMapper;

	private Entity player;
	private Position playerPos;
	private CollidableRectangle playerBounds;

	@SuppressWarnings("unchecked")
	public CheckPlayerCollisionSystem() {
		super(Aspect.all(CollidableRectangle.class, Position.class, Bar.class));

	}

	@Override
	protected void begin() {
		player = world.getManager(TagManager.class).getEntity("PLAYER");
		if (player != null) {
			playerPos = player.getComponent(Position.class);
			playerBounds = player.getComponent(CollidableRectangle.class);
		}

	}

	@Override
	protected void process(Entity e) {
		if (player != null) {
			Position posComp = positionMapper.get(e);
			CollidableRectangle boundsComp = rectangleMapper.get(e);
			if ((playerPos.x + playerBounds.width) > (posComp.x) && (playerPos.x) < (posComp.x + boundsComp.width)
					&& (playerPos.y + playerBounds.height) > (posComp.y)
					&& (playerPos.y) < (posComp.y + boundsComp.height)) {

				player.deleteFromWorld();
				float centerX = playerPos.x + (playerBounds.width / 2);
				float centerY = playerPos.y + (playerBounds.height / 2);
				new EntityBuilder(world).with(new Position(centerX, centerY),
						new RenderConnectedPoints(Color.BLACK, .05f), new RecordConnectedPoints(20),
						new MovementSpeed(5f), new AngleDirection(1f), new AddConnectedPointsFromEntityPos()).build();
				new EntityBuilder(world).with(new Position(centerX, centerY),
						new RenderConnectedPoints(Color.BLACK, .05f), new RecordConnectedPoints(20),
						new MovementSpeed(5f), new AngleDirection(0f), new AddConnectedPointsFromEntityPos()).build();
				new EntityBuilder(world).with(new Position(centerX, centerY),
						new RenderConnectedPoints(Color.BLACK, .05f), new RecordConnectedPoints(20),
						new MovementSpeed(5f), new AngleDirection(2f), new AddConnectedPointsFromEntityPos()).build();
				new EntityBuilder(world).with(new Position(centerX, centerY),
						new RenderConnectedPoints(Color.BLACK, .05f), new RecordConnectedPoints(20),
						new MovementSpeed(5f), new AngleDirection(3f), new AddConnectedPointsFromEntityPos()).build();
				new EntityBuilder(world).with(new Position(centerX, centerY),
						new RenderConnectedPoints(Color.BLACK, .05f), new RecordConnectedPoints(20),
						new MovementSpeed(5f), new AngleDirection(4f), new AddConnectedPointsFromEntityPos()).build();
				new EntityBuilder(world).with(new Position(centerX, centerY),
						new RenderConnectedPoints(Color.BLACK, .05f), new RecordConnectedPoints(20),
						new MovementSpeed(5f), new AngleDirection(5f), new AddConnectedPointsFromEntityPos()).build();
			}
		}
	}

}
