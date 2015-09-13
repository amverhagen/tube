package com.amverhagen.tube.screens;

import com.amverhagen.tube.components.AddConnectedPointsFromEntityCenter;
import com.amverhagen.tube.components.Body;
import com.amverhagen.tube.components.CameraFocus;
import com.amverhagen.tube.components.Drawable;
import com.amverhagen.tube.components.MovementDirection;
import com.amverhagen.tube.components.MovementSpeed;
import com.amverhagen.tube.components.Position;
import com.amverhagen.tube.components.RecordConnectedPoints;
import com.amverhagen.tube.components.RenderConnectedPoints;
import com.amverhagen.tube.components.SetMoveDirectionBasedOnRightOrLeftPress;
import com.amverhagen.tube.game.TubeGame;
import com.amverhagen.tube.systems.AddConnectedPointsFromEntityCenterSystem;
import com.amverhagen.tube.systems.CameraFocusSystem;
import com.amverhagen.tube.systems.DrawingSystem;
import com.amverhagen.tube.systems.MoveInDirectionSystem;
import com.amverhagen.tube.systems.RenderConnectedPointsSystem;
import com.amverhagen.tube.systems.ShiftDirectionLeftOrRightByPressSystem;
import com.artemis.World;
import com.artemis.WorldConfiguration;
import com.artemis.utils.EntityBuilder;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;

public class GameScreen implements Screen {
	private TubeGame game;
	private World batchArtWorld;

	public GameScreen(TubeGame game) {
		this.game = game;
		WorldConfiguration worldConfig = new WorldConfiguration();
		worldConfig.setSystem(new RenderConnectedPointsSystem(game.shapeRenderer));
		worldConfig.setSystem(new DrawingSystem(game.batch));
		worldConfig.setSystem(MoveInDirectionSystem.class);
		worldConfig.setSystem(ShiftDirectionLeftOrRightByPressSystem.class);
		worldConfig.setSystem(AddConnectedPointsFromEntityCenterSystem.class);
		worldConfig.setSystem(CameraFocusSystem.class);

		batchArtWorld = new World(worldConfig);
		new EntityBuilder(batchArtWorld).with(new Position(0, 0), new Body(.5f, .5f),
				new Drawable(new Texture(Gdx.files.internal("green_circle.png")), game.batch),
				new MovementDirection(MovementDirection.Direction.NORTH), new MovementSpeed(5f),
				new SetMoveDirectionBasedOnRightOrLeftPress(game.camera), new AddConnectedPointsFromEntityCenter(),
				new RecordConnectedPoints(), new RenderConnectedPoints(Color.BLUE, .05f), new CameraFocus(game.camera))
				.build();
		new EntityBuilder(batchArtWorld).with(new Position(1, 2.25f), new Body(6f, .25f),
				new Drawable(new Texture(Gdx.files.internal("black.png")), game.batch)).build();
		new EntityBuilder(batchArtWorld).with(new Position(7f, 2.5f), new Body(.25f, -6.25f),
				new Drawable(new Texture(Gdx.files.internal("black.png")), game.batch)).build();
		new EntityBuilder(batchArtWorld).with(new Position(1, 1), new Body(5f, .25f),
				new Drawable(new Texture(Gdx.files.internal("black.png")), game.batch)).build();
		new EntityBuilder(batchArtWorld).with(new Position(5.75f, 1.25f), new Body(.25f, -5f),
				new Drawable(new Texture(Gdx.files.internal("black.png")), game.batch)).build();
	}

	@Override
	public void show() {

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		game.batch.setProjectionMatrix(game.viewport.getCamera().combined);
		game.shapeRenderer.setProjectionMatrix(game.viewport.getCamera().combined);
		batchArtWorld.process();
	}

	@Override
	public void resize(int width, int height) {
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
