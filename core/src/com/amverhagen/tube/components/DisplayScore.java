package com.amverhagen.tube.components;

import com.amverhagen.tube.game.Score;

public class DisplayScore extends com.artemis.Component {
	public Score score;

	public DisplayScore(Score score) {
		this.score = score;
	}
}
