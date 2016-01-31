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

    /**
     * Get the list of set data from the API.
     *
     * @return  Set Response Object that contains the list of set data
     */
    @GET(SETS_SVC_PATH)
    SetResponseObject getSetsList();

    /**
     * Get the set image data object for a set which will in turn contain a full url for the image.
     *
     * @param imageApiPath      image api path
     * @return                  Set Image object encapsulating a full url path for the image
     */
    @GET(SETS_IMAGES_SVC_PATH)
    SetImage getSetImage(@Path("imageApiPath") String imageApiPath);

}
