package com.amverhagen.tube.game;

public class Score {
	private int score;

	public Score() {
		this.score = 0;
	}

	public void incrementScore() {
		this.score++;
	}

	public int getScore() {
		return this.score;
	}
}
