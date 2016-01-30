package uk.co.ostmodern.services;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.lang.ref.WeakReference;

/**
 * Class that inherits from Handler and uses the handleMessage()
 * hook method to forward relevant data from Messages sent from
 * {@code DownloadDataService} back to {@code DownloadDataActivity}.
 *
 * @author rahulsingh
 */
public class ServiceResultHandler extends Handler {

    private final String TAG = getClass().getSimpleName();

    private WeakReference<ServiceResult> mResult;

    public ServiceResultHandler(ServiceResult serviceResult) {
        mResult = new WeakReference<>(serviceResult);
    }

    /**
     * Called to reset ServiceResult callback instance ({@code DownloadDataActivity})
     * after a configuration change.
     *
     * @param serviceResult service result instance
     */
    public void onConfigurationChange(ServiceResult serviceResult) {
        mResult = new WeakReference<>(serviceResult);
    }

    /**
     * Extract the data from the message and pass it to the
     * {@code ServiceResult} callback.
     *
     * @param message message
     */
    @Override
    public void handleMessage(Message message) {
        Log.d(TAG, "handleMessage() hook method");

        final int resultCode = DownloadDataService.getResultCode(message);
        final Bundle data = message.getData();

        // forward the result to the callback implementation
        if (mResult.get() == null)
            Log.d(TAG, "mResult was null");
        else {
            mResult.get().onServiceResult(resultCode, data);
        }
    }
}
