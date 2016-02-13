package com.amverhagen.tube.game;

import com.badlogic.gdx.graphics.Color;

public enum GameColor {
	TUBE_WHITE(new Color(1,1,1,1));
	
	final Color color;
	
	GameColor(Color color){
		this.color = color;
	}
	
	@Override
	public String toString(){
		return "";
	}
}
