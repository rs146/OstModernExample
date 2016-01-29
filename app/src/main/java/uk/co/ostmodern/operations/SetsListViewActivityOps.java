package uk.co.ostmodern.operations;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.lang.ref.WeakReference;

import uk.co.ostmodern.R;
import uk.co.ostmodern.activities.SetsListViewActivity;

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

    public void onConfigurationChange(SetsListViewActivity activity) {
        this.mActivity = new WeakReference<>(activity);

        initializeViews();
    }

}
