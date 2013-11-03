package com.punchline.microspace.entities;

import com.lostcode.javalib.entities.Entity;
import com.lostcode.javalib.entities.EntityWorld;
import com.lostcode.javalib.entities.components.generic.Health;
import com.lostcode.javalib.entities.components.physical.Transform;
import com.lostcode.javalib.entities.events.EventCallback;
import com.lostcode.javalib.utils.SoundManager;

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
