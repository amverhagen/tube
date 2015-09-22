package com.amverhagen.tube.screens;

import java.util.ArrayList;

import com.amverhagen.tube.collections.LinkedListQueue;
import com.amverhagen.tube.components.AddConnectedPointsFromEntityCenter;
import com.amverhagen.tube.components.DrawingDimension;
import com.amverhagen.tube.components.CameraFocus;
import com.amverhagen.tube.components.Center;
import com.amverhagen.tube.components.Course;
import com.amverhagen.tube.components.DrawLineAroundBody;
import com.amverhagen.tube.components.Drawable;
import com.amverhagen.tube.components.MovementDirection;
import com.amverhagen.tube.components.MovementSpeed;
import com.amverhagen.tube.components.Position;
import com.amverhagen.tube.components.RecordConnectedPoints;
import com.amverhagen.tube.components.RenderConnectedPoints;
import com.amverhagen.tube.components.SetMoveDirectionBasedOnRightOrLeftPress;
import com.amverhagen.tube.game.TubeGame;
import com.amverhagen.tube.systems.AddConnectedPointsFromEntityCenterSystem;
import com.amverhagen.tube.systems.AddConnectedPointsFromEntityPosSystem;
import com.amverhagen.tube.systems.CameraFocusSystem;
import com.amverhagen.tube.systems.DrawingSystem;
import com.amverhagen.tube.systems.MoveInAngleDirectionSystem;
import com.amverhagen.tube.systems.MoveInDirectionSystem;
import com.amverhagen.tube.systems.RenderConnectedPointsSystem;
import com.amverhagen.tube.systems.ShiftDirectionLeftOrRightByPressSystem;
import com.amverhagen.tube.tubes.ConnectedTubeMaker;
import com.amverhagen.tube.tubes.Tube;
import com.amverhagen.tube.tween.SpriteAccessor;
import com.artemis.Entity;
import com.artemis.World;
import com.artemis.WorldConfiguration;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;

public class GameScreen implements Screen {
	private TweenManager tweenManager;
	private Sprite black;
	private TubeGame game;
	private World world;
	private Entity player;
	private LinkedListQueue<Vector2> course;

	public GameScreen(TubeGame game) {
		this.game = game;
		tweenManager = new TweenManager();
		Tween.registerAccessor(Sprite.class, new SpriteAccessor());
		WorldConfiguration worldConfig = new WorldConfiguration();
		worldConfig.setSystem(new DrawingSystem(game.gameBatch));
		worldConfig.setSystem(new RenderConnectedPointsSystem(game.shapeRenderer));
		worldConfig.setSystem(MoveInDirectionSystem.class);
		worldConfig.setSystem(ShiftDirectionLeftOrRightByPressSystem.class);
		worldConfig.setSystem(AddConnectedPointsFromEntityCenterSystem.class);
		worldConfig.setSystem(CameraFocusSystem.class);
		worldConfig.setSystem(AddConnectedPointsFromEntityPosSystem.class);
		worldConfig.setSystem(MoveInAngleDirectionSystem.class);

		world = new World(worldConfig);
		createPipes();
		addPlayer();

	}

	private void createPipes() {
		course = new LinkedListQueue<Vector2>();
		ArrayList<Tube> tubes = ConnectedTubeMaker.makeConnectedTubes(100, new Vector2(0, 0));
		for (Tube t : tubes) {
			this.addTubeToWorld(t);
		}
	}

	private void addPlayer() {
		player = world.createEntity();
		Position position = new Position(course.dequeue());
		DrawingDimension drawDimension = new DrawingDimension(.5f, .5f);
		CameraFocus cameraFocus = new CameraFocus(game.gameCamera);
		Course courseComp = new Course(course);
		Drawable drawComp = new Drawable(new Texture(Gdx.files.internal("green_circle.png")));
		MovementSpeed speedComp = new MovementSpeed(6f);
		MovementDirection directionComp = new MovementDirection(MovementDirection.Direction.EAST);
		AddConnectedPointsFromEntityCenter pointsComp = new AddConnectedPointsFromEntityCenter();
		RecordConnectedPoints recordComp = new RecordConnectedPoints(20);
		RenderConnectedPoints renderPointsComp = new RenderConnectedPoints(Color.BLUE, .05f);
		SetMoveDirectionBasedOnRightOrLeftPress setDirectionComp = new SetMoveDirectionBasedOnRightOrLeftPress(
				game.gameCamera);
		player.edit().add(position).add(drawDimension).add(cameraFocus).add(courseComp).add(drawComp).add(speedComp)
				.add(directionComp).add(pointsComp).add(recordComp).add(renderPointsComp).add(setDirectionComp);
	}

	public void addTubeToWorld(Tube t) {
		Entity tube = world.createEntity();
		DrawLineAroundBody dlac = new DrawLineAroundBody();
		Drawable dc = new Drawable(new Texture(Gdx.files.internal("black.png")));
		Position pc = new Position(t.getPosition());
		DrawingDimension ddc = new DrawingDimension(t.getBounds());
		Center cc = new Center(new Vector2(pc.x, pc.y), new Vector2(ddc.width, ddc.height));
		course.enqueue(cc.center);
		tube.edit().add(dc).add(pc).add(ddc).add(dlac).add(cc);
	}

	@Override
	public void show() {
		black = new Sprite(new Texture(Gdx.files.internal("black.png")));
		black.setPosition(-50, -50);
		black.setSize(100, 100);

		Tween.to(black, SpriteAccessor.ALPHA, .5f).target(0).start(tweenManager);

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		game.gameBatch.setProjectionMatrix(game.viewport.getCamera().combined);
		game.shapeRenderer.setProjectionMatrix(game.viewport.getCamera().combined);
		world.process();
		game.gameBatch.begin();
		black.draw(game.gameBatch);
		game.gameBatch.end();
		tweenManager.update(delta);
	}

	@Override
	public void resize(int width, int height) {
		game.viewport.setScreenSize(width, height);
		game.viewport.apply();
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
