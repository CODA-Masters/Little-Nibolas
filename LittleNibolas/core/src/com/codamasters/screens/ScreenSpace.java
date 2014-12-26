package com.codamasters.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.codamasters.LittleNibolas;
import com.codamasters.LNHelpers.InputHandlerSpace;
import com.codamasters.gameworld.SpaceRenderer;
import com.codamasters.gameworld.Worldspace;

public class ScreenSpace implements Screen{

    private Worldspace world;
    private SpaceRenderer renderer;
    private float runTime;
    private LittleNibolas game;

    // This is the constructor, not the class declaration
    public ScreenSpace(LittleNibolas game) {

    	this.game = game;
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        float gameWidth = 209;
        float gameHeight = screenHeight / (screenWidth / gameWidth);
        int midPointY = (int) (gameHeight / 2);

        world = new Worldspace(midPointY, game);
        renderer = new SpaceRenderer(world, (int) gameHeight, midPointY);
        Gdx.input.setInputProcessor(new InputHandlerSpace(world, screenWidth / gameWidth, screenHeight / gameHeight));
        
    }

    @Override
    public void render(float delta) {
        runTime += delta;
        world.update(delta);
        renderer.render(runTime);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void show() {
    	
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        // Leave blank
    }
}
