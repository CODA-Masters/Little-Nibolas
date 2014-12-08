package com.codamasters.gameobjects;

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

public class Plataforma implements ContactFilter, ContactListener {

	private Body body;
	private Fixture fixture;
	public final float WIDTH, HEIGHT;
	private World world;
	private ScreenRome pantalla;
	private boolean esMortal;
	private AnimatedSprite animatedSprite;
	
	public Plataforma(World world, ScreenRome pantalla, float x, float y, float width, float height) {
		WIDTH = width;
		HEIGHT = width * 2;
		this.world = world;
		this.pantalla = pantalla;
		this.esMortal=true;
		
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.StaticBody;
		bodyDef.position.set(x, y);
		bodyDef.fixedRotation = true;
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(width / 2, height / 8);

		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.friction = .5f;
		fixtureDef.restitution = 0;
		fixtureDef.density = 2.5f;
						
		body = world.createBody(bodyDef);
		fixture = body.createFixture(fixtureDef);
		
		shape.dispose();
				
	}

	public void update() {
		
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

