package uk.co.ostmodern.rest.errorhandling;

import android.util.Log;

import retrofit.ErrorHandler;
import retrofit.RetrofitError;
import uk.co.ostmodern.rest.exceptions.APIConnectionException;

/**
 * Records the error when a REST call is made to the API.
 *
 * @author rahulsingh
 */
public class ErrorRecorder implements ErrorHandler {

    private final String TAG = getClass().getSimpleName();

    private RetrofitError error;

    @Override
    public Throwable handleError(RetrofitError cause) {
        error = cause;

        if (error.getKind().equals(RetrofitError.Kind.NETWORK)) {
            Log.d(TAG, "Network error caught by Retrofit");
            return new APIConnectionException();
        } else if (error.getKind().equals(RetrofitError.Kind.HTTP)) {
            Log.d(TAG, "HTTP error caught by Retrofit");
        }
        return error.getCause();
    }

    public RetrofitError getError() {
        return error;
    }
}
