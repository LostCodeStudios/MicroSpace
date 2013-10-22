package com.punchline.microspace.entities.systems;

import com.badlogic.gdx.math.Vector2;
import com.punchline.javalib.entities.Entity;
import com.punchline.javalib.entities.components.generic.Cooldown;
import com.punchline.javalib.entities.components.physical.Body;
import com.punchline.javalib.entities.components.physical.Sensor;
import com.punchline.javalib.entities.processes.ExpirationProcess;
import com.punchline.javalib.entities.systems.TagSystem;
import com.punchline.javalib.utils.Convert;

public class MookSystem extends TagSystem {

	private final float BULLET_DAMAGE = 1f;
	private final float BULLET_VELOCITY = 10f;
	private final float SPEED = Convert.pixelsToMeters(50f);
	
	private Body b;
	private Sensor sensor;
	private Cooldown shoot;
	private String color;
	
	public MookSystem(String tag) {
		super("mook");
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void process(Entity e) {
		b = (Body) e.getComponent(Body.class);
		sensor = e.getComponent(Sensor.class);
		shoot = (Cooldown)e.getComponent(Cooldown.class);
		shoot.drain(1f);
		if(e.getGroup().equals("leftTeam"))
			color = "red";
		if(e.getGroup().equals("rightTeam"))
			color = "purple";
		if (shoot.isFinished())
			for(int i=0;i<sensor.getEntitiesInView().size;i++){
				if(sensor.getEntitiesInView().get(i).getGroup() != e.getGroup() ){
					Body targetBody = ((Body)sensor.getEntitiesInView().get(i).getComponent(Body.class));
					if( targetBody == null)
						return;
					Vector2 aim = targetBody.getPosition().sub(b.getPosition()).add(targetBody.getLinearVelocity().scl(targetBody.getPosition().sub(b.getPosition()).len()/BULLET_VELOCITY ));//Approximates targets future position using it's current distance, velocity, and the bullets velocity		
					world.getProcessManager().attach(new ExpirationProcess( 2f, world.createEntity("Bullet", color, b.getPosition(), aim.div(aim.len()).scl(BULLET_VELOCITY), e, BULLET_DAMAGE)));
					shoot.restart();
					b.setLinearVelocity(new Vector2(0,0));
					return;
				}
				else
				{
					if(e.getGroup().equals("rightTeam"))
						b.setLinearVelocity(new Vector2(-SPEED,0));
					if(e.getGroup().equals("leftTeam"))
						b.setLinearVelocity(new Vector2(SPEED,0));
				}
			}

	}

}
