package com.codamasters.gameobjects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class Guard {
	private Body body;
	private Fixture fixture;
	public final float WIDTH, HEIGHT;
	float runTime;
	boolean lookingRight;
	float origX, destX;
	
	public Guard(World world, float x, float y, float width, float height) {
		WIDTH = width;
		HEIGHT = height;
		runTime =0;
		lookingRight = false;
		origX = x;
		destX = x-6;

		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.KinematicBody;
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
		
		body.setLinearVelocity(new Vector2(-2,0));
		
	}
	
	public void update(){
		
		if(body.getPosition().x > origX && body.getPosition().x < origX+0.05){
			body.setLinearVelocity(new Vector2(-body.getLinearVelocity().x,0));
			lookingRight = false;
		}
		
		else if(body.getPosition().x < destX && body.getPosition().x > destX-0.05){
			body.setLinearVelocity(new Vector2(-body.getLinearVelocity().x,0));
			lookingRight = true;
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
	
	public boolean isLookingRight(){
		return lookingRight;
	}
}
