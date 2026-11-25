
package com.mygams.android;

import android.os.Bundle;
import android.util.Log;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.mygams.Main;

import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AndroidLauncher extends AndroidApplication {

    private void writeLog(String message) {
        try {
            String time = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss.SSS",
                Locale.getDefault()
            ).format(new Date());

            String line = "[" + time + "] " + message + "\n";

            FileOutputStream fos = openFileOutput(
                "logs.txt",
                MODE_APPEND
            );

            fos.write(line.getBytes("UTF-8"));
            fos.flush();
            fos.getFD().sync();
            fos.close();

            Log.i("GAME_LOG", line);

        } catch (Exception e) {
            Log.e("GAME_LOG", "LOG WRITE FAILED", e);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        writeLog("========== APPLICATION START ==========");
        writeLog("Package: " + getPackageName());
        writeLog("Files directory: " + getFilesDir().getAbsolutePath());

        Thread.setDefaultUncaughtExceptionHandler((thread, error) -> {

            writeLog("========== CRASH ==========");
            writeLog("Thread: " + thread.getName());

            StringWriter sw = new StringWriter();
            error.printStackTrace(new PrintWriter(sw));

            writeLog(sw.toString());
            writeLog("========== END CRASH ==========");

            android.os.Process.killProcess(
                android.os.Process.myPid()
            );
            System.exit(10);
        });

        writeLog("Crash handler installed");

        try {
            AndroidApplicationConfiguration configuration =
                new AndroidApplicationConfiguration();

            configuration.useImmersiveMode = true;

            writeLog("Initializing game");

            initialize(new Main(), configuration);

            writeLog("Game initialization requested");

        } catch (Throwable error) {
            writeLog("INITIALIZATION ERROR: " + error);
            throw error;
        }
    }
}
