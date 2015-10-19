package com.amverhagen.tube.game;

public class ScreenState {

	public State state;

	public ScreenState(State state) {
		this.state = state;
	}

	public enum State {
		FADING, PAUSED, RUNNING, HINT, OVER;
	}
}
