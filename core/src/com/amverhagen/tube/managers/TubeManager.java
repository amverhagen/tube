package com.amverhagen.tube.managers;

import com.amverhagen.tube.collections.LinkedListQueue;
import com.amverhagen.tube.components.CollidableComponent;
import com.amverhagen.tube.components.CollidableComponent.CollisionAction;
import com.amverhagen.tube.components.CollidableComponent.CollisionType;
import com.amverhagen.tube.components.Deletable;
import com.amverhagen.tube.components.DrawLineAroundBody;
import com.amverhagen.tube.components.HasParent;
import com.amverhagen.tube.components.PhysicsBody;
import com.amverhagen.tube.components.Position;
import com.amverhagen.tube.components.RenderBody;
import com.amverhagen.tube.entitymakers.CollidableMaker;
import com.amverhagen.tube.game.TubeGame;
import com.amverhagen.tube.tubes.Tube;
import com.amverhagen.tube.tubes.Tube.Direction;
import com.amverhagen.tube.tubes.Tube.Type;
import com.artemis.Entity;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class TubeManager extends com.artemis.Manager {
	private LinkedListQueue<Entity> activeTubes;
	private Tube firstTube;
	private Tube lastTube;
	private CollisionAction rightTutorialAction;
	private CollisionAction leftTutorialAction;
	private Entity lastTubeEntity;
	private TubeGame game;
	public Vector2 startingPoint;

	public TubeManager(TubeGame game) {
		this.game = game;
		activeTubes = new LinkedListQueue<Entity>();
		firstTube = new Tube(new Vector2(0, 0), Type.SHORT, Direction.EAST);
		startingPoint = firstTube.getCenter();
		rightTutorialAction = new CollisionAction() {
			@Override
			public void action() {
				world.getManager(TutorialPanelManager.class).showRightPanel();
			}
		};
		leftTutorialAction = new CollisionAction() {
			@Override
			public void action() {
				world.getManager(TutorialPanelManager.class).showLeftPanel();
			}
		};
	}

	public void restart() {
		while (!activeTubes.isEmpty()) {
			activeTubes.dequeue().deleteFromWorld();
		}
		this.addRandomTubeToWorld();
		if (game.tutorialOn) {
			restartWithTutorial();
		} else {
			this.resartWithoutTutorial();
		}
	}

	private void resartWithoutTutorial() {
		this.addRandomTubeToWorld();
		this.addRandomTubeToWorld();
		this.addRandomTubeToWorld();
		this.addRandomTubeToWorld();
	}

	private void restartWithTutorial() {
		Tube newTube = new Tube(lastTube, Type.CLOCK);
		addTubeToWorld(newTube);
		CollidableMaker.createTutorialCollision(world, rightTutorialAction, lastTubeEntity,
				new Vector2(lastTube.getCenter().x + PlayerManager.PLAYER_WIDTH / 2f, lastTube.getCenter().y));

		this.addRandomTubeToWorld();

		newTube = new Tube(lastTube, Type.COUNTER);
		addTubeToWorld(newTube);
		CollidableMaker.createTutorialCollision(world, leftTutorialAction, lastTubeEntity,
				new Vector2(lastTube.getCenter().x, lastTube.getCenter().y - PlayerManager.PLAYER_WIDTH / 2f));
	}

	public void addRandomTubeToWorld() {
		if (activeTubes.size() <= 0) {
			lastTubeEntity = firstTube.returnAsEntity(world, game.assManager.get("white.png", Texture.class));
			activeTubes.enqueue(lastTubeEntity);
			lastTube = firstTube;
		} else {
			if (activeTubes.size() >= 7) {
				activeTubes.dequeue().deleteFromWorld();
			}
			lastTube = new Tube(lastTube);
			lastTubeEntity = lastTube.returnAsEntity(world, game.assManager.get("white.png", Texture.class));
			activeTubes.enqueue(lastTubeEntity);
			createCollidableCenterAt(lastTube.getCenter(), lastTube.type, lastTubeEntity);
		}
	}

	private void addTubeToWorld(Tube tube) {
		if (activeTubes.size() >= 7) {
			activeTubes.dequeue().deleteFromWorld();
		}
		lastTube = tube;
		lastTubeEntity = lastTube.returnAsEntity(world, game.assManager.get("white.png", Texture.class));
		activeTubes.enqueue(lastTubeEntity);
		createCollidableCenterAt(lastTube.getCenter(), lastTube.type, lastTubeEntity);
	}

	private void createCollidableCenterAt(Vector2 center, Type t, Entity parent) {
		Entity tubeCenter = world.createEntity();
		HasParent hp = new HasParent(parent);
		PhysicsBody body = new PhysicsBody(Tube.TUBE_WIDTH, Tube.TUBE_WIDTH);
		RenderBody rb = new RenderBody(Tube.TUBE_WIDTH, Tube.TUBE_WIDTH);
		DrawLineAroundBody dlab = new DrawLineAroundBody();
		Position pos = new Position(center.x - (body.width / 2), center.y - (body.height / 2));
		CollidableComponent crc;
		if (t == Type.SHORT) {
			crc = new CollidableComponent(CollisionType.ORB, null);
		} else {
			crc = new CollidableComponent(CollisionType.POINT, null);
		}
		Deletable dc = new Deletable(false);
		tubeCenter.edit().add(body).add(pos).add(crc).add(dc).add(dlab).add(rb).add(hp);
	}
}
