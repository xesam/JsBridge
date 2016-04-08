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
    public void onTransact(String marshallingInvokeInfo, String marshallingParams) {
        ServerRequest serverRequest = new ServerRequest(marshallingInvokeInfo, marshallingParams);
        TransactHandler transactHandler = handlers.get(serverRequest.getServerMethodName());
        if (transactHandler != null) {
            transactHandler.handle(serverRequest);
        }
    }

    public void register(TransactHandler transactHandler) {
        handlers.put(transactHandler.getServerMethodName(), transactHandler);
    }
}
