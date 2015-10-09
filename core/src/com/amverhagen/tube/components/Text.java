package com.amverhagen.tube.components;

import com.amverhagen.tube.game.TubeGame;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.math.Vector2;

public class Text extends com.artemis.Component {
	public String text;
	public float xPos;
	public float yPos;
	public BitmapFont font;

	public Text(String text, Vector2 center, int size) {
		this.text = text;
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = size;
		parameter.borderWidth = 3;
		parameter.borderColor = Color.GREEN;
		font = TubeGame.fontGenerator.generateFont(parameter);
		setCenter(center);
	}

	public void setCenter(Vector2 center) {
		GlyphLayout glyphLayout = new GlyphLayout();
		glyphLayout.setText(font, "Play");
		xPos = center.x - glyphLayout.width / 2;
		yPos = center.y + glyphLayout.height / 2;
	}
}
