package com.amverhagen.tube.components;

import com.artemis.Component;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Drawable extends Component {
	public Texture texture;
	public SpriteBatch batch;

	public Drawable(Texture texture, SpriteBatch batch) {
		this.texture = texture;
		this.batch = batch;
	}
}
