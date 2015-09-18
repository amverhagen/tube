package com.amverhagen.tube.components;

import com.badlogic.gdx.graphics.Camera;

public class GluePosToCamera extends com.artemis.Component {
	public Camera camera;
	public float relativeX;
	public float relativeY;

	public GluePosToCamera(Camera camera, float relativeX, float relativeY) {
		this.camera = camera;
		this.relativeX = relativeX;
		this.relativeY = relativeY;
	}
}
