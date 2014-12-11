package edu.utexas.ee461l.slidesnap;

import android.os.Parcel;
import android.os.Parcelable;

public class Tile implements Parcelable {
    public int mColor;
    public int mNumber;

    public static final Creator CREATOR = new Creator() {
        public Tile createFromParcel(Parcel in) {
            return new Tile(in);
        }

        public Tile[] newArray(int size) {
            return new Tile[size];
        }
    };

    public Tile(int number, int color) {
        mNumber = number;
        mColor = color;
    }

    private Tile(Parcel in) {
        mColor = in.readInt();
        mNumber = in.readInt();
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(mColor);
        out.writeInt(mNumber);
    }

    public int describeContents() {
        return 0;
    }
}
