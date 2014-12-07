package net.dermetfan.blackpoint2.screens;

import net.dermetfan.blackpoint2.LevelGenerator;

import net.dermetfan.blackpoint2.entities.Player;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

public class Play implements Screen {

	private World world;
	private Box2DDebugRenderer debugRenderer;
	private SpriteBatch batch;
	private OrthographicCamera camera;

	private final float TIMESTEP = 1 / 60f;
	private final int VELOCITYITERATIONS = 8, POSITIONITERATIONS = 3;

	private LevelGenerator levelGenerator;
	private Player player;

	private Vector3 bottomLeft, bottomRight;
	
	private Array<Body> tmpBodies = new Array<Body>();

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		if(player.getBody().getPosition().x < bottomLeft.x)
			player.getBody().setTransform(bottomRight.x, player.getBody().getPosition().y, player.getBody().getAngle());
		else if(player.getBody().getPosition().x > bottomRight.x)
			player.getBody().setTransform(bottomLeft.x, player.getBody().getPosition().y, player.getBody().getAngle());
		
		player.update();
		world.step(TIMESTEP, VELOCITYITERATIONS, POSITIONITERATIONS);

		camera.position.y = player.getBody().getPosition().y > camera.position.y ? player.getBody().getPosition().y : camera.position.y;
		camera.update();

		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		world.getBodies(tmpBodies);
		for(Body body : tmpBodies)
			if(body.getUserData() instanceof Sprite) {
				Sprite sprite = (Sprite) body.getUserData();
				sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2, body.getPosition().y - sprite.getHeight() / 2);
				sprite.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
				sprite.draw(batch);
			}
		batch.end();

		debugRenderer.render(world, camera.combined);
		
		levelGenerator.generate(camera.position.y + camera.viewportHeight / 2);
	}

	@Override
	public void resize(int width, int height) {
		camera.viewportWidth = width / 25;
		camera.viewportHeight = height / 25;
	}

	@Override
	public void show() {
	
		//if(Gdx.app.getType() == ApplicationType.Desktop)
		//		Gdx.graphics.setDisplayMode((int) (Gdx.graphics.getHeight() / 1.5f), Gdx.graphics.getHeight(), false);
		world = new World(new Vector2(0, -9.81f), true);
		debugRenderer = new Box2DDebugRenderer();
		batch = new SpriteBatch();

		camera = new OrthographicCamera(Gdx.graphics.getWidth() / 25, Gdx.graphics.getHeight() / 25);

		player = new Player(world, 0, 1, 1);
		world.setContactFilter(player);
		world.setContactListener(player);
		
		Gdx.input.setInputProcessor(new InputMultiplexer(new InputAdapter() {

			@Override
			public boolean keyDown(int keycode) {
				switch(keycode) {
				case Keys.ESCAPE:
					((Game) Gdx.app.getApplicationListener()).setScreen(new LevelMenu());
					break;
				}
				return false;
			}

			@Override
			public boolean scrolled(int amount) {
				camera.zoom += amount / 25f;
				return true;
			}

		}, player));

		BodyDef bodyDef = new BodyDef();
		FixtureDef fixtureDef = new FixtureDef();

		// GROUND
		// body definition
		bodyDef.type = BodyType.StaticBody;
		bodyDef.position.set(0, 0);

		// ground shape
		ChainShape groundShape = new ChainShape();
		bottomLeft = new Vector3(0, Gdx.graphics.getHeight(), 0);
		bottomRight = new Vector3(Gdx.graphics.getWidth(), bottomLeft.y, 0);
		camera.unproject(bottomLeft);
		camera.unproject(bottomRight);

		groundShape.createChain(new float[] {bottomLeft.x, bottomLeft.y, bottomRight.x, bottomRight.y});

		// fixture definition
		fixtureDef.shape = groundShape;
		fixtureDef.friction = .5f;
		fixtureDef.restitution = 0;

		Body ground = world.createBody(bodyDef);
		ground.createFixture(fixtureDef);
		
		groundShape.dispose();
		
		levelGenerator = new LevelGenerator(ground, bottomLeft.x, bottomRight.x, player.WIDTH, player.HEIGHT * 3, player.WIDTH * 1.5f, player.WIDTH * 3.5f, player.WIDTH / 3, 20 * MathUtils.degRad);
	}

	@Override
	public void hide() {
		dispose();
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
		world.dispose();
		debugRenderer.dispose();
	}

}
