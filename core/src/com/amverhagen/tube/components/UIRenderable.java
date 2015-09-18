package com.amverhagen.tube.components;

import com.badlogic.gdx.graphics.Texture;

public class UIRenderable extends com.artemis.Component {
	public Texture texture;

	public UIRenderable(Texture texture) {
		this.texture = texture;
	}
}
