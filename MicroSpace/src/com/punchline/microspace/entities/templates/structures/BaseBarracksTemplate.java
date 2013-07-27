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
import com.punchline.javalib.entities.EntityTemplate;
import com.punchline.javalib.entities.EntityWorld;
import com.punchline.javalib.entities.components.generic.Health;
import com.punchline.javalib.entities.components.physical.Body;
import com.punchline.javalib.entities.components.render.Renderable;
import com.punchline.javalib.entities.components.render.Sprite;
import com.punchline.javalib.utils.BodyEditorLoader;

/**
 * @author William
 * @created Jul 26, 2013
 */
public class BaseBarracksTemplate implements EntityTemplate {
	private Texture barracksTexture;
	private TextureRegion barracksRegion;
	/**
	 * Builds the base barracks
	 */
	public BaseBarracksTemplate() {
		barracksTexture = new Texture(Gdx.files.internal("data/Textures/structures/baseBarracks.png"));
		barracksRegion = new TextureRegion(barracksTexture, 0,0, 72, 56);
	}

	/** {@inheritDoc}
	 * @see com.punchline.javalib.entities.EntityTemplate#buildEntity(com.punchline.javalib.entities.Entity, com.punchline.javalib.entities.EntityWorld, java.lang.Object[])
	 */
	@Override
	public Entity buildEntity(Entity e, EntityWorld world, Object... args) {
		e.init("baseBarracks", (String)args[0], "Structures"); //Builds the base ship with a team. (args[0])
		
		Vector2 position = (Vector2)args[1];
		
		
		//BODY
		BodyEditorLoader bloader = new BodyEditorLoader(Gdx.files.internal("data/Physics/physicsproj.json"));
		
		
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(position);
		
		FixtureDef fd = new FixtureDef();
		fd.density = 1;
		fd.friction = 0.5f;
		fd.restitution = 0f;
		
		Body b = e.addComponent(new Body(world, e, bodyDef));
		bloader.attachFixture(b.getBody(), "baseBarracks", fd, 128f);
		
		//SPRITE
		Sprite s = e.addComponent(Renderable.class, new Sprite(barracksTexture, barracksRegion));
		s.setOrigin(bloader.getOrigin("baseBarracks", 128f).cpy().add(0, -72));
		
		
		//HEALTH
		e.addComponent(new Health(e, world, 1500f));
		
		
		return e;
	}
}
