package com.amverhagen.tube.game;

import com.amverhagen.tube.screens.GameScreen;
import com.amverhagen.tube.screens.MainMenuScreen;
import com.amverhagen.tube.screens.ScoreScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class TubeGame extends Game {
	public static int GAME_WIDTH = 1600;
	public static int GAME_HEIGHT = 900;
	public boolean tutorialOn;
	public Color background;
	public AssetManager assManager;
	public SpriteBatch gameBatch;
	public ShapeRenderer shapeRenderer;
	public Camera gameCamera;
	public Camera uiCamera;
	public Viewport viewport;
	public Viewport uiViewport;
	public Fonts fonts;
	private MainMenuScreen menuScreen;
	private GameScreen gameScreen;
	private ScoreScreen scoreScreen;

	@Override
	public void create() {
		this.loadAssets();

		gameCamera = new OrthographicCamera();
		viewport = new FitViewport(GAME_WIDTH, GAME_HEIGHT, gameCamera);
		viewport.apply();
		viewport.getCamera().position.set((float) gameCamera.viewportWidth / 2, (float) gameCamera.viewportHeight / 2,
				0);
		viewport.getCamera().update();

		uiCamera = new OrthographicCamera();
		uiViewport = new FitViewport(GAME_WIDTH, GAME_HEIGHT, uiCamera);
		uiViewport.apply();
		uiViewport.getCamera().position.set((float) uiCamera.viewportWidth / 2, (float) uiCamera.viewportHeight / 2, 0);
		uiViewport.getCamera().update();

		gameBatch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		shapeRenderer.setAutoShapeType(true);
		shapeRenderer.setColor(background);
		menuScreen = new MainMenuScreen(this);
		gameScreen = new GameScreen(this);
		scoreScreen = new ScoreScreen(this);
		this.setToMenuScreen();
	}

	private void loadAssets() {
		fonts = new Fonts();
		assManager = new AssetManager();
		assManager.load("black.png", Texture.class);
		assManager.load("white.png", Texture.class);
		assManager.load("replay.png", Texture.class);
		assManager.load("house.png", Texture.class);
		assManager.load("tube_title.png", Texture.class);
		assManager.load("button_background.png", Texture.class);
		assManager.finishLoading();
		this.background = Colors.TUBE_BLUE;
	}

	public void setToMenuScreen() {
		this.setScreen(menuScreen);
	}

	public void setToGameScreen() {
		this.setScreen(gameScreen);
	}

	public void setToScoreScreen() {
		this.setScreen(scoreScreen);
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);
		uiViewport.update(width, height);
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		super.render();
	}

	@Override
	public void dispose() {
		assManager.dispose();
		menuScreen.dispose();
		gameScreen.dispose();
		scoreScreen.dispose();
	}
}
