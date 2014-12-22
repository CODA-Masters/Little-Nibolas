package com.codamasters;

import com.badlogic.gdx.Game;
import com.codamasters.LNHelpers.AssetLoaderSpace;
import com.codamasters.LNHelpers.AssetsLoaderActual;
import com.codamasters.LNHelpers.AssetsLoaderRome;
import com.codamasters.screens.PantallaActual;
import com.codamasters.screens.ScreenRome;
import com.codamasters.screens.ScreenSpace;
import com.codamasters.screens.logo;

public class LittleNibolas extends Game {
	
	public static final String TITLE = "Little Nibolas", VERSION = "0.1";
	
	@Override
	public void create() {
		AssetLoaderSpace.load();
		AssetsLoaderActual.load();
		AssetsLoaderRome.load();
		setScreen(new logo());
	}

	@Override
	public void dispose() {
		super.dispose();
		AssetLoaderSpace.dispose();
		AssetsLoaderActual.dispose();
		AssetsLoaderRome.dispose();
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
