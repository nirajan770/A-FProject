package com.abercrombiefitch.ui;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.VisibleForTesting;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.abercrombiefitch.R;
import com.abercrombiefitch.api.model.Promotion;
import com.abercrombiefitch.util.Log;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Adapter for the RecyclerView
 *
 * Created by Nirajan on 9/28/2015.
 */
public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder>{
    private static final String TAG = "CardAdapter";

    /**
     * Interface that implements on click listener for {@link RecyclerView}
     */
    public interface onItemClickListener{
        public void onItemClick(View view, int position);
    }

    // Click listener
    onItemClickListener mItemClickListener;

    public void setOnItemClickListener(final onItemClickListener mItemClickListener){
        this.mItemClickListener = mItemClickListener;
    }

    private List<Promotion> mItems;

    public CardAdapter() {
        mItems = new ArrayList<Promotion>();
    }

    public void addData(Promotion promotion) {
        mItems.add(promotion);
        notifyDataSetChanged();
    }

    public void clear() {
        mItems.clear();
        notifyDataSetChanged();
    }

    @Override
    public CardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_card_item, parent, false);
        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(final CardAdapter.ViewHolder holder, int position) {
        Promotion promotion = mItems.get(position);

        Picasso.with(holder.itemImage.getContext())
                .load(promotion.getImage())
                //.into(holder.itemImage);
                .into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        holder.itemImage.setImageBitmap(bitmap);
                        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                            @Override
                            public void onGenerated(Palette palette) {
                                Palette.Swatch vibrant = palette.getVibrantSwatch();
                                holder.itemTitle.setBackgroundColor(vibrant.getRgb());
                                holder.itemTitle.setTextColor(vibrant.getTitleTextColor());
                            }
                        });
                    }
                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) {
                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {
                    }
                });


        holder.itemTitle.setText(promotion.getTitle());
    }


    @Override
    public int getItemCount() {
        return mItems.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public Promotion getItem(int position) {
        return mItems.get(position);
    }

    public final class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @Bind(R.id.card_item_image)
        ImageView itemImage;
        @Bind(R.id.card_item_title)
        TextView itemTitle;

        @VisibleForTesting
        public ImageView getItemImage() {
            return itemImage;
        }
        @VisibleForTesting
        public TextView getItemTitle() {
            return itemTitle;
        }

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Log.d(TAG, "item on click");
            if (mItemClickListener != null)
                mItemClickListener.onItemClick(v, getAdapterPosition());
        }
    }
}
