package com.amverhagen.tube.managers;

import com.amverhagen.tube.components.Center;
import com.amverhagen.tube.components.Clickable;
import com.amverhagen.tube.components.Clickable.Event;
import com.amverhagen.tube.components.DrawToUI;
import com.amverhagen.tube.components.Position;
import com.amverhagen.tube.components.RenderBody;
import com.amverhagen.tube.components.SpriteComponent;
import com.amverhagen.tube.components.Text;
import com.amverhagen.tube.game.TubeGame;
import com.amverhagen.tube.game.ScreenState;
import com.amverhagen.tube.game.ScreenState.State;
import com.artemis.Entity;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class TutorialPanelManager extends com.artemis.Manager {
	private Entity leftPanel;
	private Entity leftIcon;
	private Entity rightPanel;
	private Entity rightIcon;
	private TubeGame game;
	private DrawToUI dtui;
	private Clickable leftClickable;
	private Event leftEvent;
	private Text leftText;
	private Clickable rightClickable;
	private Event rightEvent;
	private Text rightText;
	private ScreenState gameState;

	public TutorialPanelManager(TubeGame game, ScreenState gameState) {
		this.game = game;
		this.gameState = gameState;
	}

	@Override
	protected void initialize() {
		createLeftPanel();
		createRightPanel();
		dtui = new DrawToUI();
		leftClickable = new Clickable(State.HINT, leftEvent, true);
		rightClickable = new Clickable(State.HINT, rightEvent, true);
	}

	private void createLeftPanel() {
		leftEvent = new Event() {
			@Override
			public void action() {
				leftPanel.edit().remove(dtui).remove(leftClickable).remove(leftText);
				leftIcon.edit().remove(dtui);
				world.getManager(PlayerManager.class).shiftPlayerLeft();
				gameState.state = State.RUNNING;
			}
		};
		leftPanel = world.createEntity();
		Position pos = new Position(0, 0);
		RenderBody rd = new RenderBody(TubeGame.GAME_WIDTH / 2, TubeGame.GAME_HEIGHT);
		leftText = new Text("    Press left side to\n rotate counter clockwise",
				new Center(TubeGame.GAME_WIDTH * .25f, TubeGame.GAME_HEIGHT * .75f),
				game.fonts.getFont(game.background, 48));
		Sprite white = new Sprite(game.assetManager.get("white.png", Texture.class));
		white.setBounds(0, 0, rd.width, rd.height);
		white.setAlpha(.3f);
		SpriteComponent sc = new SpriteComponent(white);
		leftPanel.edit().add(pos).add(rd).add(sc);

		leftIcon = world.createEntity();
		RenderBody iconBody = new RenderBody(TubeGame.GAME_WIDTH * .10f, TubeGame.GAME_WIDTH * .10f);
		Position iconPos = new Position((TubeGame.GAME_WIDTH * .25f) - iconBody.width / 2, TubeGame.GAME_HEIGHT * .4f);
		Sprite iconSprite = new Sprite(game.assetManager.get("replay.png", Texture.class));
		iconSprite.flip(true, false);
		iconSprite.setColor(game.background);
		iconSprite.setBounds(iconPos.x, iconPos.y, iconBody.width, iconBody.height);
		SpriteComponent iconSC = new SpriteComponent(iconSprite);
		leftIcon.edit().add(iconBody).add(iconPos).add(iconSC);
	}

	private void createRightPanel() {
		rightEvent = new Event() {
			@Override
			public void action() {
				rightPanel.edit().remove(dtui).remove(rightClickable).remove(rightText);
				rightIcon.edit().remove(dtui);
				world.getManager(PlayerManager.class).shiftPlayerRight();
				gameState.state = State.RUNNING;
			}
		};
		rightPanel = world.createEntity();
		Position pos = new Position(TubeGame.GAME_WIDTH / 2, 0);
		RenderBody rd = new RenderBody(TubeGame.GAME_WIDTH / 2, TubeGame.GAME_HEIGHT);
		rightText = new Text("  Press right side\nto rotate clockwise",
				new Center(TubeGame.GAME_WIDTH * .75f, TubeGame.GAME_HEIGHT * .75f),
				game.fonts.getFont(game.background, 48));
		Sprite white = new Sprite(game.assetManager.get("white.png", Texture.class));
		white.setBounds(TubeGame.GAME_WIDTH / 2f, 0, rd.width, rd.height);
		white.setAlpha(.3f);
		SpriteComponent sc = new SpriteComponent(white);
		rightPanel.edit().add(pos).add(rd).add(sc);

		rightIcon = world.createEntity();
		RenderBody iconBody = new RenderBody(TubeGame.GAME_WIDTH * .10f, TubeGame.GAME_WIDTH * .10f);
		Position iconPos = new Position((TubeGame.GAME_WIDTH * .75f) - iconBody.width / 2, TubeGame.GAME_HEIGHT * .4f);
		Sprite iconSprite = new Sprite(game.assetManager.get("replay.png", Texture.class));
		iconSprite.setColor(game.background);
		iconSprite.setBounds(iconPos.x, iconPos.y, iconBody.width, iconBody.height);
		SpriteComponent iconSC = new SpriteComponent(iconSprite);
		rightIcon.edit().add(iconBody).add(iconPos).add(iconSC);
	}

	public void showLeftPanel() {
		this.gameState.state = State.HINT;
		leftPanel.edit().add(dtui).add(leftClickable).add(leftText);
		leftIcon.edit().add(dtui);
	}

	public void showRightPanel() {
		this.gameState.state = State.HINT;
		rightPanel.edit().add(dtui).add(rightClickable).add(rightText);
		rightIcon.edit().add(dtui);
	}
}
