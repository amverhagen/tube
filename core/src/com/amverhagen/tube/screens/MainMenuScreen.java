package com.amverhagen.tube.screens;

import java.util.ArrayList;

import com.amverhagen.tube.collections.LinkedListQueue;
import com.amverhagen.tube.components.CameraFocus;
import com.amverhagen.tube.components.Center;
import com.amverhagen.tube.components.Clickable;
import com.amverhagen.tube.components.Course;
import com.amverhagen.tube.components.DrawLineAroundBody;
import com.amverhagen.tube.components.Drawable;
import com.amverhagen.tube.components.DrawingDimension;
import com.amverhagen.tube.components.MovementSpeed;
import com.amverhagen.tube.components.Position;
import com.amverhagen.tube.components.UIRenderable;
import com.amverhagen.tube.game.TubeGame;
import com.amverhagen.tube.systems.UiClickSystem;
import com.amverhagen.tube.tubes.ConnectedTubeMaker;
import com.amverhagen.tube.tubes.Tube;
import com.amverhagen.tube.systems.CameraFocusSystem;
import com.amverhagen.tube.systems.DrawingSystem;
import com.amverhagen.tube.systems.MoveEntityAroundCourse;
import com.amverhagen.tube.systems.MoveInAngleDirectionSystem;
import com.amverhagen.tube.systems.MoveInDirectionSystem;
import com.amverhagen.tube.systems.RenderBoxSystem;
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
	LinkedListQueue<Vector2> course;

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
		worldConfig.setSystem(MoveInDirectionSystem.class);
		worldConfig.setSystem(MoveEntityAroundCourse.class);
		worldConfig.setSystem(new DrawingSystem(game.gameBatch));
		worldConfig.setSystem(new RenderUISystem(game.uiBatch, game.uiCamera));
		worldConfig.setSystem(new RenderBoxSystem(game.shapeRenderer));
		worldConfig.setSystem(CameraFocusSystem.class);
		world = new World(worldConfig);
	}

	public void createTrail() {
		course = new LinkedListQueue<Vector2>();
		ArrayList<Tube> tubes = ConnectedTubeMaker.makeConnectedTubes(100, new Vector2(0, 0));
		for (Tube t : tubes) {
			this.addTubeToWorld(t);
		}
	}

	public void createButtons() {
		playButton = createButtonEntity(new Texture(Gdx.files.internal("play.png")), new Vector2(2, .5f),
				new Vector2(2, 1));
		optionsButton = createButtonEntity(new Texture(Gdx.files.internal("options.png")), new Vector2(6, .5f),
				new Vector2(2, 1));
	}

	public void createBall() {
		ball = new EntityBuilder(world).with(new Position(course.dequeue()), new DrawingDimension(.5f, .5f),
				new CameraFocus(game.gameCamera), new Course(course),
				new Drawable(new Texture(Gdx.files.internal("green_circle.png"))), new MovementSpeed(6f)).build();
	}

	public void addTubeToWorld(Tube t) {
		Entity tube = world.createEntity();
		DrawLineAroundBody dlac = new DrawLineAroundBody();
		Drawable dc = new Drawable(new Texture(Gdx.files.internal("black.png")));
		Position pc = new Position(t.getPosition());
		DrawingDimension ddc = new DrawingDimension(t.getBounds());
		Center cc = new Center(new Vector2(pc.x, pc.y), new Vector2(ddc.width, ddc.height));
		course.enqueue(cc.center);
		tube.edit().add(dc).add(pc).add(ddc).add(dlac).add(cc);
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
		if (delta > .1)
			delta = .1f;
		world.setDelta(delta);
		world.process();
	}

	@Override
	public void resize(int width, int height) {
		game.viewport.setScreenSize(width, height);
		game.viewport.apply();
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
