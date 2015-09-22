package com.amverhagen.tube.screens;

import com.amverhagen.tube.components.Clickable;
import com.amverhagen.tube.components.Clickable.Event;
import com.amverhagen.tube.components.DrawingDimension;
import com.amverhagen.tube.components.Position;
import com.amverhagen.tube.components.UIRenderable;
import com.amverhagen.tube.game.TubeGame;
import com.amverhagen.tube.systems.UiClickSystem;
import com.amverhagen.tube.tween.SpriteAccessor;
import com.amverhagen.tube.systems.DrawingSystem;
import com.amverhagen.tube.systems.RenderUISystem;
import com.artemis.Entity;
import com.artemis.World;
import com.artemis.WorldConfiguration;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;

public class MainMenuScreen implements Screen {
	private TweenManager tweenManager;
	private Sprite black;
	TubeGame game;
	World world;
	Entity title;
	Entity playButton;

	public MainMenuScreen(TubeGame game) {
		this.game = game;
		tweenManager = new TweenManager();
		Tween.registerAccessor(Sprite.class, new SpriteAccessor());
		createWorld();
		createTitle();
		createButtons();
		createBackground();
		createBackground();
	}

	public void createWorld() {
		WorldConfiguration worldConfig = new WorldConfiguration();
		worldConfig.setSystem(new UiClickSystem(game.uiCamera));
		worldConfig.setSystem(new DrawingSystem(game.gameBatch));
		worldConfig.setSystem(new RenderUISystem(game.uiBatch, game.uiCamera));
		world = new World(worldConfig);
	}

	public void createTitle() {
		title = world.createEntity();
		Position pc = new Position(4f, 4f);
		DrawingDimension ddc = new DrawingDimension(2f, 1f);
		UIRenderable dc = new UIRenderable(new Texture(Gdx.files.internal("tube_title.png")));
		title.edit().add(pc).add(ddc).add(dc);
	}

	public void createButtons() {
		playButton = createButtonEntity(new Texture(Gdx.files.internal("play.png")), new Vector2(4, .5f),
				new Vector2(2, 1), new Event() {
					@Override
					public void action() {
						game.setToGameScreen();
					}
				});
	}

	public Entity createButtonEntity(Texture texture, Vector2 pos, Vector2 body, Event event) {
		Entity e = world.createEntity();
		UIRenderable uic = new UIRenderable(texture);
		Position pc = new Position(pos);
		DrawingDimension ddc = new DrawingDimension(body);
		Clickable cc = new Clickable(event);
		e.edit().add(uic).add(pc).add(ddc).add(cc);
		return e;
	}

	public void createBackground() {
		black = new Sprite(new Texture(Gdx.files.internal("black.png")));
		black.setSize(100, 100);
	}

	@Override
	public void show() {
		Tween.to(black, SpriteAccessor.ALPHA, .5f).target(0).start(tweenManager);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		game.gameBatch.setProjectionMatrix(game.viewport.getCamera().combined);
		game.shapeRenderer.setProjectionMatrix(game.viewport.getCamera().combined);
		if (delta > .1)
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
		world.dispose();
	}
}
