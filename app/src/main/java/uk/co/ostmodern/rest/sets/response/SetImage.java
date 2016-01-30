package uk.co.ostmodern.rest.sets.response;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Represents a Set's image from the response. A {@link Set} could have many of these instances
 * associated with it.
 *
 * @author rahulsingh
 */
public class SetImage implements Parcelable {

    private String fullImageUrl;

    public String getFullImageUrl() {
        return fullImageUrl;
    }

    public void setFullImageUrl(String fullImageUrl) {
        this.fullImageUrl = fullImageUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.fullImageUrl);
    }

    public SetImage() {
    }

    protected SetImage(Parcel in) {
        this.fullImageUrl = in.readString();
    }

    public static final Parcelable.Creator<SetImage> CREATOR = new Parcelable.Creator<SetImage>() {
        public SetImage createFromParcel(Parcel source) {
            return new SetImage(source);
        }

        public SetImage[] newArray(int size) {
            return new SetImage[size];
        }
    };
}
