package com.codamasters.LNHelpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AssetsLoader {
	
	public static Texture tNibolas;
	public static TextureRegion nibolas1, nibolas2, nibolas3, nibolas4, nibolas5, nibolas6,
	nibolas7, nibolas8, nibolas9, nibolas10, nibolas11, nibolas12;
	public static Animation nibolasAnimation, staticNibolas, nibolasAnimationCpy;
	
	public static void load() {
		
		tNibolas = new Texture(Gdx.files.internal("data/nicosheet.png"));
		tNibolas.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		
		
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
		
		
		TextureRegion[] nibolases = { nibolas1, nibolas2, nibolas3, nibolas4, nibolas5, nibolas6 };
		nibolasAnimation= new Animation(0.06f, nibolases);
		nibolasAnimation.setPlayMode(Animation.PlayMode.LOOP);
		
		TextureRegion[] tNibolas1 = {nibolas1};
		staticNibolas = new Animation(1f,tNibolas1);
		staticNibolas.setPlayMode(PlayMode.NORMAL);
		
		TextureRegion[] nibolases2 = { nibolas7, nibolas8, nibolas9, nibolas10, nibolas11, nibolas12 };
		nibolasAnimationCpy= new Animation(0.06f, nibolases2);
		nibolasAnimationCpy.setPlayMode(Animation.PlayMode.LOOP);
	}
	
	public void dispose(){
		tNibolas.dispose();
	}

}
