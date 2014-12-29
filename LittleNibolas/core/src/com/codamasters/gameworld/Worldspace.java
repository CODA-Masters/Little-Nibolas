package com.codamasters.gameworld;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.codamasters.LittleNibolas;
import com.codamasters.screens.CongratsSpace;
import com.codamasters.screens.GameOverSpace;
import com.codamasters.LNHelpers.AssetLoaderSpace;
import com.codamasters.gameobjects.Nave;
import com.codamasters.gameobjects.ScrollHandler;

public class Worldspace {
	
	private Nave myNibolas;
    private ScrollHandler scroller;
    private int score = 0;
    @SuppressWarnings("unused")
	private float runTime = 0;
    private int midPointY;
    private boolean win;
    private LittleNibolas game;


    public Worldspace(int midPointY, LittleNibolas game) {
        //currentState = GameState.MENU;
        this.midPointY = midPointY;
        myNibolas = new Nave(33, midPointY - 5, 23, 25,midPointY*2);
        // The grass should start 66 pixels below the midPointY
        scroller = new ScrollHandler(this, midPointY);
        win = false;
        this.game = game;
    }

    public void update(float delta) {
        runTime += delta;
        updateRunning(delta);


    }

   /* private void updateReady(float delta) {
    	myNibolas.updateReady(runTime);
        scroller.updateReady(delta);
    }*/

    public void updateRunning(float delta) {
        if (delta > .15f) {
            delta = .15f;
        }

        myNibolas.update(delta);
        scroller.update(delta);

        if (scroller.collides(myNibolas) && myNibolas.isAlive()) {
            //scroller.onRestart();
        	scroller.stop();
        	myNibolas.die();
        	myNibolas.decelerate();
        	AssetLoaderSpace.tobu.stop();
        	AssetLoaderSpace.setScore(score);
        	AssetLoaderSpace.dead.play();
        	if(win){
            	((Game) Gdx.app.getApplicationListener()).setScreen((new CongratsSpace(game)));
        	}
        	else{
        		((Game) Gdx.app.getApplicationListener()).setScreen((new GameOverSpace(game)));
        	}
            //myNibolas.onRestart(midPointY - 5);;
            
            
        }
        if(scroller.getTime()==150){
        	game.actionResolver.unlockAchievement(LittleNibolas.ACHIEVEMENT3);
        	win = true;
        }
        
        if(scroller.getTime()==250){
        	game.actionResolver.unlockAchievement(LittleNibolas.ACHIEVEMENT6);
        }

        /*if (Intersector.overlaps(myNibolas.getBoundingCircle(), ground)) {
            scroller.stop();
            nibolas.die();
            nibolas.decelerate();
            currentState = GameState.GAMEOVER;

            if (score > AssetLoader.getHighScore()) {
                AssetLoader.setHighScore(score);
                currentState = GameState.HIGHSCORE;
            }
        }*/
    }

    public Nave getNibolas() {
        return myNibolas;

    }

    public int getMidPointY() {
        return midPointY;
    }

    public ScrollHandler getScroller() {
        return scroller;
    }

    public int getScore() {
        return score;
    }

    public void addScore(int increment) {
        score += increment;
    }

    public void restart() {
        //currentState = GameState.READY;
        score = 0;
        myNibolas.onRestart(midPointY - 5);
        scroller.onRestart();
        AssetLoaderSpace.tobu.play();
        //currentState = GameState.READY;
    }

}
