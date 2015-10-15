package com.amverhagen.tube.screens;

import com.amverhagen.tube.components.Clickable;
import com.amverhagen.tube.components.Clickable.Event;
import com.amverhagen.tube.components.DisplayScore;
import com.amverhagen.tube.components.DrawDuringState;
import com.amverhagen.tube.components.DrawToBackground;
import com.amverhagen.tube.components.DrawToForeground;
import com.amverhagen.tube.components.DrawToUI;
import com.amverhagen.tube.components.MovementDirection;
import com.amverhagen.tube.components.Position;
import com.amverhagen.tube.components.RenderBody;
import com.amverhagen.tube.components.SpriteComponent;
import com.amverhagen.tube.components.Text;
import com.amverhagen.tube.entitymakers.ButtonMaker;
import com.amverhagen.tube.game.Score;
import com.amverhagen.tube.game.TubeGame;
import com.amverhagen.tube.managers.PlayerManager;
import com.amverhagen.tube.managers.TubeManager;
import com.amverhagen.tube.systems.AddConnectedPointsFromEntityCenterSystem;
import com.amverhagen.tube.systems.BindScoreToLabelSystem;
import com.amverhagen.tube.systems.CameraFocusSystem;
import com.amverhagen.tube.systems.CheckPlayerCollisionSystem;
import com.amverhagen.tube.systems.DeleteChildEntitySystem;
import com.amverhagen.tube.systems.DeleteEntitySystem;
import com.amverhagen.tube.systems.DrawConnectedPointsSystem;
import com.amverhagen.tube.systems.DrawTextSystem;
import com.amverhagen.tube.systems.DrawToBackgroundSystem;
import com.amverhagen.tube.systems.DrawToForegroundSystem;
import com.amverhagen.tube.systems.DrawToGameSystem;
import com.amverhagen.tube.systems.DrawToUISystem;
import com.amverhagen.tube.systems.FadeSystem;
import com.amverhagen.tube.systems.MoveInDirectionSystem;
import com.amverhagen.tube.systems.RenderBoxSystem;
import com.amverhagen.tube.systems.ScreenState;
import com.amverhagen.tube.systems.ScreenState.State;
import com.amverhagen.tube.systems.UiClickSystem;
import com.amverhagen.tube.systems.UpdateCenterSystem;
import com.amverhagen.tube.tween.SpriteAccessor;
import com.artemis.Entity;
import com.artemis.World;
import com.artemis.WorldConfiguration;
import com.artemis.managers.TagManager;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;

public class GameScreen implements Screen {
	private ScreenState gameState;
	private ScreenState pastState;
	private TweenManager tweenManager;
	private Sprite black;
	private TubeGame game;
	private World world;
	private PlayerManager playerManager;
	private Entity fade;
	private TubeManager tubeManager;
	private Score currentScore;

	public GameScreen(TubeGame game) {
		this.game = game;
		this.tweenManager = new TweenManager();
		this.tubeManager = new TubeManager(this.game);
		this.playerManager = new PlayerManager(game, tubeManager.startingPoint);
		Tween.registerAccessor(Sprite.class, new SpriteAccessor());
	}

	private void createWorld() {
		WorldConfiguration worldConfig = new WorldConfiguration();
		worldConfig.setSystem(new FadeSystem(gameState, tweenManager));
		worldConfig.setSystem(new UiClickSystem(game.uiViewport, gameState));
		worldConfig.setSystem(new DrawToBackgroundSystem(game.gameBatch, game.uiCamera));
		worldConfig.setSystem(new DrawToGameSystem(game.gameBatch, game.gameCamera));
		worldConfig.setSystem(new DrawConnectedPointsSystem(game.shapeRenderer, gameState));
		worldConfig.setSystem(new DrawToUISystem(game.gameBatch, game.uiCamera));
		worldConfig.setSystem(new DrawTextSystem(game.gameBatch, this.gameState));
		worldConfig.setSystem(new RenderBoxSystem(game.shapeRenderer));
		worldConfig.setSystem(new DrawToForegroundSystem(game.gameBatch));
		worldConfig.setSystem(new CheckPlayerCollisionSystem(gameState, currentScore));
		worldConfig.setSystem(new BindScoreToLabelSystem());
		worldConfig.setSystem(new MoveInDirectionSystem(gameState));
		worldConfig.setSystem(new UpdateCenterSystem(gameState));
		worldConfig.setSystem(AddConnectedPointsFromEntityCenterSystem.class);
		worldConfig.setSystem(CameraFocusSystem.class);
		worldConfig.setSystem(DeleteChildEntitySystem.class);
		worldConfig.setSystem(DeleteEntitySystem.class);
		worldConfig.setManager(new TagManager());
		worldConfig.setManager(tubeManager);
		worldConfig.setManager(playerManager);
		world = new World(worldConfig);
	}

	private void createLeftClickPanel() {
		Event e = new Event() {
			@Override
			public void action() {
				playerManager.player.getComponent(MovementDirection.class).shiftDirectionLeft();
			}
		};
		Entity leftClick = world.createEntity();
		Position pos = new Position(0, 0);
		RenderBody rd = new RenderBody(TubeGame.GAME_WIDTH / 2, TubeGame.GAME_HEIGHT);
		Text t = new Text("    Press left side to\nrotate counter-clockwise",
				new Vector2(TubeGame.GAME_WIDTH * .25f, TubeGame.GAME_HEIGHT * .75f),
				game.fonts.getFont(game.background, 48));
		Sprite white = new Sprite(game.assManager.get("white.png", Texture.class));
		white.setBounds(0, 0, rd.width, rd.height);
		white.setAlpha(.3f);
		SpriteComponent sc = new SpriteComponent(white);
		DrawToUI dtui = new DrawToUI();
		Clickable cl = new Clickable(State.RUNNING, e, true);
		leftClick.edit().add(pos).add(rd).add(cl).add(t).add(sc).add(dtui);

		Entity icon = world.createEntity();
		RenderBody iconBody = new RenderBody(TubeGame.GAME_WIDTH * .10f, TubeGame.GAME_WIDTH * .10f);
		Position iconPos = new Position((TubeGame.GAME_WIDTH * .25f) - iconBody.width / 2, TubeGame.GAME_HEIGHT * .4f);
		Sprite iconSprite = new Sprite(game.assManager.get("replay.png", Texture.class));
		iconSprite.setColor(game.background);
		iconSprite.flip(true, false);
		iconSprite.setBounds(iconPos.x, iconPos.y, iconBody.width, iconBody.height);
		SpriteComponent iconSC = new SpriteComponent(iconSprite);
		icon.edit().add(dtui).add(iconBody).add(iconPos).add(iconSC);

		Vector2 buttonBody = new Vector2(TubeGame.GAME_WIDTH * .10f, TubeGame.GAME_HEIGHT * .10f);
		Vector2 buttonPos = new Vector2((TubeGame.GAME_WIDTH * .25f) - buttonBody.x / 2f, TubeGame.GAME_HEIGHT * .25f);
		Sprite buttonSprite = new Sprite(game.assManager.get("button_background.png", Texture.class));
		buttonSprite.setColor(game.background);
		ButtonMaker.createButtonEntityWithText(world, buttonSprite, buttonPos, buttonBody, e, State.PAUSED,
				new Text("Ok", new Vector2(0, 0), game.fonts.getFont(game.background, 48)));
	}

	private void createRightClickPanel() {
		Event e = new Event() {
			@Override
			public void action() {
				playerManager.player.getComponent(MovementDirection.class).shiftDirectionRight();
			}
		};
		Entity rightClick = world.createEntity();
		Position pos = new Position(TubeGame.GAME_WIDTH / 2, 0);
		RenderBody rd = new RenderBody(TubeGame.GAME_WIDTH / 2, TubeGame.GAME_HEIGHT);
		Text t = new Text("  Press right side\nto rotate clockwise",
				new Vector2(TubeGame.GAME_WIDTH * .75f, TubeGame.GAME_HEIGHT * .75f),
				game.fonts.getFont(game.background, 48));
		Sprite white = new Sprite(game.assManager.get("white.png", Texture.class));
		white.setBounds(TubeGame.GAME_WIDTH / 2f, 0, rd.width, rd.height);
		white.setAlpha(.3f);
		SpriteComponent sc = new SpriteComponent(white);
		DrawToUI dtui = new DrawToUI();
		Clickable cl = new Clickable(State.RUNNING, e, true);
		rightClick.edit().add(pos).add(rd).add(cl).add(sc).add(t).add(dtui);

		Entity icon = world.createEntity();
		RenderBody iconBody = new RenderBody(TubeGame.GAME_WIDTH * .10f, TubeGame.GAME_WIDTH * .10f);
		Position iconPos = new Position((TubeGame.GAME_WIDTH * .75f) - iconBody.width / 2, TubeGame.GAME_HEIGHT * .4f);
		Sprite iconSprite = new Sprite(game.assManager.get("replay.png", Texture.class));
		iconSprite.setColor(game.background);
		iconSprite.setBounds(iconPos.x, iconPos.y, iconBody.width, iconBody.height);
		SpriteComponent iconSC = new SpriteComponent(iconSprite);
		icon.edit().add(dtui).add(iconBody).add(iconPos).add(iconSC);

		Vector2 buttonBody = new Vector2(TubeGame.GAME_WIDTH * .1f, TubeGame.GAME_HEIGHT * .1f);
		Vector2 buttonPos = new Vector2((TubeGame.GAME_WIDTH * .75f) - buttonBody.x / 2f, TubeGame.GAME_HEIGHT * .25f);
		Sprite buttonSprite = new Sprite(game.assManager.get("button_background.png", Texture.class));
		buttonSprite.setColor(game.background);
		ButtonMaker.createButtonEntityWithText(world, buttonSprite, buttonPos, buttonBody, e, State.PAUSED,
				new Text("Ok", new Vector2(0, 0), game.fonts.getFont(game.background, 48)));

	}

	private void createPointLabel() {
		Entity title = world.createEntity();
		DisplayScore ds = new DisplayScore(this.currentScore);
		Text t = new Text("", new Vector2(TubeGame.GAME_WIDTH * .9f, TubeGame.GAME_HEIGHT * .9f),
				game.fonts.getFont(game.background, 48));
		title.edit().add(t).add(ds);
	}

	private void createPauseLabel() {
		Entity label = world.createEntity();
		Text t = new Text(" Game Ready\nTap to Begin",
				new Vector2(TubeGame.GAME_WIDTH * .5f, TubeGame.GAME_HEIGHT * .75f),
				game.fonts.getFont(game.background, 60));
		DrawDuringState dds = new DrawDuringState(new ScreenState(State.PAUSED));
		Position pos = new Position(0, 0);
		RenderBody rd = new RenderBody(TubeGame.GAME_WIDTH, TubeGame.GAME_HEIGHT);
		Clickable cl = new Clickable(State.PAUSED, new Event() {
			@Override
			public void action() {
				gameState.state = State.RUNNING;
			}
		}, false);
		label.edit().add(t).add(dds).add(pos).add(rd).add(cl);
	}

	private void createBackground() {
		Entity bg = world.createEntity();
		Sprite bgs = new Sprite(game.assManager.get("white.png", Texture.class));
		SpriteComponent sc = new SpriteComponent(bgs);
		sc.sprite.setBounds(0, 0, TubeGame.GAME_WIDTH, TubeGame.GAME_HEIGHT);
		sc.sprite.setColor(game.background);
		DrawToBackground dtb = new DrawToBackground();
		bg.edit().add(sc).add(dtb);
	}

	private void createForeground() {
		fade = world.createEntity();
		black = new Sprite(game.assManager.get("black.png", Texture.class));
		black.setBounds(0, 0, game.uiViewport.getWorldWidth(), game.uiViewport.getWorldHeight());
		SpriteComponent sc = new SpriteComponent(black);
		DrawToForeground dtfc = new DrawToForeground();
		fade.edit().add(sc).add(dtfc);
	}

	private void fadeIn() {
		this.gameState.state = State.FADING;
		Tween.to(black, SpriteAccessor.ALPHA, .5f).target(0).start(tweenManager).setCallback(new TweenCallback() {
			@Override
			public void onEvent(int arg0, BaseTween<?> arg1) {
				gameState.state = State.PAUSED;
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
		this.pastState = new ScreenState(State.PAUSED);
		this.currentScore = new Score();
		this.createWorld();
		tubeManager.restart();
		playerManager.restart();
		createBackground();
		createLeftClickPanel();
		createRightClickPanel();
		createPointLabel();
		createPauseLabel();
		createForeground();
		this.fadeIn();
	}

	@Override
	public void render(float delta) {
		game.shapeRenderer.setProjectionMatrix(game.viewport.getCamera().combined);
		if (delta > .1f)
			delta = .1f;
		world.setDelta(delta);
		world.process();
		if (gameState.state == State.OVER) {
			fadeOut();
		}
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
		pastState.state = gameState.state;
		gameState.state = State.PAUSED;
	}

	@Override
	public void resume() {
		if (pastState.state == State.FADING)
			gameState.state = State.FADING;
	}

	@Override
	public void hide() {
	}

	@Override
	public void dispose() {
		if (world != null)
			world.dispose();
	}
}
