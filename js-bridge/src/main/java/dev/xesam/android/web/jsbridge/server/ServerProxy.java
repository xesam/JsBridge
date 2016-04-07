package dev.xesam.android.web.jsbridge.server;

import android.webkit.JavascriptInterface;

import dev.xesam.android.web.jsbridge.RequestDispatcher;

/**
 * Created by xesamguo@gmail.com on 16-4-7.
 */
public class ServerProxy {
    private RequestDispatcher mRequestDispatcher;
    public static final String JAVA_BRIDGE = "JAVA_PROXY";

    public ServerProxy(dev.xesam.android.web.jsbridge.RequestDispatcher requestDispatcher) {
        mRequestDispatcher = requestDispatcher;
    }

    @JavascriptInterface
    public void onTransact(final String marshalling) {
        ServerUnmarshalling serverUnmarshalling = new ServerUnmarshalling(marshalling);
        mRequestDispatcher.dispatch(serverUnmarshalling);
    }
}
