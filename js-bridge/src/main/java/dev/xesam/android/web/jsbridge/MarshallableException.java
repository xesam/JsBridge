package dev.xesam.android.web.jsbridge;

/**
 * Created by xesamguo@gmail.com on 16-4-8.
 */
public class MarshallableException extends Exception implements Marshallable {

    private String mContent;

    public MarshallableException(String content) {
        super(content);
        mContent = content;
    }

    @Override
    public String toMarshalling() {
        return mContent;
    }
}
