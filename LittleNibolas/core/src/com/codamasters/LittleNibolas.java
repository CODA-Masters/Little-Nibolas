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
import com.codamasters.screens.Instrucciones1;
import com.codamasters.screens.LevelMenu;
import com.codamasters.screens.PantallaActual;
import com.codamasters.screens.Rankings;
import com.codamasters.screens.ScreenRome;
import com.codamasters.screens.ScreenSpace;
import com.codamasters.screens.logo;

public class LittleNibolas extends Game implements ApplicationListener{
	
	public static final String TITLE = "Little Nibolas", VERSION = "1.1";
	public static ActionResolver actionResolver;
	public static int intentos = 0;
	
	// Rankings
	public static final String LEADERBOARD_NV1 = "CgkI05SL7vIIEAIQAQ";
	public static final String LEADERBOARD_NV2 = "CgkI05SL7vIIEAIQAg";
	public static final String LEADERBOARD_NV3 = "CgkI05SL7vIIEAIQAw";
	
	//Logros
	public static final String ACHIEVEMENT1 = "CgkI05SL7vIIEAIQBA";
	public static final String ACHIEVEMENT2 = "CgkI05SL7vIIEAIQBQ";
	public static final String ACHIEVEMENT3 = "CgkI05SL7vIIEAIQBg";
	public static final String ACHIEVEMENT4 = "CgkI05SL7vIIEAIQBw";
	public static final String ACHIEVEMENT5 = "CgkI05SL7vIIEAIQCA";
	public static final String ACHIEVEMENT6 = "CgkI05SL7vIIEAIQCQ";
	public static final String ACHIEVEMENT7 = "CgkI05SL7vIIEAIQCg";
	
	
	public LittleNibolas(ActionResolver actionResolver){
		this.actionResolver = actionResolver;
	}
	
	@Override
	public void create() {
		AssetLoaderSpace.load();
		AssetsLoaderActual.load();
		AssetsLoaderRome.load();
		setScreen(new logo(this));
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
