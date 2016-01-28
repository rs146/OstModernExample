package uk.co.ostmodern.operations;

import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import java.lang.ref.WeakReference;

import uk.co.ostmodern.R;
import uk.co.ostmodern.activities.SetsDownloadActivity;
import uk.co.ostmodern.fragments.SetsDownloadFragment;
import uk.co.ostmodern.rest.sets.response.SetResponseObject;
import uk.co.ostmodern.util.AsyncTaskResult;

/**
 * Operations class for the {@link uk.co.ostmodern.activities.SetsDownloadActivity}.
 * Separation of concerns between the Activity view and the operations of an Activity.
 *
 * @author rahulsingh
 */
public class SetsDownloadActivityOps {

    private static final String TAG = SetsDownloadActivityOps.class.getSimpleName();
    private static final String DOWNLOAD_FRAGMENT_TAG = "sets_download_frag";

    private WeakReference<SetsDownloadActivity> mActivity;
    private WeakReference<Toolbar> mToolbar;

    /**
     * Constructor passing in a reference of the Activity.
     *
     * @param activity activity
     */
    public SetsDownloadActivityOps(SetsDownloadActivity activity) {
        this.mActivity = new WeakReference<>(activity);
        initViewFieldsAndToolbar();
    }

    private void initViewFieldsAndToolbar() {
        this.mToolbar = new WeakReference<>((Toolbar) mActivity.get().findViewById(R.id.toolbar));
        mActivity.get().setSupportActionBar(mToolbar.get());
    }

    /**
     * Activates the download of the data by creating a worker fragment.
     */
    public void downloadData() {
        FragmentManager fragmentManager = mActivity.get().getSupportFragmentManager();
        SetsDownloadFragment downloadFragment = (SetsDownloadFragment) fragmentManager.findFragmentByTag(DOWNLOAD_FRAGMENT_TAG);

        if (downloadFragment == null) {
            Log.d(TAG, "Download fragment was null, so init");
            downloadFragment = SetsDownloadFragment.newInstance();
            fragmentManager.beginTransaction().add(downloadFragment, DOWNLOAD_FRAGMENT_TAG).commit();
        }
    }

    /**
     * Called when a configuration change occurs. Reinitialise the weak reference to the activity
     * and any views.
     *
     * @param activity activity instance
     */
    public void onConfigurationChange(SetsDownloadActivity activity) {
        this.mActivity = new WeakReference<>(activity);
        initViewFieldsAndToolbar();
    }

    /**
     * Callback implementation for the Async Task onPreExecute() hook method.
     */
    public void getSetsDataOnPreExecute() {
    }

    /**
     * Callback implementation for the Async Task onPostExecute() hook method. Here we handle
     * the result of the data download and inform the user.
     *
     * @param result    result
     */
    public void getSetsDataOnPostExecute(AsyncTaskResult<SetResponseObject> result) {

    }
}
