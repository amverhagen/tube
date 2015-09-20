package com.amverhagen.tube.game;

import com.amverhagen.tube.screens.GameScreen;
import com.amverhagen.tube.screens.MainMenuScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class TubeGame extends Game {
	public final static int GAME_WIDTH = 160000;
	public final static int GAME_HEIGHT = 90000;
	public SpriteBatch gameBatch;
	public ShapeRenderer shapeRenderer;
	public Camera gameCamera;
	public SpriteBatch uiBatch;
	public Camera uiCamera;
	public Viewport viewport;
	public Viewport uiViewport;
	private MainMenuScreen menuScreen;
	private GameScreen gameScreen;

	@Override
	public void create() {
		float aspectRatio = (float) Gdx.graphics.getHeight() / (float) Gdx.graphics.getWidth();
		gameCamera = new OrthographicCamera();

		viewport = new FitViewport(10f, 10f * aspectRatio, gameCamera);
		viewport.apply();
		viewport.getCamera().position.set((float) gameCamera.viewportWidth / 2, (float) gameCamera.viewportHeight / 2,
				0);
		viewport.getCamera().update();

		uiCamera = new OrthographicCamera();
		uiViewport = new FitViewport(10f, 10f * aspectRatio, uiCamera);
		uiViewport.apply();
		uiViewport.getCamera().position.set((float) uiCamera.viewportWidth / 2, (float) uiCamera.viewportHeight / 2, 0);
		uiViewport.getCamera().update();
		uiBatch = new SpriteBatch();

		gameBatch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		shapeRenderer.setAutoShapeType(true);
		menuScreen = new MainMenuScreen(this);
		gameScreen = new GameScreen(this);
		this.setScreen(menuScreen);
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void render() {
		super.render();
	}
}
