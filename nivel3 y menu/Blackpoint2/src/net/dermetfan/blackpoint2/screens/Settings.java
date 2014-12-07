package net.dermetfan.blackpoint2.screens;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;
import net.dermetfan.blackpoint2.Blackpoint2;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class Settings implements Screen {

	private Stage stage;
	private Table table;
	private Skin skin;

	/** @return the directory the levels will be saved to and read from */
	public static FileHandle levelDirectory() {
		String prefsDir = Gdx.app.getPreferences(Blackpoint2.TITLE).getString("leveldirectory").trim();
		if(prefsDir != null && !prefsDir.equals(""))
			return Gdx.files.absolute(prefsDir);
		else
			return Gdx.files.absolute(Gdx.files.external(Blackpoint2.TITLE + "/levels").path()); // return default level directory
	}

	/** @return if vSync is enabled */
	public static boolean vSync() {
		return Gdx.app.getPreferences(Blackpoint2.TITLE).getBoolean("vsync");
	}

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

		final CheckBox vSyncCheckBox = new CheckBox("vSync", skin,"default");
		vSyncCheckBox.setChecked(vSync());

		final TextField levelDirectoryInput = new TextField(levelDirectory().path(), skin,"default"); // creating a new TextField with the current level directory already written in it
		levelDirectoryInput.setMessageText("Nivel de directorio"); // set the text to be shown when nothing is in the TextField

		final TextButton back = new TextButton("Atras", skin,"default");
		back.pad(10);

		ClickListener buttonHandler = new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {
				// event.getListenerActor() returns the source of the event, e.g. a button that was clicked
				if(event.getListenerActor() == vSyncCheckBox) {
					// save vSync
					Gdx.app.getPreferences(Blackpoint2.TITLE).putBoolean("vsync", vSyncCheckBox.isChecked());

					// set vSync
					Gdx.graphics.setVSync(vSync());

					Gdx.app.log(Blackpoint2.TITLE, "vSync " + (vSync() ? "enabled" : "disabled"));
				} else if(event.getListenerActor() == back) {
					// save level directory
					String actualLevelDirectory = levelDirectoryInput.getText().trim().equals("") ? Gdx.files.getExternalStoragePath() + Blackpoint2.TITLE + "/levels" : levelDirectoryInput.getText().trim(); // shortened form of an if-statement: [boolean] ? [if true] : [else] // String#trim() removes spaces on both sides of the string
					Gdx.app.getPreferences(Blackpoint2.TITLE).putString("NivelDeDirectorio", actualLevelDirectory);

					// save the settings to preferences file (Preferences#flush() writes the preferences in memory to the file)
					Gdx.app.getPreferences(Blackpoint2.TITLE).flush();

					Gdx.app.log(Blackpoint2.TITLE, "Configuración guardada");

					stage.addAction(sequence(moveTo(0, stage.getHeight(), .5f), run(new Runnable() {

						@Override
						public void run() {
							((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu());
						}
					})));
				}
			}
		};

		vSyncCheckBox.addListener(buttonHandler);

		back.addListener(buttonHandler);

		// putting everything in the table
		table.add(new Label("Opciones", skin, "big")).spaceBottom(50).colspan(3).expandX().row();
		table.add();
		table.add("Nivel de directorio");
		table.add().row();
		table.add(vSyncCheckBox).top().expandY();
		table.add(levelDirectoryInput).top().fillX();
		table.add(back).bottom().right();

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
