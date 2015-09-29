package com.amverhagen.tube.screens;

import com.amverhagen.tube.components.Clickable;
import com.amverhagen.tube.components.Clickable.Event;
import com.amverhagen.tube.components.DrawToForeground;
import com.amverhagen.tube.components.DrawToUI;
import com.amverhagen.tube.components.Position;
import com.amverhagen.tube.components.RenderBody;
import com.amverhagen.tube.components.SpriteComponent;
import com.amverhagen.tube.game.TubeGame;
import com.amverhagen.tube.systems.DrawToForegroundSystem;
import com.amverhagen.tube.systems.DrawToUISystem;
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
		state = new ScreenState(State.FADING);
		tweenManager = new TweenManager();
		Tween.registerAccessor(Sprite.class, new SpriteAccessor());
		createWorld();
		createTitle();
		createButtons();
		createBackground();
	}

	public void createWorld() {
		WorldConfiguration worldConfig = new WorldConfiguration();
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
		createButtonEntity(new Texture(Gdx.files.internal("play.png")), new Vector2(4, .5f), new Vector2(2, 1),
				new Event() {
					@Override
					public void action() {
						game.setToGameScreen();
					}
				});
		createButtonEntity(new Texture(Gdx.files.internal("blue.png")), new Vector2(1.5f, .5f), new Vector2(.5f, .5f),
				new Event() {
					@Override
					public void action() {
						game.background = new Color(0, 0, 1, 1);
						System.out.println(game.background);
						game.shapeRenderer.setColor(game.background);
					}
				});
		createButtonEntity(new Texture(Gdx.files.internal("black.png")), new Vector2(.5f, .5f), new Vector2(.5f, .5f),
				new Event() {
					@Override
					public void action() {
						game.background = new Color(0, 0, 0, 1);
						System.out.println(game.background);
						game.shapeRenderer.setColor(game.background);
					}
				});
		createButtonEntity(new Texture(Gdx.files.internal("pink.png")), new Vector2(2.5f, .5f), new Vector2(.5f, .5f),
				new Event() {
					@Override
					public void action() {
						game.background = new Color(1, .5f, .5f, 1);
						System.out.println(game.background);
						game.shapeRenderer.setColor(game.background);
					}
				});
	}

	public Entity createButtonEntity(Texture texture, Vector2 pos, Vector2 body, Event event) {
		Entity e = world.createEntity();
		DrawToUI uic = new DrawToUI();
		Position pc = new Position(pos);
		RenderBody ddc = new RenderBody(body);
		SpriteComponent sc = new SpriteComponent(new Sprite(texture));
		sc.sprite.setBounds(pc.x, pc.y, ddc.width, ddc.height);
		Clickable cc = new Clickable(State.RUNNING, event);
		e.edit().add(uic).add(cc).add(sc).add(pc).add(ddc);
		return e;
	}

	public void createBackground() {
		Entity e = world.createEntity();
		black = new Sprite(game.assManager.get("black.png", Texture.class));
		black.setBounds(0, 0, 10, 10);
		SpriteComponent sc = new SpriteComponent(black);
		DrawToForeground dtfc = new DrawToForeground();
		e.edit().add(sc).add(dtfc);
	}

	@Override
	public void show() {
		Tween.to(black, SpriteAccessor.ALPHA, .5f).target(0).start(tweenManager).setCallback(new TweenCallback() {
			@Override
			public void onEvent(int arg0, BaseTween<?> arg1) {
				state.state = State.RUNNING;
			}
		});
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
		world.dispose();
	}
}
