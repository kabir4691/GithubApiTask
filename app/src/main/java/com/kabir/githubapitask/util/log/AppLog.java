package com.kabir.githubapitask.util.log;

import android.support.annotation.NonNull;
import android.util.Log;

/**
 * Created by Kabir on 30-06-2016.
 */
public class AppLog {

    public void d(@NonNull String tag, @NonNull String msg) {

        if (msg.length() > 4000) {

            int chunkCount = msg.length() / 4000;
            for (int i = 0; i <= chunkCount; i++) {
                int max = 4000 * (i + 1);
                if (max >= msg.length()) {
                    Log.d(tag, msg.substring(4000 * i));
                } else {
                    Log.d(tag, msg.substring(4000 * i, max));
                }
            }
        } else {
            Log.d(tag, msg);
        }
    }

    public void e(@NonNull String tag, @NonNull String msg) {

        if (msg.length() > 4000) {

            int chunkCount = msg.length() / 4000;
            for (int i = 0; i <= chunkCount; i++) {
                int max = 4000 * (i + 1);
                if (max >= msg.length()) {
                    Log.e(tag, msg.substring(4000 * i));
                } else {
                    Log.e(tag, msg.substring(4000 * i, max));
                }
            }
        } else {
            Log.e(tag, msg);
        }
    }
}
