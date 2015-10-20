package com.amverhagen.tube.game;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

public class Fonts {

	public HashMap<ColorSize, BitmapFont> fontsMap;
	private FreeTypeFontGenerator ftfg;
	private Color white;

	public Fonts(TubeGame game) {
		fontsMap = new HashMap<ColorSize, BitmapFont>();
		ftfg = new FreeTypeFontGenerator(Gdx.files.internal("fonts/whitrabt.ttf"));
		white = game.colors.TUBE_WHITE;

		makeFont(new ColorSize(game.colors.TUBE_WHITE, 24), false);
		makeFont(new ColorSize(game.colors.TUBE_WHITE, 48), false);
		makeFont(new ColorSize(game.colors.TUBE_WHITE, 60), false);

		makeFont(new ColorSize(game.colors.TUBE_BLUE, 24), true);
		makeFont(new ColorSize(game.colors.TUBE_BLUE, 48), true);
		makeFont(new ColorSize(game.colors.TUBE_BLUE, 60), true);

		makeFont(new ColorSize(game.colors.TUBE_BLACK, 24), true);
		makeFont(new ColorSize(game.colors.TUBE_BLACK, 48), true);
		makeFont(new ColorSize(game.colors.TUBE_BLACK, 60), true);

		makeFont(new ColorSize(game.colors.TUBE_PINK, 24), true);
		makeFont(new ColorSize(game.colors.TUBE_PINK, 48), true);
		makeFont(new ColorSize(game.colors.TUBE_PINK, 60), true);

		makeFont(new ColorSize(game.colors.TUBE_GREEN, 24), true);
		makeFont(new ColorSize(game.colors.TUBE_GREEN, 48), true);
		makeFont(new ColorSize(game.colors.TUBE_GREEN, 60), true);

		makeFont(new ColorSize(game.colors.TUBE_PURPLE, 24), true);
		makeFont(new ColorSize(game.colors.TUBE_PURPLE, 48), true);
		makeFont(new ColorSize(game.colors.TUBE_PURPLE, 60), true);

		makeFont(new ColorSize(game.colors.TUBE_RED, 24), true);
		makeFont(new ColorSize(game.colors.TUBE_RED, 48), true);
		makeFont(new ColorSize(game.colors.TUBE_RED, 60), true);

		ftfg.dispose();
	}

	public void dispose() {
		for (BitmapFont bmp : fontsMap.values()) {
			bmp.dispose();
		}
	}

	public void makeFont(ColorSize colorSize, boolean border) {
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = colorSize.fontSize;
		parameter.color = white;
		parameter.borderColor = colorSize.color;
		if (border)
			parameter.borderWidth = 3;
		fontsMap.put(colorSize, ftfg.generateFont(parameter));
	}

	public BitmapFont getFont(Color c, int size) {
		return fontsMap.get(new ColorSize(c, size));
	}

	public class ColorSize {
		int fontSize;
		Color color;

		public ColorSize(Color color, int size) {
			this.fontSize = size;
			this.color = color;
		}

		@Override
		public int hashCode() {
			return color.hashCode() + fontSize;
		};

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (!(obj instanceof ColorSize))
				return false;
			ColorSize incoming = (ColorSize) obj;
			if (this.color.equals(incoming.color) && this.fontSize == incoming.fontSize)
				return true;
			return false;
		}
	}
}
