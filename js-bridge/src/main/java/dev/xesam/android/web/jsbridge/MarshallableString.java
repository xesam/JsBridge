package dev.xesam.android.web.jsbridge;

/**
 * Created by xesamguo@gmail.com on 16-4-8.
 */
public class MarshallableString implements Marshallable {

    private String mContent;

    public MarshallableString(String content) {
        mContent = content;
    }

    @Override
    public String toMarshalling() {
        return mContent;
    }
}
