package com.amverhagen.tube.components;

import com.amverhagen.tube.systems.ScreenState;

public class DrawDuringState extends com.artemis.Component{
	public ScreenState drawState;
	
	public DrawDuringState(ScreenState state){
		this.drawState = state;
	}
}
