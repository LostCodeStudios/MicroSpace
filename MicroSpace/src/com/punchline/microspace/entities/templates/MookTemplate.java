package com.punchline.microspace.entities.templates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.lostcode.javalib.entities.Entity;
import com.lostcode.javalib.entities.EntityWorld;
import com.lostcode.javalib.entities.components.generic.Cooldown;
import com.lostcode.javalib.entities.components.generic.View;
import com.lostcode.javalib.entities.components.physical.Body;
import com.lostcode.javalib.entities.components.render.Sprite;
import com.lostcode.javalib.entities.templates.EntityTemplate;
import com.lostcode.javalib.utils.Convert;
import com.punchline.microspace.entities.GenericHealth;

public class MookTemplate implements EntityTemplate {

	private final static float BODY_RADIUS = 4f;
	private static final float SPEED = Convert.pixelsToMeters(50f);
	
	private Texture shipsTexture;
	private TextureRegion leftRegion;
	private TextureRegion rightRegion;
	
	public MookTemplate() {
		
		shipsTexture = new Texture(Gdx.files.internal("data/Textures/mookShips.png"));
		
		leftRegion = new TextureRegion(shipsTexture, 0, 0, 8, 8);
		rightRegion = new TextureRegion(shipsTexture, 8, 0, 8, 8);
		
	}
	
	@Override
	public Entity buildEntity(Entity e, EntityWorld world, Object... args) {
		
		String group = (String) args[0];
		Vector2 position = (Vector2) args[1];
		
		e.init("mook", group, "Mook");
		
		Sprite s = new Sprite();
		
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(position);
		
		if (group.equals("leftTeam")) {
			s = new Sprite(shipsTexture, leftRegion);
			bodyDef.linearVelocity.set(SPEED, 0);
		} else if (group.equals("rightTeam")) {
			s = new Sprite(shipsTexture, rightRegion);
			bodyDef.angle = (float)Math.PI;
			bodyDef.linearVelocity.set(-SPEED, 0);
		}
		
		CircleShape circle = new CircleShape();
		circle.setRadius(Convert.pixelsToMeters(BODY_RADIUS));
		
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = circle;
		
		Body b = new Body(world, e, bodyDef, fixtureDef);
		
		//e.addComponent(GenericCollisionEvents.damageVictim());
		//e.addComponent(new Collidable(){});
		
		//Sensors
		View sensor = new View(e,7f,.5f);
		
		//Cooldowns
		Cooldown shoot = new Cooldown(world, 10f);
		
		e.addComponent(s);
		e.addComponent(b);
		e.addComponent(sensor);
		e.addComponent(shoot);
		
		e.addComponent(new GenericHealth(e, world, 10f));
		
		return e;
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
	
}
