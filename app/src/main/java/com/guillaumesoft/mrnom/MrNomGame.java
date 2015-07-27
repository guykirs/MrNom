package com.guillaumesoft.mrnom;

import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.WindowManager;
import com.badlogic.androidgames.framework.Screen;
import com.badlogic.androidgames.framework.impl.GLGame;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.PublicKey;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import tv.ouya.console.api.OuyaController;
import tv.ouya.console.api.OuyaFacade;
import tv.ouya.console.api.content.OuyaContent;
import tv.ouya.console.api.Receipt;
import java.security.spec.X509EncodedKeySpec;
import java.util.Collection;
import tv.ouya.console.api.OuyaResponseListener;
import tv.ouya.console.api.GamerInfo;
import tv.ouya.console.api.CancelIgnoringOuyaResponseListener;

public class MrNomGame extends GLGame
{
    /////////////////////////////////////////////////////////////////////////
    // CLASS VARAIBLES
    boolean firstTimeCreate = true;
    boolean firstStart = true;
    private OuyaFacade mOuyaFacade;
    String TAG;
    public  static final String DEVELOPER_ID = "ab58c9eb-0774-4cfc-8309-0c24895fc58f";
    private PublicKey mPublicKey = null;
    private static final String LOG_TAG = "Platformer";

    byte[] loadApplicationKey()
    {
        // Create a PublicKey object from the key data downloaded from the developer portal.
        try
        {
            // Read in the key.der file (downloaded from the developer portal)
            InputStream inputStream = getResources().openRawResource(R.raw.key);
            byte[] applicationKey = new byte[inputStream.available()];
            inputStream.read(applicationKey);
            inputStream.close();
            return applicationKey;
        }
        catch (Exception e)
        {
            Log.e(LOG_TAG, "Unable to load application key", e);
        }

        return null;
    }

    // CLASS FUNCTION
    @Override
    public Screen getStartScreen()
    {
        return new PressStartScreen(this);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config)
    {
        super.onSurfaceCreated(gl, config);

        if (firstTimeCreate)
        {
            Settings.load(getFileIO());
            Assets.load(this);
            firstTimeCreate = false;

            // CREATE A STATIC CONTEXT FOR THE GAME
            ScreenManager.SetGameInstances(this);

            WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
            ScreenManager.SetDisplay(wm.getDefaultDisplay());

            Point size = new Point();
            ScreenManager.display.getSize(size);

            ScreenManager.WORLD_WIDTH = size.x;
            ScreenManager.WORLD_HEIGHT = size.y;
        }
        else
        {
            Assets.reload();
        }


        if (mOuyaFacade.isRunningOnOUYASupportedHardware(this))
        {
            ScreenManager.message = "Brand=" + android.os.Build.BRAND + " Model=" + android.os.Build.MODEL +
                    " Version=" + android.os.Build.VERSION.SDK_INT +
                    " isRunningOnOUYASupportedHardware="+mOuyaFacade.isRunningOnOUYASupportedHardware();

        }
        else
        {
            ScreenManager.message = " isRunningOnOUYASupportedHardware="+mOuyaFacade.isRunningOnOUYASupportedHardware();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        ScreenManager.SetGamepadInstance(OuyaController.getControllerByPlayer(OuyaController.getPlayerNumByDeviceId(event.getDeviceId())));

        switch(ScreenManager.STATE)
        {
            case ScreenManager.GAME_PAUSED:

                if(ScreenManager.menu < 1)
                    ScreenManager.menu++;
                else
                    ScreenManager.menu = 0;
                break;

            case ScreenManager.GAME_OVER:

                if(ScreenManager.menu < 1)
                    ScreenManager.menu++;
                else
                    ScreenManager.menu = 0;
                break;

            case ScreenManager.GAME_MAINMENU:

                if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN && event.getRepeatCount() == 0)
                {
                    if(ScreenManager.menu < 5)
                        ScreenManager.menu++;
                    else
                        ScreenManager.menu = 0;
                }

                if (keyCode == KeyEvent.KEYCODE_DPAD_UP && event.getRepeatCount() == 0)
                {
                    if(ScreenManager.menu > 0)
                        ScreenManager.menu--;
                    else
                        ScreenManager.menu = 5;
                }

                break;
            case ScreenManager.GAME_OPTIONSCREEN:

                if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN && event.getRepeatCount() == 0)
                {
                    if(ScreenManager.menu == 0)
                        ScreenManager.menu = 1;
                    else
                        ScreenManager.menu = 0;
                }

                if (keyCode == KeyEvent.KEYCODE_DPAD_UP && event.getRepeatCount() == 0)
                {
                    if(ScreenManager.menu == 1)
                        ScreenManager.menu = 0;
                    else
                        ScreenManager.menu = 1;
                }

                if(keyCode == OuyaController.BUTTON_O)
                {
                    /*switch(ScreenManager.menu)
                    {
                        case 0:
                            if(Settings.soundEnabled)
                                Settings.soundEnabled = false;
                            else
                                Settings.soundEnabled = true;
                            break;
                        case 1:
                            if(Settings.musicEnabled)
                                Settings.musicEnabled = false;
                            else
                                Settings.musicEnabled = true;
                            break;
                    }

                    Settings.save(getFileIO());
                    Settings.load(getFileIO());*/
                }
                break;
        }

        return OuyaController.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event)
    {
        if(firstStart)
        {
            // GAME STARTING INITIALIZE THE GAME COMPONENTS
            if (keyCode == OuyaController.BUTTON_O)
            {
                // GAME IN MENU STATE
                ScreenManager.STATE = ScreenManager.GAME_MAINMENU;

                MainMenuScreen menu = new MainMenuScreen(this);
                setScreen(menu);
            }

            firstStart = false;
        }

        return OuyaController.onKeyUp(keyCode, event) || super.onKeyUp(keyCode, event);
    }

    @Override
    public boolean onGenericMotionEvent(MotionEvent event)
    {
        return OuyaController.onGenericMotionEvent(event) || super.onGenericMotionEvent(event);
    }

    /**
     * The callback for when the list of user receipts has been requested.
     */
    private class ReceiptListener implements OuyaResponseListener<Collection<Receipt>>
    {
        /**
         * Handle the successful fetching of the data for the receipts from the server.
         *
         //* @param receiptResponse The response from the server.
         */
        @Override
        public void onSuccess(Collection<Receipt> receipts)
        {
            ScreenManager.isDemo = true;

            for (Receipt receipt : receipts)
            {
                if(receipt.getIdentifier().equals("1011"))
                {
                    // THE PLAYERIS PLAYING THE FULL GAME
                    ScreenManager.isDemo = false;
                }
            }
        }

        /**
         * Handle a failure. Because displaying the receipts is not critical to the application we just show an error
         * message rather than asking the user to authenticate themselves just to start the application up.
         *
         * @param errorCode An HTTP error code between 0 and 999, if there was one. Otherwise, an internal error code from the
         *                  //Ouya server, documented in the  class.
         *
         * @param errorMessage Empty for HTTP error codes. Otherwise, a brief, non-localized, explanation of the error.
         *
         * @param optionalData A Map of optional key/value pairs which provide additional information.
         */

        @Override
        public void onFailure(int errorCode, String errorMessage, Bundle optionalData)
        {
            Log.w(LOG_TAG, "Request Receipts error (code " + errorCode + ": " + errorMessage + ")");
            //showError("Could not fetch receipts (error " + errorCode + ": " + errorMessage + ")");
        }

        /*
         * Handle user canceling
         */
        @Override
        public void onCancel()
        {

        }
    }

    private synchronized void requestGamerInfo()
    {
        Log.d(LOG_TAG, "Requesting gamerinfo");
        mOuyaFacade.requestGamerInfo(this, new CancelIgnoringOuyaResponseListener<GamerInfo>() {
            @Override
            public void onSuccess(GamerInfo result)
            {
                ScreenManager.mUserName = result.getUsername();
            }

            @Override
            public void onFailure(int errorCode, String errorMessage, Bundle optionalData)
            {
                ScreenManager.mUserName = "";
            }
        });
    }


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        OuyaContent mContent;

        OuyaController.init(this);

        Bundle developerInfo = new Bundle();
        developerInfo.putString(OuyaFacade.OUYA_DEVELOPER_ID, DEVELOPER_ID);
        developerInfo.putByteArray(OuyaFacade.OUYA_DEVELOPER_PUBLIC_KEY, loadApplicationKey());
        mOuyaFacade = OuyaFacade.getInstance();
        mOuyaFacade.init(this, developerInfo);

        mOuyaFacade.requestReceipts(this, new ReceiptListener());
        requestGamerInfo();
        mContent = OuyaContent.getInstance();

        try
        {
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(loadApplicationKey());
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            mPublicKey = keyFactory.generatePublic(keySpec);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        mContent.init(getApplicationContext(), mPublicKey);

    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
    }

    @Override
    public void onResume()
    {
        super.onResume();
    }

    public void onPause()
    {
        super.onPause();
    }
}
