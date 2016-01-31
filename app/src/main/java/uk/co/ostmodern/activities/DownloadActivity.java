package uk.co.ostmodern.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import uk.co.ostmodern.R;
import uk.co.ostmodern.operations.DownloadActivityOps;
import uk.co.ostmodern.services.DownloadDataService;
import uk.co.ostmodern.services.ServiceResult;
import uk.co.ostmodern.util.RetainedFragmentManager;

/**
 * Download Activity that initiates a background thread to download data from the REST API.
 *
 * @author rahulsingh
 */
public class DownloadActivity extends AppCompatActivity implements ServiceResult {

    private static final String TAG = DownloadActivity.class.getSimpleName();
    private static final String DOWNLOAD_ACT_OPS_STATE = "DOWNLOAD_ACT_OPS_STATE";

    protected final RetainedFragmentManager mRetainedFragmentManager =
            new RetainedFragmentManager(this.getFragmentManager(), TAG);

    private DownloadActivityOps mDownloadActivityOps;

    /**
     * Factory method to create an Intent to start this activity for downloading an episode info data.
     * It uses the ACTION_EPISODE defined in {@link DownloadDataService} and sets the intent action to
     * this value.
     *
     * @param context   app context
     * @return          Intent intent with action ACTION_EPISODE
     */
    public static Intent makeIntentForEpisodeDownload(Context context) {
        Intent intent = new Intent(context, DownloadActivity.class);
        intent.setAction(DownloadDataService.ACTION_EPISODE);

        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sets_download);

        handleConfigurationChanges();

        mDownloadActivityOps.setBaseEndpointUrl();
        mDownloadActivityOps.downloadData();
    }

    /**
     * Handle configurations changes using the {@link RetainedFragmentManager}.
     */
    private void handleConfigurationChanges() {
        if (mRetainedFragmentManager.firstTimeIn()) {
            Log.d(TAG, "First time onCreate() called");

            mDownloadActivityOps = new DownloadActivityOps(this);

            // store the ops into the retained fragment manager
            mRetainedFragmentManager.put(DOWNLOAD_ACT_OPS_STATE, mDownloadActivityOps);
        } else {
            // runtime config change has occurred
            Log.d(TAG, "Second or subsequent onCreate() call");
            mDownloadActivityOps = new DownloadActivityOps(this);

            if (mDownloadActivityOps == null) {
                mDownloadActivityOps = new DownloadActivityOps(this);
                mRetainedFragmentManager.put(DOWNLOAD_ACT_OPS_STATE, mDownloadActivityOps);
            } else {
                mDownloadActivityOps.onConfigurationChange(this);
            }
        }
    }

    @Override
    public void onServiceResult(int resultCode, Bundle data) {
        mDownloadActivityOps.onServiceResult(resultCode, data);
    }
}
