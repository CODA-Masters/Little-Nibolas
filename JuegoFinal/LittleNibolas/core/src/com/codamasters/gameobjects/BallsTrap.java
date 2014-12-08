package com.codamasters.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.utils.Array;

public class BallsTrap {
	public final float WIDTH, HEIGHT;
	private Body body;
	private Fixture fixture;
	private Ball ball1, ball2, ball3;
	private Vector2 position;
	private World world;
	private boolean activated;
	
	public BallsTrap(World world, float x, float y, float width, float height) {
		WIDTH = width;
		HEIGHT = height;
		this.position = new Vector2(x,y);
		this.world = world;
		activated = false;
		
		
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.StaticBody;
		bodyDef.position.set(x, y);
		bodyDef.fixedRotation = true;

		PolygonShape shape = new PolygonShape();
		shape.setAsBox(width / 2, HEIGHT / 2);

		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.restitution = 0;
		fixtureDef.friction = 0;
		fixtureDef.density = 5;

		body = world.createBody(bodyDef);
		fixture = body.createFixture(fixtureDef);
		
		shape.dispose();
		
	}
	
	public void update() {
		
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
	
	public Array<Ball> getBalls(){
		Array<Ball> balls = new Array();
		balls.add(ball1);
		balls.add(ball2);
		balls.add(ball3);
		return balls;
	}
	
	public boolean isActivated(){
		return activated;
	}
	
	public void activate(){
		if(!activated){
			ball1 = new Ball(world, position.x-WIDTH/2+0f,position.y-HEIGHT/2);
			ball1.getBody().setLinearVelocity(-3,-2);
			ball2 = new Ball(world, position.x-WIDTH/2+4,position.y-HEIGHT/2);
			ball2.getBody().setLinearVelocity(-3,-3);
			ball3 = new Ball(world, position.x-WIDTH/2+8,position.y-HEIGHT/2);
			ball3.getBody().setLinearVelocity(-3,-4);
			
			activated = true;
		}
	}
}
