package com.amverhagen.tube.components;

import com.artemis.Component;
import com.badlogic.gdx.math.Vector2;

public class Position extends Component {
	public float x;
	public float y;

	public Position(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public Position(Vector2 pos) {
		this.x = pos.x;
		this.y = pos.y;
	}
}
