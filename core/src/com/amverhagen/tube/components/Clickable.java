package com.amverhagen.tube.components;

public class Clickable extends com.artemis.Component {
	public Event event;

	public Clickable(Event event) {
		this.event = event;
	}

	public interface Event {
		public void action();
	}
}
