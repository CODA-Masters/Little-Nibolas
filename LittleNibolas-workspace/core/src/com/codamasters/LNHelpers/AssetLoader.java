package com.codamasters.LNHelpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AssetLoader {

    public static Texture texture;
    public static TextureRegion bg;

    public static Animation naveAnimation;
    public static TextureRegion nave, naveDown, naveUp;

    public static TextureRegion meteor, meteor_R;
    
    public static Sound dead, flap, coin;
    public static BitmapFont font, shadow;

    public static void load() {

       // texture = new Texture(Gdx.files.internal("data/texture.png"));
        texture = new Texture(Gdx.files.internal("data/espacio.png"));
        texture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

        bg = new TextureRegion(texture, 0, 0, 877, 451);
        bg.flip(false, true);

       // grass = new TextureRegion(texture, 0, 43, 143, 11);
       // grass.flip(false, true);
        texture = new Texture(Gdx.files.internal("data/NibolasCohete.png"));
        naveDown = new TextureRegion(texture, 170, 265, 1255, 1234);
        naveDown.flip(false, true);

        texture = new Texture(Gdx.files.internal("data/NibolasCohete01.png"));
        nave = new TextureRegion(texture, 170, 265, 1255, 1234);
        nave.flip(false, true);

        texture = new Texture(Gdx.files.internal("data/NibolasCohete02.png"));
        naveUp = new TextureRegion(texture, 170, 265, 1255, 1234);
        naveUp.flip(false, true);

        TextureRegion[] naves = { naveDown, nave, naveUp };
        naveAnimation = new Animation(0.06f, naves);
        naveAnimation.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
        
        texture = new Texture(Gdx.files.internal("data/roca.png"));
        meteor = new TextureRegion(texture, 505, 505, 825, 810);
        meteor.flip(false, true);
        
        texture = new Texture(Gdx.files.internal("data/roca-Rajoy.png"));
        meteor_R = new TextureRegion(texture, 430, 300, 1120, 1250);
        meteor_R.flip(false, true);
        
        dead = Gdx.audio.newSound(Gdx.files.internal("data/dead.wav"));
        flap = Gdx.audio.newSound(Gdx.files.internal("data/flap.wav"));
        coin = Gdx.audio.newSound(Gdx.files.internal("data/coin.wav"));

        font = new BitmapFont(Gdx.files.internal("data/text.fnt"));
        font.setScale(.25f, -.25f);
        shadow = new BitmapFont(Gdx.files.internal("data/shadow.fnt"));
        shadow.setScale(.25f, -.25f);

    }

    public static void dispose() {
        // We must dispose of the texture when we are finished.
        texture.dispose();
    }
}
