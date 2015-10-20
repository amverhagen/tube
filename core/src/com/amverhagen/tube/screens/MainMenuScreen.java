package com.amverhagen.tube.screens;

import com.amverhagen.tube.components.Center;
import com.amverhagen.tube.components.Clickable.Event;
import com.amverhagen.tube.components.DrawToBackground;
import com.amverhagen.tube.components.DrawToForeground;
import com.amverhagen.tube.components.SpriteComponent;
import com.amverhagen.tube.components.Text;
import com.amverhagen.tube.entitymakers.ButtonMaker;
import com.amverhagen.tube.game.ScreenState;
import com.amverhagen.tube.game.ScreenState.State;
import com.amverhagen.tube.game.TubeGame;
import com.amverhagen.tube.managers.HintButtonManager;
import com.amverhagen.tube.systems.DrawTextSystem;
import com.amverhagen.tube.systems.DrawToBackgroundSystem;
import com.amverhagen.tube.systems.DrawToForegroundSystem;
import com.amverhagen.tube.systems.DrawToUISystem;
import com.amverhagen.tube.systems.FadeSystem;
import com.amverhagen.tube.systems.UiClickSystem;
import com.amverhagen.tube.tween.SpriteAccessor;
import com.artemis.Entity;
import com.artemis.World;
import com.artemis.WorldConfiguration;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;

public class MainMenuScreen implements Screen {
	private TweenManager tweenManager;
	private Sprite fgSprite;
	private Sprite bgSprite;
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
		createForeground();
		createBackground();
	}

	public void createWorld() {
		WorldConfiguration worldConfig = new WorldConfiguration();
		worldConfig.setSystem(new FadeSystem(state, tweenManager));
		worldConfig.setSystem(new DrawToBackgroundSystem(game.gameBatch, game.uiCamera));
		worldConfig.setSystem(new UiClickSystem(game.uiViewport, state));
		worldConfig.setSystem(new DrawToUISystem(game.gameBatch, game.uiCamera));
		worldConfig.setSystem(new DrawTextSystem(game.gameBatch, this.state));
		worldConfig.setSystem(new DrawToForegroundSystem(game.gameBatch));
		worldConfig.setManager(new HintButtonManager(game));
		world = new World(worldConfig);
	}

	private void createBackground() {
		Entity bg = world.createEntity();
		bgSprite = new Sprite(game.assManager.get("white.png", Texture.class));
		SpriteComponent sc = new SpriteComponent(bgSprite);
		sc.sprite.setBounds(0, 0, TubeGame.GAME_WIDTH, TubeGame.GAME_HEIGHT);
		sc.sprite.setColor(game.background);
		DrawToBackground dtb = new DrawToBackground();
		bg.edit().add(sc).add(dtb);
	}

	public void createTitle() {
		Entity title = world.createEntity();
		Vector2 position = new Vector2(TubeGame.GAME_WIDTH / 2f, TubeGame.GAME_HEIGHT * .75f);
		Text t = new Text("Tube", new Center(position), game.fonts.getFont(game.colors.TUBE_WHITE, 60));
		title.edit().add(t);
	}

	public void createButtons() {
		float colorWidth = TubeGame.GAME_WIDTH / 20f;
		Text text = new Text("Play", new Center(new Vector2(0, 0)), game.fonts.getFont(game.colors.TUBE_WHITE, 48));
		ButtonMaker.createButtonEntityWithText(world,
				new Sprite(game.assManager.get("button_background.png", Texture.class)),
				new Vector2(TubeGame.GAME_WIDTH * .4f, colorWidth),
				new Vector2(TubeGame.GAME_WIDTH * .2f, TubeGame.GAME_HEIGHT * .17f), new Event() {
					@Override
					public void action() {
						fadeOutToGame();
					}
				}, State.RUNNING, text);
		createColorButtonEntity(game.colors.TUBE_BLUE, new Vector2(TubeGame.GAME_WIDTH * .05f, colorWidth),
				new Vector2(colorWidth, colorWidth));
		createColorButtonEntity(game.colors.TUBE_BLACK, new Vector2(TubeGame.GAME_WIDTH * .15f, colorWidth),
				new Vector2(colorWidth, colorWidth));
		createColorButtonEntity(game.colors.TUBE_PINK, new Vector2(TubeGame.GAME_WIDTH * .25f, colorWidth),
				new Vector2(colorWidth, colorWidth));
		createColorButtonEntity(game.colors.TUBE_GREEN, new Vector2(TubeGame.GAME_WIDTH * .7f, colorWidth),
				new Vector2(colorWidth, colorWidth));
		createColorButtonEntity(game.colors.TUBE_PURPLE, new Vector2(TubeGame.GAME_WIDTH * .8f, colorWidth),
				new Vector2(colorWidth, colorWidth));
		createColorButtonEntity(game.colors.TUBE_RED, new Vector2(TubeGame.GAME_WIDTH * .9f, colorWidth),
				new Vector2(colorWidth, colorWidth));
	}

	private void createColorButtonEntity(final Color c, Vector2 pos, Vector2 body) {
		Sprite sprite = new Sprite(game.assManager.get("white.png", Texture.class));
		sprite.setColor(c);
		Event event = new Event() {
			@Override
			public void action() {
				game.background.set(c);
				game.shapeRenderer.setColor(game.background);
				bgSprite.setColor(game.background);
			}
		};
		ButtonMaker.createButtonEntity(world, sprite, pos, body, event, State.RUNNING);
	}

	public void createForeground() {
		Entity e = world.createEntity();
		fgSprite = new Sprite(game.assManager.get("black.png", Texture.class));
		fgSprite.setBounds(0, 0, game.uiViewport.getWorldWidth(), game.uiViewport.getWorldHeight());
		SpriteComponent sc = new SpriteComponent(fgSprite);
		DrawToForeground dtfc = new DrawToForeground();
		e.edit().add(sc).add(dtfc);
	}

	private void fadeIn() {
		this.state.state = State.FADING;
		Tween.to(fgSprite, SpriteAccessor.ALPHA, .6f).target(0).start(tweenManager).setCallback(new TweenCallback() {
			@Override
			public void onEvent(int arg0, BaseTween<?> arg1) {
				state.state = State.RUNNING;
			}
		});
	}

	private void fadeOutToGame() {
		this.state.state = State.FADING;
		Tween.to(fgSprite, SpriteAccessor.ALPHA, .5f).target(1).start(tweenManager).setCallback(new TweenCallback() {
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
