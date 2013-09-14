package com.punchline.microspace.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.punchline.javalib.entities.EntityWorld;
import com.punchline.javalib.entities.systems.generic.TrackingCameraSystem;
import com.punchline.javalib.entities.systems.render.HealthRenderSystem;
import com.punchline.javalib.utils.Convert;
import com.punchline.microspace.entities.systems.AsteroidSpawnSystem;
import com.punchline.microspace.entities.systems.BaseTurretSystem;
import com.punchline.microspace.entities.systems.BossSystem;
import com.punchline.microspace.entities.systems.PlayerControlSystem;
import com.punchline.microspace.entities.templates.AsteroidTemplate;
import com.punchline.microspace.entities.templates.BossTemplate;
import com.punchline.microspace.entities.templates.ExplosionTemplate;
import com.punchline.microspace.entities.templates.MookTemplate;
import com.punchline.microspace.entities.templates.PlayerTemplate;
import com.punchline.microspace.entities.templates.projectiles.BulletTemplate;
import com.punchline.microspace.entities.templates.scenery.BigPlanetTemplate;
import com.punchline.microspace.entities.templates.scenery.BigStarTemplate;
import com.punchline.microspace.entities.templates.scenery.SmallPlanetTemplate;
import com.punchline.microspace.entities.templates.scenery.SmallStarTemplate;
import com.punchline.microspace.entities.templates.scenery.StarFieldTemplate;
import com.punchline.microspace.entities.templates.structures.BaseBarracksTemplate;
import com.punchline.microspace.entities.templates.structures.BaseMineTemplate;
import com.punchline.microspace.entities.templates.structures.BaseShipTemplate;
import com.punchline.microspace.entities.templates.structures.BaseTemplate;
import com.punchline.microspace.entities.templates.structures.BaseTurretTemplate;

public class SpaceWorld extends EntityWorld {
	
	public SpaceWorld(InputMultiplexer input, Camera camera) {
		super(input, camera, new Vector2(0, 0));
		
		debugView.enabled = true;
		debugView.visible = true; //TODO: Remember to disable this...
	}

	@Override
	public void process() {
		super.process();
	}

	@Override
	public Rectangle getBounds() {
		return Convert.pixelsToMeters(
				new Rectangle(
					-Gdx.graphics.getWidth() * 2, 
					-Gdx.graphics.getHeight() / 2, 
					Gdx.graphics.getWidth() * 4, 
					Gdx.graphics.getHeight()));
	}

	@Override
	protected void positionCamera() { }
	
	@Override
	protected void buildSystems() {		
		super.buildSystems();
		
		//Input
		systems.addSystem(new PlayerControlSystem(input));
		
		//Render
		TrackingCameraSystem cameraSystem = new TrackingCameraSystem("Player", camera, getBounds());
		systems.addSystem(cameraSystem);
		
		systems.addSystem(new HealthRenderSystem(camera, 
				Gdx.files.internal("data/Textures/healthbarback.png"),
				Gdx.files.internal("data/Textures/healthbarfront.png")));
		
		//Spawning
		systems.addSystem(new AsteroidSpawnSystem());
		
		//AI
		systems.addSystem(new BossSystem("Boss"));
		systems.addSystem(new BaseTurretSystem("baseTurret"));
	}

	@Override
	protected void buildTemplates() {
		super.buildTemplates();
		
		//Projectiles
		addTemplate("Bullet", new BulletTemplate());
		
		//Scenery
		addTemplate("BigPlanet", new BigPlanetTemplate());
		addTemplate("SmallPlanet", new SmallPlanetTemplate());
		addTemplate("BigStar", new BigStarTemplate());
		addTemplate("SmallStar", new SmallStarTemplate());
		addGroupTemplate("StarField", new StarFieldTemplate());
		
		//Effects
		addTemplate("Explosion", new ExplosionTemplate());
		
		//STRUCTURES
		addTemplate("BaseShip", new BaseShipTemplate());
		addTemplate("BaseMine", new BaseMineTemplate());
		addTemplate("BaseTurret", new BaseTurretTemplate());
		addTemplate("BaseBarracks", new BaseBarracksTemplate());
		addGroupTemplate("Base", new BaseTemplate());
		
		//Entities
		addTemplate("Player", new PlayerTemplate());
		addTemplate("Asteroid", new AsteroidTemplate());
		addTemplate("Mook", new MookTemplate());
		addTemplate("Boss", new BossTemplate());
	}
	
	@Override
	protected void buildEntities() {
		super.buildEntities();
		
		createEntityGroup("StarField");	
		
		createEntity("TileMap", "data/untitled.tmx", null);
		
		//BUILD BASES
		createEntityGroup("Base", "leftTeam");
		createEntityGroup("Base", "rightTeam");
		
		createEntity("Player", "leftTeam");
		createEntity("Boss", new Vector2(3,1));
		
	}

	@Override
	protected void buildSpriteSheet() {
		// TODO Auto-generated method stub
		
	}

	
}
