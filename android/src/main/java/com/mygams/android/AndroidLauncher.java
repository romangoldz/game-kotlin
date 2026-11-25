
package com.mygams.android;

import android.os.Bundle;
import android.util.Log;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.mygams.Main;

public class AndroidLauncher extends AndroidApplication {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Підключаємо логер до запуску гри
        CrashLogger.install(getApplicationContext());

        CrashLogger.log("AndroidLauncher: onCreate started");

        AndroidApplicationConfiguration configuration =
            new AndroidApplicationConfiguration();

        configuration.useImmersiveMode = true;

        CrashLogger.log("Initializing libGDX");

        initialize(new Main(), configuration);

        CrashLogger.log("libGDX initialized");
    }
}
