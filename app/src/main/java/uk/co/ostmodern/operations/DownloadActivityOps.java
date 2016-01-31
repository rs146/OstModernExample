package uk.co.ostmodern.operations;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.RelativeLayout;

import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;

import uk.co.ostmodern.R;
import uk.co.ostmodern.activities.DownloadActivity;
import uk.co.ostmodern.activities.EpisodeViewActivity;
import uk.co.ostmodern.activities.SetsListViewActivity;
import uk.co.ostmodern.rest.episodes.response.Episode;
import uk.co.ostmodern.rest.sets.response.SetResponseObject;
import uk.co.ostmodern.services.DownloadDataService;
import uk.co.ostmodern.services.ServiceResultHandler;
import uk.co.ostmodern.util.Util;

/**
 * Operations class for the {@link DownloadActivity}.
 * Separation of concerns between the Activity view and the operations of an Activity.
 *
 * @author rahulsingh
 */
public class DownloadActivityOps {

    private static final String TAG = DownloadActivityOps.class.getSimpleName();

    private WeakReference<DownloadActivity> mActivity;
    private WeakReference<Toolbar> mToolbar;
    private WeakReference<RelativeLayout> mRootRelativeLayout;
    private Handler mServiceResultHandler;

    /**
     * Constructor passing in a reference of the Activity.
     *
     * @param activity activity
     */
    public DownloadActivityOps(DownloadActivity activity) {
        this.mActivity = new WeakReference<>(activity);
        initViewFieldsAndToolbar();
        setServiceResultHandler();
    }

    private void initViewFieldsAndToolbar() {
        this.mToolbar = new WeakReference<>((Toolbar) mActivity.get().findViewById(R.id.toolbar));
        mActivity.get().setSupportActionBar(mToolbar.get());
        this.mRootRelativeLayout = new WeakReference<>(
                (RelativeLayout) mActivity.get().findViewById(R.id.root_relative_layout_download));
    }

    private void setServiceResultHandler() {
        mServiceResultHandler = new ServiceResultHandler(mActivity.get());
    }

    /**
     * Set the base endpoint url for the rest of the app.
     */
    public void setBaseEndpointUrl() {
        if (!Util.isBaseEndpointUrlPersisted(mActivity.get())) {
            Util.setBaseEndpointUrl(mActivity.get());
        }
    }

    /**
     * Activates the download of the data by creating an Intent to start the
     * {@link uk.co.ostmodern.services.DownloadDataService} IntentService. It checks the DownloadActivity's
     * intent action to filter what download action needs to be performed.
     */
    public void downloadData() {
        if (getActionFromIntent().equals(DownloadDataService.ACTION_EPISODE)) {
            Intent intent = DownloadDataService.makeIntentForEpisodeAction(mActivity.get(), mServiceResultHandler);
            mActivity.get().startService(intent);
        } else {
            Intent intent = DownloadDataService.makeIntentForSetsAction(mActivity.get(), mServiceResultHandler);
            mActivity.get().startService(intent);
        }
    }

    public void onServiceResult(int resultCode, Bundle data) {
        Log.d(TAG, "onServiceResult called via the ServiceResult interface into the Activity client");
        if (resultCode == Activity.RESULT_CANCELED) {
            Snackbar snackbar = Snackbar.make(mRootRelativeLayout.get(), R.string.api_connect_error, Snackbar.LENGTH_LONG);
            snackbar.show();
        } else if (resultCode == HttpURLConnection.HTTP_OK) {

            if (DownloadDataService.ACTION_EPISODE.equals(getActionFromIntent())) {
                Episode episodeData = DownloadDataService.getEpisodeData(data);
                if (episodeData == null) {
                    Log.d(TAG, "episode data from service is null");
                }

                Intent intent = EpisodeViewActivity.makeIntent(mActivity.get(), episodeData);
                mActivity.get().startActivity(intent);
            } else {
                // start intent to List view
                SetResponseObject setResponseData = DownloadDataService.getSetResponseData(data);
                if (setResponseData == null) {
                    Log.d(TAG, "set response data is null");
                }

                // activate the List Activity
                Intent intent = SetsListViewActivity.makeIntent(mActivity.get(), setResponseData);
                mActivity.get().startActivity(intent);
            }
            mActivity.get().finish();
        } else {
            // HTTP error codes
            Snackbar snackbar = Snackbar.make(mRootRelativeLayout.get(), R.string.http_connect_error, Snackbar.LENGTH_LONG);
            snackbar.show();
        }
    }

    /**
     * Called when a configuration change occurs. Reinitialise the weak reference to the activity
     * and any views.
     *
     * @param activity activity instance
     */
    public void onConfigurationChange(DownloadActivity activity) {
        this.mActivity = new WeakReference<>(activity);
        initViewFieldsAndToolbar();
        setServiceResultHandler();
    }

    /**
     * Retrieve the action value from the activity's intent.
     *
     * @return  String action value
     */
    private String getActionFromIntent() {
        return mActivity.get().getIntent().getAction();
    }
}
