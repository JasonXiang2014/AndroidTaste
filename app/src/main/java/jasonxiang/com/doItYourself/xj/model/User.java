package jasonxiang.com.doityourself.xj.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by JasonXiang on 2017/1/18.
 */

public class User implements Parcelable {
    private int userId;
    private String userName;
    private boolean isMale;
    private Book book;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.userId);
        dest.writeString(this.userName);
        dest.writeByte(this.isMale ? (byte) 1 : (byte) 0);
        dest.writeParcelable(this.book, flags);
    }

    public User(int userId, String userName, boolean isMale, Book book) {
        this.userId = userId;
        this.userName = userName;
        this.isMale = isMale;
        this.book = book;
    }

    protected User(Parcel in) {
        this.userId = in.readInt();
        this.userName = in.readString();
        this.isMale = in.readByte() != 0;
        this.book = in.readParcelable(Book.class.getClassLoader());
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

    @Override
    public String toString() {
        return '[' + userId + userName + isMale + book.toString() + ']';
    }
}
