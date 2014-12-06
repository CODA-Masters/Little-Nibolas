package com.codamasters.game;

import com.badlogic.gdx.Game;
import com.codamasters.LNHelpers.AssetLoader;
import com.codamasters.screens.ScreenSpace;


public class LittleNibolas extends Game{
	
	 @Override
	    public void create() {
	        
	        AssetLoader.load();
	        setScreen(new ScreenSpace());
	    }

	    @Override
	    public void dispose() {
	        super.dispose();
	        AssetLoader.dispose();
	    }
}
