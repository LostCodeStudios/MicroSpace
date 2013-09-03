/**
 * 
 */
package com.punchline.microspace.entities.templates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.punchline.javalib.entities.Entity;
import com.punchline.javalib.entities.EntityWorld;
import com.punchline.javalib.entities.components.physical.Particle;
import com.punchline.javalib.entities.components.render.ParticleEffect;
import com.punchline.javalib.entities.templates.EntityTemplate;

/**
 * @author William
 * Creates an explosion
 */
public class ExplosionTemplate implements EntityTemplate {
	/**
	 * Constructs the explosion template.
	 */
	public ExplosionTemplate() {
	}

	/* (non-Javadoc)
	 * @see com.punchline.javalib.entities.EntityTemplate#buildEntity(com.punchline.javalib.entities.Entity, com.punchline.javalib.entities.EntityWorld, java.lang.Object[])
	 */
	@Override
	public Entity buildEntity(Entity e, EntityWorld world, Object... args) {
		//args
		Vector2 position = (Vector2)args[0];
		
		//location of the explosion.
		e.addComponent(new Particle(e, position, 0f, new Vector2(0,0)));
		
		ParticleEffect p = (ParticleEffect) e.addComponent(new ParticleEffect(
				Gdx.files.internal("data/Particles/explosion"),
				Gdx.files.internal("data/Particles")));
		p.start();
		
		return  e;
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
