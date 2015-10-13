package com.amverhagen.tube.entitymakers;

import com.amverhagen.tube.components.CollidableComponent;
import com.amverhagen.tube.components.Deletable;
import com.amverhagen.tube.components.CollidableComponent.CollisionAction;
import com.amverhagen.tube.components.CollidableComponent.CollisionType;
import com.amverhagen.tube.components.DrawLineAroundBody;
import com.amverhagen.tube.components.HasParent;
import com.amverhagen.tube.components.PhysicsBody;
import com.amverhagen.tube.components.Position;
import com.amverhagen.tube.components.RenderBody;
import com.artemis.Entity;
import com.artemis.World;
import com.badlogic.gdx.math.Vector2;

public class CollidableMaker {

	public static void createTutorialCollision(World world, Entity parent, Vector2 position, Vector2 bounds) {
		Entity tCollider = world.createEntity();
		HasParent hp = new HasParent(parent);
		Position pos = new Position(position);
		PhysicsBody pb = new PhysicsBody(bounds.x, bounds.y);
		RenderBody rd = new RenderBody(bounds);
		Deletable d = new Deletable(false);
		CollidableComponent cc = new CollidableComponent(CollisionType.TUT, new CollisionAction() {
			@Override
			public void action() {
			}
		});
		DrawLineAroundBody dlab = new DrawLineAroundBody();
		tCollider.edit().add(pos).add(pos).add(rd).add(cc).add(dlab).add(d).add(pb).add(d).add(hp);
	}
}
