package com.amverhagen.tube.components;

import com.badlogic.gdx.graphics.Color;

public class DrawShape extends com.artemis.Component {
	public Color color;
	public float width;

	public DrawShape(Color color, float width) {
		this.color = color;
		this.width = width;
	}
}
