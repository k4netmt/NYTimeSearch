package com.example.kanet.nytimesearch.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kanet.nytimesearch.models.Photo;
import com.squareup.picasso.Picasso;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kanet.nytimesearch.R;
import com.example.kanet.nytimesearch.models.Article;

import java.util.List;


/**
 * Created by Kanet on 3/17/2016.
 */
public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder>{

    private List<Article> mArticles;
    private Context context;
    // Pass in the contact array into the constructor
    public SearchAdapter(List<Article> contacts) {
        mArticles = contacts;
    }

    @Override
    public void onBindViewHolder(SearchAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        Article article = mArticles.get(position);
        // Set item views based on the data model
        TextView tvHeadline = viewHolder.tvHeadline;
        tvHeadline.setText(article.getHeadline());

        if (article.getThumbNails().size()>0) {
            Photo photo=Photo.searchSubType(article.getThumbNails(), Photo.SUBTYPE_WIDE);
            if (photo!=null){
                Picasso.with(context).load(photo.getUrl()).into(viewHolder.ivPhoto);
                Picasso.with(context).load(photo.getUrl()).resize(photo.getWidth(),photo.getHeight());
            }
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View articleView = inflater.inflate(R.layout.item_search_arts, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(articleView);
        return viewHolder;
    }

    @Override
    public int getItemCount() {
        return mArticles.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public TextView tvHeadline;
        public ImageView ivPhoto;

        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            tvHeadline = (TextView) itemView.findViewById(R.id.tvHeadline);
            ivPhoto = (ImageView) itemView.findViewById(R.id.ivPhoto);
        }
    }
}
