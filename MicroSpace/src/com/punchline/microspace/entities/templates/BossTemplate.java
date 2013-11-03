/**
 * 
 */
package com.punchline.microspace.entities.templates;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.lostcode.javalib.entities.Entity;
import com.lostcode.javalib.entities.EntityWorld;
import com.lostcode.javalib.entities.components.physical.Body;
import com.lostcode.javalib.entities.templates.EntityTemplate;
import com.punchline.microspace.entities.GenericHealth;

/**
 * @author RS504457
 *
 */
public class BossTemplate implements EntityTemplate {

	/* (non-Javadoc)
	 * @see com.badlogic.gdx.utils.Disposable#dispose()
	 */
	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.punchline.javalib.entities.templates.EntityTemplate#buildEntity(com.punchline.javalib.entities.Entity, com.punchline.javalib.entities.EntityWorld, java.lang.Object[])
	 */
	@Override
	public Entity buildEntity(Entity e, EntityWorld world, Object... args) {	
		e.init("Boss", "Boss", "Boss");
		
		Body b;
		
		BodyDef bd = new BodyDef();
		bd.position.set((Vector2)args[0]);
		bd.type = BodyType.DynamicBody;
		//bd.linearVelocity.set(new Vector2(1,0));
		
		FixtureDef fd = new FixtureDef();
		fd.shape = new CircleShape();
		fd.shape.setRadius(8);
		fd.density = 1;
		fd.friction = 1;
		
		
		
		b = new Body(world, e, bd, fd);
		b.setAngularVelocity(900);
		
		/*if(true)
			world.createEntity("Bullet", "big", b.getPosition(), new Vector2(-3,0), e, 5f);*/
		
		
		e.addComponent(b);
		
		GenericHealth h = new GenericHealth(e, world, 400);
		
		
		e.addComponent(h);
		
		return e;
	}

}
