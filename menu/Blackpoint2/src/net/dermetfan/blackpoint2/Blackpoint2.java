package net.dermetfan.blackpoint2;

import net.dermetfan.blackpoint2.screens.Splash;
import net.dermetfan.blackpoint2.screens.logo;

import com.badlogic.gdx.Game;

public class Blackpoint2 extends Game {

	public static final String TITLE = "Little Nibolas", VERSION = "0.1";

	@Override
	public void create() {
		setScreen(new logo());
	}

	@Override
	public void dispose() {
		super.dispose();
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}

	@Override
	public void pause() {
		super.pause();
	}

	@Override
	public void resume() {
		super.resume();
	}

}
