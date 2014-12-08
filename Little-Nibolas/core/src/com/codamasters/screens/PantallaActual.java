package com.codamasters.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.utils.Array;
import com.codamasters.LNHelpers.AnimatedSprite;
import com.codamasters.LNHelpers.AssetsLoader;
import com.codamasters.LNHelpers.InputHandler;
import com.codamasters.gameobjects.Bin;
import com.codamasters.gameobjects.Guard;
import com.codamasters.gameobjects.Nibolas;
import com.codamasters.gameobjects.RigidBlock;
import com.codamasters.gameobjects.SecurityCam;

public class PantallaActual implements Screen{
	
	private World world;
	private Box2DDebugRenderer debugRenderer;
	private SpriteBatch batch;
	private ShapeRenderer shapeRenderer;
	private OrthographicCamera camera;
	private float runTime;
	
	private static Animation nibolasAnimation;
	private static Animation nibolasAnimationReversed;
	private static Animation staticNibolas;
	private static Animation staticCamara;
	private static Animation guardiaAnimation;
	private static Animation guardiaAnimationCpy;
	
	private AnimatedSprite animatedSprite;
	private AnimatedSprite staticSprite;
	private AnimatedSprite reversedSprite;
	private AnimatedSprite camaraSprite;
	private AnimatedSprite guardiaSprite;
	private AnimatedSprite guardiaReversedSprite;

	private float timestep = 1 / 60f;
	private final int VELOCITYITERATIONS = 8, POSITIONITERATIONS = 3;
	
	private Array<Body> tmpBodies = new Array<Body>();
	private Nibolas myNibolas;
	private SecurityCam securityCam;
	private Guard guard;
	private Bin bin;
	private boolean hide;
	private PantallaActual pantalla;
	private boolean movAng;
	
	public PantallaActual(){
		
		initObjects();
		initAssets();
		pantalla = this;
		movAng=true;
	}
	
	private void initAssets(){
		AssetsLoader.load();
		nibolasAnimation = AssetsLoader.nibolasAnimation;
		nibolasAnimationReversed = AssetsLoader.nibolasAnimationCpy;
		staticNibolas = AssetsLoader.staticNibolas;
		staticCamara = AssetsLoader.staticCamara;
		guardiaAnimation = AssetsLoader.guardiaAnimation;
		guardiaAnimationCpy = AssetsLoader.guardiaAnimationCpy;
		
		animatedSprite = new AnimatedSprite(nibolasAnimation);
		reversedSprite = new AnimatedSprite(nibolasAnimationReversed);
		reversedSprite.flipFrames(true, false);
		staticSprite = new AnimatedSprite(staticNibolas);
		camaraSprite = new AnimatedSprite(staticCamara);
		guardiaSprite = new AnimatedSprite(guardiaAnimation);
		guardiaReversedSprite = new AnimatedSprite(guardiaAnimationCpy);
		guardiaReversedSprite.flipFrames(true, false);
	}
	
	public void initObjects(){
		float screenWidth = Gdx.graphics.getWidth();
		float screenHeight = Gdx.graphics.getHeight();
		float gameWidth = 203;
		float gameHeight = screenHeight / (screenWidth / gameWidth);
		runTime = 0;
		hide = false;
		
		world = new World(new Vector2(0, -9.81f), true);
		debugRenderer = new Box2DDebugRenderer();
		shapeRenderer = new ShapeRenderer();
		batch = new SpriteBatch();
		
		camera = new OrthographicCamera(gameWidth/10, gameHeight/10);
		
		BodyDef bodyDef = new BodyDef();
		FixtureDef fixtureDef = new FixtureDef();
		
		/*
		//BALL
		// body definition
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(0, 2);

		// ball shape
		CircleShape ballShape = new CircleShape();
		ballShape.setRadius(.5f);
	
		// fixture definition
		fixtureDef.shape = ballShape;
		fixtureDef.friction = .25f;
		fixtureDef.restitution = 0.75f;
		fixtureDef.density = 2.5f;
		
		world.createBody(bodyDef).createFixture(fixtureDef);
		ballShape.dispose();*/
		
		//BLOCKS
		new RigidBlock(world,-9,-3.75f,.25f,.5f);
		
		// GROUND
		// body definition
		bodyDef.type = BodyType.StaticBody;
		bodyDef.position.set(0, -2);

		// ground shape
		ChainShape groundShape = new ChainShape();
		
		groundShape.createChain(new Vector2[] {new Vector2(-50, -2), new Vector2(50,-2)});

		// fixture definition
		fixtureDef.shape = groundShape;
		fixtureDef.friction = .5f;
		fixtureDef.restitution = 0;
		fixtureDef.density = 2.5f;

		Body ground = world.createBody(bodyDef);
		ground.createFixture(fixtureDef);
		
		groundShape.dispose();
		
		myNibolas = new Nibolas(world, this, -2, -3f, 1f,2f);
		guard = new Guard(world, 8,-3.25f,1f,0.5f);
		securityCam = new SecurityCam(world, 12,0,1,4);
		bin = new Bin(world, 4,-3f, 1, 2);
		
		Gdx.input.setInputProcessor(new InputHandler(this,gameWidth/10,gameHeight/10));
		createCollisionListener();
	}
	
	private void drawNibolas(){
		if(myNibolas.isMoving() && !myNibolas.trincado()){
			if(myNibolas.isLookingRight()){		
				animatedSprite.setBounds(myNibolas.getBody().getPosition().x-myNibolas.WIDTH/2,
						myNibolas.getBody().getPosition().y-myNibolas.HEIGHT/2, myNibolas.WIDTH, myNibolas.HEIGHT);
				animatedSprite.setKeepSize(true);
				animatedSprite.draw(batch);
			}
			else{
				reversedSprite.setBounds(myNibolas.getBody().getPosition().x-myNibolas.WIDTH/2,
						myNibolas.getBody().getPosition().y-myNibolas.HEIGHT/2, myNibolas.WIDTH, myNibolas.HEIGHT);
				reversedSprite.setKeepSize(true);
				reversedSprite.draw(batch);
			}
		}
		else{
			
			staticSprite.setBounds(myNibolas.getBody().getPosition().x-myNibolas.WIDTH/2,
					myNibolas.getBody().getPosition().y-myNibolas.HEIGHT/2, myNibolas.WIDTH, myNibolas.HEIGHT);
			staticSprite.setKeepSize(true);
			staticSprite.draw(batch);
		}
	}
	
	private void drawSecurityCam(){
		camaraSprite.setBounds(securityCam.getBody().getPosition().x-securityCam.WIDTH/2,
				securityCam.getBody().getPosition().y-securityCam.HEIGHT, securityCam.WIDTH, securityCam.HEIGHT);
		camaraSprite.setKeepSize(true);
		camaraSprite.setOrigin(camaraSprite.getWidth(), camaraSprite.getHeight());

		camaraSprite.setRotation((float)(securityCam.getBody().getAngle()*180/Math.PI));
		
		camaraSprite.draw(batch);
	}
	
	private void drawGuard(){
		if (guard.isLookingRight()){
			guardiaSprite.setBounds(guard.getBody().getPosition().x-guard.WIDTH*2+0.1f,
					guard.getBody().getPosition().y-guard.HEIGHT*1.5f, guard.WIDTH*2.5f, guard.HEIGHT*4);
			guardiaSprite.setKeepSize(true);
			guardiaSprite.draw(batch);
		}
		else{
			guardiaReversedSprite.setBounds(guard.getBody().getPosition().x-guard.WIDTH/2-0.1f,
					guard.getBody().getPosition().y-guard.HEIGHT*1.5f, guard.WIDTH*2.5f, guard.HEIGHT*4);
			guardiaReversedSprite.setKeepSize(true);
			guardiaReversedSprite.draw(batch);
		}
	}
	
	public void salirDePapelera(float screenX, float screenY){
		
		Vector3 target = new Vector3();
		OrthographicCamera cam = pantalla.getCamera();
		cam.unproject(target.set(screenX,screenY,0));
		pantalla.setCamera(cam);
		
		bin = new Bin(world, 4,-3f, 1, 2);
		float x = bin.getBody().getPosition().x;
		float y = bin.getBody().getPosition().y;
		
		// Moverse a la derecha
		if(target.x > myNibolas.getBody().getPosition().x){
			myNibolas.destroy();
			myNibolas = new Nibolas(world, this, x+1.05f, y, 1f,2f);
		}
		// Moverse a la izquierda
		else if (target.x < myNibolas.getBody().getPosition().x){
			myNibolas.destroy();
			myNibolas = new Nibolas(world, this, x-1.05f, y, 1f,2f);
		}
		
		float screenWidth = Gdx.graphics.getWidth();
		float screenHeight = Gdx.graphics.getHeight();
		float gameWidth = 203;
		float gameHeight = screenHeight / (screenWidth / gameWidth);
		Gdx.input.setInputProcessor(new InputHandler(this,gameWidth/10,gameHeight/10));
		hide = false;
	}

	@Override
	public void render(float delta) {
		runTime += delta;
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		world.step(timestep, VELOCITYITERATIONS, POSITIONITERATIONS);
		if(myNibolas.getBody().getPosition().x > 0)
			camera.position.x = myNibolas.getBody().getPosition().x;
			
		camera.update();
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		if(hide){
			float x = bin.getBody().getPosition().x;
			float y = bin.getBody().getPosition().y;
			bin.destroy();
			myNibolas.destroy();
			myNibolas = new Nibolas(world, this, x, y, 1f,2f);
			myNibolas.becomeInvisible();
			float screenWidth = Gdx.graphics.getWidth();
			float screenHeight = Gdx.graphics.getHeight();
			float gameWidth = 203;
			float gameHeight = screenHeight / (screenWidth / gameWidth);
			Gdx.input.setInputProcessor(new InputHandler(this,gameWidth/10,gameHeight/10));
			hide = false;
		}
		drawNibolas();
		drawSecurityCam();
		drawGuard();
		batch.end();
		
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setColor(255 / 255.0f, 255 / 255.0f, 45 / 255.0f, 1);
		shapeRenderer.circle(10, 10, 5, 11);
		shapeRenderer.end();
		
		myNibolas.update();
		securityCam.update();
		guard.update(8,2);
		
		debugRenderer.render(world, camera.combined);
		
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

                if(!hide){
	                if((securityCam.getFixture() == fixtureA && myNibolas.getFixture()==fixtureB ) || ( securityCam.getFixture() == fixtureB && myNibolas.getFixture()==fixtureA ) ){
	                	Gdx.app.log("CHOCAN","");
	                	timestep = 0;
	                	myNibolas.stop();
	                }
	                if((guard.getFixture() == fixtureA && myNibolas.getFixture()==fixtureB ) || ( guard.getFixture() == fixtureB && myNibolas.getFixture()==fixtureA ) ){
	                	timestep = 0;
	                	myNibolas.stop();
	                }
	                
	               
	                if((bin.getFixture() == fixtureA && myNibolas.getFixture()==fixtureB ) || ( bin.getFixture() == fixtureB && myNibolas.getFixture()==fixtureA ) ){
	                	hide = true;
	                }
                }
                
            }

			@Override
			public void endContact(Contact contact) {
			}
        });
	}

	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void show() {
		
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

	@Override
	public void dispose() {
		world.dispose();
		debugRenderer.dispose();
	}
	
	public Nibolas getNibolas(){
		return myNibolas;
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


}
