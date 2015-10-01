package com.amverhagen.tube.screens;

import com.amverhagen.tube.components.Clickable.Event;
import com.amverhagen.tube.entitymakers.ButtonMaker;
import com.amverhagen.tube.game.TubeGame;
import com.amverhagen.tube.systems.DrawToUISystem;
import com.amverhagen.tube.systems.ScreenState;
import com.amverhagen.tube.systems.ScreenState.State;
import com.amverhagen.tube.systems.UiClickSystem;
import com.artemis.World;
import com.artemis.WorldConfiguration;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class ScoreScreen implements com.badlogic.gdx.Screen {
	private World world;
	private TubeGame game;
	private ScreenState screenState;

	public ScoreScreen(TubeGame game) {
		this.world = new World();
		this.game = game;
		this.screenState = new ScreenState(State.PAUSED);
	}

	private void createWorld() {
		WorldConfiguration wc = new WorldConfiguration();
		wc.setSystem(new DrawToUISystem(game.gameBatch, game.uiCamera));
		wc.setSystem(new UiClickSystem(game.uiCamera, screenState));
		world = new World(wc);
	}

	private void createButtons() {
		Sprite sprite = new Sprite(game.assManager.get("white.png", Texture.class));
		ButtonMaker.createButtonEntity(world, sprite, new Vector2(2, .5f), new Vector2(2, 1), new Event() {
			@Override
			public void action() {
				game.setToMenuScreen();
			}
		}, State.RUNNING);
		ButtonMaker.createButtonEntity(world, sprite, new Vector2(6, .5f), new Vector2(2, 1), new Event() {
			@Override
			public void action() {
				game.setToGameScreen();
			}
		}, State.RUNNING);
	}

	@Override
	public void show() {
		createWorld();
		createButtons();
		this.screenState.state = State.RUNNING;
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
