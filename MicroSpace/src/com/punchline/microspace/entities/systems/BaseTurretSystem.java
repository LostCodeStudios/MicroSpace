package com.punchline.microspace.entities.systems;

import com.badlogic.gdx.math.Vector2;
import com.punchline.javalib.entities.Entity;
import com.punchline.javalib.entities.components.physical.Body;
import com.punchline.javalib.entities.components.physical.Sensor;
import com.punchline.javalib.entities.systems.TagSystem;

public class BaseTurretSystem extends TagSystem {

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
		Body b = (Body) e.getComponent(Body.class);
		Sensor sensor = e.getComponent(Sensor.class);
		for(int i=0;i<sensor.getEntitiesInView().size;i++){
			if(sensor.getEntitiesInView().get(i).getGroup() != e.getGroup() )
				world.createEntity("Bullet", "big", b.getPosition(), new Vector2(12,0), e, 5f);
		}
	}

}
