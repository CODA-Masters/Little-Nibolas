package com.codamasters.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.utils.Array;
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

	private final float TIMESTEP = 1 / 60f;
	private final int VELOCITYITERATIONS = 8, POSITIONITERATIONS = 3;
	
	private Array<Body> tmpBodies = new Array<Body>();
	private Nibolas myNibolas;
	private SecurityCam securityCam;

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		world.step(TIMESTEP, VELOCITYITERATIONS, POSITIONITERATIONS);
		
		if(myNibolas.getBody().getPosition().x > 0)
			camera.position.x = myNibolas.getBody().getPosition().x;
			
		camera.update();
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		world.getBodies(tmpBodies);
		for(Body body : tmpBodies)
			if(body.getUserData() instanceof Sprite) {
				Sprite sprite = (Sprite) body.getUserData();
				sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2, body.getPosition().y - sprite.getHeight() / 2);
				sprite.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
				sprite.draw(batch);
			}
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

	@Override
	public void resize(int width, int height) {
		//camera.viewportWidth = width / 25;
		//camera.viewportHeight = height / 25;
		
	}

	@Override
	public void show() {
		
		float screenWidth = Gdx.graphics.getWidth();
		float screenHeight = Gdx.graphics.getHeight();
		float gameWidth = 203;
		float gameHeight = screenHeight / (screenWidth / gameWidth);
		
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
		securityCam = new SecurityCam(world, 1,-1f,1,3);
		
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
		
		myNibolas = new Nibolas(world, this, 0, -3.5f, .5f);
		
		Gdx.input.setInputProcessor(new InputHandler(this,gameWidth/10,gameHeight/10));
		
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
