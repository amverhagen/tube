package com.amverhagen.tube.screens;

import com.amverhagen.tube.components.AddConnectedPointsFromEntityCenter;
import com.amverhagen.tube.components.CameraFocus;
import com.amverhagen.tube.components.Center;
import com.amverhagen.tube.components.CollidableComponent;
import com.amverhagen.tube.components.CollidableComponent.CollisionType;
import com.amverhagen.tube.components.Deletable;
import com.amverhagen.tube.components.DrawShape;
import com.amverhagen.tube.components.DrawToForeground;
import com.amverhagen.tube.components.MovementDirection;
import com.amverhagen.tube.components.MovementSpeed;
import com.amverhagen.tube.components.PhysicsBody;
import com.amverhagen.tube.components.Position;
import com.amverhagen.tube.components.RecordConnectedPoints;
import com.amverhagen.tube.components.RenderBody;
import com.amverhagen.tube.components.SetMoveDirectionBasedOnRightOrLeftPress;
import com.amverhagen.tube.components.SpriteComponent;
import com.amverhagen.tube.game.TubeGame;
import com.amverhagen.tube.managers.TubeManager;
import com.amverhagen.tube.systems.AddConnectedPointsFromEntityCenterSystem;
import com.amverhagen.tube.systems.BindSpriteToPositionSystem;
import com.amverhagen.tube.systems.CameraFocusSystem;
import com.amverhagen.tube.systems.CheckPlayerCollisionSystem;
import com.amverhagen.tube.systems.DeleteChildEntitySystem;
import com.amverhagen.tube.systems.DeleteEntitySystem;
import com.amverhagen.tube.systems.DrawConnectedPointsSystem;
import com.amverhagen.tube.systems.DrawToBackgroundSystem;
import com.amverhagen.tube.systems.DrawToForegroundSystem;
import com.amverhagen.tube.systems.DrawToUISystem;
import com.amverhagen.tube.systems.FadeSystem;
import com.amverhagen.tube.systems.MoveInDirectionSystem;
import com.amverhagen.tube.systems.ScreenState;
import com.amverhagen.tube.systems.ScreenState.State;
import com.amverhagen.tube.systems.ShiftDirectionLeftOrRightByPressSystem;
import com.amverhagen.tube.systems.UiClickSystem;
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
		this.world = new World();
		this.tweenManager = new TweenManager();
		Tween.registerAccessor(Sprite.class, new SpriteAccessor());
	}

	private void createWorld() {
		WorldConfiguration worldConfig = new WorldConfiguration();
		worldConfig.setSystem(BindSpriteToPositionSystem.class);
		worldConfig.setSystem(new FadeSystem(gameState, tweenManager));
		worldConfig.setSystem(new UiClickSystem(game.uiCamera, gameState));
		worldConfig.setSystem(new DrawToBackgroundSystem(game.gameBatch));
		worldConfig.setSystem(new DrawConnectedPointsSystem(game.shapeRenderer, gameState));
		worldConfig.setSystem(new DrawToUISystem(game.gameBatch, game.uiCamera));
		worldConfig.setSystem(new DrawToForegroundSystem(game.gameBatch));
		worldConfig.setSystem(new MoveInDirectionSystem(gameState));
		worldConfig.setSystem(new UpdateCenterSystem(gameState));
		worldConfig.setSystem(AddConnectedPointsFromEntityCenterSystem.class);
		worldConfig.setSystem(new ShiftDirectionLeftOrRightByPressSystem(gameState));
		worldConfig.setSystem(CameraFocusSystem.class);
		worldConfig.setSystem(new CheckPlayerCollisionSystem(gameState));
		worldConfig.setSystem(DeleteChildEntitySystem.class);
		worldConfig.setSystem(DeleteEntitySystem.class);
		worldConfig.setManager(new TagManager());
		worldConfig.setManager(tubeManager);
		world = new World(worldConfig);
	}

	private void createPlayer() {
		player = world.createEntity();
		Deletable dc = new Deletable(false);
		Position position = new Position(.25f, .25f);
		RenderBody renderBody = new RenderBody(.5f, .5f);
		PhysicsBody physicsBody = new PhysicsBody(.5f, .5f);
		Center center = new Center(position, renderBody);
		CameraFocus cameraFocus = new CameraFocus(game.gameCamera);
		MovementSpeed speedComp = new MovementSpeed(8f);
		MovementDirection directionComp = new MovementDirection(MovementDirection.Direction.EAST);
		AddConnectedPointsFromEntityCenter pointsComp = new AddConnectedPointsFromEntityCenter();
		RecordConnectedPoints recordComp = new RecordConnectedPoints(20);
		DrawShape renderPointsComp = new DrawShape(Color.BLUE, .05f);
		SetMoveDirectionBasedOnRightOrLeftPress setDirectionComp = new SetMoveDirectionBasedOnRightOrLeftPress();
		CollidableComponent crc = new CollidableComponent(CollisionType.PLAYER);
		player.edit().add(physicsBody).add(position).add(renderBody).add(center).add(cameraFocus).add(speedComp)
				.add(directionComp).add(pointsComp).add(recordComp).add(renderPointsComp).add(setDirectionComp).add(crc)
				.add(dc);
		world.getManager(TagManager.class).register("PLAYER", player);
	}

	public void createBackground() {
		Entity e = world.createEntity();
		black = new Sprite(game.assManager.get("black.png", Texture.class));
		black.setBounds(0, 0, 10, 10);
		SpriteComponent sc = new SpriteComponent(black);
		DrawToForeground dtfc = new DrawToForeground();
		e.edit().add(sc).add(dtfc);
	}

	private void fadeIn() {
		this.gameState.state = State.FADING;
		Tween.to(black, SpriteAccessor.ALPHA, .5f).target(0).start(tweenManager).setCallback(new TweenCallback() {
			@Override
			public void onEvent(int arg0, BaseTween<?> arg1) {
				gameState.state = State.RUNNING;
			}
		});
	}

	private void fadeOut() {
		this.gameState.state = State.FADING;
		Tween.to(black, SpriteAccessor.ALPHA, .5f).target(1).start(tweenManager).setCallback(new TweenCallback() {
			@Override
			public void onEvent(int arg0, BaseTween<?> arg1) {
				game.setToScoreScreen();
			}
		});
	}

	@Override
	public void show() {
		this.gameState = new ScreenState(State.PAUSED);
		this.tubeManager = new TubeManager(this.game);
		createWorld();
		createPlayer();
		createBackground();
		tubeManager.start();
		this.fadeIn();
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(game.background.r, game.background.g, game.background.b, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		game.gameBatch.setProjectionMatrix(game.viewport.getCamera().combined);
		game.shapeRenderer.setProjectionMatrix(game.viewport.getCamera().combined);
		if (delta > .1f)
			delta = .1f;
		world.setDelta(delta);
		world.process();
		if (gameState.state == State.OVER)
			fadeOut();
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
		world.dispose();
	}
}

//
// private void createLabel() {
// Entity e = world.createEntity();
// Position pc = new Position(0, 0);
// RenderBody ddc = new RenderBody(1f, 1f);
// SpriteComponent sc = new SpriteComponent(new
// Sprite(game.assManager.get("white.png", Texture.class)));
// sc.sprite.setBounds(0, 0, 1f, 1f);
// DrawToUI dui = new DrawToUI();
// Clickable cc = new Clickable(State.PAUSED, new Event() {
// @Override
// public void action() {
// show();
// }
// });
// e.edit().add(sc).add(dui).add(pc).add(ddc).add(cc);
// }
