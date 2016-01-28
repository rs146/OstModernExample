package uk.co.ostmodern.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import uk.co.ostmodern.R;
import uk.co.ostmodern.fragments.SetsDownloadFragment;
import uk.co.ostmodern.operations.SetsDownloadActivityOps;
import uk.co.ostmodern.rest.sets.response.SetResponseObject;
import uk.co.ostmodern.util.AsyncTaskResult;
import uk.co.ostmodern.util.RetainedFragmentManager;

/**
 * Download Activity that initiates a background thread to download the "sets" data from the API.
 *
 * @author rahulsingh
 */
public class SetsDownloadActivity extends AppCompatActivity implements SetsDownloadFragment.TaskCallbacks {

    private static final String TAG = SetsDownloadActivity.class.getSimpleName();
    private static final String SETS_DOWNLOAD_ACT_OPS_STATE = "DOWNLOAD_ACT_OPS_STATE";

    protected final RetainedFragmentManager mRetainedFragmentManager =
            new RetainedFragmentManager(this.getFragmentManager(), TAG);

    private SetsDownloadActivityOps mSetsDownloadActivityOps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sets_download);

        handleConfigurationChanges();

        mSetsDownloadActivityOps.downloadData();
    }

    /**
     * Handle configurations changes using the {@link RetainedFragmentManager}.
     */
    private void handleConfigurationChanges() {
        if (mRetainedFragmentManager.firstTimeIn()) {
            Log.d(TAG, "First time onCreate() called");

            mSetsDownloadActivityOps = new SetsDownloadActivityOps(this);

            // store the ops into the retained fragment manager
            mRetainedFragmentManager.put(SETS_DOWNLOAD_ACT_OPS_STATE, mSetsDownloadActivityOps);
        } else {
            // runtime config change has occurred
            Log.d(TAG, "Second or subsequent onCreate() call");
            mSetsDownloadActivityOps = new SetsDownloadActivityOps(this);

            if (mSetsDownloadActivityOps == null) {
                mSetsDownloadActivityOps = new SetsDownloadActivityOps(this);
                mRetainedFragmentManager.put(SETS_DOWNLOAD_ACT_OPS_STATE, mSetsDownloadActivityOps);
            } else {
                mSetsDownloadActivityOps.onConfigurationChange(this);
            }
        }
    }

    @Override
    public void getSetsDataOnPreExecute() {
        mSetsDownloadActivityOps.getSetsDataOnPreExecute();
    }

    @Override
    public void getSetsDataOnPostExecute(AsyncTaskResult<SetResponseObject> result) {
        mSetsDownloadActivityOps.getSetsDataOnPostExecute(result);
    }
}
