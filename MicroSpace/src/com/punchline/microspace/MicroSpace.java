package com.punchline.microspace;

import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;
import com.lostcode.javalib.Game;
import com.lostcode.javalib.states.screens.SplashScreen;
import com.lostcode.javalib.utils.Convert;
import com.lostcode.javalib.utils.SoundManager;
import com.punchline.microspace.screens.MainMenuScreen;

public class MicroSpace extends Game {
	
	@Override
	public void create() {
		Convert.init(8f);
		
		title = "Micro Space";
		
		landscapeMode = true;
		
		cursorTexture = new Texture(Gdx.files.internal("data/Textures/cursor.png"));
		
		super.create();
		
		getScreenManager().addScreen(new SplashScreen(this, Gdx.files.internal("data/Textures/splash.png"), new MainMenuScreen(this), 1.25f, 4f, 1.25f));
	}
	
	@Override
	protected void loadSounds() { 
		Preferences pref = Gdx.app.getPreferences("settings");
		
		float soundVol = pref.getBoolean("Sound", true) ? 1f : 0f;
		float musicVol = pref.getBoolean("Music", true) ? 1f : 0f;
		
		SoundManager.setSoundVolume(soundVol);
		SoundManager.setMusicVolume(musicVol);
		
		try {
			SoundManager.loadContent(Gdx.files.internal("data/Sounds/sounds.xml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
