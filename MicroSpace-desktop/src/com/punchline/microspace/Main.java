package com.punchline.microspace;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.lostcode.javalib.utils.LogManager;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.useGL20 = false;
		cfg.resizable = false;
		
		LwjglApplication a = new LwjglApplication(new MicroSpace(), cfg); 
		LogManager.init(a);
	}
}
