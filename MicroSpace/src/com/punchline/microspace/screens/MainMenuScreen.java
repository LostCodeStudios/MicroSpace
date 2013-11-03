package com.punchline.microspace.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.lostcode.javalib.Game;
import com.lostcode.javalib.states.screens.MenuScreen;
import com.lostcode.javalib.utils.SoundManager;

/**
 * Base class for a game's Main Menu screen.
 * @author Nathaniel
 * @created Jul 24, 2013
 */
public class MainMenuScreen extends MenuScreen {
	
	/**
	 * Makes a MainMenuScreen.
	 * @param game The game.
	 * @param skinHandle A FileHandle pointing to the menu's skin.
	 * @param textureHandle A FileHandle pointing to the background texture.
	 */
	public MainMenuScreen(Game game) {
		super(game, Gdx.files.internal("data/Skin/uiskin.json"), "Micro Space", null);
	}

	@Override
	protected void initialize() {
		
		super.initialize();
		
		Button playButton = new TextButton("Play Game", skin);
		Button settingsButton = new TextButton("Settings", skin);
		Button quitButton = new TextButton("Quit", skin);
		
		//TODO: Add buttons events.
		playButton.addListener(new ClickListener() {
			
			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				super.touchUp(event, x, y, pointer, button);
				
				onPlayGamePressed();
				
				SoundManager.playSound("select");
			}
			
		});
		
		settingsButton.addListener(new ClickListener() {
			
			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				super.touchUp(event, x, y, pointer, button);
				
				onSettingsPressed();
				
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
		window.add(settingsButton);
		window.row();
		window.add(quitButton);
		window.row();
		
	}
	
	@Override
	public boolean keyDown(int keycode) {
		if (keycode == Keys.ESCAPE || keycode == Keys.BACK) {
			onQuitPressed();
			
			return true;
		}
		
		return false;
	}

	/**
	 * Called when the Play Game button is pressed.
	 */
	private void onPlayGamePressed() {
		game.getScreenManager().addScreen(new GameplayScreen(game));
	}
	
	/**
	 * Called when the Settings button is pressed.
	 */
	private void onSettingsPressed() {
		game.getScreenManager().addScreen(new SettingsScreen(game));
	}
	
	/**
	 * Called when the Quit button is pressed.
	 */
	private void onQuitPressed() {
		Gdx.app.exit();
	}
	
}
