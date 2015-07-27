package com.guillaumesoft.mrnom;


import android.view.Display;
import com.badlogic.androidgames.framework.impl.GLGame;

import tv.ouya.console.api.OuyaController;

////////////////////////////////////////////////////////////////////
// SCREENMANAGER CLASS
// JULY 14 2015
////////////////////////////////////////////////////////////////////
public abstract class ScreenManager
{
    ////////////////////////////////////////////////////////////////
    // GET THE GAME INSTANCE AND HOLD IT FOR OTHERE CLASSES
    public static GLGame GetGameInstances()
    {
        return game;
    }

    public static void SetGameInstances(GLGame value)
    {
        game = value;
    }
    // GET AND SET GAME INSTANCES
    public static GLGame game;

    ////////////////////////////////////////////////////////
    // INLINE FUNCTION FOR GETING AND SETING USERNAME VALUE
    ////////////////////////////////////////////////////////
    public static String GetUserName()
    {
        return mUserName;
    }

    public static void SetUserName(String value)
    {
        mUserName = value;
    }

    // GET AND SET USER NAME
    public static String mUserName;

    /////////////////////////////////////////////////////////
    //INLINE FUNCTION FOR GETING / SETTING DEMO VALUE
    /////////////////////////////////////////////////////////
    public static boolean GetDemo()
    {
        return isDemo;
    }

    public static void SetDemo(boolean value)
    {
        isDemo = value;
    }
    public static boolean isDemo;


    public static String message;

    /////////////////////////////////////////////////////////////////////////
    // GET AND SET GAME CONTROLLER ID
    public static  OuyaController GetGamepadInstance()
    {
        return currentInput;
    }

    public static void SetGamepadInstance(OuyaController value)
    {
        currentInput = value;
    }

    public static OuyaController currentInput;


    ////////////////////////////////////////////////////////////////////////
    // GET AND SET FUNCTION FOR THE SCORE
    public static void SetScore(int value)
    {
        score += value;
    }

    public static int GetScore()
    {
        return score;
    }

    ///////////////////////////////////////////////////////////
    // GET SET FUNCTION FOR DISPLAY SIZE
    public static Display GetDisplay()
    {
        return display;
    }

    public static void SetDisplay(Display value)
    {
        display = value;
    }

    public static Display display;

    public static int WORLD_WIDTH;
    public static int WORLD_HEIGHT;

    public static int score;

    public static int menu;
}
