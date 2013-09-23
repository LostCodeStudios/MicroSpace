package com.punchline.microspace.entities.systems;

import com.badlogic.gdx.math.Vector2;
import com.punchline.javalib.entities.Entity;
import com.punchline.javalib.entities.components.physical.Body;
import com.punchline.javalib.entities.components.physical.Sensor;
import com.punchline.javalib.entities.systems.TagSystem;

public class BaseTurretSystem extends TagSystem {
	
	private float SHOT_DELAY = 3f;
	private float BULLET_DAMAGE = 3f;
	private float elapsedShot = 0f;
	
	private Body b;
	Sensor sensor;
	
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
		if (elapsedShot >= SHOT_DELAY)
			for(int i=0;i<sensor.getEntitiesInView().size;i++){
				if(sensor.getEntitiesInView().get(i).getGroup() != e.getGroup() ){
					Body targetBody = ((Body)sensor.getEntitiesInView().get(i).getComponent(Body.class));
					Vector2 aim = targetBody.getPosition().add(targetBody.getLinearVelocity()).sub(b.getPosition());
					world.createEntity("Bullet", "big", b.getPosition(), aim.div(aim.len()).scl(12f), e, BULLET_DAMAGE);
					elapsedShot = 0f;
					break;
				}
			}
		elapsedShot += deltaSeconds();
	}

}
