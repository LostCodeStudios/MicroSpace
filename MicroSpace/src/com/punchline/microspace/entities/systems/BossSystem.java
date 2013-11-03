package com.punchline.microspace.entities.systems;

import com.lostcode.javalib.entities.Entity;
import com.lostcode.javalib.entities.components.physical.Body;
import com.lostcode.javalib.entities.systems.TagSystem;

public class BossSystem extends TagSystem {
	private float elapsedShot = 0f;
	
	public BossSystem(String tag) {
		super("Boss");
		// TODO Auto-generated constructor stub
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void process(Entity e) {
		elapsedShot += deltaSeconds();
		Body b = (Body) e.getComponent(Body.class);
		if(elapsedShot >= 1f){
			//world.createEntity("Bullet", "big", b.getPosition(), new Vector2(-6,0), e, 5f);
			elapsedShot = 0f;
		}
		b.setAngularVelocity(1);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
