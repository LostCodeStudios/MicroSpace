package com.punchline.microspace.entities;

import com.punchline.javalib.entities.Entity;
import com.punchline.javalib.entities.EntityWorld;
import com.punchline.javalib.entities.components.generic.Health;
import com.punchline.javalib.entities.components.physical.Transform;
import com.punchline.javalib.entities.events.EventCallback;
import com.punchline.javalib.utils.SoundManager;

public class GenericHealth extends Health {

	public GenericHealth(Entity owner, EntityWorld world, float max) {
		super(owner, world, max);
		
		onDeath.addCallback(this, new EventCallback() {

			@Override
			public void invoke(Entity owner, Object... args) {
				EntityWorld world = (EntityWorld) args[0];
				
				SoundManager.playSound("explosion");
				
				world.createEntity("Explosion", ((Transform) owner.getComponent(Transform.class)).getPosition());
			}
			
		});
		
		onDamage.addCallback(this, new EventCallback() {
			
			@Override
			public void invoke(Entity owner, Object... args) {
				SoundManager.playSound("hit");
			}
			
		});
	}

	
	
}
