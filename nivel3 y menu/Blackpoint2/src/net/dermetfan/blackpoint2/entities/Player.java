package net.dermetfan.blackpoint2.entities;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactFilter;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Player extends InputAdapter implements ContactFilter, ContactListener {

	private Body body;
	private Fixture fixture;
	public final float WIDTH, HEIGHT;
	private Vector2 velocity = new Vector2();
	private float movementForce = 500, jumpPower = 45;

	public Player(World world, float x, float y, float width) {
		WIDTH = width;
		HEIGHT = width * 2;

		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(x, y);
		bodyDef.fixedRotation = true;

		PolygonShape shape = new PolygonShape();
		shape.setAsBox(width / 2, HEIGHT / 2);

		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.restitution = 0;
		fixtureDef.friction = .8f;
		fixtureDef.density = 3;

		body = world.createBody(bodyDef);
		fixture = body.createFixture(fixtureDef);
	}

	public void update() {
		body.applyForceToCenter(velocity, true);
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
		if(contact.getFixtureA() == fixture || contact.getFixtureB() == fixture)
			if(contact.getWorldManifold().getPoints()[0].y <= body.getPosition().y - HEIGHT / 2)
				body.applyLinearImpulse(0, jumpPower, body.getWorldCenter().x, body.getWorldCenter().y, true);
	}

	@Override
	public void endContact(Contact contact) {
	}

	@Override
	public boolean keyDown(int keycode) {
		switch(keycode) {
		case Keys.A:
			velocity.x = -movementForce;
			break;
		case Keys.D:
			velocity.x = movementForce;
			break;
		// TODO remove this case
		case Keys.SPACE:
			body.applyLinearImpulse(0, jumpPower, body.getWorldCenter().x, body.getWorldCenter().y, true);
			break;
		default:
			return false;
		}
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		if(keycode == Keys.A || keycode == Keys.D)
			velocity.x = 0;
		else
			return false;
		return true;
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

}
