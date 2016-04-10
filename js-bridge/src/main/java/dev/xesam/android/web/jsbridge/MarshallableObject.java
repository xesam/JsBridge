package dev.xesam.android.web.jsbridge;

/**
 * Created by xesamguo@gmail.com on 16-4-8.
 */
public class MarshallableObject implements Marshallable {

    private String mContent;

    public MarshallableObject(String content) {
        mContent = content;
    }

    @Override
    public String toMarshalling() {
        return mContent;
    }
}
