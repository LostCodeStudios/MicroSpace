package com.punchline.microspace.entities.systems;

import com.badlogic.gdx.math.Vector2;
import com.punchline.javalib.entities.Entity;
import com.punchline.javalib.entities.components.generic.Cooldown;
import com.punchline.javalib.entities.components.physical.Body;
import com.punchline.javalib.entities.components.physical.Sensor;
import com.punchline.javalib.entities.systems.TagSystem;

public class BaseTurretSystem extends TagSystem {
	
	private final float BULLET_DAMAGE = 1f;
	
	private Body b;
	private Sensor sensor;
	private Cooldown shoot;
	
	public BaseTurretSystem(String tag) {
		super("baseTurret");
		// TODO Auto-generated constructor stub
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
		
		if (shoot.isFinished())//targetBody.getPosition().sub(b.getPosition()).add(targetBody.getLinearVelocity().scl(targetBody.getPosition().sub(b.getPosition()).div(12f)))
			for(int i=0;i<sensor.getEntitiesInView().size;i++){//pos1 - (pos2+(v1*dis/V2))
				if(sensor.getEntitiesInView().get(i).getGroup() != e.getGroup() ){
					Body targetBody = ((Body)sensor.getEntitiesInView().get(i).getComponent(Body.class));
					Vector2 aim = targetBody.getPosition().sub(b.getPosition()).add(targetBody.getLinearVelocity().scl(targetBody.getPosition().sub(b.getPosition()).len()/40f ));//Approximates targets future position using it's current distance, velocity, and the bullets velocity
					if(e.getGroup().equals("leftTeam"))
						world.createEntity("Bullet", "red", b.getPosition(), aim.div(aim.len()).scl(40f), e, BULLET_DAMAGE);
					if(e.getGroup().equals("rightTeam"))
						world.createEntity("Bullet", "purple", b.getPosition(), aim.div(aim.len()).scl(40f), e, BULLET_DAMAGE);
					shoot.restart();
					return;
				}
			}
	}

}
