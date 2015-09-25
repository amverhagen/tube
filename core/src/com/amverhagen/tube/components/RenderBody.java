package com.amverhagen.tube.components;

import com.artemis.Component;
import com.badlogic.gdx.math.Vector2;

public class RenderBody extends Component {
	public float width = 0;
	public float height = 0;

	public RenderBody(float width, float height) {
		this.width = width;
		this.height = height;
	}

	public RenderBody(Vector2 body) {
		this.width = body.x;
		this.height = body.y;
	}
}
