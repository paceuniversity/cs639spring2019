package com.pace.cs639spring.hw5;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Make this class implement Parcelable. Making an object implement Parcelable allows objects to be stored in a Bundle.
 * This makes it fairly easy to pass objects between Activities and Fragments. This is not the only way to do this,
 * but it is probably the easiest.
 *
 * https://developer.android.com/reference/android/os/Parcelable.html
 */
public class TabInformation implements Parcelable {

    int mImageResource;
    String mName;
    String mBiography;
    boolean mIncludeImage;

    TabInformation(String name, String biography, int imageResource) {
        mName = name;
        mBiography = biography;
        mImageResource = imageResource;
        mIncludeImage = mImageResource > 0;
    }

    protected TabInformation(Parcel in) {
        mImageResource = in.readInt();
        mName = in.readString();
        mBiography = in.readString();
        //parcels cannot contain booleans, only integers. So since we converted the boolean to an
        //integer when we were writing, do the reverse when we are reading.
        mIncludeImage = in.readInt() == 1;
    }

    public static final Creator<TabInformation> CREATOR = new Creator<TabInformation>() {
        @Override
        public TabInformation createFromParcel(Parcel in) {
            return new TabInformation(in);
        }

        @Override
        public TabInformation[] newArray(int size) {
            return new TabInformation[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mImageResource);
        dest.writeString(mName);
        dest.writeString(mBiography);
        //parcels cannot read booleans, only integers. So convert the mIncludeImage boolean into
        //an integer by setting it equal to 1 if true and 0 if false
        dest.writeInt(mIncludeImage ? 1 : 0);
    }
}


