package com.amverhagen.tube.screens;

import com.amverhagen.tube.components.DrawToForeground;
import com.amverhagen.tube.components.DrawToUI;
import com.amverhagen.tube.components.SpriteComponent;
import com.amverhagen.tube.components.Clickable.Event;
import com.amverhagen.tube.entitymakers.ButtonMaker;
import com.amverhagen.tube.game.TubeGame;
import com.amverhagen.tube.systems.BindSpriteToPositionSystem;
import com.amverhagen.tube.systems.DrawToForegroundSystem;
import com.amverhagen.tube.systems.DrawToUISystem;
import com.amverhagen.tube.systems.FadeSystem;
import com.amverhagen.tube.systems.ScreenState;
import com.amverhagen.tube.systems.ScreenState.State;
import com.amverhagen.tube.tween.SpriteAccessor;
import com.amverhagen.tube.systems.UiClickSystem;
import com.artemis.Entity;
import com.artemis.World;
import com.artemis.WorldConfiguration;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
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
		wc.setSystem(BindSpriteToPositionSystem.class);
		wc.setSystem(new FadeSystem(screenState, tweenManager));
		wc.setSystem(new DrawToUISystem(game.gameBatch, game.uiCamera));
		wc.setSystem(new DrawToForegroundSystem(game.gameBatch));
		wc.setSystem(new UiClickSystem(game.uiCamera, screenState));
		world = new World(wc);
	}

	private void createButtons() {
		Sprite sprite = new Sprite(game.assManager.get("button_background.png", Texture.class));
		ButtonMaker.createButtonEntity(world, sprite, new Vector2(200, 50f), new Vector2(200, 100), new Event() {
			@Override
			public void action() {
				game.setToMenuScreen();
			}
		}, State.RUNNING);
		ButtonMaker.createButtonEntity(world, sprite, new Vector2(600, 50f), new Vector2(200, 100), new Event() {
			@Override
			public void action() {
				game.setToGameScreen();
			}
		}, State.RUNNING);
	}

	private void createIcons() {
		Entity e = world.createEntity();
		SpriteComponent sc = new SpriteComponent(new Sprite(new Texture(Gdx.files.internal("house.png"))));
		sc.sprite.setBounds(275f, 75f, 50f, 50f);
		DrawToUI dtui = new DrawToUI();
		e.edit().add(sc).add(dtui);

		e = world.createEntity();
		sc = new SpriteComponent(new Sprite(new Texture(Gdx.files.internal("replay.png"))));
		sc.sprite.setBounds(675f, 75f, 50f, 50f);
		dtui = new DrawToUI();
		e.edit().add(sc).add(dtui);
	}

	public void createBackground() {
		Entity e = world.createEntity();
		black = new Sprite(game.assManager.get("black.png", Texture.class));
		black.setBounds(0, 0, 1000, 1000);
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

	@Override
	public void show() {
		createWorld();
		createButtons();
		createBackground();
		createIcons();
		this.fadeIn();
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(game.background.r, game.background.g, game.background.b, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		if (delta > .1f) {
			delta = .1f;
		}
		world.setDelta(delta);
		world.process();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

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
