package com.amverhagen.tube.screens;

import com.amverhagen.tube.components.Body;
import com.amverhagen.tube.components.Drawable;
import com.amverhagen.tube.components.MovementDirection;
import com.amverhagen.tube.components.MovementSpeed;
import com.amverhagen.tube.components.Position;
import com.amverhagen.tube.components.SetMoveDirectionBasedOnRightOrLeftPress;
import com.amverhagen.tube.game.TubeGame;
import com.amverhagen.tube.systems.DrawingSystem;
import com.amverhagen.tube.systems.ShiftDirectionLeftOrRightByPressSystem;
import com.amverhagen.tube.systems.MoveInDirectionSystem;
import com.artemis.World;
import com.artemis.WorldConfiguration;
import com.artemis.utils.EntityBuilder;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;

public class GameScreen implements Screen {
	private TubeGame game;
	private World artWorld;

	public GameScreen(TubeGame game) {
		this.game = game;
		WorldConfiguration worldConfig = new WorldConfiguration();
		worldConfig.setSystem(DrawingSystem.class);
		worldConfig.setSystem(MoveInDirectionSystem.class);
		worldConfig.setSystem(ShiftDirectionLeftOrRightByPressSystem.class);
		artWorld = new World(worldConfig);
		new EntityBuilder(artWorld).with(new Position(0, 0), new Body(.5f, .5f),
				new Drawable(new Texture(Gdx.files.internal("green_circle.png")), game.batch),
				new MovementDirection(MovementDirection.Direction.NORTH), new MovementSpeed(5f),
				new SetMoveDirectionBasedOnRightOrLeftPress(game.camera)).build();
	}

	@Override
	public void show() {

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		game.batch.setProjectionMatrix(game.viewport.getCamera().combined);
		game.batch.begin();
		artWorld.process();
		game.batch.end();
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
