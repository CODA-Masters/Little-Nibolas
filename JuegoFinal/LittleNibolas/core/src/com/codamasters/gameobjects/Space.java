package com.codamasters.gameobjects;


public class Space extends Scrollable{
	
	public Space(float x, float y, int width, int height, float scrollSpeed) {
		super(x, y, width, height, scrollSpeed);
		
		
	}
	@Override
	public void reset(float newx){
		super.reset(newx);
	}

	public void onRestart(float x) {
		position.x=x;
    	velocity.x = ScrollHandler.SCROLL_SPEED_SPACE;
		
	}
	public void update(float delta){
		super.update(delta);
	}
	public void changeSpeed(int v){
		velocity.x= v;
	}


}
