package com.punchline.microspace.entities.templates.scenery;

import java.util.Random;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.punchline.javalib.entities.Entity;
import com.punchline.javalib.entities.EntityWorld;
import com.punchline.javalib.entities.templates.EntityGroupTemplate;

public class StarFieldTemplate implements EntityGroupTemplate {

	private static Random rand = new Random();
	
	private static final int BIG_PLANETS = 3, BIG_PLANET_TYPES = 11;
	private static final int SMALL_PLANETS = 6, SMALL_PLANET_TYPES = 6;
	private static final int BIG_STARS = 8;
	private static final int SMALL_STARS = 150;
	
	private static final int BIG_PLANET_WIDTH = 39, BIG_PLANET_HEIGHT = 39;
	private static final int SMALL_PLANET_WIDTH = 24, SMALL_PLANET_HEIGHT = 25;
	private static final int BIG_STAR_WIDTH = 24, BIG_STAR_HEIGHT = 25;
	
	private Array<Rectangle> usedSpots = new Array<Rectangle>();
	
	@Override
	public Array<Entity> buildEntities(EntityWorld world, Object... args) {
		
		Array<Entity> group = new Array<Entity>();
		
		Rectangle bounds = world.getBounds();
		float xRange = bounds.width / 2;
		float yRange = bounds.height / 2;
		
		int furthestUsedPlanet = 0;
		
		for (int i = 0; i < BIG_PLANETS; i++) {
			
			Vector2 position = getSafePosition(xRange, yRange, BIG_PLANET_WIDTH, BIG_PLANET_HEIGHT);
			
			int type = furthestUsedPlanet + rand.nextInt(BIG_PLANET_TYPES / BIG_PLANETS) + 1;
			group.add(world.createEntity("BigPlanet", position, type));
			furthestUsedPlanet = type;
		}
		
		furthestUsedPlanet = 0;
		for (int i = 0; i < SMALL_PLANETS; i++) {
			
			Vector2 position = getSafePosition(xRange, yRange, SMALL_PLANET_WIDTH, SMALL_PLANET_HEIGHT);
			
			int type = furthestUsedPlanet + rand.nextInt(SMALL_PLANET_TYPES / SMALL_PLANETS) + 1;
			group.add(world.createEntity("SmallPlanet", position, type));
			furthestUsedPlanet = type;
		}
		
		for (int i = 0; i < BIG_STARS; i++) {
			
			Vector2 position = getSafePosition(xRange, yRange, BIG_STAR_WIDTH, BIG_STAR_HEIGHT);
			
			group.add(world.createEntity("BigStar", position));
			
		}
		
		for (int i = 0; i < SMALL_STARS; i++) {
			
			Vector2 position = getRandomPosition(xRange, yRange);
			
			group.add(world.createEntity("SmallStar", position));
			
		}
		
		return group;
		
	}
	
	private Vector2 getRandomPosition(float xRange, float yRange) {
		
		float x = rand.nextFloat() * 2 - 1; //Range [-1, 1).
		float y = rand.nextFloat() * 2 - 1; //Range from [-1, 1).
		
		x *= xRange;
		y *= yRange;
		
		return new Vector2(x, y);
		
	}
	
	private Vector2 getSafePosition(float xRange, float yRange, int boundWidth, int boundHeight) {
		
		boolean searching = true;
		
		Vector2 position = new Vector2(0, 0);
		
		while (searching) {
		
			position = getRandomPosition(xRange, yRange);
			
			Rectangle spot = new Rectangle(
					position.x - boundWidth / 2, 
					position.y - boundHeight / 2, 
					boundWidth, boundHeight);
			
			boolean safe = true;
			
			for (Rectangle usedSpot : usedSpots) {
				if (spot.overlaps(usedSpot)) safe = false;
			}
			
			if (!safe) continue;
			
			usedSpots.add(spot);
			searching = false;
		}
		
		return position;
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
