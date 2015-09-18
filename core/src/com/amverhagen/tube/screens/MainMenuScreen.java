package com.amverhagen.tube.screens;

import com.amverhagen.tube.components.CameraFocus;
import com.amverhagen.tube.components.Clickable;
import com.amverhagen.tube.components.Drawable;
import com.amverhagen.tube.components.DrawingDimension;
import com.amverhagen.tube.components.MovementDirection;
import com.amverhagen.tube.components.MovementSpeed;
import com.amverhagen.tube.components.Position;
import com.amverhagen.tube.components.UIRenderable;
import com.amverhagen.tube.game.TubeGame;
import com.amverhagen.tube.systems.UiClickSystem;
import com.amverhagen.tube.tubes.Tube;
import com.amverhagen.tube.tubes.Tube.Direction;
import com.amverhagen.tube.tubes.Tube.Type;
import com.amverhagen.tube.systems.CameraFocusSystem;
import com.amverhagen.tube.systems.DrawingSystem;
import com.amverhagen.tube.systems.MoveInAngleDirectionSystem;
import com.amverhagen.tube.systems.MoveInDirectionSystem;
import com.amverhagen.tube.systems.RenderUISystem;
import com.artemis.Entity;
import com.artemis.World;
import com.artemis.WorldConfiguration;
import com.artemis.utils.EntityBuilder;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class MainMenuScreen implements Screen {
	TubeGame game;
	World world;
	Entity trail;
	Entity playButton;
	Entity optionsButton;
	Entity ball;

	public MainMenuScreen(TubeGame game) {
		this.game = game;
		createWorld();
		createTrail();
		createButtons();
		createBall();
	}

	public void createWorld() {
		WorldConfiguration worldConfig = new WorldConfiguration();
		worldConfig.setSystem(new UiClickSystem(game.uiCamera));
		worldConfig.setSystem(MoveInAngleDirectionSystem.class);
		worldConfig.setSystem(CameraFocusSystem.class);
		worldConfig.setSystem(MoveInDirectionSystem.class);
		worldConfig.setSystem(new DrawingSystem(game.gameBatch));
		worldConfig.setSystem(new RenderUISystem(game.uiBatch, game.uiCamera));
		world = new World(worldConfig);
	}

	public void createTrail() {
		Tube tube = new Tube(new Vector2(0, 0), Type.SHORT, Direction.EAST);
		this.addTubeToWorld(tube);
		tube = new Tube(tube, Type.CLOCK);
		this.addTubeToWorld(tube);
		tube = new Tube(tube, Type.SHORT);
		this.addTubeToWorld(tube);
	}

	public void createButtons() {
		playButton = createButtonEntity(new Texture(Gdx.files.internal("play.png")), new Vector2(2, .5f),
				new Vector2(2, 1));
		optionsButton = createButtonEntity(new Texture(Gdx.files.internal("options.png")), new Vector2(6, .5f),
				new Vector2(2, 1));
	}

	public void createBall() {
		ball = new EntityBuilder(world).with(new Position(0, 0), new DrawingDimension(.5f, .5f),
				new Drawable(new Texture(Gdx.files.internal("green_circle.png"))),
				new MovementDirection(MovementDirection.Direction.EAST), new MovementSpeed(1f),
				new CameraFocus(game.gameCamera)).build();
	}

	public void addTubeToWorld(Tube t) {
		Entity tube = world.createEntity();
		Drawable dc = new Drawable(new Texture(Gdx.files.internal("pipe.png")));
		Position pc = new Position(t.getPosition());
		DrawingDimension ddc = new DrawingDimension(t.getBounds());
		tube.edit().add(dc).add(pc).add(ddc);
	}

	public Entity createButtonEntity(Texture texture, Vector2 pos, Vector2 body) {

		Entity e = world.createEntity();
		UIRenderable uic = new UIRenderable(texture);
		Position pc = new Position(pos);
		DrawingDimension ddc = new DrawingDimension(body);
		Clickable cc = new Clickable(game.gameCamera);
		e.edit().add(uic).add(pc).add(ddc).add(cc);

		return e;
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		game.gameBatch.setProjectionMatrix(game.viewport.getCamera().combined);
		game.shapeRenderer.setProjectionMatrix(game.viewport.getCamera().combined);

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
		world.dispose();
	}

}
