package com.codamasters.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Input.Keys;
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
import com.codamasters.screens.PantallaActual;

public class Nibolas{

	private Body body;
	private Fixture fixture;
	public final float WIDTH, HEIGHT;
	private Vector2 velocity = new Vector2();
	private Vector3 target = new Vector3();
	private float movementForce = 5, jumpPower = 10;
	private PantallaActual pantalla;
	private boolean isMoving;
	private boolean isLookingRight;
	private boolean trincado;
	private World world;
	private boolean visible;

	public Nibolas(World world, PantallaActual pantalla, float x, float y, float width, float height) {
		WIDTH = width;
		HEIGHT = height;
		this.pantalla = pantalla;
		isMoving = false;
		isLookingRight = true;
		trincado = false;
		this.world = world;
		visible = true;

		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(x, y);
		bodyDef.fixedRotation = true;

		PolygonShape shape = new PolygonShape();
		shape.setAsBox(width / 2, height/2);

		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.restitution = 0;
		fixtureDef.friction = 0;
		fixtureDef.density = 3;

		body = world.createBody(bodyDef);
		fixture = body.createFixture(fixtureDef);
		
		shape.dispose();
	}

	public void update() {
		//body.applyForceToCenter(velocity, true);
		
		if(target.x >= body.getPosition().x-0.05 && target.x <= body.getPosition().x+0.05){
			body.setLinearVelocity(0,body.getLinearVelocity().y);
			isMoving = false;
		}
		
		if(target.x >= body.getPosition().x)
			isLookingRight = true;
		else
			isLookingRight = false;
		
	}

	public void jump(){
		body.applyLinearImpulse(0, jumpPower, body.getWorldCenter().x, body.getWorldCenter().y, true);
	}
	
	
	public void move(int screenX, int screenY){
		OrthographicCamera cam = pantalla.getCamera();
		cam.unproject(target.set(screenX,screenY,0));
		pantalla.setCamera(cam);
		
		// Moverse a la derecha
		if(target.x > body.getPosition().x){
			body.setLinearVelocity(movementForce,0);
		}
		// Moverse a la izquierda
		else if (target.x < body.getPosition().x){
			body.setLinearVelocity(-movementForce,0);
		}
		
		isMoving = true;
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
	
	public boolean isMoving(){
		return isMoving;
	}
	
	public boolean isLookingRight(){
		return isLookingRight;
	}
	
	public void stop(){
		isMoving = false;
		trincado = true;
		body.setLinearVelocity(0,0);
	}
	
	public boolean trincado(){
		return trincado;
	}
	
	public void becomeInvisible(){
			visible = false;
			body.setType(BodyType.StaticBody);
	}
	
	public void becomeVisible(){
			visible = true;
			body.setType(BodyType.DynamicBody);
	}
	
	public boolean isVisible(){
		return visible;
	}
	
	public void destroy(){
		body.destroyFixture(fixture);
		world.destroyBody(body);
	}

}

