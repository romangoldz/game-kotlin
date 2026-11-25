
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

public class CrashLogger {

    private static File logFile;
    private static Thread.UncaughtExceptionHandler previousHandler;

    public static void install(Context context) {
        logFile = new File(context.getFilesDir(), "logs.txt");

        previousHandler = Thread.getDefaultUncaughtExceptionHandler();

        Thread.setDefaultUncaughtExceptionHandler((thread, error) -> {
            log("========== CRASH ==========");
            log("Time: " + new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss",
                Locale.getDefault()
            ).format(new Date()));

            log("Thread: " + thread.getName());

            StringWriter sw = new StringWriter();
            error.printStackTrace(new PrintWriter(sw));

            log(sw.toString());
            log("========== END CRASH ==========");

            if (previousHandler != null) {
                previousHandler.uncaughtException(thread, error);
            }
        });

        log("CrashLogger installed");
        log("Log file: " + logFile.getAbsolutePath());
    }

    public static synchronized void log(String message) {
        try {
            if (logFile == null) return;

            String time = new SimpleDateFormat(
                "HH:mm:ss.SSS",
                Locale.getDefault()
            ).format(new Date());

            FileWriter writer = new FileWriter(logFile, true);

            writer.write("[" + time + "] " + message + "\n");
            writer.flush();
            writer.close();

        } catch (Exception e) {
            Log.e("CrashLogger", "Cannot write log", e);
        }
    }
}
