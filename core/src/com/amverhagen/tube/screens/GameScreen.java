package com.amverhagen.tube.screens;

import com.amverhagen.tube.components.Center;
import com.amverhagen.tube.components.Clickable;
import com.amverhagen.tube.components.Clickable.Event;
import com.amverhagen.tube.components.DisplayScore;
import com.amverhagen.tube.components.DrawDuringState;
import com.amverhagen.tube.components.DrawToBackground;
import com.amverhagen.tube.components.DrawToForeground;
import com.amverhagen.tube.components.Position;
import com.amverhagen.tube.components.RenderBody;
import com.amverhagen.tube.components.SpriteComponent;
import com.amverhagen.tube.components.Text;
import com.amverhagen.tube.game.Score;
import com.amverhagen.tube.game.ScreenState;
import com.amverhagen.tube.game.ScreenState.State;
import com.amverhagen.tube.game.TubeGame;
import com.amverhagen.tube.managers.InputPanelManager;
import com.amverhagen.tube.managers.PlayerManager;
import com.amverhagen.tube.managers.TubeManager;
import com.amverhagen.tube.managers.TutorialPanelManager;
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
	private Entity fade;
	private TubeManager tubeManager;
	private PlayerManager playerManager;
	private InputPanelManager inputPanelManager;
	private TutorialPanelManager tutPanelManager;
	private Score currentScore;

	public GameScreen(TubeGame game) {
		this.game = game;
		this.gameState = new ScreenState(State.PAUSED);
		this.pastState = new ScreenState(State.PAUSED);
		this.tweenManager = new TweenManager();
		this.tubeManager = new TubeManager(this.game);
		this.playerManager = new PlayerManager(game, tubeManager.startingPoint);
		this.inputPanelManager = new InputPanelManager();
		this.tutPanelManager = new TutorialPanelManager(game, gameState);
		Tween.registerAccessor(Sprite.class, new SpriteAccessor());
	}

	private void createWorld() {
		WorldConfiguration worldConfig = new WorldConfiguration();
		worldConfig.setSystem(new CheckPlayerCollisionSystem(gameState, currentScore));
		worldConfig.setSystem(new FadeSystem(gameState, tweenManager));
		worldConfig.setSystem(new UiClickSystem(game.uiViewport, gameState));
		worldConfig.setSystem(new DrawToBackgroundSystem(game.gameBatch, game.uiCamera));
		worldConfig.setSystem(new DrawToGameSystem(game.gameBatch, game.gameCamera));
		worldConfig.setSystem(new DrawConnectedPointsSystem(game.shapeRenderer, gameState));
		worldConfig.setSystem(new DrawToUISystem(game.gameBatch, game.uiCamera));
		worldConfig.setSystem(new DrawTextSystem(game.gameBatch, this.gameState));
		worldConfig.setSystem(new DrawToForegroundSystem(game.gameBatch));
		worldConfig.setSystem(new BindScoreToLabelSystem());
		worldConfig.setSystem(new MoveInDirectionSystem(gameState));
		worldConfig.setSystem(new UpdateCenterSystem(gameState));
		worldConfig.setSystem(new AddConnectedPointsFromEntityCenterSystem(gameState));
		worldConfig.setSystem(CameraFocusSystem.class);
		worldConfig.setSystem(DeleteChildEntitySystem.class);
		worldConfig.setSystem(DeleteEntitySystem.class);
		worldConfig.setManager(new TagManager());
		worldConfig.setManager(tubeManager);
		worldConfig.setManager(playerManager);
		worldConfig.setManager(inputPanelManager);
		worldConfig.setManager(tutPanelManager);
		world = new World(worldConfig);
	}

	private void createPointLabel() {
		Entity title = world.createEntity();
		DisplayScore ds = new DisplayScore(this.currentScore);
		Text t = new Text("", new Center(TubeGame.GAME_WIDTH * .9f, TubeGame.GAME_HEIGHT * .9f),
				game.fonts.getFont(game.background, 48));
		title.edit().add(t).add(ds);
	}

	private void createPauseLabel() {
		Entity label = world.createEntity();
		Text t = new Text(" Game Ready\nTap to Begin",
				new Center(TubeGame.GAME_WIDTH * .5f, TubeGame.GAME_HEIGHT * .75f),
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
		this.gameState.state = State.PAUSED;
		this.pastState.state = State.PAUSED;
		this.currentScore = new Score();
		this.createWorld();
		tubeManager.restart();
		playerManager.restart();
		createBackground();
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
