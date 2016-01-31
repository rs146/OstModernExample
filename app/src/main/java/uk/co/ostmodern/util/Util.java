package uk.co.ostmodern.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.squareup.okhttp.OkHttpClient;

import java.util.concurrent.TimeUnit;

import uk.co.ostmodern.R;
import uk.co.ostmodern.constants.AppConstants;

/**
 * Static utility class for utility operations.
 *
 * @author rahulsingh
 */
public class Util {

    /**
     * Set the Base Endpoint URL by persisting it to SharedPreferences K-V store.
     *
     * @param context app context
     */
    public static void setBaseEndpointUrl(Context context) {
        String baseEndpointUrl = context.getResources().getString(R.string.base_server_endpoint_url);
        SharedPreferences.Editor editor = context.getSharedPreferences(AppConstants.SHARED_PREFERENCES_NAME,
                Context.MODE_PRIVATE).edit();
        editor.putString(AppConstants.BASE_ENDPOINT_URL_KEY, baseEndpointUrl);
        editor.apply();
    }

    /**
     * Determines if the base endpoint URL has been persisted to SharedPreferences store.
     *
     * @param context app context
     * @return true if has been persisted, false otherwise
     */
    public static boolean isBaseEndpointUrlPersisted(Context context) {
        return (getBaseEndpointUrl(context) != null);
    }

    /**
     * Retrieve the base endpoint url from the SharedPreferences storage mechanism.
     *
     * @param context app context
     * @return String base endpoint url
     */
    public static String getBaseEndpointUrl(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(AppConstants.SHARED_PREFERENCES_NAME,
                Context.MODE_PRIVATE);
        return preferences.getString(AppConstants.BASE_ENDPOINT_URL_KEY, null);
    }

    /**
     * Retrieve an {@link OkHttpClient} instance for our app.
     *
     * @param context app context
     * @return OkHttpClient with set timeout
     */
    public static OkHttpClient retrieveOkHttpClient(Context context) {
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setReadTimeout(30, TimeUnit.SECONDS);
        okHttpClient.setConnectTimeout(30, TimeUnit.SECONDS);
        return okHttpClient;
    }

    /**
     * The API contains a forward-slash at the beginning, e.g. /api/images/img_name.
     * This method extracts the API path and removes the starting forward-slash to avoid API
     * calls with two-forward slashed, e.g. //api/images/img_name.
     *
     * @param imageApiPath  image api path
     * @return              relative image api path to that of the Resource server
     */
    public static String getRelativeImageApiPath(String imageApiPath) {
        if (imageApiPath != null && !imageApiPath.isEmpty()) {
            return imageApiPath.substring(1);
        }
        return imageApiPath;
    }

    private Util() {
        throw new AssertionError();
    }
}
