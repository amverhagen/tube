package com.amverhagen.tube.managers;

import com.amverhagen.tube.components.AddConnectedPointsFromEntityCenter;
import com.amverhagen.tube.components.CameraFocus;
import com.amverhagen.tube.components.Center;
import com.amverhagen.tube.components.Deletable;
import com.amverhagen.tube.components.DrawShape;
import com.amverhagen.tube.components.MovementDirection;
import com.amverhagen.tube.components.MovementSpeed;
import com.amverhagen.tube.components.PhysicsBody;
import com.amverhagen.tube.components.Position;
import com.amverhagen.tube.components.RecordConnectedPoints;
import com.amverhagen.tube.components.RenderBody;
import com.amverhagen.tube.game.TubeGame;
import com.amverhagen.tube.tubes.Tube;
import com.artemis.Entity;
import com.artemis.managers.TagManager;
import com.badlogic.gdx.math.Vector2;

public class PlayerManager extends com.artemis.Manager {
	private TubeGame game;
	private Vector2 startingPoint;
	public Entity player;

	public PlayerManager(TubeGame game, Vector2 startingPoint) {
		this.game = game;
		this.startingPoint = startingPoint;
	}

	public void restart() {
		player = null;
		createPlayer();
	}

	private void createPlayer() {
		player = world.createEntity();
		Deletable dc = new Deletable(false);
		RenderBody renderBody = new RenderBody((Tube.TUBE_WIDTH / 5) * 2, (Tube.TUBE_WIDTH / 5) * 2);
		PhysicsBody physicsBody = new PhysicsBody((Tube.TUBE_WIDTH / 5) * 2, (Tube.TUBE_WIDTH / 5) * 2);
		Position position = new Position(startingPoint.x - renderBody.width / 2,
				startingPoint.y - renderBody.height / 2);
		Center center = new Center(position, renderBody);
		CameraFocus cameraFocus = new CameraFocus(game.gameCamera);
		MovementSpeed speedComp = new MovementSpeed(950f);
		MovementDirection directionComp = new MovementDirection(MovementDirection.Direction.EAST);
		AddConnectedPointsFromEntityCenter pointsComp = new AddConnectedPointsFromEntityCenter();
		RecordConnectedPoints recordComp = new RecordConnectedPoints(20);
		DrawShape renderPointsComp = new DrawShape(game.background, 5f);
		player.edit().add(physicsBody).add(position).add(renderBody).add(center).add(cameraFocus).add(speedComp)
				.add(directionComp).add(pointsComp).add(recordComp).add(renderPointsComp).add(dc);
		world.getManager(TagManager.class).register("PLAYER", player);
	}
}
