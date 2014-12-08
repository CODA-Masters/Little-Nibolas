package com.codamasters;

import com.badlogic.gdx.Game;
import com.codamasters.LNHelpers.AssetLoaderSpace;
import com.codamasters.LNHelpers.AssetsLoader;
import com.codamasters.screens.ScreenRome;
import com.codamasters.screens.logo;

public class LittleNibolas extends Game {
	
	public static final String TITLE = "Little Nibolas", VERSION = "0.1";
	
	@Override
	public void create() {
		AssetsLoader.load();
		AssetLoaderSpace.load();
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
