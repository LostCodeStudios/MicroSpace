package com.punchline.microspace.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.punchline.javalib.Game;
import com.punchline.javalib.states.screens.MenuScreen;
import com.punchline.javalib.utils.SoundManager;

public class PauseMenuScreen extends MenuScreen {
	
	public PauseMenuScreen(Game game) {
		super(game, Gdx.files.internal("data/Skin/uiskin.json"), "Paused", null);
	}

	@Override
	protected void initialize() {
		
		super.initialize();
		
		Button playButton = new TextButton("Resume", skin);
		Button quitButton = new TextButton("Back to Menu", skin);
		
		//TODO: Add buttons events.
		playButton.addListener(new ClickListener() {
			
			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				super.touchUp(event, x, y, pointer, button);
				
				onPlayGamePressed();
				
				SoundManager.playSound("select");
			}
			
		});
		
		quitButton.addListener(new ClickListener() {
			
			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				super.touchUp(event, x, y, pointer, button);
				
				onQuitPressed();
				
				SoundManager.playSound("back");
			}
			
		});
		
		window.add(playButton);
		window.row();
		window.add(quitButton);
		window.row();
		
	}
	
	@Override
	public boolean keyDown(int keycode) {
		if (keycode == Keys.ESCAPE || keycode == Keys.BACK) {
			onPlayGamePressed();
			return true;
		}
		
		return false;
	}

	/**
	 * Called when the Play Game button is pressed.
	 */
	private void onPlayGamePressed() {
		exit();
	}
	
	/**
	 * Called when the Quit button is pressed.
	 */
	private void onQuitPressed() {
		exit(); //Get rid of the pause screen
		game.getScreenManager().closeActiveScreen(); //Get rid of the gameplay screen too
	}
	
}
