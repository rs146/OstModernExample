package uk.co.ostmodern.operations;

import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import java.lang.ref.WeakReference;

import uk.co.ostmodern.R;
import uk.co.ostmodern.activities.EpisodeViewActivity;
import uk.co.ostmodern.constants.AppConstants;
import uk.co.ostmodern.rest.episodes.response.Episode;

/**
 * Operations class for {@link uk.co.ostmodern.activities.EpisodeViewActivity}.
 *
 * @author rahulsingh
 */
public class EpisodeViewActivityOps {

    private WeakReference<EpisodeViewActivity> mActivity;
    private WeakReference<Toolbar> mToolbar;
    private WeakReference<TextView> mTitleTextView;
    private WeakReference<TextView> mDescriptionTextView;

    /**
     * Constructor for this ops class passing in the activity reference only.
     *
     * @param activity  activity
     */
    public EpisodeViewActivityOps(EpisodeViewActivity activity) {
        this.mActivity = new WeakReference<>(activity);

        initializeViewFields();
    }

    private void initializeViewFields() {
        this.mToolbar = new WeakReference<>((Toolbar) mActivity.get().findViewById(R.id.toolbar));
        mActivity.get().setSupportActionBar(mToolbar.get());
        this.mTitleTextView = new WeakReference<>((TextView) mActivity.get().findViewById(R.id.episode_title));
        this.mDescriptionTextView = new WeakReference<>((TextView) mActivity.get().findViewById(R.id.episode_description));
    }

    /**
     * Sets the view with the episode data.
     */
    public void setView() {
        Episode episode = mActivity.get().getIntent().getParcelableExtra(AppConstants.EPISODE_EXTRA_KEY);

        mTitleTextView.get().setText(episode.getTitle());
        mDescriptionTextView.get().setText(episode.getSlug());
    }

    /**
     * Called on a configuration change to regain weak references to views lost due to configuration
     * change and the garbage collector.
     *
     * @param activity  activity
     */
    public void onConfigurationChange(EpisodeViewActivity activity) {
        this.mActivity = new WeakReference<>(activity);

        initializeViewFields();
    }
}
