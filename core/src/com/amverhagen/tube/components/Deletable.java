package com.amverhagen.tube.components;

public class Deletable extends com.artemis.Component {
	public boolean needsDeleted;

	public Deletable(boolean needsDeleted) {
		this.needsDeleted = needsDeleted;
	}
}
