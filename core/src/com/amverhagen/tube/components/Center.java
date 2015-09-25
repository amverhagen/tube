package com.amverhagen.tube.components;

import com.badlogic.gdx.math.Vector2;

public class Center extends com.artemis.Component {
	public Position position;
	public RenderBody body;
	public Vector2 center;

	public Center(Position pos, RenderBody body) {
		center = new Vector2();
		position = pos;
		this.body = body;
		updateCenter();
	}

	public void updateCenter() {
		center.x = position.x + (body.width / 2);
		center.y = position.y + (body.height / 2);
	}
}
