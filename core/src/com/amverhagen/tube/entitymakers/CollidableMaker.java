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
import com.amverhagen.tube.tubes.Tube;
import com.artemis.Entity;
import com.artemis.World;
import com.badlogic.gdx.math.Vector2;

public class CollidableMaker {
	private static Vector2 tutorialBounds = new Vector2(Tube.TUBE_WIDTH / 10, Tube.TUBE_WIDTH / 10);

	public static void createTutorialCollision(World world, CollisionAction action, Entity parent, Vector2 center,
			boolean vertical) {
		Entity tCollider = world.createEntity();
		PhysicsBody pb;
		HasParent hp = new HasParent(parent);
		if (vertical) {
			pb = new PhysicsBody(tutorialBounds.x, Tube.TUBE_WIDTH);
		} else {
			pb = new PhysicsBody(Tube.TUBE_WIDTH, tutorialBounds.y);
		}
		RenderBody rd = new RenderBody(tutorialBounds);
		Position pos = new Position(center.x - (pb.width / 2f), center.y - (pb.height / 2f));
		Deletable d = new Deletable(false);
		CollidableComponent cc = new CollidableComponent(CollisionType.TUT, action);
		DrawLineAroundBody dlab = new DrawLineAroundBody();
		tCollider.edit().add(pos).add(pos).add(rd).add(cc).add(dlab).add(d).add(pb).add(d).add(hp);
	}
}
