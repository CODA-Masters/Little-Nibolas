package com.codamasters.gameobjects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class SecurityCam {
	
	private Body body;
	private Fixture fixture;
	public final float WIDTH, HEIGHT;
	private Vector2 velocity = new Vector2();
	private Vector2 position = new Vector2();
	private float movementForce = 5, jumpPower = 10;
	private boolean movAng;

	
	public SecurityCam(World world, float x, float y, float width, float height) {
		WIDTH = width;
		HEIGHT = height;
		position.x = x;
		position.y = y;
		movAng=true;
	
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.KinematicBody;
		bodyDef.position.set(x, y);
		bodyDef.fixedRotation = true;
	
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(width / 2, HEIGHT / 2, new Vector2(0,-height/2), 0);
	
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.restitution = 0;
		fixtureDef.friction = 0;
		fixtureDef.density = 3;
	
		body = world.createBody(bodyDef);
		fixture = body.createFixture(fixtureDef);
		
		shape.dispose();
	}
	
	public void update(){
				
		if( ( body.getAngle() % (2*Math.PI) < -0.5f*Math.PI ) && (body.getAngle()% (2*Math.PI) > -0.5f*Math.PI-0.05f ) )
			movAng=false;
		else if( (body.getAngle()% (2*Math.PI) < 0.5f*Math.PI ) && (body.getAngle()% (2*Math.PI) > 0.5f*Math.PI-0.05f ) )
			movAng=true;
				
		if(movAng){
			body.setAngularVelocity(-.7f);
		}
		else{
			body.setAngularVelocity(.7f);
		}
	}
	
	public Body getBody() {
		return body;
	}

	public Fixture getFixture() {
		return fixture;
	}
}