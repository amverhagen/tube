package com.amverhagen.tube.systems;

import com.amverhagen.tube.components.Clickable;
import com.amverhagen.tube.components.RenderBody;
import com.amverhagen.tube.components.Position;
import com.amverhagen.tube.systems.ScreenState.State;
import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector3;

public class UiClickSystem extends com.artemis.systems.EntityProcessingSystem {
	@Wire
	ComponentMapper<RenderBody> dimensionMapper;
	@Wire
	ComponentMapper<Position> positionMapper;
	@Wire
	ComponentMapper<Clickable> clickMapper;
	private Camera camera;
	private Vector3 unprojected;
	private ScreenState state;

	@SuppressWarnings("unchecked")
	public UiClickSystem(Camera camera, ScreenState state) {
		super(Aspect.all(Clickable.class, RenderBody.class, Position.class));
		this.camera = camera;
		unprojected = new Vector3(0, 0, 0);
		this.state = state;
	}

	@Override
	protected void begin() {
		if (Gdx.input.justTouched()) {
			unprojected = camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
		}
	}

	@Override
	protected void process(Entity e) {
		if (state.state == State.RUNNING) {
			if (Gdx.input.justTouched()) {
				RenderBody drawComp = dimensionMapper.get(e);
				Position pos = positionMapper.get(e);
				Clickable clickComp = clickMapper.get(e);
				if ((unprojected.x >= pos.x) && (unprojected.x <= (pos.x + drawComp.width)) && (unprojected.y >= pos.y)
						&& (unprojected.y <= (pos.y + drawComp.height))) {
					clickComp.event.action();
				}
			}
		}
	}
}
