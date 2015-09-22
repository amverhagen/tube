package com.amverhagen.tube.screens;

import java.util.ArrayList;

import com.amverhagen.tube.components.AddConnectedPointsFromEntityCenter;
import com.amverhagen.tube.components.DrawingDimension;
import com.amverhagen.tube.components.CameraFocus;
import com.amverhagen.tube.components.Center;
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
import com.amverhagen.tube.systems.CameraFocusSystem;
import com.amverhagen.tube.systems.DrawingSystem;
import com.amverhagen.tube.systems.MoveInDirectionSystem;
import com.amverhagen.tube.systems.RenderConnectedPointsSystem;
import com.amverhagen.tube.systems.ScreenState;
import com.amverhagen.tube.systems.ScreenState.State;
import com.amverhagen.tube.systems.ShiftDirectionLeftOrRightByPressSystem;
import com.amverhagen.tube.systems.UpdateCenterSystem;
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

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;

public class GameScreen implements Screen {
	private ScreenState gameState;
	private TweenManager tweenManager;
	private Sprite black;
	private TubeGame game;
	private World world;
	private Entity player;
	private Vector2 startPos;

	public GameScreen(TubeGame game) {
		this.game = game;
		this.gameState = new ScreenState(State.LOADING);
		tweenManager = new TweenManager();
		Tween.registerAccessor(Sprite.class, new SpriteAccessor());
		WorldConfiguration worldConfig = new WorldConfiguration();
		worldConfig.setSystem(new DrawingSystem(game.gameBatch));
		worldConfig.setSystem(new RenderConnectedPointsSystem(game.shapeRenderer));
		worldConfig.setSystem(new MoveInDirectionSystem(gameState));
		worldConfig.setSystem(new UpdateCenterSystem(gameState));
		worldConfig.setSystem(ShiftDirectionLeftOrRightByPressSystem.class);
		worldConfig.setSystem(AddConnectedPointsFromEntityCenterSystem.class);
		worldConfig.setSystem(CameraFocusSystem.class);

		world = new World(worldConfig);
		createPipes();
		addPlayer();

	}

	private void createPipes() {
		ArrayList<Tube> tubes = ConnectedTubeMaker.makeConnectedTubes(100, new Vector2(0, 0));
		startPos = tubes.get(0).getCenter();
		for (Tube t : tubes) {
			this.addTubeToWorld(t);
		}
	}

	private void addPlayer() {
		player = world.createEntity();
		DrawingDimension drawDimension = new DrawingDimension(.5f, .5f);
		Position position = new Position(startPos.x - (drawDimension.width / 2),
				startPos.y - (drawDimension.height / 2));
		Center center = new Center(position, drawDimension);
		CameraFocus cameraFocus = new CameraFocus(game.gameCamera);
		Drawable drawComp = new Drawable(new Texture(Gdx.files.internal("green_circle.png")));
		MovementSpeed speedComp = new MovementSpeed(6f);
		MovementDirection directionComp = new MovementDirection(MovementDirection.Direction.EAST);
		AddConnectedPointsFromEntityCenter pointsComp = new AddConnectedPointsFromEntityCenter();
		RecordConnectedPoints recordComp = new RecordConnectedPoints(20);
		RenderConnectedPoints renderPointsComp = new RenderConnectedPoints(Color.BLUE, .05f);
		SetMoveDirectionBasedOnRightOrLeftPress setDirectionComp = new SetMoveDirectionBasedOnRightOrLeftPress(
				game.gameCamera);
		player.edit().add(position).add(drawDimension).add(center).add(cameraFocus).add(drawComp).add(speedComp)
				.add(directionComp).add(pointsComp).add(recordComp).add(renderPointsComp).add(setDirectionComp);
	}

	public void addTubeToWorld(Tube t) {
		Entity tube = world.createEntity();
		DrawLineAroundBody dlac = new DrawLineAroundBody();
		Drawable dc = new Drawable(game.assManager.get("black.png", Texture.class));
		Position pc = new Position(t.getPosition());
		DrawingDimension ddc = new DrawingDimension(t.getBounds());
		tube.edit().add(dc).add(pc).add(ddc).add(dlac);
	}

	@Override
	public void show() {
		black = new Sprite(game.assManager.get("black.png", Texture.class));
		black.setPosition(-50, -50);
		black.setSize(100, 100);

		Tween.to(black, SpriteAccessor.ALPHA, .5f).target(0).start(tweenManager).setCallback(new TweenCallback() {
			@Override
			public void onEvent(int arg0, BaseTween<?> arg1) {
				gameState.state = State.RUNNING;
			}
		});
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		game.gameBatch.setProjectionMatrix(game.viewport.getCamera().combined);
		game.shapeRenderer.setProjectionMatrix(game.viewport.getCamera().combined);
		if (delta > .1f)
			delta = .1f;
		world.setDelta(delta);
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
	}

	@Override
	public void resume() {
	}

	@Override
	public void hide() {
	}

	@Override
	public void dispose() {
	}

}
