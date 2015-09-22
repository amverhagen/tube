package com.amverhagen.tube.systems;

public class ScreenState {

	public State state;

	public ScreenState(State state) {
		this.state = state;
	}

	public enum State {
		LOADING, PAUSED, RUNNING;
	}
}
