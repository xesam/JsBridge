package dev.xesam.android.web.jsbridge;

/**
 * handle [js -> java] transaction
 * 注册到 Java[WebView] 端的处理函数
 * Created by xesamguo@gmail.com on 16-4-7.
 */
public interface ServerHandler {

    String getHandlerName();

    void handle(String param, ServerCallback serverCallback);
}
