package com.codamasters;

import com.badlogic.gdx.Game;
import com.codamasters.screens.PantallaActual;

public class LittleNibolas extends Game {
	@Override
	public void create() {
		setScreen(new PantallaActual());
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
