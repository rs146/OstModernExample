package uk.co.ostmodern.rest.episodes.api;

import android.content.Context;
import android.util.Log;

import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import uk.co.ostmodern.rest.episodes.response.Episode;
import uk.co.ostmodern.rest.errorhandling.ErrorRecorder;
import uk.co.ostmodern.rest.exceptions.APIConnectionException;
import uk.co.ostmodern.rest.exceptions.BadRequestException;
import uk.co.ostmodern.rest.exceptions.HttpConnectionException;
import uk.co.ostmodern.rest.exceptions.InternalServerErrorException;
import uk.co.ostmodern.rest.exceptions.ResourceNotFoundException;
import uk.co.ostmodern.rest.exceptions.ServiceUnavailableException;
import uk.co.ostmodern.rest.exceptions.UnauthorizedException;
import uk.co.ostmodern.util.Util;

/**
 * Not a true implementation of {@link EpisodesSvcApi} but it provides a proxy to the Retrofit interface
 * so that clients use this "implementation" class instead of accessing the API interface
 * directly.
 *
 * @author rahulsingh
 */
public class EpisodesSvcApiImpl {

    private static final String TAG = EpisodesSvcApiImpl.class.getSimpleName();

    private EpisodesSvcApi episodesSvcApi;
    private ErrorRecorder errorRecorder;

    /**
     * Constructor for this class.
     *
     * @param context app context
     */
    public EpisodesSvcApiImpl(Context context) {

        errorRecorder = new ErrorRecorder();
        episodesSvcApi = new RestAdapter.Builder()
                .setEndpoint(Util.getBaseEndpointUrl(context))
                .setClient(new OkClient(Util.retrieveOkHttpClient(context)))
                .setErrorHandler(errorRecorder)
                .build()
                .create(EpisodesSvcApi.class);
    }

    /**
     * Retrieve a sample episode from the REST API.
     *
     * @return                                  Episode data object
     * @throws APIConnectionException           if connection to REST API fails
     * @throws BadRequestException              if HTTP 400 status code is returned from Resource server
     * @throws UnauthorizedException            if HTTP 401 status code is returned from Resource server
     * @throws ResourceNotFoundException        if HTTP 404 status code is returned from Resource server
     * @throws InternalServerErrorException     if HTTP 500 status code is returned from Resource server
     * @throws ServiceUnavailableException      if HTTP 503 status code is returned from Resource server
     * @throws HttpConnectionException          if other HTTP error code is returned from Resource server
     */
    public Episode getEpisode() throws APIConnectionException, BadRequestException, UnauthorizedException,
            ResourceNotFoundException, InternalServerErrorException, ServiceUnavailableException,
            HttpConnectionException {

        Episode episode = new Episode();

        try {
            episode = episodesSvcApi.getEpisode();
        } catch (Exception e) {
            if (e.getCause() instanceof APIConnectionException) {
                Log.d(TAG, "API Connection handled in ApiImpl");
                throw new APIConnectionException();
            } else if (errorRecorder.getError().getKind().equals(RetrofitError.Kind.HTTP)) {
                switch (errorRecorder.getError().getResponse().getStatus()) {
                    case 400:
                        throw new BadRequestException();
                    case 401:
                        throw new UnauthorizedException();
                    case 404:
                        throw new ResourceNotFoundException();
                    case 500:
                        throw new InternalServerErrorException();
                    case 503:
                        throw new ServiceUnavailableException();
                    default:
                        throw new HttpConnectionException();
                }
            }
        }
        return episode;
    }
}
