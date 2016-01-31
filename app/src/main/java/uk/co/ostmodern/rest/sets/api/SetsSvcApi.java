package uk.co.ostmodern.rest.sets.api;

import retrofit.http.GET;
import retrofit.http.Path;
import uk.co.ostmodern.rest.sets.response.SetImage;
import uk.co.ostmodern.rest.sets.response.SetResponseObject;

/**
 * Retrofit interface for accessing the sets part of the API.
 *
 * @author rahulsingh
 */
public interface SetsSvcApi {

    String SETS_SVC_PATH = "/api/sets/";
    String SETS_IMAGES_SVC_PATH = "/{imageApiPath}";

    @GET(SETS_SVC_PATH)
    SetResponseObject getSetsList();

    @GET(SETS_IMAGES_SVC_PATH)
    SetImage getSetImage(@Path("imageApiPath") String imageApiPath);

}
