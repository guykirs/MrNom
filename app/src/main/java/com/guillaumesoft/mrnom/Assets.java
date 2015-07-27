package com.guillaumesoft.mrnom;

import com.badlogic.androidgames.framework.Pixmap;
import com.badlogic.androidgames.framework.Sound;
import com.badlogic.androidgames.framework.gl.Font;
import com.badlogic.androidgames.framework.gl.Texture;
import com.badlogic.androidgames.framework.gl.TextureRegion;
import com.badlogic.androidgames.framework.impl.GLGame;

public class Assets
{
    // GAME BACKGROUND
    public static Texture background;
    public static TextureRegion backgroundRegion;

    // FONT COLLORS
    public static Texture BlackFont;
    public static Font blackfont;

    public static Texture RedFont;
    public static Font redfont;

    public static Texture BlueFont;
    public static Font bluefont;

    public static Sound click;
    public static Sound eat;
    public static Sound bitten;

    public static void load(GLGame game)
    {
        background           = new Texture(game, "background.png");
        backgroundRegion     = new TextureRegion(background, 0, 0, 512, 512);

        BlackFont = new Texture(game, "blackFont.png");
        blackfont = new Font(BlackFont, 224, 0, 16, 16, 20);

        RedFont = new Texture(game, "redFont.png");
        redfont = new Font(RedFont, 224, 0, 16, 16, 20);

        BlueFont  = new Texture(game, "blueFont.png");
        bluefont  = new Font(BlueFont, 224, 0, 16, 16, 20);

        click  = game.getAudio().newSound("click.ogg");
        eat    = game.getAudio().newSound("eat.ogg");
        bitten = game.getAudio().newSound("bitten.ogg");
    }

    public static void reload()
    {
        background.reload();
        BlackFont.reload();
        RedFont.reload();
        BlueFont.reload();
    }


    public static void playMusic()
    {
       // if(Settings.musicEnabled)
           // music.play();
    }

    public static void playSound(Sound sound)
    {
        if(Settings.soundEnabled)
            sound.play(1.0f);
    }

}
