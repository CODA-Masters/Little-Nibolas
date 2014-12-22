package com.codamasters.screens;

import java.util.Random;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.utils.Array;
import com.codamasters.LNHelpers.AnimatedSprite;
import com.codamasters.LNHelpers.AssetLoaderSpace;
import com.codamasters.LNHelpers.AssetsLoaderActual;
import com.codamasters.LNHelpers.AssetsLoaderRome;
import com.codamasters.LNHelpers.InputHandlerRome;
import com.codamasters.gameobjects.Horse;
import com.codamasters.gameobjects.Lanza;
import com.codamasters.gameobjects.Plataforma;
import com.codamasters.gameobjects.Soldado;
import com.badlogic.gdx.physics.box2d.ContactListener;




public class ScreenRome implements Screen{
	
	private World world;
	private Box2DDebugRenderer debugRenderer;
	private SpriteBatch batch;
	private OrthographicCamera camera;

	private final float TIMESTEP = 1 / 60f;
	private final int VELOCITYITERATIONS = 8, POSITIONITERATIONS = 3;
	
	private Array<Body> tmpBodies = new Array<Body>();
	private Horse myHorse;
	private Body ground;
	private Fixture fixtureGround;
	private Array<Lanza> lanzas = new Array<Lanza>();
	private Array<Soldado> soldados = new Array<Soldado>();
	boolean muerto;
    private float time=0.0f;
    private float timePlatform =0.0f;
	private Random rand;
	private int minX = 4;
	private int maxX = 8;
	private int minPlatX = -4;
	private int maxPlatX = 4;
	private int minTiempoPlataforma = 120;
	private int maxTiempoPlataforma = 240;
	private float minY=10;
	private float maxY=15;
	private float posY;
	private int posX;
	private Plataforma plataforma;
	private boolean direc, win;
	private Soldado sold;
	private static Preferences prefs;
	private static int score=0;
	private int tiempoPlataforma;


	@Override
	public void render(float delta) {
		
		if(!muerto){
			
			Gdx.gl.glClearColor(0, 0, 0, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			
			world.step(TIMESTEP, VELOCITYITERATIONS, POSITIONITERATIONS);
			
			//camera.position.x = myHorse.getBody().getPosition().x;
			camera.update();
		
			batch.setProjectionMatrix(camera.combined);
			batch.begin();
			batch.draw(AssetsLoaderRome.background, camera.position.x-camera.viewportWidth/2, camera.position.y-camera.viewportHeight/2, camera.viewportWidth, camera.viewportHeight);
			AssetsLoaderRome.animatedSprite.setBounds(myHorse.getBody().getPosition().x-myHorse.WIDTH/2, myHorse.getBody().getPosition().y-myHorse.HEIGHT/2, myHorse.WIDTH, myHorse.HEIGHT);
			//animatedSprite.setBounds(myHorse.getBody().getPosition().x, myHorse.getBody().getPosition().y,myHorse.WIDTH*1.4f, myHorse.HEIGHT);
			AssetsLoaderRome.animatedSprite.setKeepSize(true);
			AssetsLoaderRome.animatedSprite.draw(batch);
			for (Lanza lanza : lanzas) {
				lanza.getAnimatedSprite().setBounds(lanza.getBody().getPosition().x-lanza.WIDTH/2, lanza.getBody().getPosition().y-lanza.HEIGHT/4, lanza.WIDTH, lanza.HEIGHT/2);
				lanza.getAnimatedSprite().setKeepSize(true);
				lanza.getAnimatedSprite().setOriginCenter();
				lanza.getAnimatedSprite().setRotation((float)(lanza.getBody().getAngle()*180/Math.PI));
				lanza.getAnimatedSprite().draw(batch);
			}
			plataforma.getAnimatedSprite().setBounds(plataforma.getBody().getPosition().x-plataforma.WIDTH/2, plataforma.getBody().getPosition().y-plataforma.HEIGHT/20, plataforma.WIDTH, plataforma.HEIGHT/12);
			plataforma.getAnimatedSprite().setKeepSize(true);
			plataforma.getAnimatedSprite().draw(batch);
			world.getBodies(tmpBodies);
			for(Body body : tmpBodies)
				if(body.getUserData() instanceof Sprite) {
					Sprite sprite = (Sprite) body.getUserData();
					sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2, body.getPosition().y - sprite.getHeight() / 2);
					sprite.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
					sprite.draw(batch);
				}
			
			/*
			sold.getAnimatedSprite().setBounds(sold.getBody().getPosition().x-sold.WIDTH/2, sold.getBody().getPosition().y-sold.HEIGHT/4, sold.WIDTH, sold.HEIGHT/2);
			sold.getAnimatedSprite().setKeepSize(true);
			sold.getAnimatedSprite().draw(batch);
			*/
			
		    String scoreText = getScore() + "";
	        // Draw shadow first
		    AssetsLoaderRome.shadow.setScale(0.03f);
		    AssetsLoaderRome.shadow.draw(batch, "" + getScore(),camera.position.x-scoreText.length()/2,camera.position.y+camera.viewportHeight/4);
	        // Draw text
		    AssetsLoaderRome.font.setScale(0.03f);
		    AssetsLoaderRome.font.draw(batch, "" + getScore(), camera.position.x-scoreText.length()/2-0.01f,camera.position.y+camera.viewportHeight/4);
			
			batch.end();
			
			myHorse.update();
			
			
			time+=delta;
			if(time>60*5*delta && lanzas.size < 10){
				time=0;
				posX= minX + rand.nextInt(maxX - minX + 1);
				posY= minY + rand.nextFloat()*maxX;
				Lanza lan = new Lanza(world, this, posX+camera.position.x+camera.viewportWidth/2+posX, posY, 1f, 0.5f);
				lan.setAnimatedSprite(AssetsLoaderRome.animSpriteFlecha);
				lanzas.add(lan);
			}
			


			for (Lanza lanza : lanzas) {
				if( ( lanza.getBody().getPosition().x < camera.position.x-camera.viewportWidth/2) || (lanza.getBody().getLinearVelocity().y == 0)){
					lanza.destroy();
					posX= minX + rand.nextInt(maxX - minX + 1);
					posY= minY + rand.nextFloat()*maxX;
					lanza = new Lanza(world, this, camera.position.x+camera.viewportWidth/2+posX, posY, 1f, 0.5f);		
					addScore();
					}
			}
			
			timePlatform+=delta;
			
			tiempoPlataforma = minTiempoPlataforma + rand.nextInt(maxTiempoPlataforma - minTiempoPlataforma + 1);
			
			if(timePlatform>tiempoPlataforma*delta){
				timePlatform=0;
				plataforma.destroy();
				posX= minPlatX + rand.nextInt(maxPlatX - minPlatX + 1);
				plataforma = new Plataforma(world, this, posX, -3f, 3f, 1f);
				plataforma.setAnimatedSprite(AssetsLoaderRome.animSpritePlataforma);
			}
		
			
    		//for (Soldado sold : soldados) {
    			    			
    			// direccion --> TRUE : VOY A LA IZQUIERDA
    			// direccion --> FALSE: VOY A LA DERECHA
    			/*
    			sold.update();


				if( ( ( sold.getBody().getPosition().x < camera.position.x-camera.viewportWidth/2-5) && sold.getDireccion() )
						|| ( sold.getBody().getPosition().x > camera.position.x+camera.viewportWidth/2+5) && !sold.getDireccion()){
					Gdx.app.log("HE LLEGADO AL LIMITE", "");
					sold.destroy();
					//soldados.removeIndex(soldados.size-1);
					direc = rand.nextBoolean();
					*/
					//if(direc)
					//	Gdx.app.log("VIENE DE DERECHA!!!","");
					//else
					//	Gdx.app.log("VIENE DE IZQUIERDA!!!","");
					
					/*
					if(direc)
						 sold = new Soldado(world, this, camera.position.x+camera.viewportWidth/2+4, -6.6f, 0.8f, 0.8f, direc);
					else
						 sold = new Soldado(world, this, camera.position.x-camera.viewportWidth/2-4, -6.6f, 0.8f, 0.8f, direc);		
					**/
					//soldados.add(sold);
	    		//}
				

				
    		//}

			
			//Gdx.app.log("NUMERO DE FLECHAS", lanzas.size+"");
			
			//debugRenderer.render(world, camera.combined);
			//if(score>10){
				//music_R.stop();
				//((Game) Gdx.app.getApplicationListener()).setScreen((new FinSegundoNivel1()));
			//}
		}
		else{
			
			/*
			Gdx.gl.glClearColor(0, 0, 0, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			
	        batch.begin();
	        font.setScale(0.08f);
	        font.draw(batch, " MUERTO", camera.position.x-2,camera.position.y+1);
	        batch.end();
	        */
						
		setScore(score);
		AssetsLoaderRome.music_R.stop();
		if(win)
			((Game) Gdx.app.getApplicationListener()).setScreen((new CongratsRome()));
		else
			((Game) Gdx.app.getApplicationListener()).setScreen((new GameOverRome()));

		}
		if(score>=100){
			win = true;
			
		}
		
	}
	
	 private void createCollisionListener() {
	        world.setContactListener(new ContactListener() {
	        	
	            @Override
	            public void preSolve(Contact contact, Manifold oldManifold) {
	            }

	            @Override
	            public void postSolve(Contact contact, ContactImpulse impulse) {
	            }


	            @Override
	            public void beginContact(Contact contact) {
	                Fixture fixtureA = contact.getFixtureA();
	                Fixture fixtureB = contact.getFixtureB();
	                
	        		for (Lanza lanza : lanzas) {
	        			if(( lanza.getFixture() == fixtureA && myHorse.getFixture()==fixtureB ) || ( lanza.getFixture() == fixtureB && myHorse.getFixture()==fixtureA ) ){
	        				if(lanza.EsMortal())
	        					muerto=true;
	        			}
	        			
	        			if((lanza.getFixture() == fixtureA && fixtureGround==fixtureB ) || (lanza.getFixture() == fixtureB && fixtureGround==fixtureA )){
	        				lanza.getBody().setAngularVelocity(0);
	        				lanza.setEsMortal(false);
	        			}
	        			
	        			if((lanza.getFixture() == fixtureA && plataforma.getFixture()==fixtureB ) || (lanza.getFixture() == fixtureB && plataforma.getFixture()==fixtureA )){
	        				lanza.getBody().setAngularVelocity(0);
	        			}
	        			
	        			/*
	        			
	        			if((lanza.getFixture() == fixtureA && sold.getFixture()==fixtureB ) || (lanza.getFixture() == fixtureB && sold.getFixture()==fixtureA )){
	        				lanza.getBody().setAngularVelocity(0);
	        				lanza.setEsMortal(false);
	        			}
	        			*/

	        		}
	        		
	        		if((myHorse.getFixture() == fixtureA && fixtureGround==fixtureB ) || (myHorse.getFixture() == fixtureB && fixtureGround==fixtureA )){
	        			myHorse.setNumSaltos(0);
	        		}
	        		
	        		  
	        		//for (Soldado sold : soldados) {
	        		
	        		//	if(( sold.getFixture() == fixtureA && myHorse.getFixture()==fixtureB ) || ( sold.getFixture() == fixtureB && myHorse.getFixture()==fixtureA ) ){
	        		//			muerto=true;
	        		//	}
	        		//}

	        		
	        		
	            }

	            @Override
	            public void endContact(Contact contact) {
	                Fixture fixtureA = contact.getFixtureA();
	                Fixture fixtureB = contact.getFixtureB();
	            }

	        });
	    }

	 
	@Override
	public void resize(int width, int height) {
		//camera.viewportWidth = width / 25;
		//camera.viewportHeight = height / 25;
		
	}

	@Override
	public void show() {
		
		float screenWidth = 980;
		float screenHeight = 720;
		float gameWidth = 203;
		float gameHeight = screenHeight / (screenWidth / gameWidth);

        time = 0;
        score = 0;
        win = false;
		
        AssetsLoaderRome.music_R.play();
		world = new World(new Vector2(0, -9.81f), true);
		debugRenderer = new Box2DDebugRenderer();
		batch = new SpriteBatch();
	
		

		
		rand=new Random();
		posX= minX + rand.nextInt(maxX - minX + 1);
		posY= minY + rand.nextFloat()*maxX;
		Lanza lan = new Lanza(world, this, posX, posY, 1f, 0.5f);
		lan.setAnimatedSprite(AssetsLoaderRome.animSpriteFlecha);

		lanzas.add(lan);
		
		camera = new OrthographicCamera(gameWidth/10, gameHeight/10);
		
		myHorse = new Horse(world, this, 0, -5.95f, 1f, 2f);
				
		BodyDef bodyDef = new BodyDef();
		FixtureDef fixtureDef = new FixtureDef();
	
		plataforma = new Plataforma(world, this, 5, -3f, 3f, 1f);
		plataforma.setAnimatedSprite(AssetsLoaderRome.animSpritePlataforma);
		
		//Soldado sold;
		/*
		direc= rand.nextBoolean();
		if(direc)
			 sold = new Soldado(world, this, 10, -6.6f, 0.8f, 0.8f, direc);
		else
			 sold = new Soldado(world, this, -10, -6.6f, 0.8f, 0.8f, direc);
		
		sold.setAnimatedSprite(animatedSprite);

			*/
		//soldados.add(sold);
		
		// GROUND
		// body definition
		bodyDef.type = BodyType.StaticBody;
		bodyDef.position.set(0, 0);

		// ground shape
		ChainShape groundShape = new ChainShape();
		
		groundShape.createChain(new Vector2[] {new Vector2(-50, -6), new Vector2(50,-6)});

		// fixture definition
		fixtureDef.shape = groundShape;
		fixtureDef.friction = .5f;
		fixtureDef.restitution = 0;
		fixtureDef.density = 2.5f;

		ground = world.createBody(bodyDef);
		fixtureGround = ground.createFixture(fixtureDef);
		
		Plataforma izquierda = new Plataforma(world, this, camera.position.x-camera.viewportWidth/2-0.5f, -6f, 1f, 15f);
		Plataforma derecha = new Plataforma(world, this, camera.position.x+camera.viewportWidth/2+0.5f, -6f, 1f, 15f);

        createCollisionListener();

		
		groundShape.dispose();
		
	
        prefs = Gdx.app.getPreferences("LittleNibolas");
        
        if (!prefs.contains("ScoreRoma")) {
            prefs.putInteger("ScoreRoma", 0);
        }
        if (!prefs.contains("HighScoreRoma")) {
            prefs.putInteger("HighScoreRoma", 0);
        }
		
		Gdx.input.setInputProcessor(new InputHandlerRome(this,gameWidth/10,gameHeight/10));
		
	}

	@Override
	public void hide() {
		dispose();
		
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	public Horse getHorse(){
		return myHorse;
	}
	
	public void setCamera(OrthographicCamera camera){
		this.camera = camera;
	}
	
	public OrthographicCamera getCamera(){
		return camera;
	}
	
	public World getWorld(){
		return world;
	}
	
	public void restart(){
		muerto=false;
		lanzas.clear();
		show();
	}
	
	public boolean isMuerto(){
		return muerto;
	}

	public AnimatedSprite getSprite(){
		return AssetsLoaderRome.animatedSprite;
	}
	
	   
    public static Preferences getPref(){
    	return prefs;
    }
    public static int getScore() {
	    return score;
	}
    
	public static void setHighScore(int val) {
	    prefs.putInteger("HighScoreRoma", val);
	    prefs.flush();
	}
	public static void setScore(int val) {
	    prefs.putInteger("ScoreRoma", val);
	    prefs.flush();
	}
	
	public static int getHighScore() {
	    return prefs.getInteger("HighScoreRoma");
	}
	
	public void addScore(){
		score+=1;
	}
	
	@Override
	public void dispose() {
		world.dispose();
		debugRenderer.dispose();
	}
	
	
}
