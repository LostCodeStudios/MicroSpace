package com.punchline.microspace.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.lostcode.javalib.Game;
import com.lostcode.javalib.states.screens.MenuScreen;
import com.lostcode.javalib.utils.SoundManager;

public class SettingsScreen extends MenuScreen {

	private CheckBox sounds;
	private CheckBox music;
	
	public SettingsScreen(Game game) {
		super(game, Gdx.files.internal("data/Skin/uiskin.json"), "Settings", null);
	}

	@Override
	protected void initialize() {
		super.initialize();
		
		sounds = new CheckBox(" Sound Effects", skin);
		music = new CheckBox(" Music ", skin);
		
		ClickListener listener = new ClickListener() {
			
			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				
				float soundVol = sounds.isChecked() ? 1f : 0f;
				float musicVol = music.isChecked() ? 1f : 0f;
				
				SoundManager.setSoundVolume(soundVol);
				SoundManager.setMusicVolume(musicVol);
				
				SoundManager.playSound("select");
				
			}	
			
		};
		
		sounds.addListener(listener);
		music.addListener(listener);
		
		TextButton backButton = new TextButton("Back", skin);
		backButton.addListener(new ClickListener() {

			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				SettingsScreen.this.exit();
				SoundManager.playSound("back");
			}	
			
		});
		
		window.add(sounds);
		window.row();
		window.add(music);
		window.row();
		window.add(backButton);
	}

	@Override
	public void show() {
		readSettings();
		
		super.show();
	}

	@Override
	public void hide() {
		writeSettings();
		
		super.hide();
	}
	
	private void readSettings() {
		
		Preferences prefs = Gdx.app.getPreferences("settings");
		
		boolean soundVol = prefs.getBoolean("Sound", true);
		boolean musicVol = prefs.getBoolean("Music", true);
		
		sounds.setChecked(soundVol); 
		music.setChecked(musicVol);
		
	}
	
	private void writeSettings() {
		
		Preferences prefs = Gdx.app.getPreferences("settings");
		
		prefs.putBoolean("Sound", sounds.isChecked());
		prefs.putBoolean("Music", music.isChecked());
		
		prefs.flush();
		
	}

	@Override
	public boolean keyDown(int keycode) {
		if (keycode == Keys.ESCAPE || keycode == Keys.BACK) {
			exit();
			SoundManager.playSound("back");
			
			return true;
		}
		
		return false;
	}
	
	
	
}
