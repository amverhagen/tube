package com.amverhagen.tube.screens;

import com.amverhagen.tube.components.Clickable.Event;
import com.amverhagen.tube.components.DrawToForeground;
import com.amverhagen.tube.components.DrawToUI;
import com.amverhagen.tube.components.Position;
import com.amverhagen.tube.components.RenderBody;
import com.amverhagen.tube.components.SpriteComponent;
import com.amverhagen.tube.entitymakers.ButtonMaker;
import com.amverhagen.tube.game.TubeGame;
import com.amverhagen.tube.systems.DrawToForegroundSystem;
import com.amverhagen.tube.systems.DrawToUISystem;
import com.amverhagen.tube.systems.FadeSystem;
import com.amverhagen.tube.systems.ScreenState;
import com.amverhagen.tube.systems.ScreenState.State;
import com.amverhagen.tube.systems.UiClickSystem;
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

public class MainMenuScreen implements Screen {
	private TweenManager tweenManager;
	private Sprite black;
	private TubeGame game;
	private ScreenState state;
	private World world;

	public MainMenuScreen(TubeGame game) {
		this.game = game;
		state = new ScreenState(State.PAUSED);
		tweenManager = new TweenManager();
		Tween.registerAccessor(Sprite.class, new SpriteAccessor());

	}

	public void createWorld() {
		WorldConfiguration worldConfig = new WorldConfiguration();
		worldConfig.setSystem(new FadeSystem(state, tweenManager));
		worldConfig.setSystem(new UiClickSystem(game.uiCamera, state));
		worldConfig.setSystem(new DrawToUISystem(game.gameBatch, game.uiCamera));
		worldConfig.setSystem(new DrawToForegroundSystem(game.gameBatch));
		world = new World(worldConfig);
	}

	public void createTitle() {
		Entity title = world.createEntity();
		Position pc = new Position(4f, 4f);
		RenderBody ddc = new RenderBody(2f, 1f);
		DrawToUI dtui = new DrawToUI();
		SpriteComponent sc = new SpriteComponent(new Sprite(game.assManager.get("tube_title.png", Texture.class)));
		sc.sprite.setBounds(pc.x, pc.y, ddc.width, ddc.height);
		title.edit().add(pc).add(sc).add(dtui);
	}

	public void createButtons() {
		ButtonMaker.createButtonEntity(world, new Sprite(new Texture(Gdx.files.internal("play.png"))),
				new Vector2(4, .5f), new Vector2(2, 1), new Event() {
					@Override
					public void action() {
						game.setToGameScreen();
					}
				}, State.RUNNING);
		createColorButtonEntity(new Color(45f / 255f, 101f / 255f, 174f / 255f, 1), new Vector2(.5f, .5f),
				new Vector2(.5f, .5f));
		createColorButtonEntity(new Color(0f, 0f, 0f, 1), new Vector2(1.5f, .5f), new Vector2(.5f, .5f));
		createColorButtonEntity(new Color(1f, .5f, .5f, 1), new Vector2(2.5f, .5f), new Vector2(.5f, .5f));
		createColorButtonEntity(new Color(9f / 255f, 174f / 255f, 11f / 255f, 1), new Vector2(7f, .5f),
				new Vector2(.5f, .5f));
		createColorButtonEntity(new Color(99f / 255f, 33f / 255f, 130f / 255f, 1), new Vector2(8f, .5f),
				new Vector2(.5f, .5f));
		createColorButtonEntity(new Color(209f / 255, 10f / 255f, 10f / 255f, 1), new Vector2(9f, .5f),
				new Vector2(.5f, .5f));
	}

	private void createColorButtonEntity(final Color c, Vector2 pos, Vector2 body) {
		Sprite sprite = new Sprite(game.assManager.get("white.png", Texture.class));
		sprite.setColor(c);
		Event event = new Event() {
			@Override
			public void action() {
				game.background = new Color(c.r, c.g, c.b, 1);
				game.shapeRenderer.setColor(game.background);
			}
		};
		ButtonMaker.createButtonEntity(world, sprite, pos, body, event, State.RUNNING);
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
		this.state.state = State.FADING;
		Tween.to(black, SpriteAccessor.ALPHA, .5f).target(0).start(tweenManager).setCallback(new TweenCallback() {
			@Override
			public void onEvent(int arg0, BaseTween<?> arg1) {
				state.state = State.RUNNING;
			}
		});
	}

	@Override
	public void show() {
		createWorld();
		createTitle();
		createButtons();
		createBackground();
		this.fadeIn();
	}

	@Override
	public void render(float delta) {
		if (delta > .1)
			delta = .1f;
		Gdx.gl.glClearColor(game.background.r, game.background.g, game.background.b, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		game.gameBatch.setProjectionMatrix(game.viewport.getCamera().combined);
		world.setDelta(delta);
		world.process();
	}

	@Override
	public void resize(int width, int height) {
		game.viewport.setScreenSize(width, height);
		game.viewport.apply();
		game.uiViewport.setScreenSize(width, height);
		game.uiViewport.apply();
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
