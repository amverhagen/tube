package com.amverhagen.tube.managers;

import com.amverhagen.tube.components.Center;
import com.amverhagen.tube.components.Clickable;
import com.amverhagen.tube.components.Clickable.Event;
import com.amverhagen.tube.components.DrawToUI;
import com.amverhagen.tube.components.Position;
import com.amverhagen.tube.components.RenderBody;
import com.amverhagen.tube.components.Text;
import com.amverhagen.tube.game.TubeGame;
import com.amverhagen.tube.game.ScreenState.State;
import com.artemis.Entity;

public class SoundButtonManager extends com.artemis.Manager {
	private Entity soundButton;
	private TubeGame game;
	private final String onText = "Sound: On";
	private final String offText = "Sound: Off";

	public SoundButtonManager(TubeGame game) {
		this.game = game;
	}

	@Override
	protected void initialize() {
		Event event = new Clickable.Event() {
			@Override
			public void action() {
				toggleSound();
			}
		};
		soundButton = world.createEntity();
		Position pos = new Position(TubeGame.GAME_WIDTH * .80f, TubeGame.GAME_HEIGHT * .85f);
		RenderBody rb = new RenderBody(TubeGame.GAME_WIDTH * .15f, TubeGame.GAME_HEIGHT * .1f);
		Center center = new Center(pos, rb);
		DrawToUI dtui = new DrawToUI();
		Text text = new Text(offText, center, game.fonts.getFont(game.colors.TUBE_WHITE, 48));
		Clickable click = new Clickable(State.RUNNING, event, true);

		soundButton.edit().add(pos).add(rb).add(dtui).add(text).add(click);
		setText();
	}

	private void setText() {
		if (game.soundOn)
			soundButton.getComponent(Text.class).text = onText;
		else
			soundButton.getComponent(Text.class).text = offText;
	}

	private void toggleSound() {
		game.soundOn = !game.soundOn;
		setText();
	}
}
