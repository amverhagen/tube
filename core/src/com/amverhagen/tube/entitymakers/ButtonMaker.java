package com.amverhagen.tube.entitymakers;

import com.amverhagen.tube.components.Clickable;
import com.amverhagen.tube.components.DrawToUI;
import com.amverhagen.tube.components.Position;
import com.amverhagen.tube.components.RenderBody;
import com.amverhagen.tube.components.SpriteComponent;
import com.amverhagen.tube.components.Text;
import com.amverhagen.tube.components.Clickable.Event;
import com.amverhagen.tube.systems.ScreenState.State;
import com.artemis.Entity;
import com.artemis.World;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class ButtonMaker {

	public static void createButtonEntity(World world, Sprite sprite, Vector2 pos, Vector2 body, Event event,
			State state) {
		Entity e = world.createEntity();
		DrawToUI uic = new DrawToUI();
		Position pc = new Position(pos);
		RenderBody ddc = new RenderBody(body);
		SpriteComponent sc = new SpriteComponent(new Sprite(sprite));
		sc.sprite.setBounds(pc.x, pc.y, ddc.width, ddc.height);
		Clickable cc = new Clickable(state, event);
		e.edit().add(uic).add(cc).add(sc).add(pc).add(ddc);
	}

	public static void createButtonEntityWithText(World world, Sprite sprite, Vector2 pos, Vector2 body, Event event,
			State state, Text text) {
		Entity e = world.createEntity();
		DrawToUI uic = new DrawToUI();
		Position pc = new Position(pos);
		RenderBody ddc = new RenderBody(body);
		SpriteComponent sc = new SpriteComponent(new Sprite(sprite));
		sc.sprite.setBounds(pc.x, pc.y, ddc.width, ddc.height);
		Clickable cc = new Clickable(state, event);
		text.setCenter(new Vector2((pc.x + ddc.width / 2), (pc.y + ddc.height / 2)));
		e.edit().add(uic).add(cc).add(sc).add(pc).add(ddc).add(text);
	}
}
