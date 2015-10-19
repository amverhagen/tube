package com.amverhagen.tube.managers;

import com.amverhagen.tube.components.Clickable;
import com.amverhagen.tube.components.Clickable.Event;
import com.amverhagen.tube.components.Position;
import com.amverhagen.tube.components.RenderBody;
import com.amverhagen.tube.game.TubeGame;
import com.amverhagen.tube.game.ScreenState.State;
import com.artemis.Entity;

public class InputPanelManager extends com.artemis.Manager {
	private Entity leftPanel;
	private Entity rightPanel;
	private PlayerManager playerManager;

	public InputPanelManager() {

	}

	@Override
	protected void initialize() {
		createLeftPanel();
		createRightPanel();
		playerManager = world.getManager(PlayerManager.class);
	}

	private void createLeftPanel() {
		leftPanel = world.createEntity();
		Event event = new Event() {
			@Override
			public void action() {
				playerManager.shiftPlayerLeft();
			}
		};
		Position position = new Position(0, 0);
		RenderBody rd = new RenderBody(TubeGame.GAME_WIDTH / 2f, TubeGame.GAME_HEIGHT);
		Clickable cl = new Clickable(State.RUNNING, event, true);
		leftPanel.edit().add(position).add(rd).add(cl);
	}

	private void createRightPanel() {
		rightPanel = world.createEntity();
		Event event = new Event() {
			@Override
			public void action() {
				playerManager.shiftPlayerRight();
			}
		};
		Position position = new Position(TubeGame.GAME_WIDTH / 2f, 0);
		RenderBody rd = new RenderBody(TubeGame.GAME_WIDTH / 2f, TubeGame.GAME_HEIGHT);
		Clickable cl = new Clickable(State.RUNNING, event, true);
		rightPanel.edit().add(position).add(rd).add(cl);
	}
}
