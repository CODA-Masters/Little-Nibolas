package com.codamasters.LNHelpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class AssetsLoaderRome {
		
	public static Texture backgroundTexture, tNibolas;
	public static TextureRegion background, regionPlataforma, flecha, nibolas1, nibolas2, nibolas3, nibolas4, nibolas5, nibolas6, escudo;
    public static Animation animation, animPlataforma, animFlecha, animEscudo;
    public static BitmapFont font, shadow;
	public static AnimatedSprite fondo, animSpritePlataforma, animSpriteFlecha, animatedSprite, animSpriteEscudo;
	public static Music music_R;
	public static Sound win, shield, arrow, impact;
	private static Array<TextureRegion> sprites;
	private static Preferences prefs;
	
	public static void load() {
		
		tNibolas = new Texture("data/nicosheet.png");
		
		int distancia = 438;
			
		nibolas1 = new TextureRegion(tNibolas, 0, 0, 150, 335);
		nibolas2 = new TextureRegion(tNibolas, distancia, 0, 150, 335);
		nibolas3 = new TextureRegion(tNibolas, distancia*2, 0, 150, 335);
		nibolas4 = new TextureRegion(tNibolas, distancia*3, 0, 150, 335);
		nibolas5 = new TextureRegion(tNibolas, distancia*4, 0, 150, 335);
		nibolas6 = new TextureRegion(tNibolas, distancia*5, 0, 150, 335);
		 
		sprites = new Array<TextureRegion>();
		sprites.add(nibolas1);
		sprites.add(nibolas2);
		sprites.add(nibolas3);
		sprites.add(nibolas4);
		sprites.add(nibolas5);
		sprites.add(nibolas6);

		animation = new Animation(1/12f, sprites);
		animation.setPlayMode(Animation.PlayMode.LOOP);
		animatedSprite = new AnimatedSprite(animation);
		
		flecha = new TextureRegion(new Texture(Gdx.files.internal("data/flecha.png")));
		escudo = new TextureRegion(new Texture(Gdx.files.internal("data/escudo.png")));
		 
		backgroundTexture = new Texture(Gdx.files.internal("data/background.png"));  
		background = new TextureRegion(backgroundTexture);

		
		animFlecha = new Animation(1f, flecha);
		animSpriteFlecha = new AnimatedSprite(animFlecha);
		
		animEscudo = new Animation(1f, escudo);
		animSpriteEscudo = new AnimatedSprite(animEscudo);
		
		regionPlataforma = new TextureRegion(new Texture(Gdx.files.internal("data/plataforma.png")));
		animPlataforma = new Animation(1f, regionPlataforma);
		animSpritePlataforma = new AnimatedSprite(animPlataforma);
		
		font = new BitmapFont(Gdx.files.internal("data/text.fnt"));
		font.setScale(.25f, -.25f);
        shadow = new BitmapFont(Gdx.files.internal("data/shadow.fnt"));
        shadow.setScale(.25f, -.25f);
		music_R= Gdx.audio.newMusic(Gdx.files.internal("data/romano.mp3"));
		music_R.setLooping(true);
		
		win = Gdx.audio.newSound(Gdx.files.internal("data/tada.mp3"));
		shield = Gdx.audio.newSound(Gdx.files.internal("data/getitem.wav"));
		arrow = Gdx.audio.newSound(Gdx.files.internal("data/arrow.wav"));
		impact = Gdx.audio.newSound(Gdx.files.internal("data/shieldhit.wav"));
		
		prefs = Gdx.app.getPreferences("LittleNibolas");
        
        if (!prefs.contains("ScoreRoma")) {
            prefs.putInteger("ScoreRoma", 0);
        }
        if (!prefs.contains("HighScoreRoma")) {
            prefs.putInteger("HighScoreRoma", 0);
        }
		
	}
	
	public static void reloadNibolas(){		
		
		int distancia = 438;
		
		nibolas1 = new TextureRegion(tNibolas, 0, 0, 150, 335);
		nibolas2 = new TextureRegion(tNibolas, distancia, 0, 150, 335);
		nibolas3 = new TextureRegion(tNibolas, distancia*2, 0, 150, 335);
		nibolas4 = new TextureRegion(tNibolas, distancia*3, 0, 150, 335);
		nibolas5 = new TextureRegion(tNibolas, distancia*4, 0, 150, 335);
		nibolas6 = new TextureRegion(tNibolas, distancia*5, 0, 150, 335);
		 
		sprites = new Array<TextureRegion>();
		sprites.add(nibolas1);
		sprites.add(nibolas2);
		sprites.add(nibolas3);
		sprites.add(nibolas4);
		sprites.add(nibolas5);
		sprites.add(nibolas6);

		animation = new Animation(1/12f, sprites);
		animation.setPlayMode(Animation.PlayMode.LOOP);
		animatedSprite = new AnimatedSprite(animation);
		
	}
	
    public static void dispose() {
    	    	
    	font.dispose();
    	shadow.dispose();
    	backgroundTexture.dispose();
    	music_R.dispose();
    	win.dispose();
    	arrow.dispose();
    	shield.dispose();
    	impact.dispose();
    	
    	
	}
    
    public static Preferences getPref(){
    	return prefs;
    }
    public static int getScore() {
    	return prefs.getInteger("ScoreRoma");
	}
    
	public static void setHighScore(int val) {
	    prefs.putInteger("HighScoreRoma", val);
	    prefs.flush();
	}
	public static void setScore(int val) {
	    prefs.putInteger("ScoreRoma", val);
	    prefs.flush();
	}
	
	public static int getHighScore() {
	    return prefs.getInteger("HighScoreRoma");
	}

}
