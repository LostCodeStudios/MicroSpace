package com.punchline.microspace.entities.templates.scenery;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.punchline.javalib.entities.Entity;
import com.punchline.javalib.entities.EntityWorld;
import com.punchline.javalib.entities.components.physical.Particle;
import com.punchline.javalib.entities.components.physical.Transform;
import com.punchline.javalib.entities.components.render.Animation;
import com.punchline.javalib.entities.components.render.Parallax;
import com.punchline.javalib.entities.templates.EntityTemplate;

public class BigStarTemplate implements EntityTemplate {

	private static Random rand = new Random();

	private static final int FRAMES = 4;
	private static final int ANIMATIONS = 2;
	private static final int FRAMES_PER_ANIMATION = 2;
	private static final int FRAMES_WIDTH = 96;
	private static final int FRAMES_HEIGHT = 25;
	
	private Texture sheetTexture;
	
	public BigStarTemplate() {
		sheetTexture = new Texture(Gdx.files.internal("data/Textures/Scenery/BigStars.png"));
	}
	
	@Override
	public Entity buildEntity(Entity e, EntityWorld world, Object... args) {
		e.init("", "Scenery", "BigStar");
		
		Vector2 position = (Vector2)args[0];
		
		int type = rand.nextInt(ANIMATIONS);
		
		int frameWidth = FRAMES_WIDTH / FRAMES;
		int frameHeight = FRAMES_HEIGHT;
		
		TextureRegion region = new TextureRegion(sheetTexture, type * frameWidth * 2, 0, frameWidth * FRAMES_PER_ANIMATION, frameHeight);
		
		float frameTime = rand.nextFloat() / 2 + 0.7f;
		
		Animation sprite = new Animation(sheetTexture, region, FRAMES_PER_ANIMATION, 1, frameTime);
		sprite.setStateTime(rand.nextFloat());
		e.addComponent(sprite);
		
		Transform t = new Particle(e, position, 0f);
		e.addComponent(t);
		
		e.addComponent(new Parallax(world.getCamera(), 1/8f));
		
		return e;
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
	
}