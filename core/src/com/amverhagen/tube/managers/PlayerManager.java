package com.amverhagen.tube.managers;

import com.amverhagen.tube.components.AddConnectedPointsFromEntityCenter;
import com.amverhagen.tube.components.CameraFocus;
import com.amverhagen.tube.components.Center;
import com.amverhagen.tube.components.DrawShape;
import com.amverhagen.tube.components.MovementDirection;
import com.amverhagen.tube.components.MovementSpeed;
import com.amverhagen.tube.components.PhysicsBody;
import com.amverhagen.tube.components.Position;
import com.amverhagen.tube.components.RecordConnectedPoints;
import com.amverhagen.tube.components.RenderBody;
import com.amverhagen.tube.components.MovementDirection.Direction;
import com.amverhagen.tube.game.TubeGame;
import com.amverhagen.tube.tubes.Tube;
import com.artemis.Entity;
import com.badlogic.gdx.math.Vector2;

public class PlayerManager extends com.artemis.Manager {
	public static final float PLAYER_WIDTH = (Tube.TUBE_WIDTH / 5) * 2;
	private TubeGame game;
	private Vector2 startingPoint;
	public Entity player;

	public PlayerManager(TubeGame game, Vector2 startingPoint) {
		this.game = game;
		this.startingPoint = startingPoint;
	}

	protected void initialize() {
		player = world.createEntity();
		createPlayer();
	}

	public void restart() {
		RenderBody renderBody = player.getComponent(RenderBody.class);
		Position playerPosition = player.getComponent(Position.class);
		playerPosition.x = (startingPoint.x - renderBody.width / 2);
		playerPosition.y = (startingPoint.y - renderBody.height / 2);
		player.getComponent(MovementDirection.class).direction = Direction.EAST;
		player.edit().remove(RecordConnectedPoints.class);
		player.edit().add(new RecordConnectedPoints(20));
	}

	private void createPlayer() {
		RenderBody renderBody = new RenderBody(PLAYER_WIDTH, PLAYER_WIDTH);
		PhysicsBody physicsBody = new PhysicsBody(PLAYER_WIDTH, PLAYER_WIDTH);
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
				.add(directionComp).add(pointsComp).add(recordComp).add(renderPointsComp);
	}

	public void shiftPlayerLeft() {
		player.getComponent(MovementDirection.class).shiftDirectionLeft();
	}

	public void shiftPlayerRight() {
		player.getComponent(MovementDirection.class).shiftDirectionRight();
	}
}
