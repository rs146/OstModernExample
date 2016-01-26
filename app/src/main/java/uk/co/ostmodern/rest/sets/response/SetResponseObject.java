package uk.co.ostmodern.rest.sets.response;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;

/**
 * Set Response Object that is returned from the API.
 *
 * @author rahulsingh
 */
public class SetResponseObject {

    @SerializedName(value = "objects")
    private List<Set> sets = new ArrayList<>();

    public List<Set> getSets() {
        return sets;
    }
}
