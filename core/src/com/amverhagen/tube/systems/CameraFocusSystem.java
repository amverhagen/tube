package com.amverhagen.tube.systems;

import com.amverhagen.tube.components.CameraFocus;
import com.amverhagen.tube.components.Center;
import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Wire;

public class CameraFocusSystem extends com.artemis.systems.EntityProcessingSystem {
	@Wire
	ComponentMapper<CameraFocus> cameraMapper;
	@Wire
	ComponentMapper<Center> centerMapper;

	@SuppressWarnings("unchecked")
	public CameraFocusSystem() {
		super(Aspect.all(CameraFocus.class, Center.class));
	}

	@Override
	protected void process(Entity e) {
		CameraFocus cameraComp = cameraMapper.get(e);
		Center centerComp = centerMapper.get(e);
		cameraComp.camera.position.set(centerComp.x, centerComp.y, 0);
		cameraComp.camera.update();
	}
}
