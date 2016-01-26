package uk.co.ostmodern.rest.sets.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * REST response data object that maps a particular Set.
 *
 * @author rahulsingh
 */
public class Set {

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
}
