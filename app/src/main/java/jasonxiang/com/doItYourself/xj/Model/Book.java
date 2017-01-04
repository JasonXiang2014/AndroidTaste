package jasonxiang.com.doItYourself.xj.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by JasonXiang on 2017/1/4.
 */

public class Book implements Parcelable{

    private String name;
    private int id;
    private boolean sold;

    public Book(String name, int id , boolean sold){
        this.name = name;
        this.id = id;
        this.sold = sold;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeInt(this.id);
        dest.writeByte(this.sold ? (byte) 1 : (byte) 0);
    }

    protected Book(Parcel in) {
        this.name = in.readString();
        this.id = in.readInt();
        this.sold = in.readByte() != 0;
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel source) {
            return new Book(source);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isSold() {
        return sold;
    }

    public void setSold(boolean sold) {
        this.sold = sold;
    }

    @Override
    public String toString() {
        return '[' + name +  id + sold + ']';
    }
}
