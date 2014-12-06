package net.dermetfan.blackpoint2;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {

	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = Blackpoint2.TITLE + " v" + Blackpoint2.VERSION;
		cfg.vSyncEnabled = true;
		cfg.useGL20 = true;
		cfg.width = 1280;
		cfg.height = 720;
		cfg.addIcon("img/icon.png", FileType.Internal);

		new LwjglApplication(new Blackpoint2(), cfg);
	}

}
