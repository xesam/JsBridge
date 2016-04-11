package dev.xesam.android.web.jsbridge;

/**
 * Created by xesamguo@gmail.com on 16-4-7.
 */
public class SimpleServerHandler implements ServerHandler {
    private final String name;

    public SimpleServerHandler(String name) {
        this.name = name;
    }

    @Override
    public String getHandlerName() {
        return name;
    }

    @Override
    public void handle(String param, ServerCallback serverCallback) {

    }

}
