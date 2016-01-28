package uk.co.ostmodern.fragments;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import uk.co.ostmodern.rest.sets.api.SetsSvcApiImpl;
import uk.co.ostmodern.rest.sets.response.SetResponseObject;
import uk.co.ostmodern.util.AsyncTaskResult;

/**
 * Worker Fragment that contains a static nested AsyncTask to download the sets information from the
 * REST API.
 *
 * @author rahulsingh
 */
public class SetsDownloadFragment extends Fragment {

    /**
     * Async Task callbacks.
     */
    public interface TaskCallbacks {
        void getSetsDataOnPreExecute();
        void getSetsDataOnPostExecute(AsyncTaskResult<SetResponseObject> result);
    }

    private TaskCallbacks mListener;
    private RetrieveSetsAsyncTask mRetrieveSetsAsyncTask;

    public SetsDownloadFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment SetsDownloadFragment.
     */
    public static SetsDownloadFragment newInstance() {
        return new SetsDownloadFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // retain this fragment on configuration change
        setRetainInstance(true);

        mRetrieveSetsAsyncTask = new RetrieveSetsAsyncTask();
        mRetrieveSetsAsyncTask.execute();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof TaskCallbacks) {
            mListener = (TaskCallbacks) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement TaskCallbacks interface");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * Note. Although this solution handles orientation changes, it may not be the best.
     * Services have a higher priority in the pecking order when it comes to Android killing processes
     * than background threads.
     */
    private class RetrieveSetsAsyncTask extends AsyncTask<Void, Void, AsyncTaskResult<SetResponseObject>> {

        @Override
        protected void onPreExecute() {
            if (mListener != null) {
                mListener.getSetsDataOnPreExecute();
            }
        }

        @Override
        protected AsyncTaskResult<SetResponseObject> doInBackground(Void... voids) {
            SetsSvcApiImpl setsSvcApi = new SetsSvcApiImpl(getActivity().getApplicationContext());

            try {
                return new AsyncTaskResult<>(setsSvcApi.getSetsList());
            } catch (Exception anyException) {
                return new AsyncTaskResult<>(anyException);
            }
        }

        @Override
        protected void onPostExecute(AsyncTaskResult<SetResponseObject> result) {
            if (mListener != null) {
                mListener.getSetsDataOnPostExecute(result);
            }
        }
    }
}
