package com.codamasters.gameobjects;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Circle;


public class Meteorite extends Scrollable{

    private Random altura;
   // private Random alfa;
    private Random radius;
    private Circle meteor;


    private boolean isScored = false;

    public Meteorite(float x, float y, float scrollSpeed) {
        super(x, y, 0, 0, scrollSpeed);
        meteor= new Circle();
        //alfa = new Random();
        altura = new Random();
        radius=new Random();
        
        //increm = (float)((alfa.nextInt(4000)-2000)/1000);
       /* if((int)increm==0)
        	this.height = altura.nextInt(100);
        else if((int)increm==-1)
        	this.height = altura.nextInt(50)-50;
        else if((int)increm==1)
        	this.height = altura.nextInt(50)+50;
        else
        	this.height = altura.nextInt(100);
        	*/
        	this.height = altura.nextInt(100);
        
        this.width = radius.nextInt(15)+5;
        
        
        
    }

    @Override
    public void update(float delta) {
        // Call the update method in the superclass (Scrollable)
        super.update(delta);
        meteor.set(position.x, height, width);
        //meteor.set(position.x, height+increm, width);


    }

    @Override
    public void reset(float newX) {
        // Call the reset method in the superclass (Scrollable)
        super.reset(newX);
        // Change the height to a random number
        height = altura.nextInt(100);
        width = radius.nextInt(15)+5;
       
        isScored = false;
    }
    public void onRestart(float x, float scrollSpeed){
    	velocity.x = scrollSpeed;
    	reset(x);
    }

    public boolean collides(Nave myNibolas) {
        if (position.x < myNibolas.getX() + myNibolas.getWidth()) {
            return (Intersector.overlaps(myNibolas.getBoundingCircle(), meteor));
        }
        return false;
    }

    public boolean isScored() {
        return isScored;
    }

    public void setScored(boolean b) {
        isScored = b;
    }
    public void changeSpeed(int v){
		velocity.x= v;
	}
    
    public Circle getBoundingCircle(){
    	return meteor;
    }
}
