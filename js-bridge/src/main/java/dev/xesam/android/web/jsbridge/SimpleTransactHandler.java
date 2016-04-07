package dev.xesam.android.web.jsbridge;

/**
 * Created by xesamguo@gmail.com on 16-4-7.
 */
public class SimpleTransactHandler implements TransactHandler {
    private final String name;

    public SimpleTransactHandler(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void handle() {

    }
}
