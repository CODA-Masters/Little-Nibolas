package net.dermetfan.blackpoint2.screens;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;
import net.dermetfan.blackpoint2.Blackpoint2;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class LevelMenu implements Screen {

	private Stage stage;
	private Table table;
	private Skin skin;

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		stage.act(delta);
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		stage.setViewport(width, height, false);
		table.invalidateHierarchy();
	}

	@Override
	public void show() {
		stage = new Stage();

		Gdx.input.setInputProcessor(stage);

		skin = new Skin(Gdx.files.internal("ui/menuSkin.json"), new TextureAtlas("ui/atlas.pack"));

		table = new Table(skin);
		table.setFillParent(true);

		List list = new List(new String[] {"Nivel 1: Actualidad","Nivel 2: Zona Romana","Nivel 3: Zona Espacial","Nivel 4: Zona Futuro"}, skin);
		ScrollPane scrollPane = new ScrollPane(list, skin,"default");
		

		TextButton play = new TextButton("Jugar", skin, "default");
		play.addListener(new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {
				((Game) Gdx.app.getApplicationListener()).setScreen(new Play());
			}
			
		});
		play.pad(10);

		TextButton back = new TextButton("Atras", skin,"default");
		back.addListener(new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {
				stage.addAction(sequence(moveTo(0, stage.getHeight(), .5f), run(new Runnable() {

					@Override
					public void run() {
						((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu());
					}
					
				})));
			}
		});
		back.pad(10);
		Label heading = new Label("Seleccionar nivel", skin, "big");
		heading.setFontScale(2);
		table.add(heading).colspan(20).expandX().spaceBottom(100).row();
		table.add(scrollPane);
		table.add(play).uniformX().bottom().left();
		table.add(back).uniformX().bottom().right();

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
	}

}
