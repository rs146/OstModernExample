package uk.co.ostmodern.rest.episodes.api;

import retrofit.http.GET;
import uk.co.ostmodern.rest.episodes.response.Episode;

/**
 * Retrofit API for accessing the Episodes part of the REST API.
 *
 * @author rahulsingh
 */
public interface EpisodesSvcApi {

    String SAMPLE_EPISODE_API_PATH = "/api/episodes/film_33a1c5fea05d4955ac3288bda19dcb16/";

    /**
     * Returns a sample episode from the REST API.
     *
     * @return      Episode data object
     */
    @GET(SAMPLE_EPISODE_API_PATH)
    Episode getEpisode();

}
