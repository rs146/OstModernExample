package uk.co.ostmodern.rest.sets.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Represents a set item in the REST API response for sets.
 *
 * @author rahulsingh
 */
public class SetItem implements Parcelable {

    private String self;
    @SerializedName(value = "content_type")
    private String contentType;
    @SerializedName(value = "set_url")
    private String setUrl;
    @SerializedName(value = "schedule_url")
    private String scheduleUrl;

    public String getSelf() {
        return self;
    }

    public void setSelf(String self) {
        this.self = self;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getSetUrl() {
        return setUrl;
    }

    public void setSetUrl(String setUrl) {
        this.setUrl = setUrl;
    }

    public String getScheduleUrl() {
        return scheduleUrl;
    }

    public void setScheduleUrl(String scheduleUrl) {
        this.scheduleUrl = scheduleUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.self);
        dest.writeString(this.contentType);
        dest.writeString(this.setUrl);
        dest.writeString(this.scheduleUrl);
    }

    public SetItem() {
    }

    protected SetItem(Parcel in) {
        this.self = in.readString();
        this.contentType = in.readString();
        this.setUrl = in.readString();
        this.scheduleUrl = in.readString();
    }

    public static final Parcelable.Creator<SetItem> CREATOR = new Parcelable.Creator<SetItem>() {
        public SetItem createFromParcel(Parcel source) {
            return new SetItem(source);
        }

        public SetItem[] newArray(int size) {
            return new SetItem[size];
        }
    };
}
