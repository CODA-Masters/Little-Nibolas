package com.codamasters.screens;

import com.codamasters.tween.SpriteAccessor;
import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Splash implements Screen {

	private SpriteBatch batch;
	private Sprite splash;
	private TweenManager tweenManager;
	

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();
		splash.draw(batch);
		batch.end();

		tweenManager.update(delta);
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void show() {
		//if(Gdx.app.getType() == ApplicationType.Desktop)
		//	Gdx.graphics.setDisplayMode((int) (Gdx.graphics.getHeight() / 1.5f), Gdx.graphics.getHeight(), false);
		// apply preferences
		Gdx.graphics.setVSync(Settings.vSync());
		
		
		batch = new SpriteBatch();

		tweenManager = new TweenManager();
		Tween.registerAccessor(Sprite.class, new SpriteAccessor());

		//Colocar el splash centrado y adaptado a las dimensiones de la pantalla
		float width = Gdx.graphics.getWidth();
	    float height = Gdx.graphics.getHeight();
		splash = new Sprite(new Texture("data/splash.png"));
		float desiredWidth = width * .7f;
        float scale = desiredWidth / splash.getWidth();
		splash.setSize(splash.getWidth() * scale, splash.getHeight() * scale);
		splash.setPosition((width / 2) - (splash.getWidth() / 2), (height / 2)- (splash.getHeight() / 2));
		
		Tween.set(splash, SpriteAccessor.ALPHA).target(0).start(tweenManager);
		Tween.to(splash, SpriteAccessor.ALPHA, 1.5f).target(1).repeatYoyo(1, .5f).setCallback(new TweenCallback() {

			@Override
			public void onEvent(int type, BaseTween<?> source) {
				((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu());
				
			}
		}).start(tweenManager);

		tweenManager.update(Float.MIN_VALUE); // update once avoid short flash of splash before animation
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
		batch.dispose();
		splash.getTexture().dispose();
	}

}
