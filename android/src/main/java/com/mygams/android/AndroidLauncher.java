
package com.mygams.android;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.mygams.Main;

public class AndroidLauncher extends AndroidApplication {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Увімкнення запису помилок
        Thread.setDefaultUncaughtExceptionHandler(
            new CrashLogger(getApplicationContext())
        );

        AndroidApplicationConfiguration configuration =
            new AndroidApplicationConfiguration();

        configuration.useImmersiveMode = true;

        initialize(new Main(), configuration);
    }
}
