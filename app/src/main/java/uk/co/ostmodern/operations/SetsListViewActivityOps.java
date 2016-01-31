package uk.co.ostmodern.operations;

import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.lang.ref.WeakReference;

import uk.co.ostmodern.R;
import uk.co.ostmodern.activities.DownloadActivity;
import uk.co.ostmodern.activities.SetsListViewActivity;
import uk.co.ostmodern.adapters.SetsRecyclerAdapter;
import uk.co.ostmodern.constants.AppConstants;
import uk.co.ostmodern.rest.sets.response.SetResponseObject;

/**
 * Operations class for the {@link uk.co.ostmodern.activities.SetsListViewActivity} view.
 *
 * @author rahulsingh
 */
public class SetsListViewActivityOps {

    private WeakReference<SetsListViewActivity> mActivity;
    private WeakReference<Toolbar> mToolbar;
    private WeakReference<RecyclerView> mRecyclerView;

    /**
     * Constructor for this operations class.
     *
     * @param activity activity reference
     */
    public SetsListViewActivityOps(SetsListViewActivity activity) {
        this.mActivity = new WeakReference<>(activity);

        initializeViews();
    }

    /**
     * Reinitialize the views and the toolbar when a configuration change occurs.
     */
    private void initializeViews() {
        this.mToolbar = new WeakReference<>((Toolbar) mActivity.get().findViewById(R.id.toolbar));
        mActivity.get().setSupportActionBar(mToolbar.get());

        this.mRecyclerView = new WeakReference<>((RecyclerView) mActivity.get().findViewById(R.id.setsListRecyclerView));
    }

    /**
     * Set up the recycler view with the data passed from the Intent and using the {@link SetsRecyclerAdapter}.
     */
    public void setUpRecyclerView() {
        mRecyclerView.get().setHasFixedSize(false);
        mRecyclerView.get().setLayoutManager(new LinearLayoutManager(mActivity.get()));
        mRecyclerView.get().setItemAnimator(new DefaultItemAnimator());

        SetsRecyclerAdapter setsRecyclerAdapter = new SetsRecyclerAdapter(getSetResponseObjectFromIntent().getSets(),
                mActivity.get());
        mRecyclerView.get().setAdapter(setsRecyclerAdapter);
    }

    /**
     * Called when a configuration change occurs.
     *
     * @param activity  app activity component
     */
    public void onConfigurationChange(SetsListViewActivity activity) {
        this.mActivity = new WeakReference<>(activity);

        initializeViews();
    }

    /**
     * App bar overflow menu action to view an episode.
     */
    public void actionViewEpisode() {
        Intent intent = DownloadActivity.makeIntentForEpisodeDownload(mActivity.get());
        mActivity.get().startActivity(intent);
    }

    private SetResponseObject getSetResponseObjectFromIntent() {
        return mActivity.get().getIntent().getParcelableExtra(AppConstants.SET_RESPONSE_EXTRA_KEY);
    }

}
