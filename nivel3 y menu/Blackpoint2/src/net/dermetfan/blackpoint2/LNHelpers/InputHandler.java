package net.dermetfan.blackpoint2.LNHelpers;


import com.badlogic.gdx.InputProcessor;
import net.dermetfan.blackpoint2.gameobjects.Nibolas;
import net.dermetfan.blackpoint2.gameworld.Worldspace;


public class InputHandler implements InputProcessor {
    private Nibolas myNibolas;
    @SuppressWarnings("unused")
	private Worldspace myWorld;


    private float scaleFactorX;
    private float scaleFactorY;

    public InputHandler(Worldspace myWorld, float scaleFactorX,
            float scaleFactorY) {
        this.myWorld = myWorld;
        myNibolas = myWorld.getNibolas();

        @SuppressWarnings("unused")
		int midPointY = myWorld.getMidPointY();

        this.scaleFactorX = scaleFactorX;
        this.scaleFactorY = scaleFactorY;
    }
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        screenX = scaleX(screenX);
        screenY = scaleY(screenY);
        System.out.println(screenX + " " + screenY);

        myNibolas.onClick();

        /*if (myWorld.isGameOver() || myWorld.isHighScore()) {
            myWorld.restart();
        }*/

        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;

    }

    @Override
    public boolean keyDown(int keycode) {
		return false;

    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
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
