package com.amverhagen.tube.screens;

import com.amverhagen.tube.components.Clickable.Event;
import com.amverhagen.tube.components.DrawToBackground;
import com.amverhagen.tube.components.DrawToForeground;
import com.amverhagen.tube.components.DrawToUI;
import com.amverhagen.tube.components.SpriteComponent;
import com.amverhagen.tube.entitymakers.ButtonMaker;
import com.amverhagen.tube.game.ScreenState;
import com.amverhagen.tube.game.TubeGame;
import com.amverhagen.tube.game.ScreenState.State;
import com.amverhagen.tube.systems.DrawToBackgroundSystem;
import com.amverhagen.tube.systems.DrawToForegroundSystem;
import com.amverhagen.tube.systems.DrawToUISystem;
import com.amverhagen.tube.systems.FadeSystem;
import com.amverhagen.tube.systems.UiClickSystem;
import com.amverhagen.tube.tween.SpriteAccessor;
import com.artemis.Entity;
import com.artemis.World;
import com.artemis.WorldConfiguration;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;

public class ScoreScreen implements com.badlogic.gdx.Screen {
	private World world;
	private TubeGame game;
	private ScreenState screenState;
	private TweenManager tweenManager;
	private Sprite black;

	public ScoreScreen(TubeGame game) {
		this.world = new World();
		this.game = game;
		this.screenState = new ScreenState(State.PAUSED);
		tweenManager = new TweenManager();
		Tween.registerAccessor(Sprite.class, new SpriteAccessor());
	}

	private void createWorld() {
		WorldConfiguration wc = new WorldConfiguration();
		wc.setSystem(new FadeSystem(screenState, tweenManager));
		wc.setSystem(new DrawToBackgroundSystem(game.gameBatch, game.uiCamera));
		wc.setSystem(new DrawToUISystem(game.gameBatch, game.uiCamera));
		wc.setSystem(new DrawToForegroundSystem(game.gameBatch));
		wc.setSystem(new UiClickSystem(game.uiViewport, screenState));
		world = new World(wc);
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

	private void createButtons() {
		Sprite sprite = new Sprite(game.assManager.get("button_background.png", Texture.class));
		ButtonMaker.createButtonEntity(world, sprite, new Vector2(TubeGame.GAME_WIDTH * .2f, TubeGame.GAME_WIDTH / 20f),
				new Vector2(TubeGame.GAME_WIDTH * .2f, TubeGame.GAME_WIDTH / 10f), new Event() {
					@Override
					public void action() {
						fadeOutToHome();
					}
				}, State.RUNNING);
		ButtonMaker.createButtonEntity(world, sprite, new Vector2(TubeGame.GAME_WIDTH * .6f, TubeGame.GAME_WIDTH / 20f),
				new Vector2(TubeGame.GAME_WIDTH * .2f, TubeGame.GAME_WIDTH / 10f), new Event() {
					@Override
					public void action() {
						fadeOutToGame();
					}
				}, State.RUNNING);
	}

	private void createIcons() {
		float iconWidth = TubeGame.GAME_WIDTH / 20f;
		Entity e = world.createEntity();
		SpriteComponent sc = new SpriteComponent(new Sprite(new Texture(Gdx.files.internal("house.png"))));
		sc.sprite.setBounds(TubeGame.GAME_WIDTH * .275f, TubeGame.GAME_WIDTH * .075f, iconWidth, iconWidth);
		DrawToUI dtui = new DrawToUI();
		e.edit().add(sc).add(dtui);

		e = world.createEntity();
		sc = new SpriteComponent(new Sprite(new Texture(Gdx.files.internal("replay.png"))));
		sc.sprite.setBounds(TubeGame.GAME_WIDTH * .675f, TubeGame.GAME_WIDTH * .075f, iconWidth, iconWidth);
		dtui = new DrawToUI();
		e.edit().add(sc).add(dtui);
	}

	public void createForeground() {
		Entity e = world.createEntity();
		black = new Sprite(game.assManager.get("black.png", Texture.class));
		black.setBounds(0, 0, game.uiViewport.getWorldWidth(), game.uiViewport.getWorldHeight());
		SpriteComponent sc = new SpriteComponent(black);
		DrawToForeground dtfc = new DrawToForeground();
		e.edit().add(sc).add(dtfc);
	}

	private void fadeIn() {
		this.screenState.state = State.FADING;
		Tween.to(black, SpriteAccessor.ALPHA, .5f).target(0).start(tweenManager).setCallback(new TweenCallback() {
			@Override
			public void onEvent(int arg0, BaseTween<?> arg1) {
				screenState.state = State.RUNNING;
			}
		});
	}

	private void fadeOutToHome() {
		this.screenState.state = State.FADING;
		Tween.to(black, SpriteAccessor.ALPHA, .5f).target(1).start(tweenManager).setCallback(new TweenCallback() {
			@Override
			public void onEvent(int arg0, BaseTween<?> arg1) {
				game.setToMenuScreen();
			}
		});
	}

	private void fadeOutToGame() {
		this.screenState.state = State.FADING;
		Tween.to(black, SpriteAccessor.ALPHA, .5f).target(1).start(tweenManager).setCallback(new TweenCallback() {
			@Override
			public void onEvent(int arg0, BaseTween<?> arg1) {
				game.setToGameScreen();
			}
		});
	}

	@Override
	public void show() {
		createWorld();
		createBackground();
		createButtons();
		createForeground();
		createIcons();
		this.fadeIn();
	}

	@Override
	public void render(float delta) {
		if (delta > .1f) {
			delta = .1f;
		}
		world.setDelta(delta);
		world.process();
	}

	@Override
	public void resize(int width, int height) {
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
