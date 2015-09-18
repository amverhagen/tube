package com.amverhagen.tube.components;

import com.artemis.Component;
import com.badlogic.gdx.math.Vector2;

public class DrawingDimension extends Component {
	public float width = 0;
	public float height = 0;

	public DrawingDimension(float width, float height) {
		this.width = width;
		this.height = height;
	}

	public DrawingDimension(Vector2 body) {
		this.width = body.x;
		this.height = body.y;
	}
}
