package uk.co.ostmodern.rest.episodes.response;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Arbitrary data object that maps to an Episode data structure in the REST API.
 *
 * @author rahulsingh
 */
public class Episode implements Parcelable {

    private String subtitle;
    private String uid;
    private String slug;
    private String title;
    private List<String> items;
    private String self;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getItems() {
        return items;
    }

    public void setItems(List<String> items) {
        this.items = items;
    }

    public String getSelf() {
        return self;
    }

    public void setSelf(String self) {
        this.self = self;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.subtitle);
        dest.writeString(this.uid);
        dest.writeString(this.slug);
        dest.writeString(this.title);
        dest.writeStringList(this.items);
        dest.writeString(this.self);
    }

    public Episode() {
    }

    protected Episode(Parcel in) {
        this.subtitle = in.readString();
        this.uid = in.readString();
        this.slug = in.readString();
        this.title = in.readString();
        this.items = in.createStringArrayList();
        this.self = in.readString();
    }

    public static final Parcelable.Creator<Episode> CREATOR = new Parcelable.Creator<Episode>() {
        public Episode createFromParcel(Parcel source) {
            return new Episode(source);
        }

        public Episode[] newArray(int size) {
            return new Episode[size];
        }
    };
}
