package uk.co.ostmodern.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import org.apache.commons.collections4.CollectionUtils;

import java.lang.ref.WeakReference;
import java.util.List;

import uk.co.ostmodern.R;
import uk.co.ostmodern.rest.sets.response.Set;
import uk.co.ostmodern.util.Util;

/**
 * Recycler adapter that shows a list of {@link uk.co.ostmodern.rest.sets.response.Set} data.
 *
 * @author rahulsingh
 */
public class SetsRecyclerAdapter extends RecyclerView.Adapter<SetsRecyclerAdapter.ViewHolder> {

    private static final String TAG = SetsRecyclerAdapter.class.getSimpleName();

    private List<Set> mSetsList;
    private WeakReference<Context> mContext;

    /**
     * Constructor for this Adapter class.
     *
     * @param setsList  sets list
     * @param context   app context
     */
    public SetsRecyclerAdapter(List<Set> setsList, Context context) {
        this.mSetsList = setsList;
        this.mContext = new WeakReference<>(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerlist_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Set setData = mSetsList.get(position);

        if (CollectionUtils.isEmpty(setData.getImageUrls())) {
            String setDataImageUrl = setData.getImageUrls().get(0);
            Picasso.with(mContext.get()).load(Util.getBaseEndpointUrl(mContext.get()) + setDataImageUrl)
                    .placeholder(R.drawable.default_image)
                    .error(R.drawable.default_image)
                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .fit().centerCrop()
                    .into(holder.imageView, new Callback() {
                        @Override
                        public void onSuccess() {
                            Log.d(TAG, "image loaded successfully");
                        }

                        @Override
                        public void onError() {
                            Log.d(TAG, "unable to load image successfully");
                        }
                    });
        } else {
            Picasso.with(mContext.get()).load(R.drawable.default_image)
                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .fit().centerCrop()
                    .into(holder.imageView);
        }
        holder.setTitle.setText(setData.getTitle());
    }

    @Override
    public int getItemCount() {
        return mSetsList == null ? 0 : mSetsList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView setTitle;
        TextView setDescription;

        public ViewHolder(View itemView) {
            super(itemView);

            imageView = (ImageView) itemView.findViewById(R.id.set_picture);
            setTitle = (TextView) itemView.findViewById(R.id.set_title);
            setDescription = (TextView) itemView.findViewById(R.id.set_description);
        }
    }
}
