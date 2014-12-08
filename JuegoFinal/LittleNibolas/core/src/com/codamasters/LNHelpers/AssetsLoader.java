package com.codamasters.LNHelpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AssetsLoader {
	
	public static Texture tNibolas, tStaticNibolas, tCamara, tGuardia, tBin, ofibg1, ofibg2, ofibg3, ofibg4, tBall;
	public static TextureRegion nibolas1, nibolas2, nibolas3, nibolas4, nibolas5, nibolas6,
	nibolas7, nibolas8, nibolas9, nibolas10, nibolas11, nibolas12, trStaticNibolas, trCamara,
	guardia1, guardia2, guardia3, guardia4, guardia5,
	guardia6, guardia7, guardia8, guardia9, guardia10,
	bin1, bin2, bin3, bin4, bin5, bin6, bin7,
	bg1, bg2, bg3, bg4, trBall;
	public static Animation nibolasAnimation, staticNibolas, nibolasAnimationCpy, staticCamara, guardiaAnimation,
	guardiaAnimationCpy, binAnimation, staticBin, staticBall;
	public static Music music_E1;
	
	public static void load() {
		
		ofibg1 = new Texture(Gdx.files.internal("data/ofibg1.png"));
		ofibg1.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		
		ofibg2 = new Texture(Gdx.files.internal("data/ofibg2.png"));
		ofibg2.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		
		ofibg3 = new Texture(Gdx.files.internal("data/ofibg3.png"));
		ofibg3.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		
		ofibg4 = new Texture(Gdx.files.internal("data/ofibg4.png"));
		ofibg4.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		
		tNibolas = new Texture(Gdx.files.internal("data/nicosheet.png"));
		tNibolas.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		
		tStaticNibolas = new Texture(Gdx.files.internal("data/stopednibolas.png"));
		tStaticNibolas.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		
		tCamara = new Texture(Gdx.files.internal("data/securitycam2.png"));
		tCamara.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		
		tGuardia = new Texture(Gdx.files.internal("data/guardsheet.png"));
		tGuardia.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		
		tBin = new Texture(Gdx.files.internal("data/binsheet.png"));
		tGuardia.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		
		tBall = new Texture(Gdx.files.internal("data/ball.png"));
		tBall.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

		
		int distancia = 438;
		
		nibolas1 = new TextureRegion(tNibolas, 0, 0, 150, 335);
		nibolas2 = new TextureRegion(tNibolas, distancia, 10, 150, 335);
		nibolas3 = new TextureRegion(tNibolas, distancia*2, 0, 150, 335);
		nibolas4 = new TextureRegion(tNibolas, distancia*3, 0, 150, 335);
		nibolas5 = new TextureRegion(tNibolas, distancia*4, 0, 150, 335);
		nibolas6 = new TextureRegion(tNibolas, distancia*5, 10, 150, 335);
		
		nibolas7 = new TextureRegion(tNibolas, 0, 0, 150, 337);
		nibolas8 = new TextureRegion(tNibolas, distancia, 10, 150, 335);
		nibolas9 = new TextureRegion(tNibolas, distancia*2, 0, 150, 335);
		nibolas10 = new TextureRegion(tNibolas, distancia*3, 0, 150, 335);
		nibolas11 = new TextureRegion(tNibolas, distancia*4, 0, 150, 335);
		nibolas12 = new TextureRegion(tNibolas, distancia*5, 10, 150, 335);
		
		distancia = 438;
		
		guardia1 = new TextureRegion(tGuardia,126,36,390,369);
		guardia2 = new TextureRegion(tGuardia,126+distancia,36,390,369);
		guardia3 = new TextureRegion(tGuardia,126+distancia*2,36,390,369);
		guardia4 = new TextureRegion(tGuardia,126+distancia*3,36,390,369);
		guardia5 = new TextureRegion(tGuardia,126+distancia*4,36,390,369);
		
		guardia6 = new TextureRegion(tGuardia,126,36,390,369);
		guardia7 = new TextureRegion(tGuardia,126+distancia,36,390,369);
		guardia8 = new TextureRegion(tGuardia,126+distancia*2,36,390,369);
		guardia9 = new TextureRegion(tGuardia,126+distancia*3,36,390,369);
		guardia10 = new TextureRegion(tGuardia,126+distancia*4,36,390,369);
		
		distancia = 440;
				
		bin1 = new TextureRegion(tBin,130,76,210,320);
		bin2 = new TextureRegion(tBin,130+distancia,76,210,320);
		bin3 = new TextureRegion(tBin,130+distancia*2,76,210,320);
		bin4 = new TextureRegion(tBin,130+distancia*3,76,210,320);
		bin5 = new TextureRegion(tBin,130+distancia*4,76,210,320);
		bin6 = new TextureRegion(tBin,130+distancia*5,76,210,320);
		bin7 = new TextureRegion(tBin,130,513,210,320);
		
		trStaticNibolas = new TextureRegion(tStaticNibolas, 280, 90, 344, 703);
		trCamara = new TextureRegion(tCamara);
		bg1 = new TextureRegion(ofibg1);
		bg2 = new TextureRegion(ofibg2);
		bg3 = new TextureRegion(ofibg3);
		bg4 = new TextureRegion(ofibg4);
		trBall = new TextureRegion(tBall);
		
		TextureRegion[] nibolases = { nibolas1, nibolas2, nibolas3, nibolas4, nibolas5, nibolas6 };
		nibolasAnimation= new Animation(0.06f, nibolases);
		nibolasAnimation.setPlayMode(Animation.PlayMode.LOOP);
		
		TextureRegion[] tNibolas1 = {trStaticNibolas};
		staticNibolas = new Animation(1f,tNibolas1);
		staticNibolas.setPlayMode(PlayMode.NORMAL);
		
		TextureRegion[] nibolases2 = { nibolas7, nibolas8, nibolas9, nibolas10, nibolas11, nibolas12 };
		nibolasAnimationCpy= new Animation(0.06f, nibolases2);
		nibolasAnimationCpy.setPlayMode(Animation.PlayMode.LOOP);
		
		TextureRegion[] camara = {trCamara};
		staticCamara = new Animation(1f,camara);
		staticCamara.setPlayMode(Animation.PlayMode.NORMAL);
		
		TextureRegion[] guardias = {guardia1, guardia2, guardia3, guardia4, guardia5};
		guardiaAnimation = new Animation(0.1f, guardias);
		guardiaAnimation.setPlayMode(Animation.PlayMode.LOOP);
		
		TextureRegion[] guardias2 = {guardia6, guardia7, guardia8, guardia9, guardia10};
		guardiaAnimationCpy = new Animation(0.1f, guardias2);
		guardiaAnimationCpy.setPlayMode(Animation.PlayMode.LOOP);
		
		TextureRegion[] tBin1 = {bin1};
		staticBin = new Animation(1f,tBin1);
		staticBin.setPlayMode(PlayMode.NORMAL);
		
		TextureRegion[] bins = {bin1, bin2, bin3, bin4, bin5, bin6, bin7};
		binAnimation = new Animation(0.15f, bins);
		binAnimation.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
		
		TextureRegion[] ball = {trBall};
		staticBall = new Animation(1, ball);
		staticBall.setPlayMode(PlayMode.NORMAL);
		
		music_E1 = Gdx.audio.newMusic(Gdx.files.internal("data/Musica.mp3"));
        music_E1.setLooping(true);
	}
	
	public void dispose(){
		tNibolas.dispose();
		tStaticNibolas.dispose();
		tCamara.dispose();
		tGuardia.dispose();
		tBin.dispose();
		ofibg1.dispose();
		ofibg2.dispose();
		ofibg3.dispose();
		ofibg4.dispose();
		tBall.dispose();
	}

}
