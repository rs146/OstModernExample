package uk.co.ostmodern.services;

import android.os.Bundle;

/**
 * Interface that's implemented by an Activity or client that wants to receive the
 * results of a started IntentService.
 *
 * @author rahulsingh
 */
public interface ServiceResult {

    /**
     * Callback method when a launched service sends back results
     * from Service to another component.
     *
     * @param resultCode  result code when the service finishes
     * @param data        the data
     */
    void onServiceResult(int resultCode, Bundle data);
}
