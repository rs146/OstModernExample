package uk.co.ostmodern.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import uk.co.ostmodern.R;
import uk.co.ostmodern.constants.AppConstants;
import uk.co.ostmodern.operations.SetsListViewActivityOps;
import uk.co.ostmodern.rest.sets.response.SetResponseObject;
import uk.co.ostmodern.util.RetainedFragmentManager;

/**
 * Activity view that displays a list of Sets as retrieved from the API.
 *
 * @author rahulsingh
 */
public class SetsListViewActivity extends AppCompatActivity {

    private static final String TAG = SetsListViewActivity.class.getSimpleName();
    private static final String SETS_LIST_ACT_OPS_STATE = "SETS_LIST_ACT_OPS_STATE";

    protected final RetainedFragmentManager mRetainedFragmentManager =
            new RetainedFragmentManager(this.getFragmentManager(), TAG);

    private SetsListViewActivityOps mSetsListViewActivityOps;

    /**
     * Factory method that creates an Intent to activate this Activity component.
     *
     * @param context               app context
     * @param setResponseObject     set response object to be viewed in this activity
     * @return Intent intent
     */
    public static Intent makeIntent(Context context, SetResponseObject setResponseObject) {
        Intent intent = new Intent(context, SetsListViewActivity.class);
        intent.putExtra(AppConstants.SET_RESPONSE_EXTRA_KEY, setResponseObject);

        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sets_list_view);

        handleConfigurationChanges();
        mSetsListViewActivityOps.setUpRecyclerView();
    }

    private void handleConfigurationChanges() {
        if (mRetainedFragmentManager.firstTimeIn()) {
            Log.d(TAG, "First time onCreate() called");

            mSetsListViewActivityOps = new SetsListViewActivityOps(this);

            // store the ops into the retained fragment manager
            mRetainedFragmentManager.put(SETS_LIST_ACT_OPS_STATE, mSetsListViewActivityOps);
        } else {
            // runtime config change has occurred
            Log.d(TAG, "Second or subsequent onCreate() call");
            mSetsListViewActivityOps = new SetsListViewActivityOps(this);

            if (mSetsListViewActivityOps == null) {
                mSetsListViewActivityOps = new SetsListViewActivityOps(this);
                mRetainedFragmentManager.put(SETS_LIST_ACT_OPS_STATE, mSetsListViewActivityOps);
            } else {
                mSetsListViewActivityOps.onConfigurationChange(this);
            }
        }
    }
}
