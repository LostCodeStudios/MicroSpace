package com.punchline.microspace.entities.systems;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.lostcode.javalib.entities.Entity;
import com.lostcode.javalib.entities.systems.EntitySystem;
import com.lostcode.javalib.utils.Convert;

public class AsteroidSpawnSystem extends EntitySystem {

	private static final float SPAWN_CHANCE = 0.03f;
	
	private static final int SMALL_CHANCE = 40;
	private static final int MEDIUM_CHANCE = 75;
	private static final int LARGE_CHANCE = 100;
	
	private static final int PADDING = 8;
	
	private static Random rand = new Random();
	
	@Override
	public void dispose() { }

	@Override
	public boolean canProcess(Entity e) {
		return false; //Doesn't process Entities
	}
	
	@Override
	public void processEntities() {
		super.processEntities();
		
		if (rand.nextFloat() <= SPAWN_CHANCE) {
			//Spawn an asteroid
			Integer size;
			
			int chance = rand.nextInt(100);
			
			if (chance < SMALL_CHANCE) {
				size = 0;
			} else if (chance < MEDIUM_CHANCE) {
				size = 1;
			} else if (chance < LARGE_CHANCE) {
				size = 2;
			} else {
				size = 0; //This shouldn't happen.
			}
			
			Vector2 pos = new Vector2(0, 0);
			Vector2 velocity = new Vector2(0, 0);
			
			int side = rand.nextInt(2);
			
			int speed = rand.nextInt(40);
			
			pos.x = rand.nextInt(Gdx.graphics.getWidth() / 2) - Gdx.graphics.getWidth() / 4;
			
			
			if (side == 0) {
				//Come from the top
				pos.y = Gdx.graphics.getHeight() / 2 + PADDING;
				
				velocity.y = -speed;
			} else if (side == 1) {
				//Come from the bottom
				pos.y = -Gdx.graphics.getHeight() / 2 - PADDING;
				
				velocity.y = speed;
			}
			
			world.createEntity("Asteroid", size, Convert.pixelsToMeters(pos), Convert.pixelsToMeters(velocity));
		}
	}

	@Override
	protected void process(Entity e) { }

	
	
}
