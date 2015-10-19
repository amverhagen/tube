package com.amverhagen.tube.components;

import com.amverhagen.tube.game.ScreenState.State;

public class Clickable extends com.artemis.Component {
	public Event event;
	public State actionState;
	public boolean onGCD;

	public Clickable(State state, Event event, boolean onDelay) {
		this.actionState = state;
		this.event = event;
		this.onGCD = onDelay;
	}

	public interface Event {
		public void action();
	}
}
