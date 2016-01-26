package uk.co.ostmodern.rest.sets.response;

import com.google.gson.annotations.SerializedName;

/**
 * Represents a set item in the REST API response for sets.
 *
 * @author rahulsingh
 */
public class SetItem {

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
}
