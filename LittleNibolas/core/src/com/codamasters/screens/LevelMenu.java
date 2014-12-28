package com.codamasters.screens;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import com.codamasters.LittleNibolas;
import com.codamasters.LNHelpers.AssetLoaderSpace;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class LevelMenu implements Screen {

	private Stage stage;
    private Sprite splash;
    private SpriteBatch batch;
    private Table table;
    private Skin skin;
    private float width;
    private float height;
    private LittleNibolas game;
	
	public LevelMenu(LittleNibolas game){
		this.game = game;
	}

	@Override
    public void render(float delta) {
			Gdx.gl.glClearColor(0, 0, 0, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            batch.begin();
            splash.draw(batch);
            batch.end();
            stage.act(delta);
            stage.draw();
    }

    @Override
    public void resize(int width, int height) {
            stage.getCamera().update();
        stage.getViewport().update(width, height, false);
            table.invalidateHierarchy();
    }

    @Override
    public void show() {
            stage = new Stage(new FitViewport(1280,720));

            Gdx.input.setInputProcessor(stage);
           
            if(!AssetLoaderSpace.music_menu.isPlaying()){
                    AssetLoaderSpace.music_menu.play();
            }

            skin = new Skin(Gdx.files.internal("ui/menuSkin.json"), new TextureAtlas("ui/atlas.pack"));

            table = new Table(skin);
            table.setFillParent(true);

            //List list = new List(new String[] {"Nivel 1: Actualidad","Nivel 2: Zona Romana","Nivel 3: Zona Espacial","Nivel 4: Zona Futuro"}, skin);
            //ScrollPane scrollPane = new ScrollPane(list, skin,"default");
            batch = new SpriteBatch();
           
            width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();         
            splash = new Sprite(new Texture("data/mundo.jpg"));
            splash.setBounds(0, 0, width, height);
            TextButton nivel1 = new TextButton("Nivel 1: Actualidad", skin, "default");
            nivel1.addListener(new ClickListener() {
   
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                            AssetLoaderSpace.music_menu.stop();
                            ((Game) Gdx.app.getApplicationListener()).setScreen(new IntroPrimerNivel(game));
                    }
                   
            });
            nivel1.pad(10, 50, 10, 50);
           
            TextButton nivel2 = new TextButton("Nivel 2: Roma", skin, "default");
            nivel2.addListener(new ClickListener() {
   
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                            AssetLoaderSpace.music_menu.stop();
                            ((Game) Gdx.app.getApplicationListener()).setScreen(new IntroSegundoNivel(game));
                    }
                   
            });
            nivel2.pad(10, 135, 10, 135);
           
            TextButton nivel3 = new TextButton("Nivel 3: Espacio", skin, "default");
            nivel3.addListener(new ClickListener() {
   
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                            AssetLoaderSpace.music_menu.stop();
                            ((Game) Gdx.app.getApplicationListener()).setScreen(new IntroTercerNivel(game));
                    }
                   
            });
            nivel3.pad(10, 94, 10, 94);
           
           

            TextButton back = new TextButton("Atras", skin,"default");
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
            back.pad(10);
            Label heading = new Label("Seleccionar nivel", skin, "big");
            heading.setFontScale(2);
            table.add(heading).colspan(20).expandX().spaceBottom(100).row();
            //table.add(scrollPane);
            table.add(nivel1).row();
            table.add(nivel2).row();
            table.add(nivel3).row();
            table.add(back).right().spaceTop(20);
           
            stage.addActor(table);

            stage.addAction(sequence(moveTo(0, stage.getHeight()), moveTo(0, 0, .5f))); // coming in from top animation
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
