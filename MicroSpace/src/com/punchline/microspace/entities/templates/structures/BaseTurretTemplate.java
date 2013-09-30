/**
 * 
 */
package com.punchline.microspace.entities.templates.structures;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.punchline.javalib.entities.Entity;
import com.punchline.javalib.entities.EntityWorld;
import com.punchline.javalib.entities.components.generic.Cooldown;
import com.punchline.javalib.entities.components.generic.Health;
import com.punchline.javalib.entities.components.generic.View;
import com.punchline.javalib.entities.components.physical.Body;
import com.punchline.javalib.entities.components.render.Sprite;
import com.punchline.javalib.entities.templates.EntityTemplate;
import com.punchline.javalib.utils.BodyEditorLoader;
import com.punchline.javalib.utils.Convert;

/**
 * @author William
 * @created Jul 26, 2013
 */
public class BaseTurretTemplate implements EntityTemplate {

	private Texture turretTexture;
	private TextureRegion turretRegion;
	/**
	 * Builds the base turret
	 */
	public BaseTurretTemplate() {
		turretTexture = new Texture(Gdx.files.internal("data/Textures/structures/baseTurret.png"));
		turretRegion = new TextureRegion(turretTexture, 0,0, 24, 24);
	}

	/** {@inheritDoc}
	 * @see com.punchline.javalib.entities.templates.EntityTemplate#buildEntity(com.punchline.javalib.entities.Entity, com.punchline.javalib.entities.EntityWorld, java.lang.Object[])
	 */
	@Override
	public Entity buildEntity(final Entity e, EntityWorld world, Object... args) {
		e.init("baseTurret", (String)args[0], "Structures"); //Builds the base ship with a team. (args[0])
		
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
		
		final Body b = (Body) e.addComponent(new Body(world, e, bodyDef));
		bloader.attachFixture(b.getBody(), "baseTurret", fd, Convert.pixelsToMeters(32f));
		
		//SPRITE
		Sprite s = (Sprite) e.addComponent(new Sprite(turretTexture, turretRegion));
		s.setOrigin(bloader.getOrigin("baseTurret", 32f).cpy().add(0, -10));
		
		
		//HEALTH
		Health h = (Health) e.addComponent(new Health(e, world, 1500f));
		h.render = true;
		
		//COOLDOWNS
		Cooldown shoot = new Cooldown(e, world, 10f);
		e.addComponent(shoot);
		//SENSORS
		View sensor = new View(e,30f,1f);
		//sensor.setOffset(200);
		
		e.addComponent(sensor);
		
		return e;
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
}
