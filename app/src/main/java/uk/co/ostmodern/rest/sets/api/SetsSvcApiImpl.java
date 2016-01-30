package uk.co.ostmodern.rest.sets.api;

import android.content.Context;
import android.util.Log;

import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import uk.co.ostmodern.rest.errorhandling.ErrorRecorder;
import uk.co.ostmodern.rest.exceptions.APIConnectionException;
import uk.co.ostmodern.rest.exceptions.BadRequestException;
import uk.co.ostmodern.rest.exceptions.HttpConnectionException;
import uk.co.ostmodern.rest.exceptions.InternalServerErrorException;
import uk.co.ostmodern.rest.exceptions.ResourceNotFoundException;
import uk.co.ostmodern.rest.exceptions.ServiceUnavailableException;
import uk.co.ostmodern.rest.exceptions.UnauthorizedException;
import uk.co.ostmodern.rest.sets.response.SetImage;
import uk.co.ostmodern.rest.sets.response.SetResponseObject;
import uk.co.ostmodern.util.Util;

/**
 * Retrofit implementation of {@link SetsSvcApi}. Clients use this class to access the API
 * rather than calling the Retrofit interface directly. Provides a facade to clients.
 *
 * @author rahulsingh
 */
public class SetsSvcApiImpl {

    private static final String TAG = SetsSvcApiImpl.class.getSimpleName();

    private SetsSvcApi setsSvcApi;
    private ErrorRecorder errorRecorder;

    /**
     * Constructor for this class.
     *
     * @param context app context
     */
    public SetsSvcApiImpl(Context context) {

        errorRecorder = new ErrorRecorder();
        setsSvcApi = new RestAdapter.Builder()
                .setEndpoint(Util.getBaseEndpointUrl(context))
                .setClient(new OkClient(Util.retrieveOkHttpClient(context)))
                .setErrorHandler(errorRecorder)
                .build()
                .create(SetsSvcApi.class);
    }

    /**
     * Calls the API to obtain the {@link SetResponseObject} response from the REST API.
     *
     * @return SetResponseObject                response
     * @throws APIConnectionException           thrown if cannot connect to API
     * @throws BadRequestException              thrown if HTTP 400 status is returned
     * @throws UnauthorizedException            thrown if HTTP 401 status is returned
     * @throws ResourceNotFoundException        thrown if HTTP 404 status is returned
     * @throws InternalServerErrorException     thrown if HTTP 500 status is returned
     * @throws ServiceUnavailableException      thrown if HTTP 503 status is returned
     * @throws HttpConnectionException          thrown if HTTP error is recorded
     */
    public SetResponseObject getSetsList() throws APIConnectionException, BadRequestException,
            UnauthorizedException, ResourceNotFoundException, InternalServerErrorException,
            ServiceUnavailableException, HttpConnectionException {

        SetResponseObject setResponseObject = new SetResponseObject();
        try {
            setResponseObject = setsSvcApi.getSetsList();
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
        return setResponseObject;
    }

    /**
     * Get a set image.
     *
     * @param imageApiPath                      image api path
     * @return                                  SetImage
     * @throws APIConnectionException           if connection to API fails
     * @throws BadRequestException              if HTTP 400 occurs
     * @throws UnauthorizedException            if HTTP 401 occurs
     * @throws ResourceNotFoundException        if HTTP 404 occurs
     * @throws InternalServerErrorException     if HTTP 500 occurs
     * @throws ServiceUnavailableException      if HTTP 503 occurs
     * @throws HttpConnectionException          if other HTTP exception occurs
     */
    public SetImage getSetImage(String imageApiPath) throws APIConnectionException, BadRequestException,
            UnauthorizedException, ResourceNotFoundException, InternalServerErrorException,
            ServiceUnavailableException, HttpConnectionException {

        SetImage setImage = new SetImage();
        try {
            setImage = setsSvcApi.getSetImage(imageApiPath);
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
        return setImage;
    }
}
