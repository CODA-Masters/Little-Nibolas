package com.codamasters.screens;
 
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
 
public class Credits implements Screen {
 
        private Stage stage;
        private Skin skin;
        private Table table;
        private TweenManager tweenManager;
        private Sprite splash,splash2;
        private SpriteBatch batch;
        private float width;
        private float height;
        private LittleNibolas game;
        
        public Credits(LittleNibolas game){
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
                batch.begin();
                splash.draw(batch);                    
                batch.end();
               
 
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
                Label heading = new Label("Coda Masters Team", skin, "big");
                heading.setFontScale(1);
                String texto1="enpi\njuanfranrv\njulioxus\nmaluky\nosquiya";
               
                Label text1 = new Label(texto1, skin,"small");
                text1.setFontScale(0.5f);
                //Gdx.graphics.setVSync(Settings.vSync());
               
                String texto2="Sound Effects/Music\n\nMike Koenig\nPopup Pixels\nTobu\nincompetech.com";
               
                Label text2 = new Label(texto2, skin,"small");
                text2.setFontScale(0.5f);
                batch = new SpriteBatch();
 
                tweenManager = new TweenManager();
                Tween.registerAccessor(Sprite.class, new SpriteAccessor());
                width = Gdx.graphics.getWidth();
            height = Gdx.graphics.getHeight();
                splash = new Sprite(new Texture("data/stopednibolas.png"));
                splash.setSize(width/7, height/2);
                splash.setPosition((width / 8) - (splash.getWidth() / 8), (height / 8)- (splash.getHeight() / 8));
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
                table.add(text1).spaceLeft(10).spaceRight(10);
                table.add(text2).spaceBottom(15).row();
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
                                .push(Tween.set(text1, ActorAccessor.ALPHA).target(0))
                                .push(Tween.set(text2, ActorAccessor.ALPHA).target(0))
                                .push(Tween.set(buttonExit, ActorAccessor.ALPHA).target(0))
                                .push(Tween.from(heading, ActorAccessor.ALPHA, .25f).target(0))
                                .push(Tween.to(text1, ActorAccessor.ALPHA, .25f).target(1))
                                .push(Tween.to(text2, ActorAccessor.ALPHA, .25f).target(1))
                                .push(Tween.to(buttonExit, ActorAccessor.ALPHA, .25f).target(1))
                                .end().start(tweenManager);
               
                Tween.set(splash, SpriteAccessor.ALPHA).target(0).start(tweenManager);
                Tween.to(splash, SpriteAccessor.ALPHA, 2f).target(1).setCallback(new TweenCallback() {
                       
                        @Override
                        public void onEvent(int type, BaseTween<?> source) {
 
                                //((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu());
                        }
                }).start(tweenManager);
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
                splash2.getTexture().dispose();
               
        }
 
}