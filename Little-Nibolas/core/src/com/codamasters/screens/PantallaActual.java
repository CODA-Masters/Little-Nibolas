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
	
	private AnimatedSprite animatedSprite;
	private AnimatedSprite staticSprite;
	private AnimatedSprite reversedSprite;

	private float timestep = 1 / 60f;
	private final int VELOCITYITERATIONS = 8, POSITIONITERATIONS = 3;
	
	private Array<Body> tmpBodies = new Array<Body>();
	private Nibolas myNibolas;
	private SecurityCam securityCam;
	
	public PantallaActual(){
		
		initObjects();
		initAssets();
	}
	
	private void initAssets(){
		AssetsLoader.load();
		nibolasAnimation = AssetsLoader.nibolasAnimation;
		nibolasAnimationReversed = AssetsLoader.nibolasAnimationCpy;
		staticNibolas = AssetsLoader.staticNibolas;
		
		animatedSprite = new AnimatedSprite(nibolasAnimation);
		reversedSprite = new AnimatedSprite(nibolasAnimationReversed);
		reversedSprite.flipFrames(true, false);
		staticSprite = new AnimatedSprite(staticNibolas);
	}
	
	public void initObjects(){
		float screenWidth = Gdx.graphics.getWidth();
		float screenHeight = Gdx.graphics.getHeight();
		float gameWidth = 203;
		float gameHeight = screenHeight / (screenWidth / gameWidth);
		runTime = 0;
		
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
		
		// SECURITY CAMS
		securityCam = new SecurityCam(world, 6,0,1,4);
		
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
		
		myNibolas = new Nibolas(world, this, 0, -3.5f, 1f,2f);
		
		Gdx.input.setInputProcessor(new InputHandler(this,gameWidth/10,gameHeight/10));
		createCollisionListener();
	}
	
	private void drawNibolas(){
		if(myNibolas.isMoving() && !myNibolas.trincado()){
			if(myNibolas.isLookingRight()){
				Gdx.app.log("Derecha","");
				animatedSprite.setBounds(myNibolas.getBody().getPosition().x-myNibolas.WIDTH/2,
						myNibolas.getBody().getPosition().y-myNibolas.HEIGHT/2, myNibolas.WIDTH, myNibolas.HEIGHT);
				animatedSprite.setKeepSize(true);
				animatedSprite.draw(batch);
			}
			else{
				Gdx.app.log("Izquierda","");
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
		drawNibolas();
		batch.end();
		
		//shapeRenderer.setProjectionMatrix(camera.combined);
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setColor(255 / 255.0f, 255 / 255.0f, 45 / 255.0f, 1);
		shapeRenderer.circle(10, 10, 5, 11);
		shapeRenderer.end();
		
		myNibolas.update();
		
		securityCam.update();
		
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

                if((securityCam.getFixture() == fixtureA && myNibolas.getFixture()==fixtureB ) || ( securityCam.getFixture() == fixtureB && myNibolas.getFixture()==fixtureA ) ){
                	Gdx.app.log("CHOCAN","");
                	timestep = 0;
                	myNibolas.stop();
                	
                }
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
