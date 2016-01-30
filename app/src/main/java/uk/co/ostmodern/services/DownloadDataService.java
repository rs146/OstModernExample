package uk.co.ostmodern.services;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import java.net.HttpURLConnection;
import java.util.List;

import uk.co.ostmodern.constants.AppConstants;
import uk.co.ostmodern.rest.exceptions.APIConnectionException;
import uk.co.ostmodern.rest.exceptions.BadRequestException;
import uk.co.ostmodern.rest.exceptions.HttpConnectionException;
import uk.co.ostmodern.rest.exceptions.InternalServerErrorException;
import uk.co.ostmodern.rest.exceptions.ResourceNotFoundException;
import uk.co.ostmodern.rest.exceptions.ServiceUnavailableException;
import uk.co.ostmodern.rest.exceptions.UnauthorizedException;
import uk.co.ostmodern.rest.sets.api.SetsSvcApiImpl;
import uk.co.ostmodern.rest.sets.response.Set;
import uk.co.ostmodern.rest.sets.response.SetImage;
import uk.co.ostmodern.rest.sets.response.SetResponseObject;

/**
 * Started service that handles any downloads of Set or Episode data from the REST API.
 * IntentService codifies the common design that a started service would use previously but creates
 * a background worker thread in onHandleIntent().
 *
 * @author rahulsingh
 */
public class DownloadDataService extends IntentService {

    private static final String TAG = DownloadDataService.class.getSimpleName();

    // Actions the IntentService can perform
    private static final String ACTION_SETS = "uk.co.ostmodern.services.action.SETS";
    private static final String ACTION_EPISODE = "uk.co.ostmodern.services.action.EPISODE";

    private static final String MESSENGER = "MESSENGER";
    private static final String SETS_BUNDLE_DATA_KEY = "SETS_DATA";

    public DownloadDataService() {
        super("DownloadDataService");
    }

    /**
     * Factory method that creates an Intent for a client to perform action of fetching a sets list
     * from the REST API.
     *
     * @return Intent intent
     */
    public static Intent makeIntentForSetsAction(Context context, Handler downloadHandler) {
        Intent intent = new Intent(context, DownloadDataService.class);
        intent.setAction(ACTION_SETS);
        intent.putExtra(MESSENGER, new Messenger(downloadHandler));

        return intent;
    }

    /**
     * Factory method that creates an Intent for a client to perform action of fetching an episode data.
     *
     * @return Intent intent
     */
    public static Intent makeIntentForEpisodeAction(Context context, Handler downloadHandler) {
        Intent intent = new Intent(context, DownloadDataService.class);
        intent.setAction(ACTION_EPISODE);
        intent.putExtra(MESSENGER, new Messenger(downloadHandler));

        return intent;
    }

    /**
     * Static method to obtain the Result code from the message passed by the Messenger.
     *
     * @param message   message
     * @return int      result code (usually mapped to HTTP response status codes)
     */
    public static int getResultCode(Message message) {
        return message.arg1;
    }

    /**
     * Retrieve the Set Response data from the Bundle.
     *
     * @param data      bundle
     * @return          Set Response Data
     */
    public static SetResponseObject getSetResponseData(Bundle data) {
        return data.getParcelable(SETS_BUNDLE_DATA_KEY);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            Messenger messenger = (Messenger) intent.getExtras().get(MESSENGER);
            if (ACTION_SETS.equals(action)) {
                handleActionSets(messenger);
            } else if (ACTION_EPISODE.equals(action)) {
                handleActionEpisode(messenger);
            }
        }
    }

    /**
     * Handle action ACTION_SETS in the worker thread as it is within the onHandleIntent hook method.
     */
    private void handleActionSets(Messenger messenger) {
        SetsSvcApiImpl setsSvcApi = new SetsSvcApiImpl(getApplicationContext());

        SetResponseObject setResponseObject = new SetResponseObject();
        try {
            setResponseObject = setsSvcApi.getSetsList();
            List<Set> setList = setResponseObject.getSets();
            for (Set set : setList) {
                for (String setImagePath : set.getImageUrls()) {
                    SetImage setImage = setsSvcApi.getSetImage(setImagePath);
                    set.addSetImage(setImage);
                }
            }
        } catch (APIConnectionException e) {
            Log.d(TAG, "API Connection failure to API");
            sendResponse(messenger, Activity.RESULT_CANCELED, setResponseObject);
        } catch (BadRequestException e) {
            Log.d(TAG, "Bad Request Exception to API");
            sendResponse(messenger, HttpURLConnection.HTTP_BAD_REQUEST, setResponseObject);
        } catch (UnauthorizedException e) {
            Log.d(TAG, "Unauthorized Exception to API");
            sendResponse(messenger, HttpURLConnection.HTTP_UNAUTHORIZED, setResponseObject);
        } catch (ResourceNotFoundException e) {
            Log.d(TAG, "Resource Not Found Exception to API");
            sendResponse(messenger, HttpURLConnection.HTTP_NOT_FOUND, setResponseObject);
        } catch (InternalServerErrorException e) {
            Log.d(TAG, "Internal Server Exception to API");
            sendResponse(messenger, HttpURLConnection.HTTP_INTERNAL_ERROR, setResponseObject);
        } catch (ServiceUnavailableException e) {
            Log.d(TAG, "Service Unavailable Exception to API");
            sendResponse(messenger, HttpURLConnection.HTTP_UNAVAILABLE, setResponseObject);
        } catch (HttpConnectionException e) {
            Log.d(TAG, "Other HTTP Exception to API");
            sendResponse(messenger, AppConstants.OTHER_HTTP_FAILURE, setResponseObject);
        }
        sendResponse(messenger, HttpURLConnection.HTTP_OK, setResponseObject);
    }

    /**
     * Handle action ACTION_EPISODE in the worker thread as it is within the onHandleIntent hook method.
     */
    private void handleActionEpisode(Messenger messenger) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Send the response back to the client Activity.
     *
     * @param messenger             messenger
     * @param resultCode            result code matching a HTTP result code
     * @param setResponseObject     set response object
     */
    private void sendResponse(Messenger messenger, int resultCode, SetResponseObject setResponseObject) {
        Message message = makeReplyMessage(resultCode, setResponseObject);

        try {
            messenger.send(message);
        } catch (RemoteException re) {
            Log.d(TAG, "Unable to send message back to the client");
        }
    }

    /**
     * Factory method to create a reply message.
     *
     * @param resultCode            result code (HTTP result code for client to access)
     * @param setResponseObject     set response data object
     * @return Message message
     */
    private Message makeReplyMessage(int resultCode, SetResponseObject setResponseObject) {
        Message message = Message.obtain();
        message.arg1 = resultCode;

        Bundle data = new Bundle();
        data.putParcelable(SETS_BUNDLE_DATA_KEY, setResponseObject);

        message.setData(data);

        return message;
    }
}
