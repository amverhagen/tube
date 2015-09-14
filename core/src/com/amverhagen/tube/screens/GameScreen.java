package com.amverhagen.tube.screens;

import com.amverhagen.tube.components.AddConnectedPointsFromEntityCenter;
import com.amverhagen.tube.components.Bar;
import com.amverhagen.tube.components.DrawingDimension;
import com.amverhagen.tube.components.CameraFocus;
import com.amverhagen.tube.components.CollidableRectangle;
import com.amverhagen.tube.components.Drawable;
import com.amverhagen.tube.components.MovementDirection;
import com.amverhagen.tube.components.MovementSpeed;
import com.amverhagen.tube.components.Position;
import com.amverhagen.tube.components.RecordConnectedPoints;
import com.amverhagen.tube.components.RenderConnectedPoints;
import com.amverhagen.tube.components.SetMoveDirectionBasedOnRightOrLeftPress;
import com.amverhagen.tube.game.TubeGame;
import com.amverhagen.tube.systems.AddConnectedPointsFromEntityCenterSystem;
import com.amverhagen.tube.systems.AddConnectedPointsFromEntityPosSystem;
import com.amverhagen.tube.systems.CameraFocusSystem;
import com.amverhagen.tube.systems.CheckPlayerCollisionSystem;
import com.amverhagen.tube.systems.DrawingSystem;
import com.amverhagen.tube.systems.MoveInAngleDirectionSystem;
import com.amverhagen.tube.systems.MoveInDirectionSystem;
import com.amverhagen.tube.systems.RenderConnectedPointsSystem;
import com.amverhagen.tube.systems.ShiftDirectionLeftOrRightByPressSystem;
import com.artemis.Entity;
import com.artemis.World;
import com.artemis.WorldConfiguration;
import com.artemis.managers.TagManager;
import com.artemis.utils.EntityBuilder;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;

public class GameScreen implements Screen {
	private TubeGame game;
	private World artWorld;

	public GameScreen(TubeGame game) {
		this.game = game;
		WorldConfiguration worldConfig = new WorldConfiguration();
		worldConfig.setManager(TagManager.class);
		worldConfig.setSystem(CheckPlayerCollisionSystem.class);
		worldConfig.setSystem(new RenderConnectedPointsSystem(game.shapeRenderer));
		worldConfig.setSystem(new DrawingSystem(game.batch));
		worldConfig.setSystem(MoveInDirectionSystem.class);
		worldConfig.setSystem(ShiftDirectionLeftOrRightByPressSystem.class);
		worldConfig.setSystem(AddConnectedPointsFromEntityCenterSystem.class);
		worldConfig.setSystem(CameraFocusSystem.class);
		worldConfig.setSystem(AddConnectedPointsFromEntityPosSystem.class);
		worldConfig.setSystem(MoveInAngleDirectionSystem.class);

		artWorld = new World(worldConfig);
		Entity player = new EntityBuilder(artWorld)
				.with(new Position(0, 0), new CollidableRectangle(.5f, .5f), new DrawingDimension(.5f, .5f),
						new Drawable(new Texture(Gdx.files.internal("green_circle.png")), game.batch),
						new MovementDirection(MovementDirection.Direction.NORTH), new MovementSpeed(5f),
						new SetMoveDirectionBasedOnRightOrLeftPress(game.camera),
						new AddConnectedPointsFromEntityCenter(), new RecordConnectedPoints(20),
						new RenderConnectedPoints(Color.BLUE, .05f), new CameraFocus(game.camera))
				.build();
		artWorld.getManager(TagManager.class).register("PLAYER", player);
		new EntityBuilder(artWorld)
				.with(new Position(1, 2.25f), new DrawingDimension(6f, .25f), new CollidableRectangle(6f, .25f),
						new Drawable(new Texture(Gdx.files.internal("black.png")), game.batch), new Bar())
				.build();
		new EntityBuilder(artWorld)
				.with(new Position(1, 3.25f), new DrawingDimension(6f, .25f), new CollidableRectangle(6f, .25f),
						new Drawable(new Texture(Gdx.files.internal("black.png")), game.batch), new Bar())
				.build();

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
		artWorld.process();
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
