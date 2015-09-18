package com.amverhagen.tube.components;

import com.artemis.Component;
import com.badlogic.gdx.graphics.Texture;

public class Drawable extends Component {
	public Texture texture;

	public Drawable(Texture texture) {
		this.texture = texture;
	}
}
