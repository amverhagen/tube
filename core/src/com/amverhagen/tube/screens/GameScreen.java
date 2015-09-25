package com.amverhagen.tube.screens;

import com.amverhagen.tube.components.AddConnectedPointsFromEntityCenter;
import com.amverhagen.tube.components.RenderBody;
import com.amverhagen.tube.components.ForeGround;
import com.amverhagen.tube.components.CameraFocus;
import com.amverhagen.tube.components.Center;
import com.amverhagen.tube.components.CollidableComponent;
import com.amverhagen.tube.components.Renderable;
import com.amverhagen.tube.components.MovementDirection;
import com.amverhagen.tube.components.MovementSpeed;
import com.amverhagen.tube.components.PhysicsBody;
import com.amverhagen.tube.components.Position;
import com.amverhagen.tube.components.RecordConnectedPoints;
import com.amverhagen.tube.components.RenderConnectedPoints;
import com.amverhagen.tube.components.SetMoveDirectionBasedOnRightOrLeftPress;
import com.amverhagen.tube.components.CollidableComponent.CollisionType;
import com.amverhagen.tube.components.Deletable;
import com.amverhagen.tube.game.TubeGame;
import com.amverhagen.tube.managers.TubeManager;
import com.amverhagen.tube.systems.AddConnectedPointsFromEntityCenterSystem;
import com.amverhagen.tube.systems.CameraFocusSystem;
import com.amverhagen.tube.systems.CheckPlayerCollisionSystem;
import com.amverhagen.tube.systems.DeleteChildEntitySystem;
import com.amverhagen.tube.systems.DeleteEntitySystem;
import com.amverhagen.tube.systems.DrawingSystem;
import com.amverhagen.tube.systems.ForeGroundSystem;
import com.amverhagen.tube.systems.MoveInDirectionSystem;
import com.amverhagen.tube.systems.RenderConnectedPointsSystem;
import com.amverhagen.tube.systems.ScreenState;
import com.amverhagen.tube.systems.ScreenState.State;
import com.amverhagen.tube.systems.ShiftDirectionLeftOrRightByPressSystem;
import com.amverhagen.tube.systems.UpdateCenterSystem;
import com.amverhagen.tube.tween.SpriteAccessor;
import com.artemis.Entity;
import com.artemis.World;
import com.artemis.WorldConfiguration;
import com.artemis.managers.TagManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

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
	private TubeManager tubeManager;

	public GameScreen(TubeGame game) {
		this.game = game;
		this.gameState = new ScreenState(State.LOADING);
		this.tubeManager = new TubeManager(this.game);
		this.tweenManager = new TweenManager();
		Tween.registerAccessor(Sprite.class, new SpriteAccessor());
		WorldConfiguration worldConfig = new WorldConfiguration();
		worldConfig.setSystem(new DrawingSystem(game.gameBatch));
		worldConfig.setSystem(new ForeGroundSystem(game.gameBatch));
		worldConfig.setSystem(new RenderConnectedPointsSystem(game.shapeRenderer));
		worldConfig.setSystem(new MoveInDirectionSystem(gameState));
		worldConfig.setSystem(new UpdateCenterSystem(gameState));
		worldConfig.setSystem(ShiftDirectionLeftOrRightByPressSystem.class);
		worldConfig.setSystem(AddConnectedPointsFromEntityCenterSystem.class);
		worldConfig.setSystem(CameraFocusSystem.class);
		worldConfig.setSystem(new CheckPlayerCollisionSystem(gameState));
		worldConfig.setSystem(DeleteChildEntitySystem.class);
		worldConfig.setSystem(DeleteEntitySystem.class);
		worldConfig.setManager(new TagManager());
		worldConfig.setManager(tubeManager);

		world = new World(worldConfig);
		addPlayer();
	}

	private void addPlayer() {
		player = world.createEntity();
		Deletable dc = new Deletable(false);
		RenderBody renderBody = new RenderBody(.5f, .5f);
		PhysicsBody physicsBody = new PhysicsBody(.5f, .5f);
		ForeGround fg = new ForeGround();
		Position position = new Position(.25f, .25f);
		Center center = new Center(position, renderBody);
		CameraFocus cameraFocus = new CameraFocus(game.gameCamera);
		Renderable drawComp = new Renderable(new Texture(Gdx.files.internal("green_circle.png")));
		MovementSpeed speedComp = new MovementSpeed(8f);
		MovementDirection directionComp = new MovementDirection(MovementDirection.Direction.EAST);
		AddConnectedPointsFromEntityCenter pointsComp = new AddConnectedPointsFromEntityCenter();
		RecordConnectedPoints recordComp = new RecordConnectedPoints(20);
		RenderConnectedPoints renderPointsComp = new RenderConnectedPoints(Color.BLUE, .05f);
		SetMoveDirectionBasedOnRightOrLeftPress setDirectionComp = new SetMoveDirectionBasedOnRightOrLeftPress(
				game.gameCamera);
		CollidableComponent crc = new CollidableComponent(CollisionType.PLAYER);
		player.edit().add(physicsBody).add(fg).add(position).add(renderBody).add(center).add(cameraFocus).add(drawComp)
				.add(speedComp).add(directionComp).add(pointsComp).add(recordComp).add(renderPointsComp)
				.add(setDirectionComp).add(crc).add(dc);
		world.getManager(TagManager.class).register("PLAYER", player);
	}

	@Override
	public void show() {
		black = new Sprite(game.assManager.get("black.png", Texture.class));
		black.setPosition(-50, -50);
		black.setSize(100, 100);
		tubeManager.addTube();
		tubeManager.addTube();
		tubeManager.addTube();
		tubeManager.addTube();

		Tween.to(black, SpriteAccessor.ALPHA, .5f).target(0).start(tweenManager).setCallback(new TweenCallback() {
			@Override
			public void onEvent(int arg0, BaseTween<?> arg1) {
				gameState.state = State.RUNNING;
			}
		});
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 1f, 1);
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
