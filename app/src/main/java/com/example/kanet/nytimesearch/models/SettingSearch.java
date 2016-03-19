package com.example.kanet.nytimesearch.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by Kanet on 3/19/2016.
 */

public class SettingSearch implements Parcelable{
    private String newDeskValue;
    private int sortOrder;
    private Date beginDate;

    public String getNewDeskValue() {
        return newDeskValue;
    }

    public int getSortOrder() {
        return sortOrder;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public SettingSearch(String newDeskValue, int sortOrder, Date beginDate) {
        this.newDeskValue = newDeskValue;
        this.sortOrder = sortOrder;
        this.beginDate = beginDate;
    }

    private SettingSearch(Parcel in) {
        this.newDeskValue = in.readString();
        this.sortOrder = in.readInt();
        this.beginDate = in.readParcelable(Date.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.newDeskValue);
        dest.writeInt(this.sortOrder);
        dest.writeValue(this.beginDate);
    }

    public static final Parcelable.Creator<SettingSearch> CREATOR
            = new Parcelable.Creator<SettingSearch>() {

        // This simply calls our new constructor (typically private) and
        // passes along the unmarshalled `Parcel`, and then returns the new object!
        @Override
        public SettingSearch createFromParcel(Parcel in) {
            return new SettingSearch(in);
        }

        // We just need to copy this and change the type to match our class.
        @Override
        public SettingSearch[] newArray(int size) {
            return new SettingSearch[size];
        }
    };
}
