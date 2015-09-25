package com.amverhagen.tube.components;

import java.security.InvalidParameterException;

import com.artemis.Entity;

public class HasParent extends com.artemis.Component {
	public Entity parent;

	public HasParent(Entity parent) {
		Deletable d = parent.getComponent(Deletable.class);
		if (d == null) {
			throw new InvalidParameterException("Parent must have Deletable component.");
		} else {
			this.parent = parent;
		}
	}
}
