package dev.xesam.android.web.jsbridge.demo;

import android.os.Parcel;
import android.os.Parcelable;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by xesamguo@gmail.com on 16-4-8.
 */
public class User implements Parcelable {
    private String name;
    private URL blog;

    public User() {
        this.name = "xesam";
        try {
            this.blog = new URL("https://github.com/xesam");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public String getName() {
        return name;
    }

    public URL getBlog() {
        return blog;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeSerializable(this.blog);
    }

    protected User(Parcel in) {
        this.name = in.readString();
        this.blog = (URL) in.readSerializable();
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
