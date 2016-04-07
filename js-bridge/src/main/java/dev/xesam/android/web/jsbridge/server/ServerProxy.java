package dev.xesam.android.web.jsbridge.server;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.webkit.JavascriptInterface;

/**
 * Created by xesamguo@gmail.com on 16-4-7.
 */
public class ServerProxy {
    public static final String JAVA_BRIDGE = "JAVA_PROXY";

    @JavascriptInterface
    public void onTransact(final String marshalling) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Log.e("ServerProxy", marshalling);
            }
        });
    }
}
