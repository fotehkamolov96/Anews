/**
 * Contact
 * telegram: @kamolovme
 * email: kamolov-9696@mail.ru
 */
package com.kamolovproducts.anews.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.kamolovproducts.anews.ui.NewsDetailsActivity;
import com.kamolovproducts.anews.R;
import com.kamolovproducts.anews.data.NewsModel;

import java.util.List;

public class AdapterListNews extends RecyclerView.Adapter<AdapterListNews.NewsViewHolder> {
    private Context context;
    private List<NewsModel> items;

    public AdapterListNews(Context context, List<NewsModel> items) {
        this.items = items;
        this.context = context;
    }


    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_content, parent, false);
        return new NewsViewHolder(view);
    }


    @Override
    public void onBindViewHolder(NewsViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.titleTV.setText(items.get(position).getNewsTitle());
        holder.sourceTV.setText(items.get(position).getNewsDescription());

        Glide.with(holder.imageView.getContext())
                .load(items.get(position).getNewsImage())
                .placeholder(R.drawable.ic_cover_img)
                .error(R.drawable.ic_cover_img)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, com.bumptech.glide.request.target.Target<Drawable> target, boolean isFirstResource) {
                        holder.progressBar.setVisibility(View.VISIBLE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        holder.progressBar.setVisibility(View.GONE);
                        return false;
                    }

                })
                .into(holder.imageView);


        holder.itemView.setOnClickListener(v -> {
            NewsModel article = items.get(position);
            Intent intent = new Intent(context, NewsDetailsActivity.class);

            intent.putExtra("content", article.getContent());
            intent.putExtra("img_news", article.getNewsImage());
            intent.putExtra("author", article.getAuthor());
            intent.putExtra("description", article.getNewsDescription());
            intent.putExtra("publishedAt", article.getNewsPublishedDate());
            intent.putExtra("url", article.getNewsUrl());
            intent.putExtra("title", article.getNewsTitle());

            intent.putExtra("name", article.getSource().getSourceName());

            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder {

        private TextView titleTV, sourceTV;
        private ImageView imageView;
        private ProgressBar progressBar;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            progressBar = itemView.findViewById(R.id.progress_i);
            imageView = itemView.findViewById(R.id.newsImage);
            titleTV = itemView.findViewById(R.id.newsTitle);
            sourceTV = itemView.findViewById(R.id.newsSourceName);

        }

    }
}