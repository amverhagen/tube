package com.amverhagen.tube.managers;

import com.amverhagen.tube.components.Center;
import com.amverhagen.tube.components.Clickable;
import com.amverhagen.tube.components.DrawToUI;
import com.amverhagen.tube.components.Position;
import com.amverhagen.tube.components.RenderBody;
import com.amverhagen.tube.components.Text;
import com.amverhagen.tube.components.Clickable.Event;
import com.amverhagen.tube.game.TubeGame;
import com.amverhagen.tube.game.ScreenState.State;
import com.artemis.Entity;

public class HintButtonManager extends com.artemis.Manager {
	private Entity hintButton;
	private TubeGame game;
	private final String onText = "Hints: On";
	private final String offText = "Hints: Off";

	public HintButtonManager(TubeGame game) {
		this.game = game;
	}

	@Override
	protected void initialize() {
		Event event = new Clickable.Event() {
			@Override
			public void action() {
				toggleHint();
			}
		};
		hintButton = world.createEntity();
		Position pos = new Position(TubeGame.GAME_WIDTH * .05f, TubeGame.GAME_HEIGHT * .85f);
		RenderBody rb = new RenderBody(TubeGame.GAME_WIDTH * .15f, TubeGame.GAME_HEIGHT * .1f);
		Center center = new Center(pos, rb);
		DrawToUI dtui = new DrawToUI();
		Text text = new Text(offText, center, game.fonts.getFont(game.colors.TUBE_WHITE, 48));
		Clickable click = new Clickable(State.RUNNING, event, true);

		hintButton.edit().add(pos).add(rb).add(dtui).add(text).add(click);
		setText();
	}

	private void setText() {
		if (game.tutorialOn)
			hintButton.getComponent(Text.class).text = onText;
		else
			hintButton.getComponent(Text.class).text = offText;
	}

	private void toggleHint() {
		game.tutorialOn = !game.tutorialOn;
		setText();
	}
}
