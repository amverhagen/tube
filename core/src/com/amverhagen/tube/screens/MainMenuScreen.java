package com.amverhagen.tube.screens;

import com.amverhagen.tube.components.Clickable.Event;
import com.amverhagen.tube.components.DrawToForeground;
import com.amverhagen.tube.components.SpriteComponent;
import com.amverhagen.tube.components.Text;
import com.amverhagen.tube.entitymakers.ButtonMaker;
import com.amverhagen.tube.game.TubeGame;
import com.amverhagen.tube.systems.BindSpriteToPositionSystem;
import com.amverhagen.tube.systems.DrawTextSystem;
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
		createWorld();
		createTitle();
		createButtons();
		createBackground();
	}

	public void createWorld() {
		WorldConfiguration worldConfig = new WorldConfiguration();
		worldConfig.setSystem(BindSpriteToPositionSystem.class);
		worldConfig.setSystem(new FadeSystem(state, tweenManager));
		worldConfig.setSystem(new UiClickSystem(game.uiViewport, state));
		worldConfig.setSystem(new DrawToUISystem(game.gameBatch, game.uiCamera));
		worldConfig.setSystem(new DrawTextSystem(game.gameBatch));
		worldConfig.setSystem(new DrawToForegroundSystem(game.gameBatch));
		world = new World(worldConfig);
	}

	public void createTitle() {
		Entity title = world.createEntity();
		Text t = new Text("Tube", new Vector2(TubeGame.GAME_WIDTH / 2f, 500), 48);
		title.edit().add(t);
	}

	public void createButtons() {
		float colorWidth = TubeGame.GAME_WIDTH / 20f;
		Text text = new Text("Play", new Vector2(0, 0), 48);
		ButtonMaker.createButtonEntityWithText(world,
				new Sprite(game.assManager.get("button_background.png", Texture.class)),
				new Vector2(TubeGame.GAME_WIDTH * .4f, colorWidth),
				new Vector2(TubeGame.GAME_WIDTH * .2f, TubeGame.GAME_HEIGHT * .17f), new Event() {
					@Override
					public void action() {
						fadeOutToGame();
					}
				}, State.RUNNING, text);
		createColorButtonEntity(new Color(45f / 255f, 101f / 255f, 174f / 255f, 1), new Vector2(colorWidth, colorWidth),
				new Vector2(colorWidth, colorWidth));
		createColorButtonEntity(new Color(0f, 0f, 0f, 1), new Vector2(TubeGame.GAME_WIDTH * .15f, colorWidth),
				new Vector2(colorWidth, colorWidth));
		createColorButtonEntity(new Color(1f, .5f, .5f, 1), new Vector2(TubeGame.GAME_WIDTH * .25f, colorWidth),
				new Vector2(colorWidth, colorWidth));
		createColorButtonEntity(new Color(9f / 255f, 174f / 255f, 11f / 255f, 1),
				new Vector2(TubeGame.GAME_WIDTH * .7f, colorWidth), new Vector2(colorWidth, colorWidth));
		createColorButtonEntity(new Color(99f / 255f, 33f / 255f, 130f / 255f, 1),
				new Vector2(TubeGame.GAME_WIDTH * .8f, colorWidth), new Vector2(colorWidth, colorWidth));
		createColorButtonEntity(new Color(209f / 255, 10f / 255f, 10f / 255f, 1),
				new Vector2(TubeGame.GAME_WIDTH * .9f, colorWidth), new Vector2(colorWidth, colorWidth));
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
		black.setBounds(0, 0, game.uiViewport.getWorldWidth(), game.uiViewport.getWorldHeight());
		SpriteComponent sc = new SpriteComponent(black);
		DrawToForeground dtfc = new DrawToForeground();
		e.edit().add(sc).add(dtfc);
	}

	private void fadeIn() {
		this.state.state = State.FADING;
		Tween.to(black, SpriteAccessor.ALPHA, .6f).target(0).start(tweenManager).setCallback(new TweenCallback() {
			@Override
			public void onEvent(int arg0, BaseTween<?> arg1) {
				state.state = State.RUNNING;
			}
		});
	}

	private void fadeOutToGame() {
		this.state.state = State.FADING;
		Tween.to(black, SpriteAccessor.ALPHA, .5f).target(1).start(tweenManager).setCallback(new TweenCallback() {
			@Override
			public void onEvent(int arg0, BaseTween<?> arg1) {
				game.setToGameScreen();
			}
		});
	}

	@Override
	public void show() {
		this.fadeIn();
	}

	@Override
	public void render(float delta) {
		if (delta > .1)
			delta = .1f;
		Gdx.gl.glClearColor(game.background.r, game.background.g, game.background.b, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
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
		world.dispose();
	}
}
