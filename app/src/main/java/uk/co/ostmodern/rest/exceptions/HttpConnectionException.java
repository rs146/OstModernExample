package uk.co.ostmodern.rest.exceptions;

/**
 * Generic HTTP Connection Exception for Retrofit HTTP errors that are not specifically handled
 * by the HTTP status code exceptions.
 *
 * @author rahulsingh
 */
public class HttpConnectionException extends Exception {

    public HttpConnectionException() {
        super();
    }
}
