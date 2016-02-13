package com.amverhagen.tube.game;

import com.amverhagen.tube.screens.GameScreen;
import com.amverhagen.tube.screens.MainMenuScreen;
import com.amverhagen.tube.screens.ScoreScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class TubeGame extends Game {
	public static final int GAME_WIDTH = 1600;
	public static final int GAME_HEIGHT = 900;
	public boolean tutorialOn;
	public Color background;
	public AssetManager assetManager;
	public SpriteBatch gameBatch;
	public ShapeRenderer shapeRenderer;
	public Camera gameCamera;
	public Camera uiCamera;
	public Viewport viewport;
	public Viewport uiViewport;
	public Fonts fonts;
	public Colors colors;
	private String saveFileName = "saves.txt";
	private MainMenuScreen menuScreen;
	private GameScreen gameScreen;
	private ScoreScreen scoreScreen;

	@Override
	public void create() {
		this.loadAssets();
		this.readSave();

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
		colors = new Colors();
		fonts = new Fonts(this);
		assetManager = new AssetManager();
		assetManager.load("black.png", Texture.class);
		assetManager.load("white.png", Texture.class);
		assetManager.load("replay.png", Texture.class);
		assetManager.load("house.png", Texture.class);
		assetManager.load("tube_title.png", Texture.class);
		assetManager.load("button_background.png", Texture.class);
		assetManager.finishLoading();
		this.background = new Color(colors.TUBE_BLUE);
	}

	private void readSave() {
		try {
			FileHandle handle = Gdx.files.local(saveFileName);
			String currentLine = null;
			currentLine = handle.readString();
			if (currentLine.equals("1")) {
				this.tutorialOn = true;
			} else {
				this.tutorialOn = true;
			}
		} catch (GdxRuntimeException ex) {
			this.tutorialOn = false;
		}
	}

	private void writeSave() {
		try {
			FileHandle handle = Gdx.files.local(saveFileName);
			if (this.tutorialOn) {
				handle.writeString("1", false);
			} else {
				handle.writeString("0", false);
			}
		} catch (GdxRuntimeException ex) {
			ex.printStackTrace();
		}
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
		this.writeSave();
		assetManager.dispose();
		menuScreen.dispose();
		gameScreen.dispose();
		scoreScreen.dispose();
		fonts.dispose();
	}
}
