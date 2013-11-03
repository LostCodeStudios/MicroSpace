package com.punchline.microspace.entities.templates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.lostcode.javalib.entities.Entity;
import com.lostcode.javalib.entities.EntityWorld;
import com.lostcode.javalib.entities.GenericCollisionEvents;
import com.lostcode.javalib.entities.components.generic.Health;
import com.lostcode.javalib.entities.components.generic.View;
import com.lostcode.javalib.entities.components.physical.Body;
import com.lostcode.javalib.entities.components.physical.Transform;
import com.lostcode.javalib.entities.components.render.Sprite;
import com.lostcode.javalib.entities.events.EventCallback;
import com.lostcode.javalib.entities.templates.EntityCreationArgs;
import com.lostcode.javalib.entities.templates.EntityTemplate;
import com.lostcode.javalib.utils.Convert;
import com.lostcode.javalib.utils.SoundManager;
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
		h.setRegenerationRate(world, 1);
		
		View sensor = new View(e, 15f, 0.3f);
		
		e.addComponent(sensor);
		
		h.onDeath.addCallback(this, new EventCallback() {
			@Override
			public void invoke(Entity owner, Object... args) {
				EntityWorld world = (EntityWorld) args[0];
				
				SoundManager.playSound("explosion");
				
				world.createEntity("Explosion", ((Transform) owner.getComponent(Transform.class)).getPosition());
				
				world.create(new EntityCreationArgs("Player", false, owner.getGroup())); //respawn
			}
		});
		
		e.addComponent(h);
		e.addComponent(GenericCollisionEvents.damageVictim());
		
		return e;
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
