package com.amverhagen.tube.components;

import com.amverhagen.tube.game.ScreenState;
import com.badlogic.gdx.graphics.Color;

public class Fadeable extends com.artemis.Component {

	public Color fadeColor;
	public final FadeEvent fadeEvent;
	public ScreenState fadeState;
	public float fadeDuration;

	public Fadeable(Color fadeColor, FadeEvent fadeEvent) {
		this.fadeColor = fadeColor;
		this.fadeEvent = fadeEvent;
	}

	public interface FadeEvent {
		public void event();
	}
}
