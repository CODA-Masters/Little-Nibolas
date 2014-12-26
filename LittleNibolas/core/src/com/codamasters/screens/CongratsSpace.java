package com.codamasters.screens;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import com.codamasters.LittleNibolas;
import com.codamasters.LNHelpers.AssetLoaderSpace;
import com.codamasters.LNHelpers.AssetsLoaderActual;
import com.codamasters.tween.ActorAccessor;

import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class CongratsSpace implements Screen {

	private Stage stage;
	private Skin skin;
	private Table table;
	private TweenManager tweenManager;
	private Label puntos, lHighscore;
	private LittleNibolas game;
	
	public CongratsSpace(LittleNibolas game){
		this.game = game;
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(delta);
		stage.draw();
	

		tweenManager.update(delta);
	}

	@Override
	public void resize(int width, int height) {
	    stage.getViewport().update(width, height, false);

		table.invalidateHierarchy();
	}

	@Override
	public void show() {
		stage = new Stage(new FitViewport(1280,720));
		Gdx.input.setInputProcessor(stage);

		skin = new Skin(Gdx.files.internal("ui/menuSkin.json"), new TextureAtlas("ui/atlas.pack"));

		table = new Table(skin);
		table.setFillParent(true);

		// creating heading
		Label heading = new Label("Enhorabuena", skin, "big");
		heading.setFontScale(2);
		
		
		int score = AssetLoaderSpace.getScore();
		int highscore = AssetLoaderSpace.getHighScore();
		
		puntos = new Label("Puntuacion obtenida:" + score,skin);
		puntos.setFontScale(1);
		if(score > highscore){
			lHighscore = new Label("Nuevo record !!!",skin);
			lHighscore.setFontScale(1);
			AssetLoaderSpace.setHighScore(score);
		}else{
			lHighscore = new Label("Puntuacion maxima:" + highscore,skin);
			lHighscore.setFontScale(1);
		}
		
		// creating buttons
		TextButton buttonPlay = new TextButton("Continuar", skin,"big");
		buttonPlay.addListener(new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {
				stage.addAction(sequence(moveTo(0, -stage.getHeight(), .5f), run(new Runnable() {

					@Override
					public void run() {						
						((Game) Gdx.app.getApplicationListener()).setScreen(new FinTercerNivel1(game));
					}
				})));
			}
		});
		buttonPlay.pad(10, 60, 10, 60);

		TextButton buttonRetry = new TextButton("Reintentar", skin,"big");
		buttonRetry.addListener(new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {
				stage.addAction(sequence(moveTo(0, -stage.getHeight(), .5f), run(new Runnable() {
					
					@Override
					public void run() {
						((Game) Gdx.app.getApplicationListener()).setScreen(new ScreenSpace(game));
					}
				})));
			}
		});
		buttonRetry.pad(10);

		// putting stuff together
		table.add(heading).spaceBottom(50).row();
		table.add(puntos).spaceBottom(10).row();
		table.add(lHighscore).spaceBottom(20).row();
		table.add(buttonPlay).spaceBottom(15).row();
		table.add(buttonRetry).spaceBottom(15).row();

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
				.push(Tween.set(buttonRetry, ActorAccessor.ALPHA).target(0))
				.push(Tween.from(heading, ActorAccessor.ALPHA, .25f).target(0))
				.push(Tween.to(buttonPlay, ActorAccessor.ALPHA, .25f).target(1))
				.push(Tween.to(buttonRetry, ActorAccessor.ALPHA, .25f).target(1))
				.end().start(tweenManager);

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

	}
}