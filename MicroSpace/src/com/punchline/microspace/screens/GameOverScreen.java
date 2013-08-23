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
import com.punchline.microspace.MicroGameOverInfo;

public class GameOverScreen extends MenuScreen {
	
	public GameOverScreen(Game game, MicroGameOverInfo info) {
		super(game, Gdx.files.internal("data/Skin/uiskin.json"), info.winningGroup.equals("leftTeam") ? "You won!" : "You lost!", null);
	}

	@Override
	protected void initialize() {
		super.initialize();
		
		Button quitButton = new TextButton("Back to Menu", skin);
		quitButton.addListener(new ClickListener() {
			
			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				super.touchUp(event, x, y, pointer, button);
				
				game.setScreen(new MainMenuScreen(game));
				
				SoundManager.playSound("back");
			}
			
		});
		
		window.add(quitButton);
		
	}

	@Override
	public boolean keyDown(int keycode) {
		if (keycode == Keys.ESCAPE || keycode == Keys.BACK) {
			game.setScreen(new MainMenuScreen(game));
			
			SoundManager.playSound("back");
			
			return true;
		}
		
		return false;
	}
	
	
	
}
