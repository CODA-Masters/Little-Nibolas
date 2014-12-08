package com.codamasters.LNHelpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AssetsLoader {
	
	public static Texture tNibolas, tStaticNibolas, tCamara, tGuardia;
	public static TextureRegion nibolas1, nibolas2, nibolas3, nibolas4, nibolas5, nibolas6,
	nibolas7, nibolas8, nibolas9, nibolas10, nibolas11, nibolas12, trStaticNibolas, trCamara,
	guardia1, guardia2, guardia3, guardia4, guardia5,
	guardia6, guardia7, guardia8, guardia9, guardia10;
	public static Animation nibolasAnimation, staticNibolas, nibolasAnimationCpy, staticCamara, guardiaAnimation,
	guardiaAnimationCpy;
	
	public static void load() {
		
		tNibolas = new Texture(Gdx.files.internal("data/nicosheet.png"));
		tNibolas.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		
		tStaticNibolas = new Texture(Gdx.files.internal("data/stopednibolas.png"));
		tStaticNibolas.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		
		tCamara = new Texture(Gdx.files.internal("data/securitycam.png"));
		tCamara.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		
		tGuardia = new Texture(Gdx.files.internal("data/guardsheet.png"));
		tGuardia.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		
		int distancia = 877;
		
		nibolas1 = new TextureRegion(tNibolas, 303, 117, 303, 679);
		nibolas2 = new TextureRegion(tNibolas, 303+distancia, 117, 303, 679);
		nibolas3 = new TextureRegion(tNibolas, 303+distancia*2, 117, 303, 679);
		nibolas4 = new TextureRegion(tNibolas, 303+distancia*3, 117, 303, 679);
		nibolas5 = new TextureRegion(tNibolas, 303+distancia*4, 117, 303, 679);
		nibolas6 = new TextureRegion(tNibolas, 303+distancia*5, 117, 303, 679);
		
		nibolas7 = new TextureRegion(tNibolas, 303, 117, 303, 679);
		nibolas8 = new TextureRegion(tNibolas, 303+distancia, 117, 303, 679);
		nibolas9 = new TextureRegion(tNibolas, 303+distancia*2, 117, 303, 679);
		nibolas10 = new TextureRegion(tNibolas, 303+distancia*3, 117, 303, 679);
		nibolas11 = new TextureRegion(tNibolas, 303+distancia*4, 117, 303, 679);
		nibolas12 = new TextureRegion(tNibolas, 303+distancia*5, 117, 303, 679);
		
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
		
		trStaticNibolas = new TextureRegion(tStaticNibolas, 280, 90, 344, 703);
		trCamara = new TextureRegion(tCamara,357,98,126,572);
		
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
		guardiaAnimation = new Animation(0.06f, guardias);
		guardiaAnimation.setPlayMode(Animation.PlayMode.LOOP);
		
		TextureRegion[] guardias2 = {guardia6, guardia7, guardia8, guardia9, guardia10};
		guardiaAnimationCpy = new Animation(0.06f, guardias2);
		guardiaAnimationCpy.setPlayMode(Animation.PlayMode.LOOP);
	}
	
	public void dispose(){
		tNibolas.dispose();
	}

}
