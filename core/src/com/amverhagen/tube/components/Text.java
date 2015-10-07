package com.amverhagen.tube.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.math.Vector2;

public class Text extends com.artemis.Component {
	public String text;
	public float xPos;
	public float yPos;
	public BitmapFont font;

	public Text(String text, Vector2 center, int size) {
		this.text = text;

		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/whitrabt.ttf"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = size;
		font = generator.generateFont(parameter);
		generator.dispose();
		setCenter(center);
	}

	public void setCenter(Vector2 center) {
		GlyphLayout glyphLayout = new GlyphLayout();
		glyphLayout.setText(font, "Play");
		xPos = center.x - glyphLayout.width / 2;
		yPos = center.y + glyphLayout.height / 2;
	}
}
