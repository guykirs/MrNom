package com.guillaumesoft.mrnom;


import android.content.Intent;
import com.badlogic.androidgames.framework.Game;
import com.badlogic.androidgames.framework.gl.Camera2D;
import com.badlogic.androidgames.framework.gl.SpriteBatcher;
import com.badlogic.androidgames.framework.impl.GLScreen;
import javax.microedition.khronos.opengles.GL10;
import tv.ouya.console.api.OuyaController;

//////////////////////////////////////////////////////////////////
// june 25 2014
// Guillaume Swolfs
// guillaumesoft
// MainMenuScreen class
//////////////////////////////////////////////////////////////////
public class MainMenuScreen extends GLScreen
{
    /////////////////////////////////////////
    // CLASS VARAIBLES
    private SpriteBatcher batcher;
    private float scale = 0.0f;
    Intent openIntent;
    private Camera2D guiCam;

    /////////////////////////////////////////
    // CLASS CONSTRUCTOR
    /////////////////////////////////////////
    public MainMenuScreen(Game game)
    {
        super(game);

        batcher  = new SpriteBatcher(glGraphics, 100);
        guiCam   = new Camera2D(glGraphics, 1920, 1080);

        openIntent = new Intent(ScreenManager.game.getPackageName()+".ACTION2");
    }

    @Override
    public void update(float deltaTime)
    {
        if (ScreenManager.currentInput.getButton(OuyaController.BUTTON_O))
        {
            switch(ScreenManager.menu)
            {
                case 0:
                    // PLAY SCREEN
                    ScreenManager.STATE = ScreenManager.GAME_PLAYSCREEN;
                    GameScreen gamescreen = new GameScreen(game);
                    game.setScreen(gamescreen);
                    break;

                case 1:
                    /*// CREDIT SCREEN
                    GameCreditScreen creditscreen = new GameCreditScreen(game);
                    game.setScreen(creditscreen);
*/
                    break;
                case 2:
                   /* // OPTIONS SCREEN
                    GameOptionScreen optionscreen = new GameOptionScreen(game);
                    game.setScreen(optionscreen);*/

                    break;
                case 3:
                   /* // HIGHSCORE SCREEN
                    HighScoreScreen highscorescreen = new HighScoreScreen(game);
                    game.setScreen(highscorescreen);*/

                    break;
                case 4:
                    /*// HELP SCREEN
                    GameHelpScreen gamehelpscreen = new GameHelpScreen(game);
                    game.setScreen(gamehelpscreen);*/

                    break;
                case 5:
                    // EXIT THE GAME
                    ScreenManager.game.finish();
                    break;
            }
        }

        // Pulsate the size of the selected menu entry.
        double time = System.currentTimeMillis() / 60;
        float pulsate = (float) Math.sin(time * 6) + 1;

        scale = 1 + pulsate * 0.05f;// * selectionFade;
    }

    @Override
    public void present(float deltaTime)
    {
        GL10 gl = glGraphics.getGL();
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

        guiCam.setViewportAndMatrices();

        gl.glEnable(GL10.GL_TEXTURE_2D);

        batcher.beginBatch(Assets.background);

           // SHOW THE BACKGROUND IMAGE TO THE MAINMENU SCREEN
           batcher.drawSprite(guiCam.position.x, guiCam.position.y, 1920, 1080, Assets.backgroundRegion);

        batcher.endBatch();

        gl.glEnable(GL10.GL_BLEND);
        gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);

        batcher.beginBatch(Assets.BlackFont);

           // PRINT THE GAME NAME ON THE SCREEN
           Assets.blackfont.drawText(batcher, "MrNom", 1920 / 2 - 300, 800, 45.0f, 45.0f);

        batcher.endBatch();

        batcher.beginBatch(Assets.RedFont);

        switch(ScreenManager.menu)
        {
            case 0:
                //PLAY SCREEN SELECTED
                Assets.redfont.drawText(batcher, "Play",       1920 /2 - 80, 1080 /2, 50.0f * scale, 50.0f * scale);
                Assets.redfont.drawText(batcher, "Credit",     400, 1080 /2  - 250,   25.0f, 25.0f );
                Assets.redfont.drawText(batcher, "Options",    600, 1080 /2  - 250,   25.0f, 25.0f );
                Assets.redfont.drawText(batcher, "Highscore",  800, 1080 /2  - 250,   25.0f, 25.0f );
                Assets.redfont.drawText(batcher, "Help",      1100, 1080 /2  - 250,   25.0f, 25.0f );
                Assets.redfont.drawText(batcher, "Exit",      1300, 1080 / 2 - 250,   25.0f, 25.0f );

                break;
            case 1:
                //PLAY SCREEN SELECTED
                //PLAY SCREEN SELECTED
                Assets.redfont.drawText(batcher, "Play",       1920 /2 - 80, 1080 /2, 50.0f, 50.0f);
                Assets.redfont.drawText(batcher, "Credit",     400, 1080 /2  - 250,   25.0f * scale, 25.0f * scale);
                Assets.redfont.drawText(batcher, "Options",    600, 1080 /2  - 250,   25.0f, 25.0f );
                Assets.redfont.drawText(batcher, "Highscore",  800, 1080 /2  - 250,   25.0f, 25.0f );
                Assets.redfont.drawText(batcher, "Help",      1100, 1080 /2  - 250,   25.0f, 25.0f );
                Assets.redfont.drawText(batcher, "Exit",      1300, 1080 / 2 - 250,   25.0f, 25.0f );

                break;
            case 2:
                //PLAY SCREEN SELECTED
                Assets.redfont.drawText(batcher, "Play",       1920 /2 - 80, 1080 /2, 50.0f, 50.0f);
                Assets.redfont.drawText(batcher, "Credit",     400, 1080 /2  - 250,   25.0f, 25.0f);
                Assets.redfont.drawText(batcher, "Options",    600, 1080 /2  - 250,   25.0f * scale, 25.0f * scale );
                Assets.redfont.drawText(batcher, "Highscore",  800, 1080 /2  - 250,   25.0f, 25.0f );
                Assets.redfont.drawText(batcher, "Help",      1100, 1080 /2  - 250,   25.0f, 25.0f );
                Assets.redfont.drawText(batcher, "Exit",      1300, 1080 / 2 - 250,   25.0f, 25.0f );

                break;

            case 3:
                //PLAY SCREEN SELECTED
                //PLAY SCREEN SELECTED
                Assets.redfont.drawText(batcher, "Play",       1920 /2 - 80, 1080 /2, 50.0f, 50.0f);
                Assets.redfont.drawText(batcher, "Credit",     400, 1080 /2  - 250,   25.0f, 25.0f);
                Assets.redfont.drawText(batcher, "Options",    600, 1080 /2  - 250,   25.0f, 25.0f);
                Assets.redfont.drawText(batcher, "Highscore",  800, 1080 /2  - 250,   25.0f * scale, 25.0f * scale);
                Assets.redfont.drawText(batcher, "Help",      1100, 1080 /2  - 250,   25.0f, 25.0f );
                Assets.redfont.drawText(batcher, "Exit",      1300, 1080 / 2 - 250,   25.0f, 25.0f );

                break;
            case 4:
                //PLAY SCREEN SELECTED
                //PLAY SCREEN SELECTED
                Assets.redfont.drawText(batcher, "Play",       1920 /2 - 80, 1080 /2, 50.0f, 50.0f);
                Assets.redfont.drawText(batcher, "Credit",     400, 1080 /2  - 250,   25.0f, 25.0f);
                Assets.redfont.drawText(batcher, "Options",    600, 1080 /2  - 250,   25.0f, 25.0f);
                Assets.redfont.drawText(batcher, "Highscore",  800, 1080 /2  - 250,   25.0f, 25.0f);
                Assets.redfont.drawText(batcher, "Help",      1100, 1080 /2  - 250,   25.0f * scale, 25.0f * scale);
                Assets.redfont.drawText(batcher, "Exit",      1300, 1080 / 2 - 250,   25.0f, 25.0f );

                break;
            case 5:
                //PLAY SCREEN SELECTED
                //PLAY SCREEN SELECTED
                Assets.redfont.drawText(batcher, "Play",       1920 /2 - 80, 1080 /2, 50.0f, 50.0f);
                Assets.redfont.drawText(batcher, "Credit",     400, 1080 /2 - 250,   25.0f, 25.0f  );
                Assets.redfont.drawText(batcher, "Options",    600, 1080 /2 - 250,   25.0f, 25.0f  );
                Assets.redfont.drawText(batcher, "Highscore",  800, 1080 /2 - 250,   25.0f, 25.0f );
                Assets.redfont.drawText(batcher, "Help",      1100, 1080 /2 - 250,   25.0f, 25.0f );
                Assets.redfont.drawText(batcher, "Exit",      1300, 1080 /2 - 250,   25.0f * scale, 25.0f  * scale);

                break;
            case 6:
                Assets.redfont.drawText(batcher, "Play",       1920 /2 - 80, 1080 /2, 50.0f, 50.0f);
                Assets.redfont.drawText(batcher, "Credit",     1920 /2 - 80, 1080 /2 - 250, 25.0f, 25.0f );
                Assets.redfont.drawText(batcher, "Options",    1920 /2 - 80, 1080 /2 - 250, 25.0f, 25.0f );
                Assets.redfont.drawText(batcher, "Highscore",  1920 /2 - 80, 1080 /2 - 250, 25.0f, 25.0f );
                Assets.redfont.drawText(batcher, "Help",       1920 /2 - 80, 1080 /2 - 250, 25.0f, 25.0f );
                Assets.redfont.drawText(batcher, "Exit",       1920 /2 - 80, 1080 /2 - 250, 25.0f , 25.0f);

                break;
        }

        batcher.endBatch();

        batcher.beginBatch(Assets.BlackFont);

           Assets.blackfont.drawText(batcher, "Press O to select", 1920 /2 - 300, 1080 / 2 - 400, 35.0f, 35.0f);

        batcher.endBatch();

        gl.glDisable(GL10.GL_BLEND);
    }

    @Override
    public void pause()
    {
        game.getCurrentScreen();
    }

    @Override
    public void resume() {  }

    @Override
    public void dispose() { }
}
