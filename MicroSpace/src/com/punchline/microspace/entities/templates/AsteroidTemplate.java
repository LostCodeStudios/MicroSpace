package com.punchline.microspace.entities.templates;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.punchline.javalib.entities.Entity;
import com.punchline.javalib.entities.EntityWorld;
import com.punchline.javalib.entities.GenericCollisionEvents;
import com.punchline.javalib.entities.components.generic.Health;
import com.punchline.javalib.entities.components.physical.Body;
import com.punchline.javalib.entities.components.render.Sprite;
import com.punchline.javalib.entities.templates.EntityTemplate;
import com.punchline.javalib.utils.Convert;
import com.punchline.microspace.entities.GenericHealth;

public class AsteroidTemplate implements EntityTemplate {
	
	private static final float SMALL_RADIUS = 2f;
	private static final float MEDIUM_RADIUS = 4f;
	private static final float BIG_RADIUS = 8f;
	
	private static Random rand = new Random();
	
	private Texture asteroidsTexture;
	private TextureRegion[] smallAsteroidRegions;
	private TextureRegion[] mediumAsteroidRegions;
	private TextureRegion[] bigAsteroidRegions;
	
	public AsteroidTemplate() {
		
		asteroidsTexture = new Texture(Gdx.files.internal("data/Textures/asteroids.png"));
		
		smallAsteroidRegions = new TextureRegion[] {
				new TextureRegion(asteroidsTexture, 32, 0, 8, 8),
				new TextureRegion(asteroidsTexture, 40, 0, 8, 8),
				new TextureRegion(asteroidsTexture, 48, 0, 8, 8),
				new TextureRegion(asteroidsTexture, 56, 0, 8, 8)
		};
		
		mediumAsteroidRegions = new TextureRegion[] {
				new TextureRegion(asteroidsTexture, 0, 0, 8, 8),
				new TextureRegion(asteroidsTexture, 8, 0, 8, 8),
				new TextureRegion(asteroidsTexture, 16, 0, 8, 8),
				new TextureRegion(asteroidsTexture, 24, 0, 8, 8)
		};
		
		bigAsteroidRegions = new TextureRegion[] {
				new TextureRegion(asteroidsTexture, 0, 8, 16, 16),
				new TextureRegion(asteroidsTexture, 16, 8, 16, 16),
				new TextureRegion(asteroidsTexture, 32, 8, 16, 16),
				new TextureRegion(asteroidsTexture, 48, 8, 16, 16)
		};
		
	}
	
	@Override
	public Entity buildEntity(Entity e, EntityWorld world, Object... args) {
		Integer size = (Integer)args[0];
		Vector2 position = (Vector2)args[1];
		Vector2 velocity = (Vector2)args[2];
		
		e.init("", "Asteroids", "Asteroid");
		
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(position);
		bodyDef.linearVelocity.set(velocity);
		bodyDef.angularVelocity = (rand.nextFloat() - 0.5f) * 10;
		
		FixtureDef fixtureDef = new FixtureDef();
		CircleShape shape = new CircleShape();
		
		TextureRegion region;
		
		float maxHealth;
		
		switch (size) {
		
		default:
		case 0: //Small
			
			shape.setRadius(Convert.pixelsToMeters(SMALL_RADIUS));
			region = smallAsteroidRegions[rand.nextInt(smallAsteroidRegions.length)];
			maxHealth = 1f;
			
			break;
			
		case 1: //Medium
			
			shape.setRadius(Convert.pixelsToMeters(MEDIUM_RADIUS));
			region = mediumAsteroidRegions[rand.nextInt(mediumAsteroidRegions.length)];
			maxHealth = 3f;
			
			break;
			
		case 2: //Large
			
			shape.setRadius(Convert.pixelsToMeters(BIG_RADIUS));
			region = bigAsteroidRegions[rand.nextInt(bigAsteroidRegions.length)];
			maxHealth = 5f;
			
			break;
		
		}
		
		fixtureDef.shape = shape;
		
		Body body = new Body(world, e, bodyDef, fixtureDef);
		e.addComponent(body);
		
		Sprite sprite = new Sprite(asteroidsTexture, region);
		e.addComponent(sprite);
		
		Health health = new GenericHealth(e, world, maxHealth);
		e.addComponent(health);
		
		e.addComponent(GenericCollisionEvents.damageVictim());
		
		return e;
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
