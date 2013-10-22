package com.punchline.microspace.entities.templates.scenery;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.punchline.javalib.entities.Entity;
import com.punchline.javalib.entities.EntityWorld;
import com.punchline.javalib.entities.components.physical.Particle;
import com.punchline.javalib.entities.components.physical.Transform;
import com.punchline.javalib.entities.components.render.Animation;
import com.punchline.javalib.entities.components.render.Parallax;
import com.punchline.javalib.entities.templates.EntityTemplate;

public class BigPlanetTemplate implements EntityTemplate {
	
	private static final int SHEET_COLUMNS = 6;
	private static final int SHEET_ROWS = 2;
	private static final int FRAMES_WIDTH = 236;
	private static final int FRAMES_HEIGHT = 78;
	
	private Texture sheetTexture;
	private TextureRegion framesRegion;
	
	public BigPlanetTemplate() {
		sheetTexture = new Texture(Gdx.files.internal("data/Textures/Scenery/BigPlanets.png"));
		framesRegion = new TextureRegion(sheetTexture, FRAMES_WIDTH, FRAMES_HEIGHT);
	}
	
	/**
	 * Makes a Big Planet entity.
	 * @param args args[0] = Vector2 position.
	 */
	@Override
	public Entity buildEntity(Entity e, EntityWorld world, Object... args) {
		e.init("", "Scenery", "BigPlanet");
		
		//Retrieve args
		Vector2 position = (Vector2)args[0];
		Integer type = (Integer)args[1];
		
		Animation sprite = new Animation(sheetTexture, framesRegion, SHEET_COLUMNS, SHEET_ROWS, 1f) {
			public void draw(SpriteBatch spriteBatch, float deltaSeconds) {
				super.draw(spriteBatch, 0); //Never animate
			}
		};
		
		sprite.setStateTime(type); //Set type
		
		e.addComponent(sprite);
		
		Transform t = new Particle(e, position, 0f);
		e.addComponent(t);
		e.addComponent(new Parallax(world.getCamera(), 1/4f));
		
		return e;
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
	
}
