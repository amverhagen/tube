package com.amverhagen.tube.tubes;

import java.util.ArrayList;
import java.util.Stack;

import com.amverhagen.tube.tubes.Tube.Type;
import com.badlogic.gdx.math.Vector2;

public class ConnectedTubeMaker {

	public static ArrayList<Tube> makeConnectedTubes(int size, Vector2 position) {
		Stack<Tube> tubes = new Stack<Tube>();
		if (tubes.size() < size) {
			tubes.push(new Tube(position, Tube.Type.SHORT, Tube.Direction.EAST));
			addTubeUntilSizeReached(tubes, size);
		}
		ArrayList<Tube> tubeList = new ArrayList<Tube>(tubes);
		return tubeList;
	}

	private static boolean addTubeUntilSizeReached(Stack<Tube> tubes, int size) {
		if (tubes.size() >= size)
			return true;
		ArrayList<Type> types = new ArrayList<Type>();
		if (tubes.peek().getType() == Type.SHORT) {
			types = Type.getTurnList();
		} else {
			types = Type.getStraightList();
		}

		while (types.size() > 0) {
			Type type = types.remove((int) (Math.random() * types.size()));
			Tube tube = new Tube(tubes.peek(), type);
			ArrayList<Tube> tubeList = new ArrayList<Tube>(tubes);
			if (compareTubeListForCollision(tubeList, tube)) {
				tubes.push(tube);
				if (addTubeUntilSizeReached(tubes, size))
					return true;
				else
					tubes.pop();
			}
		}
		return false;
	}

	public static boolean compareTubeListForCollision(ArrayList<Tube> tubes, Tube tube) {
		float tubeRightBound = tube.getPosition().x + tube.getBounds().x;
		float tubeLeftBound = tube.getPosition().x;
		float tubeLowerBound = tube.getPosition().y;
		float tubeUpperBound = tube.getPosition().y + tube.getBounds().y;

		for (Tube t : tubes) {
			if (t.getPosition().x < tubeRightBound && (t.getPosition().x + t.getBounds().x) > tubeLeftBound) {
				if (t.getPosition().y < tubeUpperBound && (t.getPosition().y + t.getBounds().y) > tubeLowerBound) {
					return false;
				}
			}
		}
		return true;
	}

}
