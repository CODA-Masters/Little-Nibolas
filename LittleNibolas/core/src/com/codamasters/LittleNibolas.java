package com.codamasters;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
import com.codamasters.LNHelpers.ActionResolver;
import com.codamasters.LNHelpers.AssetLoaderSpace;
import com.codamasters.LNHelpers.AssetsLoaderActual;
import com.codamasters.LNHelpers.AssetsLoaderRome;
import com.codamasters.screens.PantallaActual;
import com.codamasters.screens.ScreenRome;
import com.codamasters.screens.ScreenSpace;
import com.codamasters.screens.logo;

public class LittleNibolas extends Game implements ApplicationListener{
	
	public static final String TITLE = "Little Nibolas", VERSION = "0.1";
	public static ActionResolver actionResolver;
	public static int intentos = 0;
	
	public LittleNibolas(ActionResolver actionResolver){
		this.actionResolver = actionResolver;
	}
	
	@Override
	public void create() {
		AssetLoaderSpace.load();
		AssetsLoaderActual.load();
		AssetsLoaderRome.load();
		setScreen(new ScreenRome(this));
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
