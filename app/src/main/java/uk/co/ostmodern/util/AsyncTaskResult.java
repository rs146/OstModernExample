package uk.co.ostmodern.util;

/**
 * Encapsulates an AsyncTask result that can encapsulate the results of the background thread
 * and any exception thrown in the background task.
 *
 * @author rahulsingh
 */
public class AsyncTaskResult<T> {

    private T mResult;
    private Exception mError;

    public T getResult() {
        return mResult;
    }

    public Exception getError() {
        return mError;
    }

    public AsyncTaskResult(T result) {
        this.mResult = result;
    }

    public AsyncTaskResult(Exception error) {
        this.mError = error;
    }
}
