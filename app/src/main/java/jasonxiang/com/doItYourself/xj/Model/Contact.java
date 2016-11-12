package jasonxiang.com.doItYourself.xj.Model;

import java.util.ArrayList;

/**
 * Created by xiangjian on 2016/11/12.
 */

public class Contact {
    private String mName;
    private boolean mOnline;
    private int id;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Contact(String name, boolean online, int id) {
        mName = name;
        mOnline = online;
        this.id = id;
    }

    public String getName() {
        return mName;
    }

    public boolean isOnline() {
        return mOnline;
    }

    private static int lastContactId = 0;

    public static ArrayList<Contact> createContactsList(int numContacts) {
        ArrayList<Contact> contacts = new ArrayList<Contact>();

        for (int i = 1; i <= numContacts; i++) {
            contacts.add(new Contact("Person " + ++lastContactId, i <= numContacts / 2, lastContactId));
        }

        return contacts;
    }
}
