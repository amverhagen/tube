package com.amverhagen.tube.managers;

import com.amverhagen.tube.collections.LinkedListQueue;
import com.amverhagen.tube.components.CollidableEntity;
import com.amverhagen.tube.components.Position;
import com.amverhagen.tube.components.CollidableEntity.CollisionType;
import com.amverhagen.tube.game.TubeGame;
import com.amverhagen.tube.tubes.Tube.Direction;
import com.amverhagen.tube.tubes.Tube;
import com.amverhagen.tube.tubes.Tube.Type;
import com.artemis.Entity;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class TubeManager extends com.artemis.managers.GroupManager {
	private LinkedListQueue<Entity> activeTubes;
	private Tube lastTube;
	private TubeGame game;

	public TubeManager(TubeGame game) {
		this.game = game;
		activeTubes = new LinkedListQueue<Entity>();

	}

	public void addTube() {
		if (activeTubes.size() <= 0) {
			lastTube = new Tube(new Vector2(0, 0), Type.SHORT, Direction.EAST);
			Entity tube = lastTube.returnAsEntity(world, game.assManager.get("white.png", Texture.class));
			activeTubes.enqueue(tube);
		} else {
			if (activeTubes.size() >= 7) {
				activeTubes.dequeue().deleteFromWorld();
			}
			Tube newTube = new Tube(lastTube);
			Entity eTube = newTube.returnAsEntity(world, game.assManager.get("white.png", Texture.class));
			activeTubes.enqueue(eTube);
			createCollidableCenterAt(newTube.getCenter());
			lastTube = newTube;
		}
	}

	private void createCollidableCenterAt(Vector2 center) {
		Entity tubeCenter = world.createEntity();
		CollidableEntity crc = new CollidableEntity(2f, 2f, CollisionType.ORB);
		Position pc = new Position(center.x - (crc.width / 2), center.y - (crc.height / 2));
		tubeCenter.edit().add(pc).add(crc);
	}
}
