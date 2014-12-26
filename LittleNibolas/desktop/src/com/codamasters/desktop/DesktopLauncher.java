package com.codamasters.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.codamasters.LittleNibolas;
import com.codamasters.LNHelpers.ActionResolverDesktop;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 1280;
		config.height = 720;
		new LwjglApplication(new LittleNibolas(new ActionResolverDesktop()), config);
	}
}
