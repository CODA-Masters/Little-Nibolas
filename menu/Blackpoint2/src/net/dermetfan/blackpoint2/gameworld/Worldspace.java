package net.dermetfan.blackpoint2.gameworld;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

import net.dermetfan.blackpoint2.screens.GameOver;
import net.dermetfan.blackpoint2.screens.LevelMenu;
import net.dermetfan.blackpoint2.gameobjects.Nibolas;
import net.dermetfan.blackpoint2.gameobjects.ScrollHandler;

public class Worldspace {
	
	private Nibolas myNibolas;
    private ScrollHandler scroller;
    private int score = 0;
    @SuppressWarnings("unused")
	private float runTime = 0;
    private int midPointY;

    private GameState currentState;

    public enum GameState {
        MENU, READY, RUNNING, GAMEOVER, HIGHSCORE
    }

    public Worldspace(int midPointY) {
        //currentState = GameState.MENU;
        this.midPointY = midPointY;
        myNibolas = new Nibolas(33, midPointY - 5, 23, 25,midPointY*2);
        // The grass should start 66 pixels below the midPointY
        scroller = new ScrollHandler(this, midPointY);
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
            ((Game) Gdx.app.getApplicationListener()).setScreen((new GameOver()));
            //myNibolas.onRestart(midPointY - 5);;
            //AssetLoader.dead.play();
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

    public Nibolas getNibolas() {
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

    public void start() {
        currentState = GameState.RUNNING;
    }

    public void ready() {
        currentState = GameState.READY;
    }

    public void restart() {
        //currentState = GameState.READY;
        score = 0;
        myNibolas.onRestart(midPointY - 5);
        scroller.onRestart();
        //currentState = GameState.READY;
    }

    public boolean isReady() {
        return currentState == GameState.READY;
    }

    public boolean isGameOver() {
        return currentState == GameState.GAMEOVER;
    }

    public boolean isHighScore() {
        return currentState == GameState.HIGHSCORE;
    }

    public boolean isMenu() {
        return currentState == GameState.MENU;
    }

    public boolean isRunning() {
        return currentState == GameState.RUNNING;
    }

}
