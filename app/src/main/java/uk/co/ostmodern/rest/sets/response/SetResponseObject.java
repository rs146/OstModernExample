package uk.co.ostmodern.rest.sets.response;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;

/**
 * Set Response Object that is returned from the API.
 *
 * @author rahulsingh
 */
public class SetResponseObject implements Parcelable {

    @SerializedName(value = "objects")
    private List<Set> sets = new ArrayList<>();

    public List<Set> getSets() {
        return sets;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.sets);
    }

    public SetResponseObject() {
    }

    protected SetResponseObject(Parcel in) {
        this.sets = new ArrayList<>();
        in.readList(this.sets, List.class.getClassLoader());
    }

    public static final Parcelable.Creator<SetResponseObject> CREATOR = new Parcelable.Creator<SetResponseObject>() {
        public SetResponseObject createFromParcel(Parcel source) {
            return new SetResponseObject(source);
        }

        public SetResponseObject[] newArray(int size) {
            return new SetResponseObject[size];
        }
    };
}
