package com.punchline.microspace.entities.templates;

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
import com.punchline.javalib.entities.components.generic.Health.HealthEventCallback;
import com.punchline.javalib.entities.components.generic.View;
import com.punchline.javalib.entities.components.physical.Body;
import com.punchline.javalib.entities.components.physical.Transform;
import com.punchline.javalib.entities.components.render.Sprite;
import com.punchline.javalib.entities.templates.EntityCreationArgs;
import com.punchline.javalib.entities.templates.EntityTemplate;
import com.punchline.javalib.utils.Convert;
import com.punchline.javalib.utils.SoundManager;
import com.punchline.microspace.entities.GenericHealth;

public class PlayerTemplate implements EntityTemplate {

	private static final float BODY_RADIUS = 8f;
	
	
	private Texture shipsTexture;
	private TextureRegion leftRegion;
	private TextureRegion rightRegion;
	
	public PlayerTemplate() {
		shipsTexture = new Texture(Gdx.files.internal("data/Textures/playerships.png"));
		
		leftRegion = new TextureRegion(shipsTexture, 0, 0, 16, 16);
		rightRegion = new TextureRegion(shipsTexture, 16, 0, 16, 16);
	}
	
	@Override
	public Entity buildEntity(Entity e, EntityWorld world, Object... args) {
		String group = (String)args[0];
		
		String tag = group.equals("leftTeam") ? "Player" : "";
		
		e.init(tag, group, "Player");
		
		Vector2 pos = new Vector2();
		Sprite s = new Sprite();
		
		BodyDef bd = new BodyDef();
		bd.type = BodyType.DynamicBody;
		
		CircleShape circle = new CircleShape();
		circle.setRadius(Convert.pixelsToMeters(BODY_RADIUS));
		
		FixtureDef fd = new FixtureDef();
		fd.shape = circle;
		
		if (group.equals("leftTeam")) {
			pos.x = -((world.getBounds().width/2f)-Convert.pixelsToMeters(200));
			s = new Sprite(shipsTexture, leftRegion);
		} else if (group.equals("rightTeam")) {
			pos.x = (world.getBounds().width/2f)-Convert.pixelsToMeters(200);
			s = new Sprite(shipsTexture, rightRegion);
			bd.angle = (float)Math.PI;
		}
		
		bd.position.set(pos);
		
		Body b  = new Body(world, e, bd, fd);
		
		e.addComponent(s);
		e.addComponent(b);
		
		
		final Health h = new GenericHealth(e, world, 10f);
		h.render = true;
		
		View sensor = new View(e, 15f, 0.3f);
		
		e.addComponent(sensor);
		
		h.onDeath = new HealthEventCallback() {
			@Override
			public void invoke(Entity owner, EntityWorld world) {
				SoundManager.playSound("explosion");
				
				world.safelyCreateEntity("Explosion", ((Transform) owner.getComponent(Transform.class)).getPosition());
				
				world.safeCreate(new EntityCreationArgs("Player", false, owner.getGroup())); //respawn
			}
		};
		
		e.addComponent(h);
		e.addComponent(GenericCollisionEvents.damageVictim());
		
		return e;
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
