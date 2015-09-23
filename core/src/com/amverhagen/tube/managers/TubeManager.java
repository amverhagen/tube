package com.amverhagen.tube.managers;

import com.amverhagen.tube.collections.LinkedListQueue;
import com.amverhagen.tube.tubes.Tube;

public class TubeManager extends com.artemis.managers.GroupManager {
	LinkedListQueue<Tube> activeTubes;
	LinkedListQueue<Tube> upcomingTubes;

	public TubeManager() {

	}

	public void hitCenter() {

	}
}
