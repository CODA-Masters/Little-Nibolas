package com.codamasters.gameobjects;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class Ball {

	private Body body;
	private Fixture fixture;
	private World world;
	public final float RADIUS = .5f;
	
	public Ball(World world, float x, float y) {
		
		BodyDef bodyDef = new BodyDef();
		FixtureDef fixtureDef = new FixtureDef();
		this.world = world;
		
		// body definition
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(x, y);

		// ball shape
		CircleShape ballShape = new CircleShape();
		ballShape.setRadius(RADIUS);
	
		// fixture definition
		fixtureDef.shape = ballShape;
		fixtureDef.friction = .25f;
		fixtureDef.restitution = 0.9f;
		fixtureDef.density = 2.5f;
		
		body = world.createBody(bodyDef);
		fixture = body.createFixture(fixtureDef);
		
		ballShape.dispose();
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
	}
}
