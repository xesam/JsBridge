package dev.xesam.android.web.jsbridge.server;

import android.webkit.JavascriptInterface;

import java.util.HashMap;
import java.util.Map;

import dev.xesam.android.web.jsbridge.TransactHandler;

/**
 * Created by xesamguo@gmail.com on 16-4-7.
 */
public class ServerProxy {
    public static final String JAVA_BRIDGE = "JAVA_PROXY";

    private Map<String, TransactHandler> handlers = new HashMap<>();

    public ServerProxy() {

    }

    @JavascriptInterface
    public void onTransact(final String marshalling) {
        ServerUnmarshalling serverUnmarshalling = new ServerUnmarshalling(marshalling);
        TransactHandler transactHandler = handlers.get(serverUnmarshalling.getServerMethodName());
        if (transactHandler != null) {
            transactHandler.handle();
        }
    }

    public void register(TransactHandler transactHandler) {
        handlers.put(transactHandler.getName(), transactHandler);
    }
}
