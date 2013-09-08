package com.punchline.microspace;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;
import com.punchline.javalib.Game;
import com.punchline.javalib.states.screens.SplashScreen;
import com.punchline.javalib.utils.Convert;
import com.punchline.javalib.utils.SoundManager;
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
		
		SoundManager.addSound("back", Gdx.files.internal("data/Sounds/back.wav"));
		SoundManager.addSound("select", Gdx.files.internal("data/Sounds/select.wav"));
		SoundManager.addSound("shot", Gdx.files.internal("data/Sounds/shot.wav"));
		SoundManager.addSound("explosion", Gdx.files.internal("data/Sounds/explosion.wav"));
		SoundManager.addSound("hit", Gdx.files.internal("data/Sounds/hit.wav"));
	}
	
}
