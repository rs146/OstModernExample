package uk.co.ostmodern.rest.exceptions;

/**
 * Exception class that represents situation where Retrofit cannot access
 * or communicate with the REST API.
 *
 * @author rahulsingh
 */
public class APIConnectionException extends Exception {

    public APIConnectionException() {
        super();
    }
}
