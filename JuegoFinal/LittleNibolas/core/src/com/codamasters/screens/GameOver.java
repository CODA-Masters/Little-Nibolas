package com.codamasters.screens;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import com.codamasters.LittleNibolas;
import com.codamasters.LNHelpers.AssetLoaderSpace;
import com.codamasters.tween.ActorAccessor;
import com.codamasters.tween.SpriteAccessor;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Timeline;
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
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GameOver implements Screen {

	private Stage stage;
	private Skin skin;
	private Table table;
	private TweenManager tweenManager;
	private Label puntos;
	private Sprite splash;
	private SpriteBatch batch;

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(delta);
		stage.draw();
		batch.begin();
		splash.draw(batch);			
		batch.end();
	

		tweenManager.update(delta);
	}

	@Override
	public void resize(int width, int height) {
	    stage.getViewport().update(width, height, false);

		table.invalidateHierarchy();
	}

	@Override
	public void show() {
		stage = new Stage();

		//Gdx.graphics.setDisplayMode((int) (Gdx.graphics.getHeight() / 1.5f), Gdx.graphics.getHeight(), false);
		Gdx.input.setInputProcessor(stage);

		skin = new Skin(Gdx.files.internal("ui/menuSkin.json"), new TextureAtlas("ui/atlas.pack"));

		table = new Table(skin);
		table.setFillParent(true);

		// creating heading
		Label heading = new Label("Game Over", skin, "big");
		heading.setFontScale(3);
		
		Gdx.graphics.setVSync(Settings.vSync());
		
		
		batch = new SpriteBatch();

		tweenManager = new TweenManager();
		Tween.registerAccessor(Sprite.class, new SpriteAccessor());
		float width = Gdx.graphics.getWidth();
	    float height = Gdx.graphics.getHeight();
		splash = new Sprite(new Texture("data/roca-Rajoy.png"));
		float desiredWidth = width * .7f;
        float scale = 0.5f;
		splash.setSize(splash.getWidth() * scale, splash.getHeight() * scale);
		splash.setPosition((width / 8) - (splash.getWidth() / 8), (height / 8)- (splash.getHeight() / 8));
		
		int score = AssetLoaderSpace.getScore();
		int highscore = AssetLoaderSpace.getHighScore();
		
		if(score > highscore){
			puntos = new Label("Nuevo record:" + score + "!!!",skin);
			puntos.setFontScale(1);
			AssetLoaderSpace.setHighScore(score);
		}else{
			puntos = new Label("Puntuacion obtenida:" + score,skin);
			puntos.setFontScale(1);
		}
		
		// creating buttons
		TextButton buttonPlay = new TextButton("Reintentar", skin,"big");
		buttonPlay.addListener(new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {
				stage.addAction(sequence(moveTo(0, -stage.getHeight(), .5f), run(new Runnable() {

					@Override
					public void run() {
						((Game) Gdx.app.getApplicationListener()).setScreen(new ScreenSpace());
					}
				})));
			}
		});
		buttonPlay.pad(10, 60, 10, 60);

		TextButton buttonSettings = new TextButton("Volver al menu", skin,"big");
		buttonSettings.addListener(new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {
				stage.addAction(sequence(moveTo(0, -stage.getHeight(), .5f), run(new Runnable() {
					
					@Override
					public void run() {
						AssetLoaderSpace.music_menu.play();
						AssetLoaderSpace.estrellado.stop();
						((Game) Gdx.app.getApplicationListener()).setScreen(new LevelMenu());
					}
				})));
			}
		});
		buttonSettings.pad(10);

		// putting stuff together
		table.add(heading).spaceBottom(100).row();
		table.add(puntos).spaceBottom(50).row();
		table.add(buttonPlay).spaceBottom(15).row();
		table.add(buttonSettings).spaceBottom(15).row();

		stage.addActor(table);

		// creating animations
		tweenManager = new TweenManager();
		Tween.registerAccessor(Actor.class, new ActorAccessor());

		// heading color animation
		Timeline.createSequence().beginSequence()
				.push(Tween.to(heading, ActorAccessor.RGB, .5f).target(1, 0, 0))

				.end().repeat(Tween.INFINITY, 0).start(tweenManager);

		// heading and buttons fade-in
		Timeline.createSequence().beginSequence()
				.push(Tween.set(buttonPlay, ActorAccessor.ALPHA).target(0))
				.push(Tween.set(buttonSettings, ActorAccessor.ALPHA).target(0))
				.push(Tween.from(heading, ActorAccessor.ALPHA, .25f).target(0))
				.push(Tween.to(buttonPlay, ActorAccessor.ALPHA, .25f).target(1))
				.push(Tween.to(buttonSettings, ActorAccessor.ALPHA, .25f).target(1))
				.end().start(tweenManager);

		Tween.set(splash, SpriteAccessor.ALPHA).target(0).start(tweenManager);
		AssetLoaderSpace.estrellado.play();
		//Tween.to(splash, SpriteAccessor.ALPHA, 1.5f).target(1).start(tweenManager);
		Tween.to(splash, SpriteAccessor.ALPHA, 2f).target(1).repeatYoyo(1, .5f).setCallback(new TweenCallback() {
			
			@Override
			public void onEvent(int type, BaseTween<?> source) {

				//((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu());
			}
		}).start(tweenManager);
		// table fade-in
		Tween.from(table, ActorAccessor.ALPHA, .75f).target(0).start(tweenManager);
		Tween.from(table, ActorAccessor.Y, .75f).target(Gdx.graphics.getHeight() / 8).start(tweenManager);

		tweenManager.update(Gdx.graphics.getDeltaTime());
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
		stage.dispose();
		skin.dispose();
		splash.getTexture().dispose();
	}

}