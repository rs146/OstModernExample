package uk.co.ostmodern.rest.sets.api;

import retrofit.http.GET;
import uk.co.ostmodern.rest.sets.response.SetResponseObject;

/**
 * Retrofit interface for accessing the sets part of the API.
 *
 * @author rahulsingh
 */
public interface SetsSvcApi {

    String SETS_SVC_PATH = "/api/sets/";

    @GET(SETS_SVC_PATH)
    SetResponseObject getSetsList();
}
