package dev.xesam.android.web.jsbridge.server;

/**
 * Created by xesamguo@gmail.com on 16-4-7.
 */
public interface ServerHandler {
    String getHandlerName();

    void handle(String param, ServerCallback serverCallback);
}
