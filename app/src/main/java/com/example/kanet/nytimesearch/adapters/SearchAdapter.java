package com.example.kanet.nytimesearch.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kanet.nytimesearch.activities.WebActivity;
import com.example.kanet.nytimesearch.models.Photo;
import com.example.kanet.nytimesearch.models.TimeStamp;
import com.squareup.picasso.Picasso;

import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kanet.nytimesearch.R;
import com.example.kanet.nytimesearch.models.Article;

import java.util.List;


/**
 * Created by Kanet on 3/17/2016.
 */
public class SearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private static final int THUMBNAIL=0;
    private static final int TEXT=1;
    private List<Article> mArticles;
    private Context context;
    // Pass in the contact array into the constructor
    public SearchAdapter(List<Article> contacts) {
        mArticles = contacts;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        switch (viewHolder.getItemViewType()) {
            case THUMBNAIL:{
                ViewHolderThumbnails vh1 = (ViewHolderThumbnails) viewHolder;
                configureViewHolderThumbnail(vh1, position);
                break;
            }
            case TEXT: {
                ViewHolderText vh2 = (ViewHolderText) viewHolder;
                configureViewHolderText(vh2, position);
                break;
            }
            default: {
                ViewHolderText vh = (ViewHolderText) viewHolder;
                configureViewHolderText(vh, position);
                break;
            }
        }
    }

    private void configureViewHolderText(ViewHolderText vh1, int position) {
        final Article article = (Article) mArticles.get(position);
        if (article != null) {
            // Set item views based on the data model
            TextView tvHeadline = vh1.tvHeadline;
            tvHeadline.setText(article.getHeadline());
            LinearLayout llGroupItem=vh1.llGroupItem;
            llGroupItem.setBackgroundColor(context.getResources().getColor(article.getColor_id()));
            TextView tvType=vh1.tvType;
            tvType.setText(article.getNew_desk());
            tvType.setTextColor(context.getResources().getColor(article.getColor_id()));
            TextView tvTime=vh1.tvTime;
            tvTime.setText(TimeStamp.getDistanceTime(TimeStamp.getTime("yyyy-MM-dd",article.getDate()),TimeStamp.CHARACTER_TIME));
            vh1.setClickListener(new ItemClickListener() {
                @Override
                public void onClick(View view, int position, boolean isLongClick) {
                    Intent intent=new Intent(context,WebActivity.class);
                    intent.putExtra("url",article.getWebUrl());
                    context.startActivity(intent);
                }
            });
        }
    }
    private void configureViewHolderThumbnail(ViewHolderThumbnails vh1, int position) {
        final Article article = (Article) mArticles.get(position);
        if (article != null) {
            // Set item views based on the data model
            TextView tvHeadline = vh1.tvHeadline;
            tvHeadline.setText(article.getHeadline());
            LinearLayout llGroupItem=vh1.llGroupItem;
            llGroupItem.setBackgroundColor(context.getResources().getColor(article.getColor_id()));
            TextView tvType=vh1.tvType;
            tvType.setText(article.getNew_desk());
            tvType.setTextColor(context.getResources().getColor(article.getColor_id()));
            TextView tvTime=vh1.tvTime;
            tvTime.setText(TimeStamp.getDistanceTime(TimeStamp.getTime("yyyy-MM-dd",article.getDate()),TimeStamp.CHARACTER_TIME));
            if (article.getThumbNails().size()>0) {
                Photo photo=Photo.searchSubType(article.getThumbNails(), Photo.SUBTYPE_WIDE);
                if (photo!=null){
                    Picasso.with(context).load(photo.getUrl()).into(vh1.ivPhoto);
                    Picasso.with(context).load(photo.getUrl()).resize(photo.getWidth(),photo.getHeight());
                }
            }
            vh1.setClickListener(new ItemClickListener() {
                @Override
                public void onClick(View view, int position, boolean isLongClick) {
                    Intent intent=new Intent(context,WebActivity.class);
                    intent.putExtra("url",article.getWebUrl());
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mArticles.get(position).getThumbNails().size()==0) {
            return TEXT;
        } else if (mArticles.get(position).getThumbNails().size()>0) {
            return THUMBNAIL;
        }
        return TEXT;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case THUMBNAIL:
                View v1 = inflater.inflate(R.layout.item_search_arts_thumbnail, parent, false);
                viewHolder = new ViewHolderThumbnails(v1);
                break;
            case TEXT:
                View v2 = inflater.inflate(R.layout.item_search_arts_text, parent, false);
                viewHolder = new ViewHolderText(v2);
                break;
            default:
                View v = inflater.inflate(R.layout.item_search_arts_text, parent, false);
                viewHolder = new ViewHolderText(v);
                break;
        }
        return viewHolder;
    }

    @Override
    public int getItemCount() {
        return mArticles.size();
    }
    public interface ItemClickListener {
        void onClick(View view, int position, boolean isLongClick);
    }

    public static class ViewHolderThumbnails extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public TextView tvHeadline;
        public ImageView ivPhoto;
        public TextView tvType;
        public LinearLayout llGroupItem;
        public TextView tvTime;
        private ItemClickListener clickListener;
        public ViewHolderThumbnails(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            tvHeadline = (TextView) itemView.findViewById(R.id.tvHeadline);
            ivPhoto = (ImageView) itemView.findViewById(R.id.ivPhoto);
            tvType=(TextView)itemView.findViewById(R.id.tvType);
            llGroupItem=(LinearLayout)itemView.findViewById(R.id.llGroupItem);
            tvTime=(TextView)itemView.findViewById(R.id.tvTime);
            itemView.setOnClickListener(this);
        }

        public void setClickListener(ItemClickListener itemClickListener) {
            this.clickListener = itemClickListener;
        }
        @Override
        public void onClick(View view) {
            clickListener.onClick(view, getPosition(), false);
        }
        @Override
        public boolean onLongClick(View view) {
            clickListener.onClick(view, getPosition(), true);
            return true;
        }
    }

    public static class ViewHolderText extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public TextView tvHeadline;
        public TextView tvType;
        public LinearLayout llGroupItem;
        public TextView tvTime;
        private ItemClickListener clickListener;
        public ViewHolderText(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            tvHeadline = (TextView) itemView.findViewById(R.id.tvHeadline);
            tvType=(TextView)itemView.findViewById(R.id.tvType);
            llGroupItem=(LinearLayout)itemView.findViewById(R.id.llGroupItem);
            tvTime=(TextView)itemView.findViewById(R.id.tvTime);
        }

        public void setClickListener(ItemClickListener itemClickListener) {
            this.clickListener = itemClickListener;
        }
        @Override
        public void onClick(View view) {
            clickListener.onClick(view, getPosition(), false);
        }
        @Override
        public boolean onLongClick(View view) {
            clickListener.onClick(view, getPosition(), true);
            return true;
        }
    }
}
