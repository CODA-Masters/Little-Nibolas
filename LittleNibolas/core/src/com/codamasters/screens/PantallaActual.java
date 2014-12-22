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
	private static Animation staticCamaraCpy;
	private static Animation guardiaAnimation;
	private static Animation guardiaAnimationCpy;
	private static Animation binAnimation;
	private static Animation staticBin;
	private static Animation staticBall;
	
	private AnimatedSprite animatedSprite;
	private AnimatedSprite staticSprite;
	private AnimatedSprite reversedSprite;
	private AnimatedSprite camaraSprite;
	private AnimatedSprite camaraReversedSprite;
	private AnimatedSprite guardiaSprite;
	private AnimatedSprite guardiaReversedSprite;
	private AnimatedSprite binSprite;
	private AnimatedSprite binStaticSprite;
	private AnimatedSprite ballSprite;
	
	private TextureRegion bg1;
	private TextureRegion bg2;
	private TextureRegion bg3;
	private TextureRegion bg4;

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
	private boolean stop;
	private float fondoY;
	private float groundPos;
	
	public PantallaActual(){
		
		float screenWidth = 980;
		float screenHeight = 720;
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
		stop = false;
		
		camera = new OrthographicCamera(gameWidth/15, gameHeight/15);
		
		groundPos = -2;
		
		initObjects();
		initAssets();
		
		Gdx.input.setInputProcessor(new InputHandler(this,gameWidth/10,gameHeight/10));
		createCollisionListener();
	}
	
	private void initAssets(){
		AssetsLoader.music_E1.play();
		nibolasAnimation = AssetsLoader.nibolasAnimation;
		nibolasAnimationReversed = AssetsLoader.nibolasAnimationCpy;
		staticNibolas = AssetsLoader.staticNibolas;
		staticCamara = AssetsLoader.staticCamara;
		staticCamaraCpy = AssetsLoader.staticCamaraCpy;
		guardiaAnimation = AssetsLoader.guardiaAnimation;
		guardiaAnimationCpy = AssetsLoader.guardiaAnimationCpy;
		binAnimation = AssetsLoader.binAnimation;
		staticBin = AssetsLoader.staticBin;
		staticBall = AssetsLoader.staticBall;
		bg1 = AssetsLoader.bg1;
		bg2 = AssetsLoader.bg2;
		bg3 = AssetsLoader.bg3;
		bg4 = AssetsLoader.bg4;
		
		animatedSprite = new AnimatedSprite(nibolasAnimation);
		reversedSprite = new AnimatedSprite(nibolasAnimationReversed);
		reversedSprite.flipFrames(true, false);
		staticSprite = new AnimatedSprite(staticNibolas);
		camaraSprite = new AnimatedSprite(staticCamara);
		camaraReversedSprite = new AnimatedSprite(staticCamaraCpy);
		camaraReversedSprite.flipFrames(true, false);
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
		
		// GROUND
		// body definition
		bodyDef.type = BodyType.StaticBody;
		bodyDef.position.set(0, groundPos);

		// ground shape
		ChainShape groundShape = new ChainShape();
		
		groundShape.createChain(new Vector2[] {new Vector2(-500, groundPos), new Vector2(500,groundPos)});

		// fixture definition
		fixtureDef.shape = groundShape;
		fixtureDef.friction = .5f;
		fixtureDef.restitution = 0;
		fixtureDef.density = 2.5f;

		Body ground = world.createBody(bodyDef);
		ground.createFixture(fixtureDef);
		
		groundShape.dispose();
		
		new RigidBlock(world,-4, groundPos-1.75f,.25f,.5f);
		
		myNibolas = new Nibolas(world, this, -2, groundPos-1, .6f,2f);
		
		// DISEÑO DEL MAPA
		
		float yBin = groundPos-1;
		float yGuard = groundPos-1.25f;
		float yCam = groundPos+2;
		float yBallsTrap = groundPos+10;
		
		bin = new Bin(world, 4, yBin, 1, 2);
		bins.add(bin);
		
		guard = new Guard(world, 8, yGuard,1f,0.5f);
		guards.add(guard);
		
		bin = new Bin(world, 14, yBin, 1, 2);
		bins.add(bin);
		
		securityCam = new SecurityCam(world, 14, yCam,0.5f,4);
		securityCams.add(securityCam);
		
		trap =  new BallsTrap(world, 28, yBallsTrap,6,1);
		ballsTraps.add(trap);
		
		bin = new Bin(world, 35,yBin, 1, 2);
		bins.add(bin);
		
		guard = new Guard(world, 38, yGuard,1f,0.5f);
		guards.add(guard);
		
		guard = new Guard(world, 40, yGuard,1f,0.5f);
		guards.add(guard);
		
		securityCam = new SecurityCam(world, 45, yCam,0.5f,4);
		securityCams.add(securityCam);
		
		bin = new Bin(world, 46, yBin, 1, 2);
		bins.add(bin);
		
		securityCam = new SecurityCam(world, 48, yCam,0.5f,4);
		securityCams.add(securityCam);
		
		bin = new Bin(world, 52, yBin, 1, 2);					
		bins.add(bin);
		
		guard = new Guard(world, 56, yGuard,1f,0.5f);
		guards.add(guard);
		
		trap =  new BallsTrap(world, 60, yBallsTrap,6,1);
		ballsTraps.add(trap);
		
		bin = new Bin(world, 67, yBin, 1, 2);
		bins.add(bin);
		
		guard = new Guard(world, 68, yGuard,1f,0.5f);
		guards.add(guard);
		
		securityCam = new SecurityCam(world, 75, yCam,0.5f,4);
		securityCams.add(securityCam);
		
		bin = new Bin(world, 74, yBin, 1, 2);
		bins.add(bin);
		
		
		bin = new Bin(world, 79, yBin, 1, 2);
		bins.add(bin);
		
		securityCam = new SecurityCam(world, 79, yCam,0.5f,4);
		securityCams.add(securityCam);
		
		
		guard = new Guard(world, 82, yGuard,1f,0.5f);
		guards.add(guard);
		
		guard = new Guard(world, 84, yGuard,1f,0.5f);
		guards.add(guard);
		
		trap =  new BallsTrap(world, 90, yBallsTrap,6,1);
		ballsTraps.add(trap);
		
		securityCam = new SecurityCam(world, 99, yCam,0.5f,4);
		securityCams.add(securityCam);
		
		bin = new Bin(world, 100, yBin, 1, 2);
		bins.add(bin);
		
		trap =  new BallsTrap(world, 120, yBallsTrap,6,1);
		ballsTraps.add(trap);
		
		bin = new Bin(world, 127, yBin, 1, 2);
		bins.add(bin);
		
		guard = new Guard(world, 128, yGuard,1f,0.5f);
		guards.add(guard);
		
		securityCam = new SecurityCam(world, 133, yCam,0.5f,4);
		securityCams.add(securityCam);
		
		bin = new Bin(world, 138, yBin, 1, 2);
		bins.add(bin);
		
		securityCam = new SecurityCam(world, 141, yCam,0.5f,4);
		securityCams.add(securityCam);
		
		bin = new Bin(world, 142, yBin, 1, 2);
		bins.add(bin);
		
		securityCam = new SecurityCam(world, 143, yCam,0.5f,4);
		securityCams.add(securityCam);
		
		trap =  new BallsTrap(world, 155, yBallsTrap,6,1);
		ballsTraps.add(trap);
		
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
		else if(myNibolas.isVisible()){
			
			staticSprite.setBounds(myNibolas.getBody().getPosition().x-myNibolas.WIDTH/1.4f,
					myNibolas.getBody().getPosition().y-myNibolas.HEIGHT/2, myNibolas.WIDTH*1.4f, myNibolas.HEIGHT);
			staticSprite.setKeepSize(true);
			staticSprite.draw(batch);
		}
	}
	
	private void drawSecurityCam(){
		for(SecurityCam securityCam : securityCams){
			float angle = (float)(securityCam.getBody().getAngle()*180/Math.PI);
			if(angle < 0){
				camaraSprite.setBounds(securityCam.getBody().getPosition().x-securityCam.WIDTH+0.22f,
						securityCam.getBody().getPosition().y-securityCam.HEIGHT, securityCam.WIDTH, securityCam.HEIGHT);
				camaraSprite.setKeepSize(true);
				camaraSprite.setOrigin( camaraSprite.getWidth()-0.22f, camaraSprite.getHeight());
		
				camaraSprite.setRotation(angle);
				
				camaraSprite.draw(batch);
			}
			else{
				camaraReversedSprite.setBounds(securityCam.getBody().getPosition().x-securityCam.WIDTH+0.22f,
						securityCam.getBody().getPosition().y-securityCam.HEIGHT, securityCam.WIDTH, securityCam.HEIGHT);
				camaraReversedSprite.setKeepSize(true);
				camaraReversedSprite.setOrigin( camaraReversedSprite.getWidth()-0.22f, camaraReversedSprite.getHeight());
		
				camaraReversedSprite.setRotation(angle);
				
				camaraReversedSprite.draw(batch);
			}
		}
	}
	
	private void drawGuard(){
		for(Guard guard : guards){
			if (guard.isLookingRight()){
				guardiaSprite.setBounds(guard.getBody().getPosition().x-guard.WIDTH*1.5f+0.1f,
						guard.getBody().getPosition().y-guard.HEIGHT*1.5f, guard.WIDTH*2f, guard.HEIGHT*4);
				guardiaSprite.setKeepSize(true);
				guardiaSprite.draw(batch);
			}
			else{
				guardiaReversedSprite.setBounds(guard.getBody().getPosition().x-guard.WIDTH/2-0.1f,
						guard.getBody().getPosition().y-guard.HEIGHT*1.5f, guard.WIDTH*2f, guard.HEIGHT*4);
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
			myNibolas = new Nibolas(world, this, x+1.05f, y, .6f,2f);
		}
		// Moverse a la izquierda
		else if (target.x < myNibolas.getBody().getPosition().x){
			myNibolas.destroy();
			myNibolas = new Nibolas(world, this, x-1.05f, y, .6f,2f);
		}
		
		float screenWidth = Gdx.graphics.getWidth();
		float screenHeight = Gdx.graphics.getHeight();
		float gameWidth = 203;
		float gameHeight = screenHeight / (screenWidth / gameWidth);
		Gdx.input.setInputProcessor(new InputHandler(this,gameWidth/15,gameHeight/15));
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
		

		fondoY = camera.position.y-camera.viewportHeight/2;

		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		
		
		// DIBUJAR FONDO
		float inicio = -10.5f;
		float dist = 17;
		batch.draw(bg1, inicio, fondoY, camera.viewportWidth+4f, camera.viewportHeight);
		inicio+=dist;
		batch.draw(bg2, inicio, fondoY, camera.viewportWidth+4f, camera.viewportHeight);
		inicio+=dist;
		batch.draw(bg3, inicio, fondoY, camera.viewportWidth+4f, camera.viewportHeight);
		inicio+=dist;
		batch.draw(bg1, inicio, fondoY, camera.viewportWidth+4f, camera.viewportHeight);
		inicio+=dist;
		batch.draw(bg2, inicio, fondoY, camera.viewportWidth+4f, camera.viewportHeight);
		inicio+=dist;
		batch.draw(bg3, inicio, fondoY, camera.viewportWidth+4f, camera.viewportHeight);
		inicio+=dist;
		batch.draw(bg1, inicio, fondoY, camera.viewportWidth+4f, camera.viewportHeight);
		inicio+=dist;
		batch.draw(bg2, inicio, fondoY, camera.viewportWidth+4f, camera.viewportHeight);
		inicio+=dist;
		batch.draw(bg3, inicio, fondoY, camera.viewportWidth+4f, camera.viewportHeight);
		inicio+=dist;
		batch.draw(bg1, inicio, fondoY, camera.viewportWidth+4f, camera.viewportHeight);
		inicio+=dist;
		batch.draw(bg4, inicio, fondoY, camera.viewportWidth+4f, camera.viewportHeight);
		inicio+=dist;
		batch.draw(bg2, inicio, fondoY, camera.viewportWidth+4f, camera.viewportHeight);
		
		
		if(hide){
			for(int i = 0; i < bins.size; i++){
				if(myNibolas.getBody().getPosition().x - 2 < bins.get(i).getBody().getPosition().x  &&
						myNibolas.getBody().getPosition().x + 2 > bins.get(i).getBody().getPosition().x){
					float x = bins.get(i).getBody().getPosition().x;
					float y = bins.get(i).getBody().getPosition().y;
					bins.get(i).destroy();
					lastBin = i;
					myNibolas.destroy();
					myNibolas = new Nibolas(world, this, x, y, .6f,2f);
					myNibolas.becomeInvisible();
					
					Gdx.input.setInputProcessor(new InputHandler(this,gameWidth/15,gameHeight/15));
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
					if(ball.getBody().getPosition().x < camera.position.x-camera.viewportWidth/2 ||
							(ball.getBody().getPosition().x > camera.position.x+camera.viewportWidth/2 &&
									ball.getBody().getPosition().y < camera.position.y)){
						ball.destroy();
						ball = new Ball(world, 0,-100);
					}
				}
			}
		}
		
		//debugRenderer.render(world, camera.combined);


		if(stop) stop();
		
		// CONDICIÓN DE ACABAR NIVEL
		
		if(myNibolas.getBody().getPosition().x > 173 && !stop){
			AssetsLoader.music_E1.stop();
			((Game)Gdx.app.getApplicationListener()).setScreen(new FinPrimerNivel1());
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

                if(!hide){
                	for(SecurityCam securityCam : securityCams){
		                if((securityCam.getFixture() == fixtureA && myNibolas.getFixture()==fixtureB ) || ( securityCam.getFixture() == fixtureB && myNibolas.getFixture()==fixtureA ) ){
		                	//Gdx.app.log("CHOCAN","");
		                	stop = true;
		                }
                	}
                	for(Guard guard : guards){
		                if((guard.getFixture() == fixtureA && myNibolas.getFixture()==fixtureB ) || ( guard.getFixture() == fixtureB && myNibolas.getFixture()==fixtureA ) ){
		                	stop = true;
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
				                	stop =  true;
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
		AssetsLoader.music_E1.stop();
		timestep = 0;
		myNibolas.stop();
		guardiaSprite.pause(); // esto no funciona
		AssetsLoader.dispose();
		AssetsLoader.load();
		((Game)Gdx.app.getApplicationListener()).setScreen(new GameOver1());
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
