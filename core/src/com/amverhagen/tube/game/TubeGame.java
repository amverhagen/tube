package com.amverhagen.tube.game;

import com.amverhagen.tube.screens.GameScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class TubeGame extends Game {
	public final static int GAME_WIDTH = 1600;
	public final static int GAME_HEIGHT = 900;
	public SpriteBatch batch;
	public Camera camera;
	public Viewport viewport;
	private GameScreen gameScreen;

	@Override
	public void create() {
		float aspectRatio = (float) Gdx.graphics.getHeight() / (float) Gdx.graphics.getWidth();
		camera = new OrthographicCamera();
		viewport = new FitViewport(10f, 10f * aspectRatio, camera);
		viewport.apply();
		viewport.getCamera().position.set((float) camera.viewportWidth / 2, (float) camera.viewportHeight / 2, 0);
		viewport.getCamera().update();
		batch = new SpriteBatch();
		gameScreen = new GameScreen(this);
		this.setScreen(gameScreen);
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);
	}

	@Override
	public void render() {
		super.render();
	}
}
