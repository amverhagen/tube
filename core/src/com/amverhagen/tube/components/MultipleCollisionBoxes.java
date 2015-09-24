package com.amverhagen.tube.components;

import java.util.ArrayList;

public class MultipleCollisionBoxes extends com.artemis.Component {

	public ArrayList<CollidableEntity> collidableBoxes;

	public MultipleCollisionBoxes(ArrayList<CollidableEntity> boxes) {
		collidableBoxes = boxes;
	}
}
