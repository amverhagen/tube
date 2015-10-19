package com.amverhagen.tube.systems;

import com.amverhagen.tube.game.ScreenState;
import com.amverhagen.tube.game.ScreenState.State;

import aurelienribon.tweenengine.TweenManager;

public class FadeSystem extends com.artemis.BaseSystem {
	private ScreenState screenState;
	private TweenManager tweenManager;

	public FadeSystem(ScreenState state, TweenManager tweenManager) {
		this.screenState = state;
		this.tweenManager = tweenManager;
	}

	@Override
	protected void processSystem() {
		if (this.screenState.state == State.FADING) {
			tweenManager.update(world.delta);
		}
	}
}
