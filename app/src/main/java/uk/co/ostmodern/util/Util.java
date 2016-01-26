package uk.co.ostmodern.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.squareup.okhttp.OkHttpClient;

import java.util.concurrent.TimeUnit;

import uk.co.ostmodern.constants.AppConstants;

/**
 * Static utility class for utility operations.
 *
 * @author rahulsingh
 */
public class Util {

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

    private Util() {
        throw new AssertionError();
    }
}
