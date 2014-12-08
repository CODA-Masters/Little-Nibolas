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
import com.codamasters.gameobjects.Ball;
import com.codamasters.gameobjects.BallsTrap;
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
	private static Animation binAnimation;
	private static Animation staticBin;
	private static Animation staticBall;
	
	private AnimatedSprite animatedSprite;
	private AnimatedSprite staticSprite;
	private AnimatedSprite reversedSprite;
	private AnimatedSprite camaraSprite;
	private AnimatedSprite guardiaSprite;
	private AnimatedSprite guardiaReversedSprite;
	private AnimatedSprite binSprite;
	private AnimatedSprite binStaticSprite;
	private AnimatedSprite ballSprite;
	
	private TextureRegion bg;

	private float timestep = 1 / 60f;
	private final int VELOCITYITERATIONS = 8, POSITIONITERATIONS = 3;
	
	private Array<Body> tmpBodies = new Array<Body>();
	private Array<Bin> bins= new Array();
	private Array<Guard> guards= new Array();
	private Array<SecurityCam> securityCams= new Array();
	private Array<BallsTrap> ballsTraps = new Array();
	private Nibolas myNibolas;
	private SecurityCam securityCam;
	private Guard guard;
	private Bin bin;
	private BallsTrap trap;
	private boolean hide;
	private boolean insideBin;
	private PantallaActual pantalla;
	private boolean movAng;
	private int lastBin;
	
	public PantallaActual(){
		
		float screenWidth = Gdx.graphics.getWidth();
		float screenHeight = Gdx.graphics.getHeight();
		float gameWidth = 203;
		float gameHeight = screenHeight / (screenWidth / gameWidth);
		runTime = 0;
		hide = false;
		insideBin = false;
		pantalla = this;
		world = new World(new Vector2(0, -9.81f), true);
		debugRenderer = new Box2DDebugRenderer();
		shapeRenderer = new ShapeRenderer();
		batch = new SpriteBatch();
		lastBin = -1;
		
		camera = new OrthographicCamera(gameWidth/10, gameHeight/10);
		
		initObjects();
		initAssets();
		
		Gdx.input.setInputProcessor(new InputHandler(this,gameWidth/10,gameHeight/10));
		createCollisionListener();
	}
	
	private void initAssets(){
		AssetsLoader.load();
		nibolasAnimation = AssetsLoader.nibolasAnimation;
		nibolasAnimationReversed = AssetsLoader.nibolasAnimationCpy;
		staticNibolas = AssetsLoader.staticNibolas;
		staticCamara = AssetsLoader.staticCamara;
		guardiaAnimation = AssetsLoader.guardiaAnimation;
		guardiaAnimationCpy = AssetsLoader.guardiaAnimationCpy;
		binAnimation = AssetsLoader.binAnimation;
		staticBin = AssetsLoader.staticBin;
		staticBall = AssetsLoader.staticBall;
		bg = AssetsLoader.bg;
		
		animatedSprite = new AnimatedSprite(nibolasAnimation);
		reversedSprite = new AnimatedSprite(nibolasAnimationReversed);
		reversedSprite.flipFrames(true, false);
		staticSprite = new AnimatedSprite(staticNibolas);
		camaraSprite = new AnimatedSprite(staticCamara);
		guardiaSprite = new AnimatedSprite(guardiaAnimation);
		guardiaReversedSprite = new AnimatedSprite(guardiaAnimationCpy);
		guardiaReversedSprite.flipFrames(true, false);
		binSprite = new AnimatedSprite(binAnimation);
		binStaticSprite = new AnimatedSprite(staticBin);
		ballSprite = new AnimatedSprite(staticBall);
	}
	
	public void initObjects(){
		// Definir objetos del mapa
		
		BodyDef bodyDef = new BodyDef();
		FixtureDef fixtureDef = new FixtureDef();
		
		
		//BLOCKS
		new RigidBlock(world,-9,-3.75f,.25f,.5f);
		
		// GROUND
		// body definition
		bodyDef.type = BodyType.StaticBody;
		bodyDef.position.set(0, -2);

		// ground shape
		ChainShape groundShape = new ChainShape();
		
		groundShape.createChain(new Vector2[] {new Vector2(-500, -2), new Vector2(500,-2)});

		// fixture definition
		fixtureDef.shape = groundShape;
		fixtureDef.friction = .5f;
		fixtureDef.restitution = 0;
		fixtureDef.density = 2.5f;

		Body ground = world.createBody(bodyDef);
		ground.createFixture(fixtureDef);
		
		groundShape.dispose();
		
		myNibolas = new Nibolas(world, this, -2, -3f, .8f,2f);
		
		// DISEÑO DEL MAPA
		
		trap =  new BallsTrap(world, 0,8,6,1);
		ballsTraps.add(trap);
		
		bin = new Bin(world, 4,-3f, 1, 2);
		bins.add(bin);
		
		guard = new Guard(world, 8,-3.25f,1f,0.5f);
		guards.add(guard);
		
		bin = new Bin(world, 14,-3f, 1, 2);
		bins.add(bin);
		
		securityCam = new SecurityCam(world, 15,0,1,4);
		securityCams.add(securityCam);
		
		trap =  new BallsTrap(world, 28,8,6,1);
		ballsTraps.add(trap);
		
		bin = new Bin(world, 35,-3f, 1, 2);
		bins.add(bin);
	}
	
	private void drawNibolas(){
		if(myNibolas.isMoving() && !myNibolas.trincado()){
			if(myNibolas.isLookingRight()){		
				animatedSprite.setBounds(myNibolas.getBody().getPosition().x-myNibolas.WIDTH/1.4f,
						myNibolas.getBody().getPosition().y-myNibolas.HEIGHT/2, myNibolas.WIDTH*1.4f, myNibolas.HEIGHT);
				animatedSprite.setKeepSize(true);
				animatedSprite.draw(batch);
			}
			else{
				reversedSprite.setBounds(myNibolas.getBody().getPosition().x-myNibolas.WIDTH/1.4f,
						myNibolas.getBody().getPosition().y-myNibolas.HEIGHT/2, myNibolas.WIDTH*1.4f, myNibolas.HEIGHT);
				reversedSprite.setKeepSize(true);
				reversedSprite.draw(batch);
			}
		}
		else{
			
			staticSprite.setBounds(myNibolas.getBody().getPosition().x-myNibolas.WIDTH/1.4f,
					myNibolas.getBody().getPosition().y-myNibolas.HEIGHT/2, myNibolas.WIDTH*1.4f, myNibolas.HEIGHT);
			staticSprite.setKeepSize(true);
			staticSprite.draw(batch);
		}
	}
	
	private void drawSecurityCam(){
		for(SecurityCam securityCam : securityCams){
			camaraSprite.setBounds(securityCam.getBody().getPosition().x-securityCam.WIDTH/2,
					securityCam.getBody().getPosition().y-securityCam.HEIGHT, securityCam.WIDTH, securityCam.HEIGHT);
			camaraSprite.setKeepSize(true);
			camaraSprite.setOrigin(camaraSprite.getWidth(), camaraSprite.getHeight());
	
			camaraSprite.setRotation((float)(securityCam.getBody().getAngle()*180/Math.PI));
			
			camaraSprite.draw(batch);
		}
	}
	
	private void drawGuard(){
		for(Guard guard : guards){
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
	}
	
	private void drawBin(){
		for(Bin bin : bins){
			if(!bin.isNibolasInside()){
				binStaticSprite.setBounds(bin.getBody().getPosition().x-bin.WIDTH/2,
						bin.getBody().getPosition().y-bin.HEIGHT/2, bin.WIDTH, bin.HEIGHT);
				binStaticSprite.setKeepSize(true);
				binStaticSprite.draw(batch);
			}
			else{
				binSprite.setBounds(bin.getBody().getPosition().x-bin.WIDTH/2,
						bin.getBody().getPosition().y-bin.HEIGHT/2, bin.WIDTH, bin.HEIGHT);
				binSprite.setKeepSize(true);
				binSprite.draw(batch);
			}
		}
	}
	
	private void drawBall(){
		for(BallsTrap trap : ballsTraps){
			if(trap.isActivated()){
				Array<Ball> balls = trap.getBalls();
				for(Ball ball : balls){
					ballSprite.setBounds(ball.getBody().getPosition().x-ball.RADIUS,
							ball.getBody().getPosition().y-ball.RADIUS, ball.RADIUS*2, ball.RADIUS*2);
					ballSprite.setKeepSize(true);
					ballSprite.draw(batch);
				}
			}
		}
	}
	
	public void salirDePapelera(float screenX, float screenY){
		
		Vector3 target = new Vector3();
		OrthographicCamera cam = pantalla.getCamera();
		cam.unproject(target.set(screenX,screenY,0));
		pantalla.setCamera(cam);
		
		bin = new Bin(world, myNibolas.getBody().getPosition().x,myNibolas.getBody().getPosition().y, 1, 2);
		bin.setNibolasInside(false);
		bins.set(lastBin, bin);
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
		Gdx.gl.glClearColor(90 / 255f, 89 / 255f, 94 / 255f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		world.step(timestep, VELOCITYITERATIONS, POSITIONITERATIONS);
		if(myNibolas.getBody().getPosition().x > 0)
			camera.position.x = myNibolas.getBody().getPosition().x;
			
		camera.update();
		
		float screenWidth = Gdx.graphics.getWidth();
		float screenHeight = Gdx.graphics.getHeight();
		float gameWidth = 203;
		float gameHeight = screenHeight / (screenWidth / gameWidth);
		
		for(BallsTrap trap : ballsTraps){
			if(myNibolas.getBody().getPosition().x > trap.getBody().getPosition().x-6)
				trap.activate();
			}
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		
		// DIBUJAR FONDO
		batch.draw(bg, -10.5f, -5.7f, camera.viewportWidth+4f, camera.viewportHeight+4f);
		batch.draw(bg, 13.5f, -5.7f, camera.viewportWidth+4f, camera.viewportHeight+4f);
		batch.draw(bg, 37.5f, -5.7f, camera.viewportWidth+4f, camera.viewportHeight+4f);
		batch.draw(bg, 61.5f, -5.7f, camera.viewportWidth+4f, camera.viewportHeight+4f);
		
		if(hide){
			for(int i = 0; i < bins.size; i++){
				if(myNibolas.getBody().getPosition().x - 2 < bins.get(i).getBody().getPosition().x  &&
						myNibolas.getBody().getPosition().x + 2 > bins.get(i).getBody().getPosition().x){
					float x = bins.get(i).getBody().getPosition().x;
					float y = bins.get(i).getBody().getPosition().y;
					bins.get(i).destroy();
					lastBin = i;
					myNibolas.destroy();
					myNibolas = new Nibolas(world, this, x, y, 1f,2f);
					myNibolas.becomeInvisible();
					
					Gdx.input.setInputProcessor(new InputHandler(this,gameWidth/10,gameHeight/10));
					hide = false;
					bins.get(i).setNibolasInside(true);
					break;
				}
			}
		}
		drawNibolas();
		drawSecurityCam();
		drawBin();
		drawGuard();
		drawBall();
		
		batch.end();
		
		myNibolas.update();
		for(SecurityCam securityCam : securityCams)
			securityCam.update();
		for(Guard guard : guards){
			guard.update();
		}
		
		for(BallsTrap trap : ballsTraps){
			if(trap.isActivated()){
				Array<Ball> balls = trap.getBalls();
				for(Ball ball : balls){
					if(ball.getBody().getPosition().x < camera.position.x-camera.viewportWidth/2){
						ball.destroy();
						ball = new Ball(world, 0,-100);
					}
				}
			}
		}
		
		//debugRenderer.render(world, camera.combined);
		
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
                	for(SecurityCam securityCam : securityCams){
		                if((securityCam.getFixture() == fixtureA && myNibolas.getFixture()==fixtureB ) || ( securityCam.getFixture() == fixtureB && myNibolas.getFixture()==fixtureA ) ){
		                	Gdx.app.log("CHOCAN","");
		                	stop();
		                }
                	}
                	for(Guard guard : guards){
		                if((guard.getFixture() == fixtureA && myNibolas.getFixture()==fixtureB ) || ( guard.getFixture() == fixtureB && myNibolas.getFixture()==fixtureA ) ){
		                	stop();
		                }
                	}
	                
                	for(Bin bin : bins){
		                if((bin.getFixture() == fixtureA && myNibolas.getFixture()==fixtureB ) || ( bin.getFixture() == fixtureB && myNibolas.getFixture()==fixtureA ) ){
		                	hide = true;
		                }
                	}
                	
                	for(BallsTrap trap : ballsTraps){
                		if(trap.isActivated()){
                			Array<Ball> balls = trap.getBalls();
                			for(Ball ball : balls){
				                if((ball.getFixture() == fixtureA && myNibolas.getFixture()==fixtureB ) || ( ball.getFixture() == fixtureB && myNibolas.getFixture()==fixtureA ) ){
				                	stop();
				                }
                			}
                		}
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
	
	public void stop(){
		timestep = 0;
		myNibolas.stop();
		guardiaSprite.pause(); // esto no funciona
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
