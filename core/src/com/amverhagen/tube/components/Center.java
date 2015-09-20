package com.amverhagen.tube.components;

import com.badlogic.gdx.math.Vector2;

public class Center extends com.artemis.Component {
	public Vector2 center;

	public Center(Vector2 pos, Vector2 body) {
		center = new Vector2();
		center.x = pos.x + (body.x / 2);
		center.y = pos.y + (body.y / 2);
	}
}
