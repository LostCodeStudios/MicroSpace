package com.punchline.microspace.entities.systems;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad.TouchpadStyle;
import com.punchline.javalib.entities.Entity;
import com.punchline.javalib.entities.components.physical.Body;
import com.punchline.javalib.entities.systems.InputSystem;
import com.punchline.javalib.utils.Convert;
import com.punchline.javalib.utils.Display;
import com.punchline.javalib.utils.SoundManager;

public class PlayerControlSystem extends InputSystem {

	private static final float SHOT_DELAY = 0.25f;
	private static final float PLAYER_SPEED = Convert.pixelsToMeters(140f);
	private static final float BULLET_SPEED = Convert.pixelsToMeters(250f);
	private static final float BULLET_DAMAGE = 1f;

	private boolean movingLeft = false;
	private boolean movingRight = false;
	private boolean movingUp = false;
	private boolean movingDown = false;

	private float elapsedShot = 0f;
	private Vector2 aim;

	private Stage stage;
	private Touchpad movepad;
	private Touchpad aimpad;

	public PlayerControlSystem(InputMultiplexer input) {
		super(input);
		aim = new Vector2();

		stage = new Stage(Display.getPreferredWidth(),
				Display.getPreferredHeight(), false);
		if (Gdx.app.getType() == ApplicationType.Android) {
			Skin touchpadSkin = new Skin();
			touchpadSkin.add("back", new Texture("data/Textures/padBack.png"));
			touchpadSkin
					.add("front", new Texture("data/Textures/padFront.png"));

			TouchpadStyle style = new TouchpadStyle();
			style.background = touchpadSkin.getDrawable("back");
			style.knob = touchpadSkin.getDrawable("front");

			movepad = new Touchpad(10, style);
			aimpad = new Touchpad(10, style);
			aimpad.setPosition(Display.getPreferredWidth() - aimpad.getWidth(),
					0);

			stage.addActor(movepad);
			stage.addActor(aimpad);
		}
	}

	@Override
	public boolean canProcess(Entity e) {
		return e.getType().equals("Player");
	}

	@Override
	protected void process(Entity e) {
		super.process(e);

		Body b = (Body) e.getComponent(Body.class);

		Vector2 velocity = new Vector2();

		if (Gdx.app.getType() == ApplicationType.Android) {
			stage.act(deltaSeconds());
			stage.draw();

			float x = movepad.getKnobPercentX();
			float y = movepad.getKnobPercentY();
			velocity.set(x, y);
			velocity.scl(PLAYER_SPEED);

			b.setLinearVelocity(velocity);
		} else {
			if (movingLeft) {
				velocity.x = -1f;
			} else if (movingRight) {
				velocity.x = 1f;
			}

			if (movingUp) {
				velocity.y = 1f;
			} else if (movingDown) {
				velocity.y = -1f;
			}

			velocity.nor();
			velocity.scl(PLAYER_SPEED);
		}

		b.setLinearVelocity(velocity);

		Vector2 fireL = new Vector2();

		if (Gdx.app.getType() == ApplicationType.Android) {
			fireL.x = aimpad.getKnobPercentX();
			fireL.y = aimpad.getKnobPercentY();
		} else {

			aim.x = Convert.pixelsToMeters(Gdx.input.getX()
					- Gdx.graphics.getWidth() / 2f
					+ world.getCamera().position.x);
			aim.y = Convert.pixelsToMeters(-Gdx.input.getY()
					+ Gdx.graphics.getHeight() / 2f
					+ world.getCamera().position.y);
			fireL = aim.cpy().sub(b.getPosition());

		}

		if (!velocity.equals(Vector2.Zero))
			b.setRotation((float) Math.toRadians(velocity.angle()));
		else
			b.setRotation((float) Math.toRadians(fireL.angle()));

		elapsedShot += deltaSeconds();

		if (Gdx.app.getType() == ApplicationType.Android) {
			if (!fireL.equals(Vector2.Zero)) {
				// shoot
				b.setRotation((float) Math.toRadians(fireL.angle()));
				if (elapsedShot >= SHOT_DELAY) {
					b.setRotation((float) Math.toRadians(fireL.angle()));
					fireL.angle();

					if (!fireL.equals(new Vector2())) {
						fireL.nor();
						fireL.scl(BULLET_SPEED);

						world.createEntity("Bullet", "red", b.getPosition(),
								fireL, e, BULLET_DAMAGE);
						elapsedShot = 0f;
						SoundManager.playSound("shot", 0.5f);
					}
				}
			}
		} else {

			if (Gdx.input.isTouched()) {
				b.setRotation((float) Math.toRadians(fireL.angle()));
				if (elapsedShot >= SHOT_DELAY) {
					b.setRotation((float) Math.toRadians(fireL.angle()));
					fireL.angle();

					if (!fireL.equals(new Vector2())) {
						fireL.nor();
						fireL.scl(BULLET_SPEED);

						world.createEntity("Bullet", "red", b.getPosition(),
								fireL, e, BULLET_DAMAGE);
						elapsedShot = 0f;
						SoundManager.playSound("shot", 0.5f);
					}
				}
			}
		}

	}

	@Override
	public void pause() {
		movingLeft = false;
		movingRight = false;
		movingUp = false;
		movingDown = false;

		super.pause();

		input.removeProcessor(stage);
	}

	@Override
	public void resume() {

		input.addProcessor(stage);
	}

	@Override
	public boolean keyDown(int keycode) {
		if (keycode == Keys.A) {
			movingLeft = true;
			return true;
		}

		if (keycode == Keys.W) {
			movingUp = true;
			return true;
		}

		if (keycode == Keys.D) {
			movingRight = true;
			return true;
		}

		if (keycode == Keys.S) {
			movingDown = true;
			return true;
		}

		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		if (keycode == Keys.A) {
			movingLeft = false;
			return true;
		}

		if (keycode == Keys.W) {
			movingUp = false;
			return true;
		}

		if (keycode == Keys.D) {
			movingRight = false;
			return true;
		}

		if (keycode == Keys.S) {
			movingDown = false;
			return true;
		}

		return false;
	}
}
