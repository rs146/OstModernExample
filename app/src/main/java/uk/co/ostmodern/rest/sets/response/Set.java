package uk.co.ostmodern.rest.sets.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * REST response data object that maps a particular Set.
 *
 * @author rahulsingh
 */
public class Set implements Parcelable {

    private String title;
    private String self;
    private String uid;
    private String slug;
    private String summary;
    private String body;
    @SerializedName(value="hierarchy_url")
    private String hierarchyUrl;
    @SerializedName(value="film_count")
    private int filmCount;
    private List<SetItem> items;
    @SerializedName(value = "image_urls")
    private List<String> imageUrls;
    private String created;
    @SerializedName(value = "ends_on")
    private String endsOn;

    private List<SetImage> setImageList;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSelf() {
        return self;
    }

    public void setSelf(String self) {
        this.self = self;
    }

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

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getHierarchyUrl() {
        return hierarchyUrl;
    }

    public void setHierarchyUrl(String hierarchyUrl) {
        this.hierarchyUrl = hierarchyUrl;
    }

    public int getFilmCount() {
        return filmCount;
    }

    public void setFilmCount(int filmCount) {
        this.filmCount = filmCount;
    }

    public List<SetItem> getItems() {
        return items;
    }

    public void setItems(List<SetItem> items) {
        this.items = items;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getEndsOn() {
        return endsOn;
    }

    public void setEndsOn(String endsOn) {
        this.endsOn = endsOn;
    }

    public List<SetImage> getSetImageList() {
        return setImageList;
    }

    public void setSetImageList(List<SetImage> setImageList) {
        this.setImageList = setImageList;
    }

    public void addSetImage(SetImage setImage) {
        if (setImageList == null) {
            setImageList = new ArrayList<>();
        }
        setImageList.add(setImage);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.self);
        dest.writeString(this.uid);
        dest.writeString(this.slug);
        dest.writeString(this.summary);
        dest.writeString(this.body);
        dest.writeString(this.hierarchyUrl);
        dest.writeInt(this.filmCount);
        dest.writeTypedList(items);
        dest.writeStringList(this.imageUrls);
        dest.writeString(this.created);
        dest.writeString(this.endsOn);
        dest.writeTypedList(setImageList);
    }

    public Set() {
    }

    protected Set(Parcel in) {
        this.title = in.readString();
        this.self = in.readString();
        this.uid = in.readString();
        this.slug = in.readString();
        this.summary = in.readString();
        this.body = in.readString();
        this.hierarchyUrl = in.readString();
        this.filmCount = in.readInt();
        this.items = in.createTypedArrayList(SetItem.CREATOR);
        this.imageUrls = in.createStringArrayList();
        this.created = in.readString();
        this.endsOn = in.readString();
        this.setImageList = in.createTypedArrayList(SetImage.CREATOR);
    }

    public static final Parcelable.Creator<Set> CREATOR = new Parcelable.Creator<Set>() {
        public Set createFromParcel(Parcel source) {
            return new Set(source);
        }

        public Set[] newArray(int size) {
            return new Set[size];
        }
    };
}
