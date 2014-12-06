package com.codamasters.gameobjects;

import com.codamasters.gameworld.Worldspace;


public class ScrollHandler {

    public static final int SCROLL_SPEED = -59;

    private Worldspace gameWorld;

    public ScrollHandler(Worldspace gameWorld, float yPos) {
        this.gameWorld = gameWorld;
    }

    public void updateReady(float delta) {


    }

    public void update(float delta) {

    }

    public void stop() {

    }

    public boolean collides(Nibolas nibolas) {


        return false;
    }

    private void addScore(int increment) {
        gameWorld.addScore(increment);
    }

	public void onRestart() {
		// TODO Auto-generated method stub
		
	}


}
