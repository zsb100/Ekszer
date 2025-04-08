package com.example.ekszer;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.EventListener;

public class ItemAdapter
        extends RecyclerView.Adapter<ItemAdapter.ViewHolder>
        implements Filterable {
    // Member variables.
    private ArrayList<Item> mShoppingData;
    private ArrayList<Item> mSoppingDataAll;
    private Context mContext;
    private int lastPosition = -1;

    ItemAdapter(Context context, ArrayList<Item> itemsData) {
        this.mShoppingData = itemsData;
        this.mSoppingDataAll = itemsData;
        this.mContext = context;
    }

    @Override
    public ItemAdapter.ViewHolder onCreateViewHolder(
            ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext)
                .inflate(R.layout.list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ItemAdapter.ViewHolder holder, int position) {
        // Get current sport.
        Item currentItem = mShoppingData.get(position);

        // Populate the textviews with data.
        holder.bindTo(currentItem);


        if(holder.getAdapterPosition() > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.showup);
            holder.itemView.startAnimation(animation);
            lastPosition = holder.getAdapterPosition();
        }
    }

    @Override
    public int getItemCount() {
        return mShoppingData.size();
    }


    /**
     * RecycleView filter
     * **/
    @Override
    public Filter getFilter() {
        return shoppingFilter;
    }

    private Filter shoppingFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<Item> filteredList = new ArrayList<>();
            FilterResults results = new FilterResults();

            if(charSequence == null || charSequence.length() == 0) {
                results.count = mSoppingDataAll.size();
                results.values = mSoppingDataAll;
            } else {
                String filterPattern = charSequence.toString().toLowerCase().trim();
                for(Item item : mSoppingDataAll) {
                    if(item.getName().toLowerCase().contains(filterPattern)){
                        filteredList.add(item);
                    }
                }

                results.count = filteredList.size();
                results.values = filteredList;
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            mShoppingData = (ArrayList)filterResults.values;
            notifyDataSetChanged();
        }
    };

    class ViewHolder extends RecyclerView.ViewHolder {
        // Member Variables for the TextViews
        private TextView mTitleText;
        private TextView mInfoText;
        private TextView mPriceText;
        private ImageView mItemImage;
        private RatingBar mRatingBar;

        ViewHolder(View itemView) {
            super(itemView);

            // Initialize the views.
            mTitleText = itemView.findViewById(R.id.itemTitle);
            mInfoText = itemView.findViewById(R.id.subTitle);
            mItemImage = itemView.findViewById(R.id.itemImage);
            mRatingBar = itemView.findViewById(R.id.ratingBar);
            mPriceText = itemView.findViewById(R.id.price);

            // itemView.findViewById(R.id.add_to_cart).setOnClickListener(view -> ((ShopActivity)mContext).updateAlertIcon());
        }

        void bindTo(Item currentItem){

            mTitleText.setText(currentItem.getName());
            mInfoText.setText(currentItem.getDescription());
            mPriceText.setText(currentItem.getPrice());
            mRatingBar.setRating(currentItem.getRating());

            // Load the images into the ImageView using the Glide library.
            Glide.with(mContext).load(currentItem.getImageUrl()).into(mItemImage);
        }
    }
}