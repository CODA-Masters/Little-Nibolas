package com.codamasters.LNHelpers;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.codamasters.gameobjects.Nibolas;
import com.codamasters.screens.PantallaActual;


public class InputHandler implements InputProcessor {
	
    private float scaleFactorX;
    private float scaleFactorY;
    private Nibolas myNibolas;
    private PantallaActual pantalla;

    public InputHandler(PantallaActual miPantalla, float scaleFactorX,
            float scaleFactorY) {

        this.scaleFactorX = scaleFactorX;
        this.scaleFactorY = scaleFactorY;
        myNibolas = miPantalla.getNibolas();
        pantalla = miPantalla;

    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
    	if(myNibolas.isVisible())
    		myNibolas.move(screenX, screenY);
    	else{
    		pantalla.salirDePapelera(screenX,screenY);
    	}
        return true;
        
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
    	return false;
    }

    @Override
    public boolean keyDown(int keycode) {
    	switch(keycode){
	    	case Keys.LEFT:
	    		myNibolas.moveLeft();
	    		break;
	    	case Keys.RIGHT:
	    		myNibolas.moveRight();
	    		break;
    	}
    	if(!myNibolas.isVisible()){
    		if(myNibolas.isLookingRight())
    			pantalla.salirDePapelera(1000,0);
    		else
    			pantalla.salirDePapelera(-1000,0);
    	}
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
    	myNibolas.stay();
    	return true;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    private int scaleX(int screenX) {
        return (int) (screenX / scaleFactorX);
    }

    private int scaleY(int screenY) {
        return (int) (screenY / scaleFactorY);
    }

}
