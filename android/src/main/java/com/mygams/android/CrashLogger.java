
package com.mygams.android;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CrashLogger implements Thread.UncaughtExceptionHandler {

    private final Context context;
    private final Thread.UncaughtExceptionHandler previousHandler;

    public CrashLogger(Context context) {
        this.context = context.getApplicationContext();
        this.previousHandler = Thread.getDefaultUncaughtExceptionHandler();
    }

    @Override
    public void uncaughtException(Thread thread, Throwable error) {

        try {
            File logFile = new File(context.getFilesDir(), "logs.txt");

            String time = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss",
                Locale.getDefault()
            ).format(new Date());

            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            error.printStackTrace(pw);
            pw.flush();

            synchronized (CrashLogger.class) {
                FileWriter writer = new FileWriter(logFile, true);

                writer.write("\n\n================================\n");
                writer.write("CRASH: " + time + "\n");
                writer.write("THREAD: " + thread.getName() + "\n");
                writer.write("ERROR:\n");
                writer.write(sw.toString());
                writer.write("\n================================\n");

                writer.flush();
                writer.close();
            }

            Log.e("GAME_CRASH", "Game crashed", error);

        } catch (Exception e) {
            Log.e("GAME_CRASH", "Cannot write crash log", e);
        }

        if (previousHandler != null) {
            previousHandler.uncaughtException(thread, error);
        }
    }
}
