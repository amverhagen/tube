package com.amverhagen.tube.components;

import com.amverhagen.tube.collections.LinkedListQueue;
import com.badlogic.gdx.math.Vector2;

public class Course extends com.artemis.Component {
	public LinkedListQueue<Vector2> course;

	public Course(LinkedListQueue<Vector2> course) {
		this.course = course;
	}
}
