package com.codamasters.gameobjects;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
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
import com.codamasters.screens.ScreenRome;

public class Horse implements ContactFilter, ContactListener {

	private Body body;
	private Fixture fixture;
	public final float WIDTH, HEIGHT;
	private Vector2 velocity = new Vector2();
	private Vector3 target = new Vector3();
	private float movementForce = 5, jumpPower = 10;
	private World world;
	private ScreenRome pantalla;
	private float last_screen_x;
	private boolean salto_1, salto_2;
	private boolean mov_ant, mov_nuevo; // true--> Derecha, false-->Izquierda
	private int num_saltos;


	public Horse(World world, ScreenRome pantalla, float x, float y, float width, float height) {
		
		WIDTH = width;
		HEIGHT = width * 2;
		this.world = world;
		this.pantalla = pantalla;
		this.salto_1=this.salto_2=false;
		this.mov_ant=true;
		this.mov_nuevo=true;
		this.num_saltos=0;
		
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(x, y);
		bodyDef.fixedRotation = true;
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(width / 2, height / 2);

		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.restitution = 0;
		fixtureDef.friction = 0;
		fixtureDef.density = 3;
						
		body = world.createBody(bodyDef);
		body.setLinearVelocity(5,0);

		fixture = body.createFixture(fixtureDef);
				
		shape.dispose();
				
	}

	public void update() {
		//body.applyForceToCenter(velocity, true);
		//body.setLinearVelocity(new Vector2(movementForce, 0));
		
		//if(target.x >= body.getPosition().x-0.05 && target.x <= body.getPosition().x+0.05){
		//	body.setLinearVelocity(3.5f,body.getLinearVelocity().y);
		//}
		
	}

	@Override
	public boolean shouldCollide(Fixture fixtureA, Fixture fixtureB) {
		if(fixtureA == fixture || fixtureB == fixture)
			return body.getLinearVelocity().y < 0;
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
		
		
		if(last_screen_x!=target.x){
			

			
			if(target.x > body.getPosition().x){
				if(mov_ant){
					mov_nuevo=mov_ant;
				}
				else{
					mov_nuevo=!mov_ant;
				}
					body.setLinearVelocity(5,0);

			}
			else if(target.x < body.getPosition().x){
				if(!mov_ant){ 
					mov_nuevo=mov_ant;
				}
				else{
					mov_nuevo=!mov_ant;
				}
					body.setLinearVelocity(-5,0);			
			}
			
			if( mov_ant!=mov_nuevo)
				pantalla.getSprite().flipFrames(true, false);
			
			if(num_saltos < 2 && mov_ant==mov_nuevo){
				body.setLinearVelocity(body.getLinearVelocity().x, 5);
				num_saltos++;
			}
			
			mov_ant=mov_nuevo;	
		}
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
	
	public int getNumSaltos(){
		return num_saltos;
	}
	
	public void setNumSaltos(int num_saltos){
		this.num_saltos=num_saltos;
	}

}

