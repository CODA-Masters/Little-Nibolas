package com.codamasters.gameworld;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import com.codamasters.LNHelpers.AssetLoaderSpace;
import com.codamasters.gameobjects.Meteorite;
import com.codamasters.gameobjects.Nave;
import com.codamasters.gameobjects.ScrollHandler;
import com.codamasters.gameobjects.Space;

public class SpaceRenderer {

    private Worldspace myWorld;
    private OrthographicCamera cam;
    private ShapeRenderer shapeRenderer;

    private SpriteBatch batcher;

    private int midPointY;
    
    // Game Objects
    @SuppressWarnings("unused")
	private Nave myNibolas;
    private ScrollHandler scroller;
    private Meteorite meteor1,meteor2,meteor3,meteor4,meteor5;
    private Space space1,space2;
    private BitmapFont text,shadow;
    
    public SpaceRenderer(Worldspace world, int gameHeight, int midPointY) {
        myWorld = world;

        // The word "this" refers to this instance.
        // We are setting the instance variables' values to be that of the
        // parameters passed in from GameScreen.
        this.midPointY = midPointY;

        cam = new OrthographicCamera();
        cam.setToOrtho(true, 209, gameHeight);
        batcher = new SpriteBatch();
        batcher.setProjectionMatrix(cam.combined);
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(cam.combined);
        
        initGameObjects();
        AssetLoaderSpace.tobu.play();
    }
    private void initGameObjects() {
        myNibolas = myWorld.getNibolas();
        scroller = myWorld.getScroller();
        space1 = scroller.getSpace1();
        space2 = scroller.getSpace2();
        meteor1 = scroller.getMeteor1();
        meteor2 = scroller.getMeteor2();
        meteor3 = scroller.getMeteor3();
        meteor4= scroller.getMeteor4();
        meteor5 = scroller.getMeteor5();
    }
    public void render(float runTime) {

        // We will move these outside of the loop for performance later.
        Nave myNibolas = myWorld.getNibolas();

        // Fill the entire screen with black, to prevent potential flickering.
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Begin ShapeRenderer
        //shapeRenderer.begin(ShapeType.Filled);
        
    	
    	//shapeRenderer.setColor(55 / 255.0f, 80 / 255.0f, 100 / 255.0f, 1);
        //shapeRenderer.rect(0, 0, 209, midPointY + 66);
    	//shapeRenderer.end();
        // Draw Background color
        //shapeRenderer.setColor(55 / 255.0f, 80 / 255.0f, 100 / 255.0f, 1);
        //shapeRenderer.rect(0, 0, 209, midPointY + 66);
       
         

        // Begin SpriteBatch
        batcher.begin();
        // Disable transparency
        // This is good for performance when drawing images that do not require
        // transparency.
        batcher.disableBlending();
        batcher.draw(AssetLoaderSpace.bg, space1.getX(), space1.getY(), space1.getWidth(), space1.getHeight());
        batcher.draw(AssetLoaderSpace.bg, space2.getX(), space2.getY(), space2.getWidth(), space2.getHeight());
        
        // The bird needs transparency, so we enable that again.
        batcher.enableBlending();
        // Draw bird at its coordinates. Retrieve the Animation object from
        // AssetLoader
        // Pass in the runTime variable to get the current frame.
        batcher.draw(AssetLoaderSpace.naveAnimation.getKeyFrame(runTime),
                myNibolas.getX(), myNibolas.getY(), myNibolas.getWidth(), myNibolas.getHeight());
        drawMeteor();
 
        // Convert integer into String
        String score="";
        if(scroller.getTime()>=80 && scroller.getTime()<=86){
        	if(scroller.getTime()==80){
        		score="MODO EXTREMO";
        	}else if(scroller.getTime()==81){
        		score="5";
        	}else if(scroller.getTime()==82){
        		score="4";
        	}else if(scroller.getTime()==83){
        		score="3";
        	}else if(scroller.getTime()==84){
        		score="2";
        	}else if(scroller.getTime()==85){
        		score="1";
        	}else if(scroller.getTime()==86){
        		score="PAAAH";
        	}
        	AssetLoaderSpace.shadow.draw(batcher, "" + score , (209/ 2) - (5 * score.length()), midPointY);
            // Draw text
            AssetLoaderSpace.font.draw(batcher, "" + score, (209 / 2) - (5 * score.length() - 1),midPointY);
        }
        	
        
        score = myWorld.getScore() + "";
        // Draw shadow first
        AssetLoaderSpace.shadow.draw(batcher, "" + myWorld.getScore(), (209/ 2)
                - (3 * score.length()), midPointY-50);
        // Draw text
        AssetLoaderSpace.font.draw(batcher, "" + myWorld.getScore(), (209 / 2)
                - (3 * score.length() - 1),midPointY-50);
        batcher.end();
       /* shapeRenderer.begin(ShapeType.Filled);
        shapeRenderer.setColor(255 / 255.0f, 255 / 255.0f, 255 / 255.0f, 1);
        shapeRenderer.circle(meteor1.getX(), meteor1.getHeight(), meteor1.getWidth());
    	shapeRenderer.circle(meteor2.getX(), meteor2.getHeight(), meteor2.getWidth());
    	shapeRenderer.circle(meteor3.getX(), meteor3.getHeight(), meteor3.getWidth());
    	shapeRenderer.circle(meteor4.getX(), meteor4.getHeight(), meteor4.getWidth());
    	shapeRenderer.circle(meteor5.getX(), meteor5.getHeight(), meteor5.getWidth());
        // End ShapeRenderer
        shapeRenderer.end();*/

    }
    private void drawMeteor(){
    	
    	
    	batcher.draw(AssetLoaderSpace.meteor, meteor1.getX()- meteor1.getWidth(), meteor1.getHeight()- meteor1.getWidth(),meteor1.getWidth()*2, meteor1.getWidth()*2);
    	batcher.draw(AssetLoaderSpace.meteor, meteor2.getX()- meteor2.getWidth(), meteor2.getHeight()- meteor2.getWidth(),meteor2.getWidth()*2, meteor2.getWidth()*2);
    	batcher.draw(AssetLoaderSpace.meteor_R, meteor3.getX()- meteor3.getWidth(), meteor3.getHeight()- meteor3.getWidth(),meteor3.getWidth()*2, meteor3.getWidth()*2);
    	batcher.draw(AssetLoaderSpace.meteor, meteor4.getX()- meteor4.getWidth(), meteor4.getHeight()- meteor4.getWidth(),meteor4.getWidth()*2, meteor4.getWidth()*2);
    	batcher.draw(AssetLoaderSpace.meteor, meteor5.getX()- meteor5.getWidth(), meteor5.getHeight()- meteor5.getWidth(),meteor5.getWidth()*2, meteor5.getWidth()*2);
    	
    }
}
