package com.codamasters.gameobjects;

import com.badlogic.gdx.Gdx;

import com.codamasters.gameworld.Worldspace;



public class ScrollHandler {
	
	private Meteorite meteor1,meteor2,meteor3,meteor4,meteor5;
    public static final int SCROLL_SPEED = -70;
    public static final int SPEED_EXTREME = -40;
    public static final int SCROLL_SPEED_SPACE = -30;
    public static int SPACE = 50;
    private float inc;
    private int time;
    private boolean modoExtreme;
    private Worldspace gameWorld;
    private Space space1,space2;

    public ScrollHandler(Worldspace gameWorld, int yPos) {
        this.gameWorld = gameWorld;
        modoExtreme=false;
        inc=0f;
        space1 = new Space(0, 0, 209, (yPos*2)+1, SCROLL_SPEED_SPACE);
        space2 = new Space(space1.getTailX(), 0, 209, (yPos*2)+1, 

SCROLL_SPEED_SPACE);
        meteor1= new Meteorite(420, 0,SCROLL_SPEED);
        meteor2= new Meteorite(meteor1.getTailX()+ SPACE, 0,SCROLL_SPEED);
        meteor3= new Meteorite(meteor2.getTailX()+ SPACE, 0,SCROLL_SPEED);
        meteor4= new Meteorite(meteor3.getTailX()+ SPACE, 0,SCROLL_SPEED);
        meteor5= new Meteorite(meteor4.getTailX()+ SPACE, 0,SCROLL_SPEED);
    }

    public void updateReady(float delta) {


    }

    public void update(float delta) {
    	inc+=1;
    	if(inc%60==0){
    		addScore(1);
    		time+=1;
    		inc=0;
    	}

    	
    	space1.update(delta);
    	space2.update(delta);
    	meteor1.update(delta);
    	meteor2.update(delta);
    	meteor3.update(delta);
    	meteor4.update(delta);
    	meteor5.update(delta);
    	
    	
    	if(time==86){
    		modoExtreme=true;
    		space1.changeSpeed(SCROLL_SPEED_SPACE + SPEED_EXTREME);
    		space2.changeSpeed(SCROLL_SPEED_SPACE + SPEED_EXTREME);
    		meteor1.changeSpeed(SCROLL_SPEED + SPEED_EXTREME);
            meteor2.changeSpeed(SCROLL_SPEED + SPEED_EXTREME);
            meteor3.changeSpeed(SCROLL_SPEED + SPEED_EXTREME);
            meteor4.changeSpeed(SCROLL_SPEED + SPEED_EXTREME);
            meteor5.changeSpeed(SCROLL_SPEED + SPEED_EXTREME);
    	}
    			
    	
        
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
    	
	    if(space1.isScrolledLeft()){
	    	space1.reset(space2.getTailX());
	    }else if(space2.isScrolledLeft()){
	    	space2.reset(space1.getTailX());
	    }
	    
    }
   
    public void stop() {
    	space1.stop();
    	space2.stop();
    	meteor1.stop();
    	meteor2.stop();
    	meteor3.stop();
    	meteor4.stop();
    	meteor5.stop();
    	
    }

    public boolean collides(Nave nibolas) {
    	/*if (!meteor1.isScored() && meteor1.getX() + 
    			(meteor1.getWidth() / 2) < nibolas.getX()
                        + nibolas.getWidth()) {
           // addScore(1);
            meteor1.setScored(true);
            //AssetLoader.coin.play();
        }
    	if (!meteor2.isScored() && meteor2.getX() + 
    			(meteor2.getWidth() / 2) < nibolas.getX()
                        + nibolas.getWidth()) {
            //addScore(1);
            meteor2.setScored(true);
            //AssetLoader.coin.play();
        }
    	if (!meteor3.isScored() && meteor3.getX() + 
    			(meteor3.getWidth() / 2) < nibolas.getX()
                        + nibolas.getWidth()) {
            //addScore(1);
            meteor3.setScored(true);
            //AssetLoader.coin.play();
        }
    	if (!meteor4.isScored() && meteor4.getX() + 
    			(meteor4.getWidth() / 2) < nibolas.getX()
                        + nibolas.getWidth()) {
            //addScore(1);
            meteor4.setScored(true);
            //AssetLoader.coin.play();
        }
    	if (!meteor5.isScored() && meteor5.getX() + 
    			(meteor5.getWidth() / 2) < nibolas.getX()
                        + nibolas.getWidth()) {
           // addScore(1);
            meteor5.setScored(true);
            //AssetLoader.coin.play();
        }*/

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
		space1.onRestart(0);
		space2.onRestart(space1.getTailX());
		meteor1.onRestart(210, SCROLL_SPEED);
		meteor2.onRestart(meteor1.getTailX() + SPACE, SCROLL_SPEED);
		meteor3.onRestart(meteor2.getTailX() + SPACE, SCROLL_SPEED);
		meteor4.onRestart(meteor3.getTailX() + SPACE, SCROLL_SPEED);
		meteor5.onRestart(meteor4.getTailX() + SPACE, SCROLL_SPEED);
		
	}

	public Space getSpace1() {
		return space1;
	}
	public Space getSpace2() {
		return space2;
	}
	public int getTime(){
		return time;
	}


}
    		
