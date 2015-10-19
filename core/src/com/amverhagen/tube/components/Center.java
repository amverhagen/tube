package com.amverhagen.tube.components;

import com.badlogic.gdx.math.Vector2;

public class Center extends com.artemis.Component {
	private Position position;
	private RenderBody body;
	public float x;
	public float y;

	public Center(Position pos, RenderBody body) {
		position = pos;
		this.body = body;
		updateCenter();
	}

	public Center(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public Center(Vector2 center) {
		this.x = center.x;
		this.y = center.y;
	}

	public void updateCenter() {
		x = position.x + (body.width / 2);
		y = position.y + (body.height / 2);
	}
}
