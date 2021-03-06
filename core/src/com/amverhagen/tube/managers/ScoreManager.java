package com.amverhagen.tube.managers;

import com.amverhagen.tube.components.Center;
import com.amverhagen.tube.components.Text;
import com.amverhagen.tube.game.TubeGame;
import com.artemis.Entity;

public class ScoreManager extends com.artemis.Manager {
	public int score;
	public Entity scoreLabel;
	private TubeGame game;

	public ScoreManager(TubeGame game) {
		this.game = game;
	}

	@Override
	protected void initialize() {
		scoreLabel = world.createEntity();
		Text t = new Text("0", new Center(TubeGame.GAME_WIDTH * .9f, TubeGame.GAME_HEIGHT * .9f),
				game.fonts.getFont(game.background, 48));
		scoreLabel.edit().add(t);
	}

	public void restart() {
		this.score = 0;
		Text scoreText = scoreLabel.getComponent(Text.class);
		scoreText.text = "" + this.score;
		scoreText.font = game.fonts.getFont(game.background, 48);
	}

	public void increaseScore() {
		this.score++;
		scoreLabel.getComponent(Text.class).text = "" + this.score;
	}
}
