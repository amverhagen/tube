package com.amverhagen.tube.systems;

import com.amverhagen.tube.components.Clickable;
import com.amverhagen.tube.components.RenderBody;
import com.amverhagen.tube.components.Position;
import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;

public class UiClickSystem extends com.artemis.systems.EntityProcessingSystem {
	@Wire
	ComponentMapper<RenderBody> dimensionMapper;
	@Wire
	ComponentMapper<Position> positionMapper;
	@Wire
	ComponentMapper<Clickable> clickMapper;
	private Viewport uiViewport;
	private Vector2 unprojected;
	private ScreenState state;
	private final float GCD;
	private float timeSinceLastClick;
	private boolean onGCD;

	@SuppressWarnings("unchecked")
	public UiClickSystem(Viewport uiViewport, ScreenState state) {
		super(Aspect.all(Clickable.class, RenderBody.class, Position.class));
		this.uiViewport = uiViewport;
		unprojected = new Vector2(0, 0);
		this.GCD = .15f;
		this.state = state;
		this.onGCD = false;
	}

	@Override
	protected void begin() {
		if (onGCD)
			timeSinceLastClick += world.delta;
		if (timeSinceLastClick > GCD)
			onGCD = false;
		if (Gdx.input.justTouched()) {
			unprojected = uiViewport.unproject((new Vector2(Gdx.input.getX(), Gdx.input.getY())));
		}
	}

	@Override
	protected void process(Entity e) {
		Clickable clickComp = clickMapper.get(e);
		if (state.state == clickComp.actionState) {
			if (Gdx.input.justTouched()) {
				if (!clickComp.onGCD || !onGCD) {
					RenderBody drawComp = dimensionMapper.get(e);
					Position pos = positionMapper.get(e);

					if ((unprojected.x >= pos.x) && (unprojected.x <= (pos.x + drawComp.width))
							&& (unprojected.y >= pos.y) && (unprojected.y <= (pos.y + drawComp.height))) {
						clickComp.event.action();
						if (clickComp.onGCD) {
							onGCD = true;
							timeSinceLastClick = 0;
						}
					}
				}
			}
		}
	}
}
