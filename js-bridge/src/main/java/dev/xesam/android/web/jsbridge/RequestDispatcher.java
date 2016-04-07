package dev.xesam.android.web.jsbridge;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import dev.xesam.android.web.jsbridge.server.ServerUnmarshalling;

/**
 * Created by xesamguo@gmail.com on 16-4-7.
 */
public class RequestDispatcher {
    public void dispatch(final ServerUnmarshalling serverUnmarshalling) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Log.e("ServerProxy", serverUnmarshalling.toString());
            }
        });
    }
}
