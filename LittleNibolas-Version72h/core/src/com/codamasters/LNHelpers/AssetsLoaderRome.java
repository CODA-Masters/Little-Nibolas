package com.codamasters.LNHelpers;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.codamasters.gameobjects.Horse;
import com.codamasters.gameobjects.Lanza;
import com.codamasters.gameobjects.Plataforma;
import com.codamasters.gameobjects.Soldado;

public class AssetsLoaderRome {
		
	public static Texture backgroundTexture, tNibolas;
	public static TextureRegion background, regionPlataforma, flecha, nibolas1, nibolas2, nibolas3, nibolas4, nibolas5, nibolas6;
    public static Animation animation, animPlataforma, animFlecha;
    public static BitmapFont font, shadow;
	public static AnimatedSprite fondo, animSpritePlataforma, animSpriteFlecha, animatedSprite;
	public static Music music_R;
	private static Array<TextureRegion> sprites;
	
	public static void load() {
		
		tNibolas = new Texture("data/nicosheet.png");
		
		int distancia = 438;
			
		nibolas1 = new TextureRegion(tNibolas, 0, 0, 150, 335);
		nibolas2 = new TextureRegion(tNibolas, distancia, 10, 150, 335);
		nibolas3 = new TextureRegion(tNibolas, distancia*2, 0, 150, 335);
		nibolas4 = new TextureRegion(tNibolas, distancia*3, 0, 150, 335);
		nibolas5 = new TextureRegion(tNibolas, distancia*4, 0, 150, 335);
		nibolas6 = new TextureRegion(tNibolas, distancia*5, 10, 150, 335);
		 
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
		
		flecha = new TextureRegion(new Texture(Gdx.files.internal("flecha.png")));
		 
		 
		
		backgroundTexture = new Texture(Gdx.files.internal("background.png"));  
		background = new TextureRegion(backgroundTexture);

		
		animFlecha = new Animation(1f, flecha);
		animSpriteFlecha = new AnimatedSprite(animFlecha);
		
		regionPlataforma = new TextureRegion(new Texture(Gdx.files.internal("plataforma.jpg")));
		animPlataforma = new Animation(1f, regionPlataforma);
		animSpritePlataforma = new AnimatedSprite(animPlataforma);
		
		font = new BitmapFont(Gdx.files.internal("data/text.fnt"));
        font.setScale(.25f, -.25f);
        shadow = new BitmapFont(Gdx.files.internal("data/shadow.fnt"));
        shadow.setScale(.25f, -.25f);
	
		music_R= Gdx.audio.newMusic(Gdx.files.internal("data/romano.mp3"));
		music_R.setLooping(true);
		
	}
	
	public static void reloadNibolas(){		
		
		int distancia = 438;
		
		nibolas1 = new TextureRegion(tNibolas, 0, 0, 150, 335);
		nibolas2 = new TextureRegion(tNibolas, distancia, 10, 150, 335);
		nibolas3 = new TextureRegion(tNibolas, distancia*2, 0, 150, 335);
		nibolas4 = new TextureRegion(tNibolas, distancia*3, 0, 150, 335);
		nibolas5 = new TextureRegion(tNibolas, distancia*4, 0, 150, 335);
		nibolas6 = new TextureRegion(tNibolas, distancia*5, 10, 150, 335);
		 
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
    	
    	
	}

}
