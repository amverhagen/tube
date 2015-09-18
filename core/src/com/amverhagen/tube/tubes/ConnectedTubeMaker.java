package com.amverhagen.tube.tubes;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;

public class ConnectedTubeMaker {

	public static ArrayList<Tube> makeConnectedTubes(int size, Vector2 position) {
		ArrayList<Tube> tubes = new ArrayList<Tube>();
		if (tubes.size() < size) {
			tubes.add(new Tube(position, Tube.Type.LONG, Tube.Direction.EAST));
			addTubeUntilSizeReached(tubes, size);
		}
		return tubes;
	}

	private static boolean addTubeUntilSizeReached(ArrayList<Tube> tubes, int size) {
		if (tubes.size() >= size)
			return true;
		if (tubes.size() < 1) {

		}

		return false;

	}

}
