package com.codamasters.gameobjects;

import com.codamasters.gameworld.Worldspace;



public class ScrollHandler {
	
	private Meteorite meteor1,meteor2,meteor3,meteor4,meteor5;
    public static final int SCROLL_SPEED = -59;
    public static final int SPACE = 40;
    private Worldspace gameWorld;

    public ScrollHandler(Worldspace gameWorld, float yPos) {
        this.gameWorld = gameWorld;
        meteor1= new Meteorite(210, 0,SCROLL_SPEED);
        meteor2= new Meteorite(meteor1.getTailX()+ SPACE, 0,SCROLL_SPEED);
        meteor3= new Meteorite(meteor2.getTailX()+ SPACE, 0,SCROLL_SPEED);
        meteor4= new Meteorite(meteor3.getTailX()+ SPACE, 0,SCROLL_SPEED);
        meteor5= new Meteorite(meteor4.getTailX()+ SPACE, 0,SCROLL_SPEED);
    }

    public void updateReady(float delta) {


    }

    public void update(float delta) {
    	meteor1.update(delta);
    	meteor2.update(delta);
    	meteor3.update(delta);
    	meteor4.update(delta);
    	meteor5.update(delta);
    	if (meteor1.isScrolledLeft()) {
            meteor1.reset(meteor5.getTailX() + SPACE);
    	}
    	else if (meteor2.isScrolledLeft()) {
            meteor2.reset(meteor1.getTailX() + SPACE);
    	}
    	else if (meteor3.isScrolledLeft()) {
            meteor3.reset(meteor2.getTailX() + SPACE);
    	}
    	else if (meteor4.isScrolledLeft()) {
            meteor4.reset(meteor3.getTailX() + SPACE);
    	}
    	else if (meteor5.isScrolledLeft()) {
            meteor5.reset(meteor4.getTailX() + SPACE);
    	}
    }

    public void stop() {
    	meteor1.stop();
    	meteor2.stop();
    	meteor3.stop();
    	meteor4.stop();
    	meteor5.stop();
    }

    public boolean collides(Nibolas nibolas) {
    	if (!meteor1.isScored() && meteor1.getX() + 
    			(meteor1.getWidth() / 2) < nibolas.getX()
                        + nibolas.getWidth()) {
            addScore(1);
            meteor1.setScored(true);
            //AssetLoader.coin.play();
        }
    	if (!meteor2.isScored() && meteor2.getX() + 
    			(meteor2.getWidth() / 2) < nibolas.getX()
                        + nibolas.getWidth()) {
            addScore(1);
            meteor2.setScored(true);
            //AssetLoader.coin.play();
        }
    	if (!meteor3.isScored() && meteor3.getX() + 
    			(meteor3.getWidth() / 2) < nibolas.getX()
                        + nibolas.getWidth()) {
            addScore(1);
            meteor3.setScored(true);
            //AssetLoader.coin.play();
        }
    	if (!meteor4.isScored() && meteor4.getX() + 
    			(meteor4.getWidth() / 2) < nibolas.getX()
                        + nibolas.getWidth()) {
            addScore(1);
            meteor4.setScored(true);
            //AssetLoader.coin.play();
        }
    	if (!meteor5.isScored() && meteor5.getX() + 
    			(meteor5.getWidth() / 2) < nibolas.getX()
                        + nibolas.getWidth()) {
            addScore(1);
            meteor5.setScored(true);
            //AssetLoader.coin.play();
        }

        return (meteor1.collides(nibolas)|| meteor2.collides(nibolas)||
        		meteor3.collides(nibolas)|| meteor4.collides(nibolas)||
        		meteor5.collides(nibolas));
    }
    public Meteorite getMeteor1(){
    	return meteor1;
    }
    public Meteorite getMeteor2(){
    	return meteor2;
    }
    public Meteorite getMeteor3(){
    	return meteor3;
    }
    public Meteorite getMeteor4(){
    	return meteor4;
    }
    public Meteorite getMeteor5(){
    	return meteor5;
    }
    private void addScore(int increment) {
        gameWorld.addScore(increment);
    }

	public void onRestart() {
		// TODO Auto-generated method stub
		meteor1.onRestart(210, SCROLL_SPEED);
		meteor2.onRestart(meteor1.getTailX() + SPACE, SCROLL_SPEED);
		meteor3.onRestart(meteor2.getTailX() + SPACE, SCROLL_SPEED);
		meteor4.onRestart(meteor3.getTailX() + SPACE, SCROLL_SPEED);
		meteor5.onRestart(meteor4.getTailX() + SPACE, SCROLL_SPEED);
		
	}


}
