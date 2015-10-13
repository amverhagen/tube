package com.amverhagen.tube.components;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Vector2;

public class Text extends com.artemis.Component {
	public String text;
	public float xPos;
	public float yPos;
	public BitmapFont font;

	public Text(String text, Vector2 center, BitmapFont bmFont) {
		this.text = text;
		font = bmFont;
		setCenter(center);
	}

	public void setCenter(Vector2 center) {
		GlyphLayout glyphLayout = new GlyphLayout();
		glyphLayout.setText(font, text);
		xPos = center.x - glyphLayout.width / 2;
		yPos = center.y + glyphLayout.height / 2;
	}
}
