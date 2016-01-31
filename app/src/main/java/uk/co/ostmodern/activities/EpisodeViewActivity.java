package uk.co.ostmodern.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import uk.co.ostmodern.R;
import uk.co.ostmodern.constants.AppConstants;
import uk.co.ostmodern.operations.EpisodeViewActivityOps;
import uk.co.ostmodern.rest.episodes.response.Episode;
import uk.co.ostmodern.util.RetainedFragmentManager;

/**
 * Episode View Activity. Views an Episode.
 *
 * @author rahulsingh
 */
public class EpisodeViewActivity extends AppCompatActivity {

    private static final String TAG = EpisodeViewActivity.class.getSimpleName();
    private static final String EPISODE_ACT_OPS_STATE = "EPISODE_ACT_OPS_STATE";

    protected final RetainedFragmentManager mRetainedFragmentManager =
            new RetainedFragmentManager(this.getFragmentManager(), TAG);

    private EpisodeViewActivityOps mEpisodeViewActivityOps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_episode_view);

        handleConfigurationChanges();

        mEpisodeViewActivityOps.setView();
    }

    /**
     * Factory method to create an Intent to start this activity app component passing in the
     * Episode data to this view.
     *
     * @param context   app context
     * @param episode   episode data
     * @return          Intent intent
     */
    public static Intent makeIntent(Context context, Episode episode) {
        Intent intent = new Intent(context, EpisodeViewActivity.class);
        intent.putExtra(AppConstants.EPISODE_EXTRA_KEY, episode);

        return intent;
    }

    /**
     * Handle configuration changes by accessing the {@link RetainedFragmentManager}.
     */
    private void handleConfigurationChanges() {
        if (mRetainedFragmentManager.firstTimeIn()) {
            Log.d(TAG, "First time onCreate() called");

            mEpisodeViewActivityOps = new EpisodeViewActivityOps(this);

            // store the ops into the retained fragment manager
            mRetainedFragmentManager.put(EPISODE_ACT_OPS_STATE, mEpisodeViewActivityOps);
        } else {
            // runtime config change has occurred
            Log.d(TAG, "Second or subsequent onCreate() call");
            mEpisodeViewActivityOps = new EpisodeViewActivityOps(this);

            if (mEpisodeViewActivityOps == null) {
                mEpisodeViewActivityOps = new EpisodeViewActivityOps(this);
                mRetainedFragmentManager.put(EPISODE_ACT_OPS_STATE, mEpisodeViewActivityOps);
            } else {
                mEpisodeViewActivityOps.onConfigurationChange(this);
            }
        }
    }
}
