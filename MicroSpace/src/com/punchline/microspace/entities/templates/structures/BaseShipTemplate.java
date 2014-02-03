/**
 * 
 */
package com.punchline.microspace.entities.templates.structures;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.lostcode.javalib.entities.Entity;
import com.lostcode.javalib.entities.EntityWorld;
import com.lostcode.javalib.entities.components.generic.EntitySpawner;
import com.lostcode.javalib.entities.components.generic.Health;
import com.lostcode.javalib.entities.components.physical.Body;
import com.lostcode.javalib.entities.components.render.Sprite;
import com.lostcode.javalib.entities.events.EventCallback;
import com.lostcode.javalib.entities.templates.EntityTemplate;
import com.lostcode.javalib.utils.BodyEditorLoader;
import com.lostcode.javalib.utils.Convert;
import com.lostcode.javalib.utils.SoundManager;
import com.punchline.microspace.MicroGameOverInfo;
import com.punchline.microspace.entities.GenericHealth;

/**
 * @author William
 * @created Jul 26, 2013
 */
public class BaseShipTemplate implements EntityTemplate {
	
	private static final int HEALTH = 750;
	private static final float HORIZONTAL_OFFSET = Convert.pixelsToMeters(50f);
	
	private Texture shipTexture;
	private TextureRegion shipRegion;
	/**
	 * Builds the base ship template
	 */
	public BaseShipTemplate() {
		shipTexture = new Texture(Gdx.files.internal("data/Textures/structures/baseShip.png"));
		shipRegion = new TextureRegion(shipTexture, 0,0, 120, 127);
	}

	@Override
	public Entity buildEntity(Entity e, EntityWorld world, Object... args) {
		final String group = (String)args[0];
		e.init("", group, "Base"); //Builds the base ship with a team. (args[0])
		
		Vector2 position = (Vector2)args[1];
		
		
		//BODY
		BodyEditorLoader bloader = new BodyEditorLoader(Gdx.files.internal("data/Physics/physicsproj.json"));
		
		
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.StaticBody;
		bodyDef.position.set(position);
		
		FixtureDef fd = new FixtureDef();
		fd.density = 1;
		fd.friction = 0.5f;
		fd.restitution = 0f;
		
		Body b = (Body) e.addComponent(new Body(world, e, bodyDef));
		bloader.attachFixture(b.getBody(), "baseShip", fd, Convert.pixelsToMeters(120f));
		
		//SPRITE
		Sprite s = (Sprite) e.addComponent(new Sprite(shipTexture, shipRegion));
		s.setOrigin(bloader.getOrigin("baseShip", 120f));
		
		
		//HEALTH
		
		Health health = new GenericHealth(e, world, HEALTH);
		
		health.render = true;
		health.onDeath.addCallback(this, new EventCallback() {

			@Override
			public void invoke(Entity owner, Object... args) {
				EntityWorld world = (EntityWorld) args[0];
				
				SoundManager.playSound("explosion");
				
				String winningTeam = group.equals("leftTeam") ? "rightTeam" : "leftTeam";
				
				world.setGameOverInfo(new MicroGameOverInfo(winningTeam));
			}
			
		});
		
		e.addComponent(health);
		float offset = group.equals("leftTeam") ? HORIZONTAL_OFFSET : -HORIZONTAL_OFFSET;
		e.addComponent(new EntitySpawner("Mook", false, 5, e.getGroup(), b.getPosition().cpy().add(new Vector2(offset, 0))));
		
		
		return e;
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
