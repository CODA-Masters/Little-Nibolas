package com.codamasters.desktop;


import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.codamasters.LittleNibolas;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.vSyncEnabled = true;
		cfg.width = 1280;
		cfg.height = 720;
		
		new LwjglApplication(new LittleNibolas(), cfg);
	}
}
