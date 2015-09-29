package com.amverhagen.tube.components;

import com.amverhagen.tube.systems.ScreenState.State;

public class Clickable extends com.artemis.Component {
	public Event event;
	public State actionState;

	public Clickable(State state, Event event) {
		this.actionState = state;
		this.event = event;
	}

	public interface Event {
		public void action();
	}
}
