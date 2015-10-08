package com.amverhagen.tube.managers;

import com.amverhagen.tube.collections.LinkedListQueue;
import com.amverhagen.tube.components.CollidableComponent;
import com.amverhagen.tube.components.Position;
import com.amverhagen.tube.components.RenderBody;
import com.amverhagen.tube.components.CollidableComponent.CollisionType;
import com.amverhagen.tube.components.Deletable;
import com.amverhagen.tube.components.DrawLineAroundBody;
import com.amverhagen.tube.components.PhysicsBody;
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

	public void start() {
		this.addTube();
		this.addTube();
		this.addTube();
		this.addTube();
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
			createCollidableCenterAt(newTube.getCenter(), newTube.type);
			lastTube = newTube;
		}
	}

	private void createCollidableCenterAt(Vector2 center, Type t) {
		Entity tubeCenter = world.createEntity();
		PhysicsBody body = new PhysicsBody(Tube.TUBE_WIDTH, Tube.TUBE_WIDTH);
		RenderBody rb = new RenderBody(Tube.TUBE_WIDTH, Tube.TUBE_WIDTH);
		DrawLineAroundBody dlab = new DrawLineAroundBody();
		Position pos = new Position(center.x - (body.width / 2), center.y - (body.height / 2));
		CollidableComponent crc;
		if (t == Type.SHORT) {
			crc = new CollidableComponent(CollisionType.ORB);
		} else {
			crc = new CollidableComponent(CollisionType.POINT);
		}
		Deletable dc = new Deletable(false);
		tubeCenter.edit().add(body).add(pos).add(crc).add(dc).add(dlab).add(rb);
	}
}
