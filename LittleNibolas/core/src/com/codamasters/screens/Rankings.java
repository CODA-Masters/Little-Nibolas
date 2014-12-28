package com.codamasters.screens;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;
import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
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
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.codamasters.LittleNibolas;
import com.codamasters.LNHelpers.AssetLoaderSpace;
import com.codamasters.tween.ActorAccessor;
import com.codamasters.tween.SpriteAccessor;

public class Rankings implements Screen {
	 
    private Stage stage;
    private Skin skin;
    private Table table;
    private TweenManager tweenManager;
    private Sprite splash2;
    private SpriteBatch batch;
    private float width;
    private float height;
    private LittleNibolas game;
    
    public Rankings(LittleNibolas game){
		this.game = game;
	}

    @Override
    public void render(float delta) {
    		Gdx.gl.glClearColor(0, 0, 0, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            batch.begin();
            splash2.draw(batch);
            batch.end();
           
            stage.act(delta);
           
            stage.draw();
           

            tweenManager.update(delta);
    }

    @Override
    public void resize(int width, int height) {
            stage.getCamera().update();
        stage.getViewport().update(width, height, false);
            table.invalidateHierarchy();
           
           
    }

    @Override
    public void show() {
           

            if(!AssetLoaderSpace.music_menu.isPlaying())
                    AssetLoaderSpace.music_menu.play();
           
            stage = new Stage(new FitViewport(1280,720));
            Gdx.graphics.setDisplayMode((int) (Gdx.graphics.getWidth()), Gdx.graphics.getHeight(), false);
            Gdx.input.setInputProcessor(stage);

            skin = new Skin(Gdx.files.internal("ui/menuSkin.json"), new TextureAtlas("ui/atlas.pack"));

            table = new Table(skin);
            table.setFillParent(true);

           
            // creating heading
            Label heading = new Label("Rankings", skin, "big");
            heading.setFontScale(2);
            
            TextButton buttonNv1 = new TextButton("Actualidad", skin,"big");
            buttonNv1.addListener(new ClickListener() {

                    @Override
                    public void clicked(InputEvent event, float x, float y) {

                            stage.addAction(sequence(moveTo(0, -stage.getHeight(), .5f), run(new Runnable() {
                                   
                                    @Override
                                    public void run() {
                                            game.actionResolver.displayLeaderboard(LittleNibolas.LEADERBOARD_NV1);
                                            ((Game) Gdx.app.getApplicationListener()).setScreen(new Rankings(game));
                                    }
                            })));
                    }
            });
           
            buttonNv1.pad(5, 60, 5, 60);

            TextButton buttonNv2 = new TextButton("Roma", skin,"big");
            buttonNv2.addListener(new ClickListener() {

                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                            stage.addAction(sequence(moveTo(0, -stage.getHeight(), .5f), run(new Runnable() {

                                    @Override
                                    public void run() {
                                    	game.actionResolver.displayLeaderboard(LittleNibolas.LEADERBOARD_NV2);
                                    	((Game) Gdx.app.getApplicationListener()).setScreen(new Rankings(game));
                                    }
                            })));
                    }
            });
            buttonNv2.pad(5, 152, 5, 152);

            TextButton buttonNv3 = new TextButton("Espacio", skin,"big");
            buttonNv3.addListener(new ClickListener() {

            	@Override
                public void clicked(InputEvent event, float x, float y) {
                        stage.addAction(sequence(moveTo(0, -stage.getHeight(), .5f), run(new Runnable() {

                                @Override
                                public void run() {
                                	game.actionResolver.displayLeaderboard(LittleNibolas.LEADERBOARD_NV3);
                                	((Game) Gdx.app.getApplicationListener()).setScreen(new Rankings(game));
                                }
                        })));
                }
            });
            buttonNv3.pad(5, 110, 5, 110);
            
            TextButton buttonLogros = new TextButton("Logros", skin,"big");
            buttonLogros.addListener(new ClickListener() {

            	@Override
                public void clicked(InputEvent event, float x, float y) {
                        stage.addAction(sequence(moveTo(0, -stage.getHeight(), .5f), run(new Runnable() {

                                @Override
                                public void run() {
                                	game.actionResolver.displayAchievements();
                                	((Game) Gdx.app.getApplicationListener()).setScreen(new Rankings(game));
                                }
                        })));
                }
            });
            buttonLogros.pad(5, 125, 5, 125);
            
            TextButton back = new TextButton("Volver", skin,"default");
            back.addListener(new ClickListener() {

                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                            stage.addAction(sequence(moveTo(0, stage.getHeight(), .5f), run(new Runnable() {

                                    @Override
                                    public void run() {
                                            //AssetLoaderSpace.music_menu.stop();
                                            ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu(game));
                                    }
                                   
                            })));
                    }
            });
            back.pad(5);
            
            batch = new SpriteBatch();

            tweenManager = new TweenManager();
            Tween.registerAccessor(Sprite.class, new SpriteAccessor());
            width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();
            
            splash2 = new Sprite(new Texture("data/mundo.jpg"));
           
            splash2.setBounds(0, 0, width, height);

            // creating buttons
            TextButton buttonExit = new TextButton("Volver", skin,"big");
            buttonExit.addListener(new ClickListener() {

                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                            Timeline.createParallel().beginParallel()
                                            .push(Tween.to(table, ActorAccessor.ALPHA, .75f).target(0))
                                            .push(Tween.to(table, ActorAccessor.Y, .75f).target(table.getY() - 50)
                                                            .setCallback(new TweenCallback() {

                                                                    @Override
                                                                    public void onEvent(int type, BaseTween<?> source) {
                                                                            ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu(game));
                                                                    }
                                                            }))
                                            .end().start(tweenManager);
                    }
            });
            buttonExit.pad(5, 40, 5, 40);
            // putting stuff together
            table.add(heading).spaceBottom(70).row();
            table.add(buttonNv1).spaceBottom(5).row();
            table.add(buttonNv2).spaceBottom(5).row();
            table.add(buttonNv3).spaceBottom(5).row();
            table.add(buttonLogros).spaceBottom(30).row();
            table.add(buttonExit).center();

            stage.addActor(table);

            // creating animations
            tweenManager = new TweenManager();
            Tween.registerAccessor(Actor.class, new ActorAccessor());

            // heading color animation
            Timeline.createSequence().beginSequence()
                            .push(Tween.to(heading, ActorAccessor.RGB, .5f).target(0, 0, 1))
                            .push(Tween.to(heading, ActorAccessor.RGB, .5f).target(0, 1, 0))
                            .push(Tween.to(heading, ActorAccessor.RGB, .5f).target(1, 0, 0))
                            .push(Tween.to(heading, ActorAccessor.RGB, .5f).target(1, 1, 0))
                            .push(Tween.to(heading, ActorAccessor.RGB, .5f).target(0, 1, 1))
                            .push(Tween.to(heading, ActorAccessor.RGB, .5f).target(1, 0, 1))
                            .push(Tween.to(heading, ActorAccessor.RGB, .5f).target(1, 1, 1))
                            .end().repeat(Tween.INFINITY, 0).start(tweenManager);

            // heading and buttons fade-in
            Timeline.createSequence().beginSequence()
                            .push(Tween.set(buttonNv1, ActorAccessor.ALPHA).target(0))
                            .push(Tween.set(buttonNv2, ActorAccessor.ALPHA).target(0))
                            .push(Tween.set(buttonNv3, ActorAccessor.ALPHA).target(0))
                            .push(Tween.set(buttonLogros, ActorAccessor.ALPHA).target(0))
                            .push(Tween.set(buttonExit, ActorAccessor.ALPHA).target(0))
                            .push(Tween.from(heading, ActorAccessor.ALPHA, .25f).target(0))
                            .push(Tween.to(buttonNv1, ActorAccessor.ALPHA, .25f).target(1))
                            .push(Tween.to(buttonNv2, ActorAccessor.ALPHA, .25f).target(1))
                            .push(Tween.to(buttonNv3, ActorAccessor.ALPHA, .25f).target(1))
                            .push(Tween.to(buttonLogros, ActorAccessor.ALPHA, .25f).target(1))
                            .push(Tween.to(buttonExit, ActorAccessor.ALPHA, .25f).target(1))
                            .end().start(tweenManager);
           
            
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
            splash2.getTexture().dispose();
           
    }

}