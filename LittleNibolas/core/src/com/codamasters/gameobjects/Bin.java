package com.codamasters.gameobjects;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class Bin {
	
	private Body body;
	private Fixture fixture;
	public final float WIDTH, HEIGHT;
	private World world;
	private boolean nibolasInside;
	
	public Bin(World world, float x, float y, float width, float height) {
		WIDTH = width;
		HEIGHT = height;
		this.world = world;
		nibolasInside = false;

		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.StaticBody;
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
	
	public void setNibolasInside(boolean nibolasInside){
		this.nibolasInside = nibolasInside;
	}
	
	public boolean isNibolasInside(){
		return nibolasInside;
	}

}
