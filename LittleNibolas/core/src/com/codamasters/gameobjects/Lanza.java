package com.codamasters.gameobjects;

import java.util.Random;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactFilter;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.codamasters.LNHelpers.AnimatedSprite;
import com.codamasters.screens.ScreenRome;

public class Lanza implements ContactFilter, ContactListener {

	private Body body;
	private Fixture fixture;
	public final float WIDTH, HEIGHT;
	private Vector2 velocity = new Vector2();
	private Vector3 target = new Vector3();
	private float movementForce = 5, jumpPower = 10;
	private World world;
	private ScreenRome pantalla;
	private float last_screen_x;
	private Random rand;
	private float x,y;
	private boolean esMortal;
	private int minVelocidad = 2;
	private int maxVelocidad = 9;
	private int velocidad;
	private AnimatedSprite animatedSprite;
	public final static int DANIO = 1;
	
	public Lanza(World world, ScreenRome pantalla, float x, float y, float width, float height) {
		WIDTH = width;
		HEIGHT = width * 2;
		this.world = world;
		this.pantalla = pantalla;
		this.x=x;
		this.y=y;
		this.esMortal=true;
		
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(x, y);
		bodyDef.fixedRotation = true;
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(width / 2, height / 8);

		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.restitution = 0;
		fixtureDef.friction = 1;
		fixtureDef.density = 3;
						
		body = world.createBody(bodyDef);
		fixture = body.createFixture(fixtureDef);
		
		rand = new Random();
		velocidad = minVelocidad + rand.nextInt(maxVelocidad - minVelocidad + 1);
		
		body.setLinearVelocity(-velocidad, 0);
		
		float minAngulo = 0.2f;
		float maxAngulo = 0.4f;

		float angulo = rand.nextFloat() * (maxAngulo - minAngulo) + minAngulo;
		body.setAngularVelocity(angulo);
		
		shape.dispose();
				
	}

	public void update() {
		
		/*
		if(body.getPosition().y > -2 && body.getPosition().y < -1.95f  ){
			OrthographicCamera cam = pantalla.getCamera();
			body.getTransform().setPosition(new Vector2(x+cam.position.x+cam.viewportWidth,y));
			rand = new Random();
			int minVelocidad = 4;
			int maxVelocidad = 8;
			int velocidad = minVelocidad + rand.nextInt(maxVelocidad - minVelocidad + 1);
			body.setLinearVelocity(-velocidad, 0);
			
			float minAngulo = 0.2f;
			float maxAngulo = 0.6f;

			float angulo = rand.nextFloat() * (maxAngulo - minAngulo) + minAngulo;
			body.setAngularVelocity(angulo);
		}
		*/
	}

	@Override
	public boolean shouldCollide(Fixture fixtureA, Fixture fixtureB) {
		if(fixtureA == fixture || fixtureB == fixture){
			return body.getLinearVelocity().y < 0;
		}
		return false;
	}

	@Override
	public void beginContact(Contact contact) {
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
	}

	@Override
	public void endContact(Contact contact) {
	}

	public void onClick(){
		//body.applyLinearImpulse(0, jumpPower, body.getWorldCenter().x, body.getWorldCenter().y, true);
	}
	
	
	public void move(int screenX, int screenY){
		OrthographicCamera cam = pantalla.getCamera();
		last_screen_x = target.x;
		cam.unproject(target.set(screenX,screenY,0));
		pantalla.setCamera(cam);
		
		body.setLinearVelocity(0,5);

	}

	public float getRestitution() {
		return fixture.getRestitution();
	}

	public void setRestitution(float restitution) {
		fixture.setRestitution(restitution);
	}

	public Body getBody() {
		return body;
	}

	public Fixture getFixture() {
		return fixture;
	}

	public void destroy(){
		body.destroyFixture(fixture);
		world.destroyBody(body);
		esMortal=true;
	}
	
	public boolean EsMortal(){
		return esMortal;
	}
	
	public void setEsMortal(boolean esMortal){
		this.esMortal = esMortal;
	}
	
	public void setAnimatedSprite(AnimatedSprite animatedSprite){
		this.animatedSprite = animatedSprite;
	}
	
	public AnimatedSprite getAnimatedSprite(){
		return animatedSprite;
	}

}

