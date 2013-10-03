/**
 * 
 */
package com.punchline.microspace.entities.templates.projectiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.punchline.javalib.entities.Entity;
import com.punchline.javalib.entities.EntityWorld;
import com.punchline.javalib.entities.components.ComponentManager;
import com.punchline.javalib.entities.components.generic.Bullet;
import com.punchline.javalib.entities.components.generic.Health;
import com.punchline.javalib.entities.components.physical.Body;
import com.punchline.javalib.entities.components.physical.Collidable;
import com.punchline.javalib.entities.components.physical.Particle;
import com.punchline.javalib.entities.components.render.Sprite;
import com.punchline.javalib.entities.templates.EntityTemplate;
import com.punchline.javalib.utils.LogManager;

/**
 * @author William
 * @created Jul 23, 2013
 */
public class BulletTemplate implements EntityTemplate {
	private Texture bulletTex;
	private TextureRegion[] bulletRect;
	
	/**
	 * Constructs the bullet template for use with bullets.
	 */
	public BulletTemplate(){
		bulletTex = new Texture(Gdx.files.internal("data/Textures/bulletSprites.png"));
		bulletRect = new TextureRegion[]{
			new TextureRegion(bulletTex, 0, -1, 2, 1), //Blue
			new TextureRegion(bulletTex, 0, -2, 2, 1), //Red
			new TextureRegion(bulletTex, 2, -2, 2, 1), //Yellow
			new TextureRegion(bulletTex, 2, -1, 2, 1), //Purple
			new TextureRegion(bulletTex, 0, 2, 4, 2)  //Big
		};
	}
	
	
	/** {@inheritDoc}
	 * @see com.punchline.javalib.entities.templates.EntityTemplate#buildEntity(com.punchline.javalib.entities.Entity, com.punchline.javalib.entities.EntityWorld, java.lang.Object[])
	 */
	@SuppressWarnings("unused")
	@Override
	public Entity buildEntity(Entity e, EntityWorld world, Object... args) {
		e.init("", "Bullets", "Projectiles");
		
		//ARGS
		String type = (String)args[0];
		TextureRegion region = bulletRect[0]; //blue
		if(type.equalsIgnoreCase("red"))
			region = bulletRect[1];
		else if(type.equalsIgnoreCase("yellow"))
			region = bulletRect[2];
		else if(type.equalsIgnoreCase("purple"))
			region = bulletRect[3];
		else if(type.equalsIgnoreCase("big"))
			region = bulletRect[4];
		Vector2 position = (Vector2)args[1];
		
		Vector2 linearVelocity = (Vector2)args[2];
		float rotation = (float) Math.toRadians(linearVelocity.angle());
		
		Entity firer = (Entity)args[3];
		float damage = (Float)args[4];
		
		Vector2 firePos = ((Body)firer.getComponent(Body.class)).getPosition();
		//double range = (Double)args[5];MAKE BULLET SYSTEM HANDLE RANGE 'N SHIT
	
		

		//Particle
		Particle p = (Particle)e.addComponent(new Particle(e, position, rotation, new Vector2(region.getRegionWidth()/2f, region.getRegionHeight()/2f)));
		
		if(linearVelocity.len() <= 0){
			LogManager.error("BulletCreation", "VELOCITY IS LESS THAN 0");
			linearVelocity = new Vector2(1,1);
		}
		
		p.setLinearVelocity(linearVelocity);
		
		

		//Bullet
		Bullet b = (Bullet)e.addComponent(new Bullet(firer, damage));
		
		//OnCollide
		Collidable col = (Collidable)e.addComponent(
			new Collidable(){
				@Override
				public void onAdd(ComponentManager container) {
				}
	
				@Override
				public void onRemove(ComponentManager container) {	
				}
	
				@Override
				public void onCollide(Entity container, Entity victim) {					
					Bullet b = (Bullet) container.getComponent(Bullet.class);
					
					//IF THE VICTIM HAS HEALTH! RAWR
					if(victim == null)
						return;
					if(victim.hasComponent(Health.class))
					{
						Health h = (Health) victim.getComponent(Health.class);
						h.drain(b.getDamage());
					}
					
					container.delete();
					
				}

				@Override
				public float continueCollision(Entity container, Entity victim) {
					Bullet b = (Bullet) container.getComponent(Bullet.class);
					
					if (victim == null) {
						container.delete();
						return 0;
					}
					
					//If the bullet hits the firer, continue firing the bullet.
					if(victim.getGroup().equals(b.getFirer().getGroup())) 
						return 1;
					else 
						return 0;
				}
			});
		
		//Sprite TODO: Consider sprite pooling.
		e.addComponent(new Sprite(bulletTex, bulletRect[0]));
		
		return e;
	}


	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
