package com.amverhagen.tube.systems;

import com.amverhagen.tube.components.Course;
import com.amverhagen.tube.components.MovementSpeed;
import com.amverhagen.tube.components.Position;
import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class MoveEntityAroundCourse extends com.artemis.systems.EntityProcessingSystem {
	private Vector2 destination;

	@Wire
	ComponentMapper<Position> positionMapper;
	@Wire
	ComponentMapper<Course> courseMapper;
	@Wire
	ComponentMapper<MovementSpeed> speedMapper;

	@SuppressWarnings("unchecked")
	public MoveEntityAroundCourse() {
		super(Aspect.all(Position.class, Course.class, MovementSpeed.class));
	}

	@Override
	protected void process(Entity e) {
		Position posComp = positionMapper.get(e);
		Course courseComp = courseMapper.get(e);
		MovementSpeed speedComp = speedMapper.get(e);

		if (destination == null) {
			destination = courseComp.course.dequeue();
		}

		if (Vector2.dst(posComp.x, posComp.y, destination.x, destination.y) < .1) {
			destination = courseComp.course.dequeue();
		}

		System.out.println(Gdx.graphics.getDeltaTime());
		System.out.println(world.delta);
		float distance = world.delta * speedComp.movementSpeed;
		float slope = (destination.y - posComp.y) / (destination.x - posComp.x);
		float xDif = (float) Math.sqrt((distance * distance) / ((slope * slope) + 1));
		if (destination.x < posComp.x)
			xDif *= -1;
		float yDif = xDif * slope;
		posComp.x += xDif;
		posComp.y += yDif;
	}

}
