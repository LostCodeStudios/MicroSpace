package com.punchline.microspace.entities.templates.structures;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;
import com.punchline.javalib.entities.Entity;
import com.punchline.javalib.entities.EntityGroupTemplate;
import com.punchline.javalib.entities.EntityWorld;
import com.punchline.javalib.entities.components.physical.Body;

/**
 * Builds a base!
 * @author William
 * @created Jul 27, 2013
 */
public class BaseTemplate implements EntityGroupTemplate {

	public BaseTemplate() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public ArrayList<Entity> buildEntities(EntityWorld world, Object... args) {
		ArrayList<Entity> baseStructures = new ArrayList<Entity>();
		
		//ARGS
		String team = (String)args[0];
		int multiplier =0;
		if(team.equalsIgnoreCase("leftTeam"))
			multiplier = -1;
		else
			multiplier = 1;
		
		float ww = world.getBounds().width/2f;
		float wh = world.getBounds().width/2f;
		//BUILD BASE
		
		baseStructures.add(world.createEntity("BaseShip", team, new Vector2(ww-100, 0).scl(multiplier)));
		
		//Set up towers.
			//T1
			baseStructures.add(world.createEntity("BaseTurret", team, new Vector2(ww - 230, -30).scl(multiplier))); 
			baseStructures.add(world.createEntity("BaseTurret", team, new Vector2(ww - 230, 30).scl(multiplier))); 
			baseStructures.add(world.createEntity("BaseTurret", team, new Vector2(ww - 290, -60).scl(multiplier))); 
			baseStructures.add(world.createEntity("BaseTurret", team, new Vector2(ww - 290, 60).scl(multiplier))); 
			baseStructures.add(world.createEntity("BaseTurret", team, new Vector2(ww - 290, -120).scl(multiplier))); 
			baseStructures.add(world.createEntity("BaseTurret", team, new Vector2(ww - 290, 120).scl(multiplier)));
		
			//T2
			baseStructures.add(world.createEntity("BaseTurret", team, new Vector2(ww - 820, -45).scl(multiplier))); 
			baseStructures.add(world.createEntity("BaseTurret", team, new Vector2(ww - 820, 45).scl(multiplier)));
			
			//T3
			baseStructures.add(world.createEntity("BaseTurret", team, new Vector2(ww - 1220, -45).scl(multiplier))); 
			baseStructures.add(world.createEntity("BaseTurret", team, new Vector2(ww - 1220, 45).scl(multiplier)));
		
		//set up barracks
		baseStructures.add(world.createEntity("BaseBarracks", team, new Vector2(ww - 250, -90).scl(multiplier))); 
		baseStructures.add(world.createEntity("BaseBarracks", team, new Vector2(ww - 250, 90).scl(multiplier))); 
		
		//set up mining rigs
		baseStructures.add(world.createEntity("BaseMine", team, new Vector2(ww - 80, 110).scl(multiplier))); 
		baseStructures.add(world.createEntity("BaseMine", team, new Vector2(ww - 110, -110).scl(multiplier))); 
		
		//rotate entities for respective sides
		for(Entity e : baseStructures){
			Body b = e.getComponent();
			b.setRotation(b.getRotation() + (float)(Math.PI/2f + multiplier*Math.PI/2f));
		}
		
		
		
		return baseStructures;
	}

}
