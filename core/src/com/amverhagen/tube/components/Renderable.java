package com.amverhagen.tube.components;

import com.artemis.Component;
import com.badlogic.gdx.graphics.Texture;

public class Renderable extends Component {
	public Texture texture;

	public Renderable(Texture texture) {
		this.texture = texture;
	}
}
